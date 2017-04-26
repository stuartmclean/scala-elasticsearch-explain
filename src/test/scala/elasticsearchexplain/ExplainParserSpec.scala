package elasticsearchexplain

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ExplainParserSpec extends FlatSpec with Matchers {
  it should "return all children" in {
    val fileContent = Source
      .fromInputStream(getClass.getResourceAsStream("/fixtures/testExplain.json")).getLines.mkString

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    result shouldBe "1: name: 100.0"
  }
}
