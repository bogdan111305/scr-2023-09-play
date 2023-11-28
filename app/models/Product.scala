package models

import models.dto.ProductDTO

import scala.util.Random

case class Product(id: String, title: String, description: String, items: Seq[String])

object Product{
  def apply(dto: ProductDTO, items: Seq[String]): Product = new Product(
    id = dto.id.getOrElse(Random.nextString(20)),
    title = dto.title,
    description = dto.description,
    items = items
  )

  def toDto(p: Product, items: Seq[ProductItem]): ProductDTO = new ProductDTO(
    id = Some(p.id),
    title = p.title,
    description = p.description,
    items = items.map(i => ProductItem.toDto(i))
  )
}
