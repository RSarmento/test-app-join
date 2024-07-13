package br.com.join.testAppJoin.repository;

import br.com.join.testAppJoin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product, Long> {



}
