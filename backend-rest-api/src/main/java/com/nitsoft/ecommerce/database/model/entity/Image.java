package com.nitsoft.ecommerce.database.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image extends AbstractEntity {
    @Column(name="product_id")
    private Long productId;

    @Column(name="image_type")
    private String imageType;

    @Column(name="image")
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
