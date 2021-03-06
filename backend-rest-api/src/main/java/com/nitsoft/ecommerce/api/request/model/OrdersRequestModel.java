
package com.nitsoft.ecommerce.api.request.model;

import lombok.AllArgsConstructor;
import lombok.*;

/**
 *
 * @author Louis Duong
 */
@Data

public class OrdersRequestModel {
    private String searchKey;
    private int sortCase;
    private boolean ascSort;
    private int pageNumber;
    private int pageSize;
    private int status;
}
