package com.nitsoft.ecommerce.api.request.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryRequestModel {

    private Long parentId;

    @NotNull
    private String name;

    private Integer position;

    private String description;
}
