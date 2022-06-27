package com.myntra.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "brand")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(generator = "brandGenerator")
    @GenericGenerator(name = "brandGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "brand_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")},
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private int brandId;


    @Length(min = 1, max = 60, message = "BrandRequest name should be between 1 and 60 characters")
    private String brandName;


    @JsonIgnore
    @OneToMany(mappedBy = "productBrand",
    cascade = CascadeType.PERSIST)
    private List<Product> products;

    public Brand(String brandName) {
        this.brandName = brandName;
    }
}
