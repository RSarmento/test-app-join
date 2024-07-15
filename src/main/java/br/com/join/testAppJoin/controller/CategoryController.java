package br.com.join.testAppJoin.controller;

import br.com.join.testAppJoin.entity.Category;
import br.com.join.testAppJoin.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController{

    @Autowired
    private CategoryRepository categoryRepository;

    
    @GetMapping
    @PreAuthorize("authenticated")
    public ResponseEntity<?> index(@Valid Pageable pageable) {

        return ResponseEntity.ok().body(this.categoryRepository.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {

        return this.categoryRepository
                .findById(id)
                .map(p -> ResponseEntity.ok().body(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Category category) {

        Category newCategory = this.categoryRepository
                .save(category);
        return ResponseEntity.ok().body(newCategory);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Category category) {

        return this.categoryRepository
                .findById(id)
                .map(p -> {
                    p.setName(category.getName());
                    p.setDescription(category.getDescription());
                    Category updatedCategory = this.categoryRepository.save(p);
                    return ResponseEntity.ok().body(updatedCategory);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return this.categoryRepository
                .findById(id)
                .map(p -> {
                    this.categoryRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
