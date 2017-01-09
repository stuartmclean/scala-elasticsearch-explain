package elasticsearchexplain

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ExplainParserSuite extends FunSuite {
  test("childrenReturnsAllChildren") {
    val parser = new ExplainParser
    val result = parser.parse("1", getClass.getResource("testExplain.json").toString)
    assert(result == "1: name: 0.0")
  }
}
