package models.dao.repositories

import models.dao.entities.ProductItem
import models.dao.schema.PhoneBookSchema

import scala.collection.mutable

trait ProductItemRepository {
  def listByProductId(ids: String): Seq[ProductItem]
  def save(p: Seq[ProductItem]): Seq[ProductItem]
  def deleteByProductId(id: String): Unit
}
class ProductItemRepositoryImpl extends ProductItemRepository {

  import org.squeryl.PrimitiveTypeMode._

  override def listByProductId(id: String): Seq[ProductItem] = transaction {
    from(PhoneBookSchema.productItem)(pi =>
      where(pi.productId === id)
        select pi
    ).toList
  }

  override def save(p: Seq[ProductItem]): Seq[ProductItem] = transaction {
    p.map(PhoneBookSchema.productItem.insert)
  }

  override def deleteByProductId(id: String): Unit = transaction {
    PhoneBookSchema.productItem.deleteWhere(_.productId === id)
  }
}
