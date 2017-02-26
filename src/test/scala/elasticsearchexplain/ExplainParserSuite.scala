package elasticsearchexplain

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import scala.io.Source

@RunWith(classOf[JUnitRunner])
class ExplainParserSuite extends FunSuite {
  test("childrenReturnsAllChildren") {
    val testFilePath = System.getProperty("user.dir") + "/src/test/scala/elasticsearchexplain/testdocs/testExplain.json"
    val fileContent = Source.fromFile(testFilePath).getLines.mkString("\n")

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    assert(result == "1: : 100.01: name: 100.0") // todo - this is still not correct output
  }
}
