package elasticsearchexplain

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import play.api.libs.json._

object Explain extends App {

  if (args.length == 0) {
    println("no configuration given")
    sys.exit(-1)
  }

  val config = Json.parse(args(0))

  val restClient = RestClient.builder(
    new HttpHost((config \ "hostname").as[String], (config \ "port").as[Int], (config \ "scheme").as[String])).build()
  val searcher = new Searcher(restClient)

  val endpoint = (config \ "endpoint").as[String].replaceAll("^/|/$", "")

  val query = {
    val queryContents = Json.stringify((config \ "query").as[JsValue])
    s"""{"query": $queryContents}"""
  }

  val result = searcher.getDocumentsForQuery(endpoint + "/_search", query)

  val resultParser = new ResultParser

  if (resultParser.getCount(result) == 0) {
    println("no results found for query")
    sys.exit(1)
  }

  val explainParser = new ExplainParser

  val ids = resultParser.getIds(result)

  ids.map(
    id => {
      explainParser.parse(
        id,
        searcher.getDocumentsForQuery(
          endpoint + s"/$id/_explain",
          query)
      )
    }
  ).foreach(println)

  sys.exit(0)
}
