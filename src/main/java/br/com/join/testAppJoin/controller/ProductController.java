package br.com.join.testAppJoin.controller;

import br.com.join.testAppJoin.entity.Product;
import br.com.join.testAppJoin.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> index(@Valid Pageable pageable) {

        return ResponseEntity.ok().body(this.productRepository.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {

        return this.productRepository
                .findById(id)
                .map(p -> ResponseEntity.ok().body(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Product product) throws ValidationException {

        Product newProduct = this.productRepository
                .saveAndFlush(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Product product)
            throws ValidationException {

        return this.productRepository
                .findById(id)
                .map(p -> {
                    p.setName(product.getName());
                    p.setDescription(product.getDescription());
                    p.setPrice(product.getPrice());
                    p.setCategory(product.getCategory());
                    Product updatedProduct = this.productRepository.saveAndFlush(p);
                    return ResponseEntity.ok().body(updatedProduct);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return this.productRepository
                .findById(id)
                .map(p -> {
                    this.productRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}



