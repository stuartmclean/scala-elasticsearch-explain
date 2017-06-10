package elasticsearchexplain

import scala.io.Source
import java.util

import org.apache.http.entity.StringEntity
import org.elasticsearch.client.RestClient

class Searcher(restClient: RestClient) {

  def getDocumentsForQuery(endpoint: String, query: String): String = {
    Source.fromInputStream(
      restClient.performRequest(
        "GET",
        endpoint,
        new util.HashMap[String, String](),
        new StringEntity(query)
      ).getEntity.getContent
    ).mkString
  }
}
