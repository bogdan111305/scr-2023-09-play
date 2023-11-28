package controllers

import com.google.inject.Inject
import models.ProductFilter
import models.dto.ProductDTO
import models.dto.ProductDTO.productDTOFormat
import models.service.ProductService
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ProductController @Inject()(productService: ProductService) extends Controller{

  def list(filter: Option[ProductFilter]): Action[AnyContent] = Action {
    Ok(Json.toJson(productService.list(filter)))
  }

  def create(): Action[ProductDTO] = Action(parse.json[ProductDTO]) { implicit rc =>
    Ok(Json.toJson(productService.create(rc.body)))
  }

  def update(): Action[ProductDTO] = Action(parse.json[ProductDTO]) { implicit rc =>
    productService.update(rc.body)
      .map(p => Ok(Json.toJson(p)))
      .getOrElse(NotFound)
  }

  def delete(id: String): Action[AnyContent] = Action {
    productService.delete(id)
      .map(id => Ok(Json.toJson(id)))
      .getOrElse(NotFound)
  }
}
