package com.myntra.product.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "productGenerator")
    @GenericGenerator(name = "productGenerator",
    parameters = {
            @Parameter(name = "sequence_name", value = "product_sequence"),
            @Parameter(name = "initial_value", value = "10000"),
            @Parameter(name = "increment_value", value = "1")},
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private long productId;

    private String productName;



    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "product")
    private Price price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brand_id")
    private Brand productBrand;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;
}
