package module

import models.dao.repositories.{PhoneRecordRepository, PhoneRecordRepositoryImpl, ProductItemRepository, ProductItemRepositoryImpl, ProductRepository, ProductRepositoryImpl}
import models.service.{LoginService, LoginServiceImpl, ProductService, ProductServiceImpl}

class ScrModule extends AppModule {
  override def configure(): Unit = {
    bindSingleton[LoginService, LoginServiceImpl]
    bindSingleton[PhoneRecordRepository, PhoneRecordRepositoryImpl]
    bindSingleton[ProductService, ProductServiceImpl]
    bindSingleton[ProductItemRepository, ProductItemRepositoryImpl]
    bindSingleton[ProductRepository, ProductRepositoryImpl]
  }
}
