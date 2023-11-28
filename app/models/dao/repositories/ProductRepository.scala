package models.dao.repositories

import com.google.inject.Inject
import models.dao.entities.Product.toDto
import models.dao.entities.{Product, ProductItem}
import models.dao.schema.PhoneBookSchema
import models.dto.ProductDTO

import scala.collection.mutable

trait ProductRepository {
  def getById(id: String): Option[Product]
  def getAll: Seq[ProductDTO]
  def getByFilter(filter: Product => Boolean): Seq[ProductDTO]
  def add(p: Product,  items: Seq[ProductItem]): (Product, Seq[ProductItem])
  def update(p: Product,  items: Seq[ProductItem]): (Product, Seq[ProductItem])
  def delete(id: String): Unit

}
class ProductRepositoryImpl @Inject()(productItemRepository: ProductItemRepository) extends ProductRepository {

  import org.squeryl.PrimitiveTypeMode._

  override def getById(id: String): Option[Product] = transaction {
    from(PhoneBookSchema.product)(p =>
      where(p.id === id)
      select(p)
    ).headOption
  }

  override def getAll: Seq[ProductDTO] = transaction {
    from(PhoneBookSchema.product)(p => select(p))
      .toSeq.map(p => toDto(p, productItemRepository.listByProductId(p.id)))
  }

  override def getByFilter(filter: Product => Boolean): Seq[ProductDTO] = transaction {
    from(PhoneBookSchema.product)(p => where(filter(p) === true) select (p))
      .toSeq.map(p => toDto(p, productItemRepository.listByProductId(p.id)))
  }

  override def add(p: Product, items: Seq[ProductItem]): (Product, Seq[ProductItem]) = transaction {
    val newProduct = PhoneBookSchema.product.insert(p)
    val productItems = items.map(i => i.copy(productId = newProduct.id)).map(PhoneBookSchema.productItem.insert)
    (newProduct, productItems)
  }

  override def update(p: Product, items: Seq[ProductItem]): (Product, Seq[ProductItem]) = transaction {
    delete(p.id)
    add(p, items)
  }

  override def delete(id: String): Unit = transaction {
    PhoneBookSchema.product.delete(id)
    PhoneBookSchema.productItem.deleteWhere(_.productId === id)
  }
}