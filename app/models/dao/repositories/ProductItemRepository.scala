package models.dao.repositories

import models.ProductItem
import scala.collection.mutable

trait ProductItemRepository {
  def listById(ids: Seq[String]): Seq[ProductItem]
  def save(p: Seq[ProductItem]): Seq[ProductItem]
  def delete(id: String): Unit
}
class ProductItemRepositoryImpl extends ProductItemRepository {

  private val products = mutable.HashMap.empty[String, ProductItem]

  override def listById(ids: Seq[String]): Seq[ProductItem] = {
    ids.flatten(id => products.get(id))
  }

  override def save(p: Seq[ProductItem]): Seq[ProductItem] = {
    p.flatten(i => products.put(i.id, i))
  }

  override def delete(id: String): Unit = products.-=(id)
}
