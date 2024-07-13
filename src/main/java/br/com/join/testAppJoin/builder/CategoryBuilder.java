package br.com.join.testAppJoin.builder;

import br.com.join.testAppJoin.entity.Category;

public class CategoryBuilder {
    
    private final Category category = new Category();

    public CategoryBuilder withName(String name) {

        this.category.setName(name);
        return this;
    }

    public CategoryBuilder withDescription(String description) {

        this.category.setDescription(description);
        return this;
    }

    public Category build() {
        return this.category;
    }
}
