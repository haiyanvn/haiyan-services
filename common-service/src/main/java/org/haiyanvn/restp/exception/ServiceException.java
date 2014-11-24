/*
 * Copyright (c) 2014 HaiyanVN Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.haiyanvn.restp.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {
// ------------------------------ FIELDS ------------------------------

    private String message;
    private HttpStatus httpStatusCode;
    private Throwable cause;
    private StackTraceElement[] stackTraceElements;
    private String additionInfo;
    private Object object;

// --------------------------- CONSTRUCTORS ---------------------------

    public ServiceException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public ServiceException(String message, Object object) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, null, object);
    }

    public ServiceException(String message, HttpStatus errorCode) {
        this(message, errorCode, null);
    }

    public ServiceException(String message, Throwable cause) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public ServiceException(String message, HttpStatus errorCode, Throwable cause) {
        this.message = message;
        this.cause = cause;
        this.httpStatusCode = errorCode;
        if (cause != null) {
            this.stackTraceElements = cause.getStackTrace();
        }
    }

    public ServiceException(String message, HttpStatus errorCode, Throwable cause, Object object) {
        this.message = message;
        this.cause = cause;
        this.httpStatusCode = errorCode;
        if (object instanceof String) {
            this.additionInfo = object.toString();
        } else {
            this.object = object;
        }
        if (cause != null) {
            this.stackTraceElements = cause.getStackTrace();
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override
    public Throwable getCause() {
        return cause;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public String getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(String additionInfo) {
        this.additionInfo = additionInfo;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
