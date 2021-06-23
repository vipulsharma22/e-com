package com.nitsoft.ecommerce.response.pickrr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PickrrCancelOrderResponse extends PickrrResponse{

    @JsonProperty("cancelled_id_list")
    List<String> cancelledIdList;
}
