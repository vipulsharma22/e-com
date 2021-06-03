package com.nitsoft.ecommerce.api.request.model;

import com.nitsoft.util.Constant;
import lombok.Data;

import java.util.List;

@Data
public class ImageRequest {
    private Long productId;
    private List<Constant.IMAGE_TYPE> imageTypes;
}
