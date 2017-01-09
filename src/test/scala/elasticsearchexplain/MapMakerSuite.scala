package elasticsearchexplain

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MapMakerSuite extends FunSuite {

  test("add elements to string") {
    val map = MapMaker.makeMap
    assert(map("foo") == 0.0)
  }

  test("concat elements with same key") {
    val map = MapMaker.makeMap
    map += ("foo" -> (1.1 + map("foo")))
    map += ("foo" -> (2.1 + map("foo")))
    assert(map("foo") == 3.2)
  }
}
