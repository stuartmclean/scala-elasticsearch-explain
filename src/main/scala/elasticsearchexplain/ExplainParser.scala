package elasticsearchexplain

import play.api.libs.json._

import scala.collection.mutable


class ExplainParser {
  private var weights = mutable.Map[String, Double]()
  private var descriptions = mutable.Set[String]()

  def parse(id: String, result: String): String = {
    weights = MapMaker.makeMap
    val streamContent = Json.parse(result)
    val mainNodeData = (streamContent \ "explanation").as[JsValue]

    findWeightsForNode(new ExplainNode(mainNodeData, null))

    descriptions.foldLeft("")((res, desc) => res + s"$id: $desc: " + weights(desc).toString)
  }

  private def findWeightsForNode(node: ExplainNode): Unit = {
    if (node.nodeType == "result") {
      descriptions += node.getDescriptionSummary
      weights += (node.getDescriptionSummary -> (node.absoluteImpactPercentage + weights(node.getDescriptionSummary)))
    }
    node.children.foreach(a => findWeightsForNode(a))
  }
}
