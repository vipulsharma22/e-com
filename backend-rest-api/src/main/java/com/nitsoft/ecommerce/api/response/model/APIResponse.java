package com.nitsoft.ecommerce.api.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class APIResponse implements Serializable {

    /**
     * status & message fields have not setter. They are assigned value when
     * initial by APIStatus parameter
     */
    private int status;
    private String message;
    private Object data;

    public APIResponse(APIStatus apiStatus, Object data) {

        if (apiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = apiStatus.getCode();
        this.message = apiStatus.getDescription();
        this.data = data;
    }

    public static APIResponse successResponse(Object data){
        return new APIResponse(APIStatus.OK,data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
