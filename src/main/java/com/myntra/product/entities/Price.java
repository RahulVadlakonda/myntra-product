package com.myntra.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity(name = "price")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @Id
    @GeneratedValue(generator = "priceGenerator")
    @GenericGenerator(name = "priceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "price_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")},
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private long priceId;

    @PositiveOrZero(message = "mrp must be positive or zero")
    @Column(nullable = false)
    private double mrp;

    @PositiveOrZero(message = "price must be positive or zero")
    private double salePrice;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "product_id")
    @MapsId
    @JsonIgnore
    private Product product;
}
