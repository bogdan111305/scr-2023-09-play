package models

import models.dto.ProductItemDTO

import scala.util.Random

case class ProductItem(id: String, price: Long, count: Long, inStock: Boolean)

object ProductItem{
  def apply(dto: ProductItemDTO): ProductItem = new ProductItem(
    id = dto.id.getOrElse(Random.nextString(20)),
    price = dto.price,
    count = dto.count,
    inStock = dto.inStock
  )

  def toDto(p: ProductItem): ProductItemDTO = new ProductItemDTO(
    id = Some(p.id),
    price = p.price,
    count = p.count,
    inStock = p.inStock
  )
}