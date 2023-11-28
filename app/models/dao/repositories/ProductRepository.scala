package models.dao.repositories

import models.Product
import scala.collection.mutable

trait ProductRepository {
  def add(p: Product): Product
  def delete(id: String): Unit
  def getById(id: String): Option[Product]
  def getAll: Seq[Product]
  def getByFilter(filter: Product => Boolean): Seq[Product]
}
class ProductRepositoryImpl extends ProductRepository {

  private val products = mutable.HashMap.empty[String, Product]

  override def add(p: Product): Product = products.put(p.id, p).get

  override def delete(id: String): Unit = products -= id

  override def getById(id: String): Option[Product] = products.get(id)

  override def getAll: Seq[Product] = products.values.toList

  override def getByFilter(filter: Product => Boolean): Seq[Product] = products.values.toList.filter(filter)
}