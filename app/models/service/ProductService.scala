package models.service

import com.google.inject.Inject
import models.dao.entities.{Product, ProductItem}
import models.ProductFilter
import models.dao.entities.Product.toDto
import models.dao.repositories.{ProductItemRepository, ProductRepository}
import models.dto.ProductDTO


trait ProductService {
  def list(filter: Option[ProductFilter]): Seq[ProductDTO]
  def create(p: ProductDTO): ProductDTO
  def update(p: ProductDTO): Option[ProductDTO]
  def delete(id: String): Option[String]

}
class ProductServiceImpl @Inject()(productRepository: ProductRepository) extends ProductService {
  override def list(filter: Option[ProductFilter]): Seq[ProductDTO] = {
    filter
      .map(filter => productRepository.getByFilter(filter.getFilter()))
      .getOrElse(productRepository.getAll)
  }

  override def create(p: ProductDTO): ProductDTO = {
    val newProduct = productRepository.add(Product(p), p.items.map(i => ProductItem(i)))
    toDto(newProduct._1, newProduct._2)
  }

  override def update(p: ProductDTO): Option[ProductDTO] = {
    p.id.flatMap(i => productRepository.getById(i)) match {
      case Some(pOld) =>
        val newProduct = productRepository.update(pOld, p.items.map(i => ProductItem(i)))
        Some(toDto(newProduct._1, newProduct._2))
      case _ => None
    }
  }

  override def delete(id: String): Option[String] = {
    productRepository.getById(id) match {
      case Some(p) =>
        productRepository.delete(p.id)
        Some(id)
      case _ => None
    }
  }
}