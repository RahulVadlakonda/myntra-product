package com.myntra.product.repository;

import com.myntra.product.entities.Image;
import com.myntra.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value = "select * from images where product_id=?1", nativeQuery = true)
    List<Image> findAllByProductId(long productId);
}
