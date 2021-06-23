package com.nitsoft.ecommerce.response.pickrr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PickrrTradeOrderResponse extends PickrrResponse{

    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("client_order_id")
    private String clientOrderId;

    @JsonProperty("order_type")
    private String orderType;

    private Info info;

    private Status status;

    @JsonProperty("track_arr")
    private Track trackArr;


    @Data
    private static class Info{
        @JsonProperty("cod_amount")
        private Double codAmount;

        @JsonProperty("from_city")
        private String fromCity;

        @JsonProperty("from_pincode")
        private String fromPincode;

        @JsonProperty("to_city")
        private String toCity;

        @JsonProperty("to_pincode")
        private String toPincode;
    }

    @Data
    private static class Status{
        @JsonProperty("current_status_type")
        private Double currentStatusType;

        @JsonProperty("current_status_body")
        private String currentStatusBody;

        @JsonProperty("current_status_time")
        private String currentStatusTime;

        @JsonProperty("current_status_location")
        private String currentStatusLocation;

        @JsonProperty("received_by")
        private String receivedBy;
    }

    @Data
    private static class Track{
        @JsonProperty("status_name")
        private String statusName;

        @JsonProperty("status_arr")
        private List<StatusArr> statusArr;

    }

    @Data
    private static class StatusArr{

        @JsonProperty("status_body")
        private String statusBody;

        @JsonProperty("status_location")
        private String statusLocation;

        @JsonProperty("status_time")
        private String statusTime;
    }
}
