package models.service

import com.google.inject.Inject
import models.{Product, ProductFilter, ProductItem}
import models.Product.toDto
import models.dao.repositories.{ProductItemRepository, ProductRepository}
import models.dto.ProductDTO


trait ProductService {
  def list(filter: Option[ProductFilter]): Seq[ProductDTO]
  def create(p: ProductDTO): ProductDTO
  def update(p: ProductDTO): Option[ProductDTO]
  def delete(id: String): Option[String]

}
class ProductServiceImpl @Inject()(productRepository: ProductRepository,
                                   productItemRepository: ProductItemRepository) extends ProductService {
  override def list(filter: Option[ProductFilter]): Seq[ProductDTO] = {
    val products = filter
      .map(filter => productRepository.getByFilter(filter.getFilter()))
      .getOrElse(productRepository.getAll)
    products.map(p => (p, productItemRepository.listById(p.items))).map(pi => toDto(pi._1, pi._2))
  }

  override def create(p: ProductDTO): ProductDTO = {
    val items = productItemRepository.save(p.items.map(i => ProductItem(i)))
    toDto(productRepository.add(Product(p, items.map(_.id))), items)
  }

  override def update(p: ProductDTO): Option[ProductDTO] = {
    p.id.flatMap(i => productRepository.getById(i)) match {
      case Some(pOld) =>
        pOld.items.foreach(i => productItemRepository.delete(i))
        productRepository.delete(pOld.id)
        Some(create(p))
      case _ => None
    }
  }

  override def delete(id: String): Option[String] = {
    productRepository.getById(id) match {
      case Some(p) =>
        p.items.foreach(i => productItemRepository.delete(i))
        productRepository.delete(id)
        Some(id)
      case _ => None
    }
  }
}