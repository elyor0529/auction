package by.auction.controller;

import by.auction.entity.Category;
import by.auction.entity.Product;
import by.auction.service.CategoryService;
import by.auction.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAllProducts() {
        logger.info(messageSource.getMessage("controller.product.get", null, Locale.getDefault()));
        return ResponseEntity.ok(productService.findAll());
    }

    @RequestMapping(value = "/{productId:[\\d]+}", method = RequestMethod.GET)
    ResponseEntity getProductById(@PathVariable Long productId) {
        logger.info(messageSource.getMessage("controller.product.get.by.id", new Object[]{productId}, Locale.getDefault()));
        if ((productService.findById(productId)).isPresent()) {
            logger.info(messageSource.getMessage("controller.product.get.by.id.ok", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.ok(productService.findById(productId).get());
        } else {
            logger.info(messageSource.getMessage("controller.product.error.bet.not.found", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity saveProduct(@RequestBody Product product) {
        logger.info(messageSource.getMessage("controller.product.save.product", new Object[]{product}, Locale.getDefault()));
        if (!categoryService.findByName(product.getCategory_name()).isPresent()) {
            logger.info(messageSource.getMessage("controller.product.save.product.error", new Object[]{product}, Locale.getDefault()));
            return ResponseEntity.unprocessableEntity().build();
        }

        Product result = new Product();

        result.setName(product.getName());
        result.setCategory(new Category(product.getCategory_name()));
        result.setPrice(product.getPrice());
        result.setDescription(product.getDescription());

        result = productService.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{productId}")
                .buildAndExpand(result.getId()).toUri();

        logger.info(messageSource.getMessage("controller.product.save.product.ok", new Object[]{result}, Locale.getDefault()));
        return ResponseEntity.created(location).body(result);
    }

    @RequestMapping(value = "/{productId:[\\d]+}", method = RequestMethod.DELETE)
    ResponseEntity deleteProduct(@PathVariable Long productId) {
        logger.info(messageSource.getMessage("controller.product.delete.product", new Object[]{productId}, Locale.getDefault()));
        if (productService.findById(productId).isPresent()) {
            productService.deleteById(productId);
            logger.info(messageSource.getMessage("controller.product.delete.product.ok", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.ok().build();
        } else {
            logger.info(messageSource.getMessage("controller.product.error.bet.not.found", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.notFound().build();
        }
    }

}
