package models.dao.schema

import models.dao.entities.{Address, PhoneRecord, ProductItem}
import org.squeryl.Schema


object PhoneBookSchema extends Schema {
  val phoneRecords = table[PhoneRecord]
  val addresses = table[Address]
  val product = table[models.dao.entities.Product]
  val productItem = table[ProductItem]
}
