package models.dao.entities

import models.dto.ProductItemDTO
import org.squeryl.KeyedEntity

import scala.util.Random

case class ProductItem(id: String, price: Long, count: Long, inStock: Boolean, productId: String) extends KeyedEntity[String]

object ProductItem{
  def apply(dto: ProductItemDTO): ProductItem = new ProductItem(
    id = dto.id.getOrElse(Random.nextString(20)),
    price = dto.price,
    count = dto.count,
    inStock = dto.inStock,
    productId = ""
  )

  def toDto(p: ProductItem): ProductItemDTO = new ProductItemDTO(
    id = Some(p.id),
    price = p.price,
    count = p.count,
    inStock = p.inStock
  )
}