package elasticsearchexplain

import java.util.regex.Pattern

import play.api.libs.json._

class ExplainNode(nodeData: JsValue, parent: ExplainNode) {

  def this(nodeData: JsValue) = this(nodeData, null)

  val children: List[ExplainNode] = (nodeData \ "details").as[List[JsValue]].map(value => new ExplainNode(value, this))

  private val description = (nodeData \ "description").as[String]

  val nodeType: String = {
    def matchDescriptionToString(string: String): Boolean = string.r.findAllIn(description).nonEmpty

    if (matchDescriptionToString("max plus [0-9]*\\.?[0-9]* times others of:")) "max"
    else if (matchDescriptionToString("sum of:")) "sum"
    else if (matchDescriptionToString("result of:")) "result"
    else if (matchDescriptionToString("product of:")) "product"
    else "leaf"

  }

  val score: Double = (nodeData \ "value").as[Double]

  val getDescriptionSummary: String = {
    if (nodeType == "result") description.split("\\(")(1).split(":")(0)
    else ""
  }

  lazy val absoluteImpactPercentage: Double = {
    if (parent == null) 100.0
    else if (List("sum", "result").contains(nodeType)) getPercentageForSumParent
    else if (nodeType == "max") getPercentageForMaxParent
    else if (nodeType == "product") getPercentageForProductParent
    else 0.0
  }

  val tieBreaker: Double = {
    val matcher = Pattern.compile("/plus (?<tiebreaker>.*) times/").matcher(description)

    if (nodeType == "max" && matcher.find()) matcher.group("tiebreaker").toDouble
    else 0.0
  }

  private def getPercentageForSumParent: Double = {
    val scoreRelativeToParent = (100.0 / parent.score) * score
    (parent.absoluteImpactPercentage / 100.0) * scoreRelativeToParent
  }

  private def getPercentageForMaxParent: Double = {
    val isMaxNode = parent.children.reduceLeft((x, y) => if (x.score > y.score) x else y) == this

    if (parent.tieBreaker > 0) {
      val parentScorePart = (score / parent.score) * 100.0
      val parentScorePartPercentage = parent.absoluteImpactPercentage / 100.0

      if (isMaxNode) parentScorePart * parentScorePartPercentage
      else parentScorePart * parentScorePartPercentage * parent.tieBreaker
    } else if (isMaxNode) parent.absoluteImpactPercentage
    else 0.0
  }

  private def getPercentageForProductParent: Double = {
    val neighbours = parent.children

    if (neighbours != Nil && neighbours.length > 1) {
      val scoreSum = neighbours.foldLeft(0.0)((acc, node) => acc + node.score)
      val multiplier = 100.0 / scoreSum
      val parentMultiplier = parent.absoluteImpactPercentage / 100.0
      score * multiplier * parentMultiplier
    } else {
      parent.absoluteImpactPercentage
    }
  }
}
