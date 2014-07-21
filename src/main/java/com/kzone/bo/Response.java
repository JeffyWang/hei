package com.kzone.bo;

import com.kzone.constants.ErrorCode;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = -4538451335221843800L;
	private String code;
	private String message;
    private Object data;

	public Response() {
		this(ErrorCode.SUCCESS_CODE, ErrorCode.SUCCESS_MSG);
	}

	public Response(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
        this.data = data;
	}

    public Response(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Response setResponse(String code, String message) {
        Response response = new Response(code, message);
        return response;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
