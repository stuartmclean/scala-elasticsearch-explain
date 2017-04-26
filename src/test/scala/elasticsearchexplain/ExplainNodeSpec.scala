package elasticsearchexplain

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json._

import scala.io.Source

class ExplainNodeSpec extends FlatSpec with Matchers {
  val fileContent = Source.fromInputStream(getClass.getResourceAsStream("/fixtures/testExplainSimple1.json")).getLines.mkString
  val testData = Json.parse(fileContent)

  it should "return all children" in {
    val node = new ExplainNode((testData \ "explanation").as[JsValue], null)
    val children = node.children
    assert(children.length == 2)
  }
}
