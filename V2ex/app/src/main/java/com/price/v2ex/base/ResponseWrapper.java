package com.price.v2ex.base;

/**
 * Created by YC on 15-1-7.
 */
public class ResponseWrapper<T> {

    private int requestId;
    private T response;

    public int getRequestId() {
        return requestId;
    }

    public T getResponse() {
        return response;
    }

    public ResponseWrapper(int requestId, T response) {
        this.requestId = requestId;
        this.response = response;
    }

    public static <T> ResponseWrapper wrapResponse(int requestId, T response) {
        return new ResponseWrapper(requestId, response);
    }
}
