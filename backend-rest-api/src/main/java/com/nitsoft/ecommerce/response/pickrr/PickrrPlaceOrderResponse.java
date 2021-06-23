package com.nitsoft.ecommerce.response.pickrr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PickrrPlaceOrderResponse extends PickrrResponse{

    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_pk")
    private String orderPk;

    @JsonProperty("manifest_link")
    private String manifestLink;

    @JsonProperty("routing_code")
    private String routingCode;

    @JsonProperty("client_order_id")
    private String clientOrderId;

    @JsonProperty("courier")
    private String courier;

    @JsonProperty("dispatch_mode")
    private String dispatchMode;

    @JsonProperty("child_waybill_list")
    private List<String> childWaybillList;

}
