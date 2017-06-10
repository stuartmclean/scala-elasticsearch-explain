package elasticsearchexplain

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ExplainParserSpec extends FlatSpec with Matchers {
  it should "return reduced explanation from simple result" in {
    val fileContent = Source
      .fromInputStream(getClass.getResourceAsStream("/fixtures/testExplainSimple1.json")).getLines.mkString

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    result shouldBe "1: name: 100.0"
  }

  it should "return different reduced explanation from second simple result" in {
    val fileContent = Source
      .fromInputStream(getClass.getResourceAsStream("/fixtures/testExplainSimple2.json")).getLines.mkString

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    result shouldBe "1: message: 100.0"
  }

  it should "return reduced explanation from complex result" in {
    val fileContent = Source
      .fromInputStream(getClass.getResourceAsStream("/fixtures/testExplainComplex1.json")).getLines.mkString

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    result shouldBe "1: name.ngram: 9.3750007910499461: name.edgengram: 12.5000010547332631: name: 121.875010283649271: name.multilanguage: 56.25000474629965"
  }

  it should "return different reduced explanation from second complex result" in {
    val fileContent = Source
      .fromInputStream(getClass.getResourceAsStream("/fixtures/testExplainComplex2.json")).getLines.mkString

    val parser = new ExplainParser
    val result = parser.parse("1", fileContent)
    result shouldBe "1: name.ngram: 5.9579716700171571: name.edgengram: 7.9439622981730411: name: 66.388834849349421: name.multilanguage: 30.640997895347077"
  }
}
