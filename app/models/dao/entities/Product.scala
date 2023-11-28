package models.dao.entities

import models.dto.ProductDTO
import org.squeryl.KeyedEntity

import scala.util.Random

case class Product(id: String, title: String, description: String) extends KeyedEntity[String]

object Product{
  def apply(dto: ProductDTO): Product = new Product(
    id = dto.id.getOrElse(Random.nextString(20)),
    title = dto.title,
    description = dto.description
  )

  def toDto(p: Product, items: Seq[ProductItem]): ProductDTO = new ProductDTO(
    id = Some(p.id),
    title = p.title,
    description = p.description,
    items = items.map(i => ProductItem.toDto(i))
  )
}
