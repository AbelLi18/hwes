package com.abel.hwes.exception;

public class ServiceException extends RuntimeException {

/**
 * Solve ParameterException(userName/password is error).
 * @author Abel.li
 *
 */
    private static final long serialVersionUID = -8063960611811622798L;

    private int code;
    private String message;

    public ServiceException(String message) {
        super();
        this.message = message;
    }
    public ServiceException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
