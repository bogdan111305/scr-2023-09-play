package controllers

import com.google.inject.Inject
import controllers.actions.authAction
import models.ProductFilter
import models.dto.ProductDTO
import models.service.{LoginService, ProductService}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

class ProductController @Inject()(loginService: LoginService,
                                  productService: ProductService) extends Controller{

  def list(filter: Option[ProductFilter]): Action[AnyContent] = Action {
    Ok(Json.toJson(productService.list(filter)))
  }

  def create(): Action[ProductDTO] = Action(parse.json[ProductDTO]) { implicit rc =>
    Ok(Json.toJson(productService.create(rc.body)))
  }

  def update(): Action[ProductDTO] = Action(parse.json[ProductDTO]) { implicit rc =>
    productService.update(rc.body) match {
      case Some(p) => Ok(Json.toJson(p))
      case _ => NotFound
    }
  }

  def delete(id: String): Action[AnyContent] = Action {
    productService.delete(id) match {
      case Some(id) => Ok(Json.toJson(id))
      case _ => NotFound
    }
  }
}
