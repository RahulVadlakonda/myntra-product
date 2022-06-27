package com.myntra.product.repository;

import com.myntra.product.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findTopByBrandName(String brandName);
}
