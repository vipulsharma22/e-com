package com.nitsoft.ecommerce.response.pickrr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PickrrFetchPriceResponse extends PickrrResponse{

    @Data
    public static class RateList{
        @JsonProperty("courier_id")
        private String courierId;

        @JsonProperty("delivered_charges")
        private Double deliveredCharges;

        @JsonProperty("courier")
        private String courier;

        @JsonProperty("returned_charges")
        private String returnedCharges;
    }

    @JsonProperty("rate_list")
    private List<RateList> rateLists;

    @JsonProperty("billing_zone")
    private String billingZone;

    @JsonProperty("chargeable_weight")
    private Double chargeableWeight;
}
