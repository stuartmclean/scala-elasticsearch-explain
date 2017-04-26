package elasticsearchexplain

import org.apache.http.HttpEntity
import org.elasticsearch.client.{Response, RestClient}
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when
import java.util

import org.apache.http.entity.StringEntity

class SearcherSpec extends FlatSpec with MockitoSugar with Matchers {

  object Skip extends Tag("elasticsearchexplain.skip")

  val searchUrl = "foo_bar"

  def getRestClient(query: String, fixturePath: String) = {
    val restClient = mock[RestClient]
    val responseObject = mock[Response]
    val entity = mock[HttpEntity]
    val fixtureStream = getClass.getResourceAsStream(fixturePath)

    when(entity.getContent).thenReturn(fixtureStream)
    when(responseObject.getEntity).thenReturn(entity)
    when(
      restClient.performRequest("GET", searchUrl, new util.HashMap[String, String](), new StringEntity(query))
    ).thenReturn(responseObject)

    restClient
  }

  val resultParser = new ResultParser

  it should "get no count from elasticsearch when no document exists" taggedAs Skip in {
    val query =
      """
        |{
        |  "query": {
        |    "match": {
        |      "name": "foobar"
        |    }
        |  }
        |}
      """.stripMargin
    val searcher = new Searcher(getRestClient(query, "/fixtures/emptyResult.json"))
    val docs = searcher.getDocumentsForQuery(searchUrl, query)

    resultParser.getCount(docs) shouldBe 0
  }

  it should "gets count from elasticsearch when documents exist" taggedAs Skip in {
    val query =
      """
        |{
        |  "query": {
        |    "match": {
        |      "name": "pizza"
        |    }
        |  }
        |}
      """.stripMargin
    val searcher = new Searcher(getRestClient(query, "/fixtures/emptyResult.json"))
    val docs = searcher.getDocumentsForQuery(searchUrl, query)

    resultParser.getCount(docs) shouldBe 5
  }

  it should "get ids from elasticsearch when documents exist" taggedAs Skip in {
    val query =
      """
        |{
        |  "query": {
        |    "match": {
        |      "name": "pizza"
        |    }
        |  }
        |}
      """.stripMargin
    val searcher = new Searcher(getRestClient(query, "/fixtures/emptyResult.json"))
    val docs = searcher.getDocumentsForQuery(searchUrl, query)
    val result = resultParser.getIds(docs)

    result.map(_.toString) shouldBe List("803", "790", "801", "820", "768")
  }
}
