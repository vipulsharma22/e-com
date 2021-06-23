package com.nitsoft.ecommerce.client;

import com.nitsoft.ecommerce.response.pickrr.PickrrCancelOrderResponse;
import com.nitsoft.ecommerce.response.pickrr.PickrrCheckServiceResponse;
import com.nitsoft.ecommerce.response.pickrr.PickrrPlaceOrderResponse;
import com.nitsoft.ecommerce.response.pickrr.PickrrTradeOrderResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PickrrClient {

    @POST("/api/place-order")
    Call<PickrrPlaceOrderResponse> placeOrder(@Query("auth_token") String authkey, @Query("item_name") String itemName,
                                              @Query("from_name") String fromName, @Query("from_email") String fromEmail,
                                              @Query("from_phone_number") String fromPhone, @Query("from_address") String fromAddress,
                                              @Query("from_pincode") String fromPincode, @Query("pickup_gstin") String pickupGstin,
                                              @Query("to_name") String toName, @Query("to_email") String toEmail,
                                              @Query("to_phone_number") String toPhone, @Query("to_address") String toAddress,
                                              @Query("quantity") String quantity, @Query("invoice_value") String invoiceValue);

    @GET("/api/check-pincode-service")
    Call<PickrrCheckServiceResponse> checkPincodeService(@Query("auth_token") String authkey, @Query("from_pincode") String fromPincode,
                                                         @Query("to_pincode") String toPincode);

    @GET("/api-v2/client/fetch-price-calculator-generic")
    Call<PickrrPlaceOrderResponse> calculatePrice(@Query("auth_token") String authkey, @Query("shipment_type") String shipmentType,
                                                  @Query("pickup_pincode") String pickupPincode, @Query("drop_pincode") String dropPincode,
                                                  @Query("delivery_mode") String deliveryMode, @Query("length") Double length,
                                                  @Query("breadth") Double breadth, @Query("height") Double height,
                                                  @Query("weight") Double weight, @Query("payment_mode") String paymentMode,
                                                  @Query("cod_amount") Double codAmount);

    @POST("/api/order-cancellation")
    Call<PickrrCancelOrderResponse> orderCancellation(@Query("auth_token") String authkey, @Query("tracking_id") String trackingId);


    @GET("/api/tracking-json")
    Call<PickrrTradeOrderResponse> trackJson(@Query("auth_token") String authkey, @Query("tracking_id") String trackingId);
}
