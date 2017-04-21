 package com.abel.hwes.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Solve ParameterException(userName/password is empty).
 * @author Abel.li
 *
 */
public class ParameterException extends RuntimeException {

    private static final long serialVersionUID = 4901092369474213036L;

    // New Map<String, String> Object save parameter exception message
    Map<String, String> errorFields = new HashMap<String, String>();

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields) {
        this.errorFields = errorFields;
    }

    public synchronized void addErrorFields(String field, String message) {
        if (errorFields==null) {
            errorFields = new HashMap<String, String>();
        }
        errorFields.put(field, message);
    }

    public boolean isErrorFields() {
        return !errorFields.isEmpty();
    }

}
