package com.myntra.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "images")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(generator = "imageGenerator")
    @GenericGenerator(name = "imageGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "image_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")},
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private long imageId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private String imageUrl;
}
