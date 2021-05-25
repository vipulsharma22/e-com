/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.auth.service.CustomUserAuthService;
import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.util.Constant;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Setting up some stuff using for all API
 *
 */
public abstract class AbstractBaseAPI {
    
    @Autowired
    private CustomUserAuthService userDetailsService;

    //
    // Build setting for Gson class accept NULL value
    //
    Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(Constant.API_FORMAT_DATE).create();

    // Mapper object is used to convert object and etc...
    public final static ObjectMapper mapper = new ObjectMapper();

    

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .setDateFormat(new SimpleDateFormat(Constant.API_FORMAT_DATE));
    }

    @Autowired
    AppConfig appConfig;

    protected String writeObjectToJson(Object obj) {
        try {

            return mapper.writeValueAsString(obj);

        } catch (JsonProcessingException ex) {
            // Throw our exception
            throw new ApplicationException(ex.getCause());
        }
    }
    public StatusResponse statusResponse = null;

}
