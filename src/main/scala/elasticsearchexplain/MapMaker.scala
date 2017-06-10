package elasticsearchexplain

import scala.collection.mutable

object MapMaker {
  def makeMap: mutable.Map[String, Double] = {
    new mutable.HashMap[String, Double] with
        mutable.SynchronizedMap[String, Double] {
      override def default(key: String) = 0
    }
  }
}
