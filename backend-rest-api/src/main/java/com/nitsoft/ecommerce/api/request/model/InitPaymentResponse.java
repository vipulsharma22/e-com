package com.nitsoft.ecommerce.api.request.model;

import lombok.Builder;

@Builder
public class InitPaymentResponse {
    private final Long orderId;
    private final Long paymentId;
    private final String paymentPartnerOrderId;

    private InitPaymentResponse(Long orderId, Long paymentId, String paymentPartnerOrderId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paymentPartnerOrderId = paymentPartnerOrderId;
    }

    public static InitPaymentResponse of(Long orderId,Long paymentId,String paymentPartnerOrderId){
        return new InitPaymentResponse(orderId,paymentId,paymentPartnerOrderId);
    }

}
