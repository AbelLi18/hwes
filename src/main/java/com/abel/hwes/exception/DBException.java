package com.abel.hwes.exception;

/**
 * Solve DBException.
 * @author Abel.li
 *
 */
public class DBException extends RuntimeException {

    private static final long serialVersionUID = 387954675604114633L;

    private String errorMsg;
    private int errorCode;

    public DBException(String message) {
        super(message);
    }

    public DBException(int errorCode, String message) {
        setErrorMsg(message);
        setErrorCode(errorCode);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
