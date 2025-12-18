package day09

import common.Point2D
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldHaveSameHashCodeAs
import org.junit.jupiter.api.Test

class RectangleTest {

  @Test
  fun width() {
    Rectangle(Point2D(2, 5), Point2D(9, 7)).width shouldBe 8
  }

  @Test
  fun `width of vertical line rectangle`() {
    Rectangle(Point2D(3, 3), Point2D(3, 10)).width shouldBe 1
  }

  @Test
  fun height() {
    Rectangle(Point2D(2, 5), Point2D(9, 7)).height shouldBe 3
  }

  @Test
  fun `height of horizontal line rectangle`() {
    Rectangle(Point2D(7, 3), Point2D(2, 3)).height shouldBe 1L
  }

  @Test
  fun minX() {
    Rectangle(Point2D(0, 4), Point2D(5, 7)).minX shouldBe 0
    Rectangle(Point2D(5, 7), Point2D(0, 4)).minX shouldBe 0
  }

  @Test
  fun maxX() {
    Rectangle(Point2D(0, 4), Point2D(5, 7)).maxX shouldBe 5
    Rectangle(Point2D(5, 7), Point2D(0, 4)).maxX shouldBe 5
  }

  @Test
  fun minY() {
    Rectangle(Point2D(0, 4), Point2D(5, 7)).minY shouldBe 4
    Rectangle(Point2D(5, 7), Point2D(0, 4)).minY shouldBe 4
  }

  @Test
  fun maxY() {
    Rectangle(Point2D(0, 4), Point2D(5, 7)).maxY shouldBe 7
    Rectangle(Point2D(5, 7), Point2D(0, 4)).maxY shouldBe 7
  }

  @Test
  fun area() {
    Rectangle(Point2D(2, 5), Point2D(9, 7)).area shouldBe 24L
    Rectangle(Point2D(7, 1), Point2D(11, 7)).area shouldBe 35L
    Rectangle(Point2D(2, 5), Point2D(11, 1)).area shouldBe 50L
  }

  @Test
  fun `area of horizontal line rectangle`() {
    Rectangle(Point2D(7, 3), Point2D(2, 3)).area shouldBe 6L
  }

  @Test
  fun equals() {
    Rectangle(Point2D(7, 3), Point2D(2, 3)) shouldBeEqual Rectangle(Point2D(7, 3), Point2D(2, 3))
    Rectangle(Point2D(7, 3), Point2D(2, 3)) shouldNotBeEqual Rectangle(Point2D(3, 7), Point2D(2, 3))
  }

  @Test
  fun `rectangles with swapped p1 and p2 are equal`() {
    Rectangle(Point2D(7, 3), Point2D(2, 3)) shouldBeEqual Rectangle(Point2D(2, 3), Point2D(7, 3))
  }

  @Test
  fun `rectangles with swapped p1 and p2 have same hashCode`() {
    Rectangle(Point2D(7, 3), Point2D(2, 3)) shouldHaveSameHashCodeAs
      Rectangle(Point2D(2, 3), Point2D(7, 3))
  }
}
