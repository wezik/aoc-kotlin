import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

@AutoScan
object TimerListener : BeforeTestListener, AfterTestListener {

    var startTime = 0L

    override suspend fun beforeTest(testCase: TestCase) {
        startTime = System.currentTimeMillis()
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        println("${testCase.name.testName} took ${System.currentTimeMillis() - startTime} ms")
    }
}