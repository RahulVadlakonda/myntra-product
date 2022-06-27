package com.myntra.product.repository;

import com.myntra.product.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findTopByCategoryName(String categoryName);
}
