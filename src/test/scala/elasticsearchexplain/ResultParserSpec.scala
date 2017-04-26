package elasticsearchexplain

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ResultParserSpec extends FlatSpec with Matchers {

  trait ResultChecker extends ResultParser {
    def loadFixture(fixturePath: String): String =
      Source.fromInputStream(getClass.getResourceAsStream(fixturePath)).mkString
  }

  it should "get no count when no documents returned" in {
    new ResultChecker {
      getCount(loadFixture("/fixtures/emptyResult.json")) shouldBe 0
    }
  }

  it should "get count when documents exist" in {
    new ResultChecker {
      getCount(loadFixture("/fixtures/smallResult.json")) shouldBe 5
    }
  }

  it should "get ids when documents exist" in {
    new ResultChecker {
      getIds(loadFixture("/fixtures/smallResult.json")) shouldBe List("803", "790", "801", "820", "768")
    }
  }
}
