package com.kzone.util;

import com.kzone.bo.Response;

/**
 * Created by Jeffy on 14-4-24.
 */
public class ResponseUtil {
    public static Response returnResponse(String code, String message) {
        Response response = new Response(code, message);
        return response;
    }

    public static Response returnResponse(Response response, Object data) {
        response.setData(data);
        return response;
    }
}
