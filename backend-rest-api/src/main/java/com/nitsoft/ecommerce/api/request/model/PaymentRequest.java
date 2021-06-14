package com.nitsoft.ecommerce.api.request.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private String paymentOrderId;
    private double amount;
    private Long paymentId;
    private String razorpay_payment_id;
    private String razorpay_order_id;
    private String razorpay_signature;
}
