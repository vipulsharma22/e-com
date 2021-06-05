package com.nitsoft.ecommerce.client;

import com.nitsoft.ecommerce.api.response.model.APIResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Message91Client {

    @POST("/sendotp.php")
    Call<APIResponse> sendOTP(@Query("authkey") String authkey,@Query("mobile") String mobile,@Query("message") String message,@Query("sender") String sender,@Query("otp") String otp);

}
