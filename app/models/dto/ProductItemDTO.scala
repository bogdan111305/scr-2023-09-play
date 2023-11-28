package models.dto

import play.api.libs.json.Json

case class ProductItemDTO(id: Option[String], price: Long, count: Long, inStock: Boolean)

object ProductItemDTO {
  implicit val productItemDTOFormat = Json.format[ProductItemDTO]
}