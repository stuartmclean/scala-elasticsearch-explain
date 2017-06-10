package elasticsearchexplain

import java.io.InputStream

import play.api.libs.json._

class ResultParser {

  def getCount(result: String): Int = {
    (Json.parse(result) \ "hits" \ "total").as[Int]
  }

  def getIds(result: String): List[String] = {
    (Json.parse(result) \ "hits" \ "hits")
      .as[List[JsValue]]
      .map(result => (result \ "_id").get.toString.replaceAll("^\"|\"$", ""))
  }
}
