package app.wezik.aoc.infrastructure

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionSelector
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class ReflectionSolutionSelector : SolutionSelector {
    private val packageNameFormat = "app.wezik.aoc.solutions.y%s"

    // selects the solution for the given day and year with reflection, avoiding errors and mapping them to nulls
    override fun select(day: Int, year: Int): Solution? {
        val classes = findObjects(packageNameFormat.format(year), Solution::class.java)
        return classes.firstOrNull { it.day == day }
    }

    // NOTE: this implementation is a mashup of code, works but for sure could be more elegant
    // I might have gotten a bit to stubborn on the idea of using relfections for this x)
    private fun <T : Any> findObjects(
        packageName: String,
        base: Class<T>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): List<T> {
        // 1) find all .class names under the package
        val path     = packageName.replace('.', '/')
        val resources = classLoader.getResources(path).toList()
        val candidates = mutableListOf<String>()
        for (url in resources) {
            when (url.protocol) {
                "file" -> {
                    java.io.File(url.toURI()).walkTopDown()
                    .filter { it.extension == "class" }
                    .forEach {
                        val rel = it
                        .relativeTo(java.io.File(url.toURI()))
                        .path
                        .removeSuffix(".class")
                        .replace(java.io.File.separatorChar, '.')
                        candidates += "$packageName.$rel"
                    }
                }
                "jar" -> {
                    val conn = (url.openConnection() as java.net.JarURLConnection)
                    conn.jarFile.entries().asSequence()
                    .mapNotNull { entry ->
                        val name = entry.name
                        if (name.startsWith(path) && name.endsWith(".class")) {
                            name.removeSuffix(".class").replace('/', '.')
                        } else null
                    }
                    .forEach { candidates += it }
                }
            }
        }

        // 2) load, filter, grab objectInstance
        return candidates.mapNotNull { className ->
            try {
                @Suppress("UNCHECKED_CAST")
                val rawCls = classLoader.loadClass(className) as Class<out T>
                val kcls: KClass<out T> = rawCls.kotlin
                // is it a direct/indirect subclass *and* a Kotlin object?
                if (kcls.isSubclassOf(base.kotlin) && kcls.objectInstance != null) {
                    kcls.objectInstance!!
                } else null
            } catch (e: Throwable) {
                null  // ignore unloadable classes
            }
        }
    }
}
