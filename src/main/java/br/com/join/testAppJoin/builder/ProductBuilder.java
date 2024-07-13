package br.com.join.testAppJoin.builder;

import br.com.join.testAppJoin.entity.Category;
import br.com.join.testAppJoin.entity.Product;

public class ProductBuilder {

    private final Product product = new Product();

    public ProductBuilder withName(String name) {

        this.product.setName(name);
        return this;
    }

    public ProductBuilder withDescription(String description) {

        this.product.setDescription(description);
        return this;
    }

    public ProductBuilder withPrice(Double price) {

        this.product.setPrice(price);
        return this;
    }

    public ProductBuilder withCategory(Category category) {

        this.product.setCategory(category);
        return this;
    }

    public Product build() {
        return this.product;
    }
}
