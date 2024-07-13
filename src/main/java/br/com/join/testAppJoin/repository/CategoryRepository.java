package br.com.join.testAppJoin.repository;

import br.com.join.testAppJoin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {

}
