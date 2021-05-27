package com.nitsoft.ecommerce.client;

import com.nitsoft.ecommerce.api.response.model.APIResponse;
import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.Map;

public interface Message91Client {

    @POST("/sendOtp")//todo: set api end point
    Call<APIResponse> validateUserToken(@HeaderMap Map<String, String> headers);

}
