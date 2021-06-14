package com.nitsoft.ecommerce.database.model;

import lombok.Data;

import javax.persistence.*;


@Data
public class Image {
    private Long id;
    private Long productId;
    private String imageType;
    private byte[] image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
