package com.myntra.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(generator = "categoryGenerator")
    @GenericGenerator(name = "categoryGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "category_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")},
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private int categoryId;

    @Length(min = 1, max = 60, message = "BrandRequest name should be between 1 and 60 characters")
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category",
            cascade = CascadeType.PERSIST)
    private List<Product> products;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
