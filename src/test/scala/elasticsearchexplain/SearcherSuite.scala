package elasticsearchexplain

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SearcherSuite extends FunSuite {

  val searcher = new Searcher(RestClient.builder(
    new HttpHost("172.28.128.101", 9200, "http")).build())

  val resultParser = new ResultParser

  val searchUrl = "foodpanda_rw_development_en/vendor/_search"

  test("gets no count from elasticsearch when no document exists") {
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
    val docs = searcher.getDocumentsForQuery(searchUrl, query)
    assert(resultParser.getCount(docs) == 0)
  }

  test("gets count from elasticsearch when documents exist") {
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
    val docs = searcher.getDocumentsForQuery(searchUrl, query)
    assert(resultParser.getCount(docs) == 6)
  }

  test("gets ids from elasticsearch when documents exist") {
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
    val docs = searcher.getDocumentsForQuery(searchUrl, query)
    val result = resultParser.getIds(docs)
    println(result)
    assert(result.map(_.toString) == List("1000000000500000018", "1000000000000000018", "803", "801", "768", "820"))
  }
}
