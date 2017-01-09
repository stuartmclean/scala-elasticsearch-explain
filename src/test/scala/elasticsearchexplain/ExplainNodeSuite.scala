package elasticsearchexplain

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import play.api.libs.json._

@RunWith(classOf[JUnitRunner])
class ExplainNodeSuite extends FunSuite {
  val testData = Json.parse(getClass.getResourceAsStream("testExplain.json"))

  test("children returns all children") {
    val node = new ExplainNode((testData \ "explanation").as[JsValue], null)
    val children = node.children
    assert(children.length == 2)
  }
}
