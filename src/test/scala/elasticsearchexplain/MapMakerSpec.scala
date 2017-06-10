package elasticsearchexplain

import org.scalatest.{FlatSpec, Matchers}

class MapMakerSpec extends FlatSpec with Matchers {

  it should "add elements to string" in {
    val map = MapMaker.makeMap
    map("foo") shouldBe 0.0
  }

  it should "concat elements with same key" in {
    val map = MapMaker.makeMap
    map += ("foo" -> (1.1 + map("foo")))
    map += ("foo" -> (2.1 + map("foo")))
    map("foo") shouldBe 3.2
  }
}
