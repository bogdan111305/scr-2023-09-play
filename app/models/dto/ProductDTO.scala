package models.dto

import play.api.libs.json.Json

case class ProductDTO(id: Option[String], title: String, description: String, items: Seq[ProductItemDTO])

object ProductDTO {
  implicit val productDTOFormat = Json.format[ProductDTO]
}
