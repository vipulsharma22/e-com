package com.nitsoft.ecommerce.response.pickrr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PickrrCheckServiceResponse extends PickrrResponse{
    @JsonProperty("cod_limit")
    private Double codLimit;

    @JsonProperty("has_cod")
    private Boolean hasCOD;

    @JsonProperty("has_pickup")
    private Boolean hasPickup;

    @JsonProperty("has_prepaid")
    private Boolean hasPrepaid;

    @JsonProperty("to_city")
    private String toCity;

    @JsonProperty("to_state")
    private String toState;

    @JsonProperty("edd_stamp")
    private String eddStamp;
}
