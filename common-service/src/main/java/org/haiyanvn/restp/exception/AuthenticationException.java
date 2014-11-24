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

public class AuthenticationException extends Exception {
// ------------------------------ FIELDS ------------------------------

    private String message;
    private HttpStatus httpStatusCode;
    private Throwable cause;
    private StackTraceElement[] stackTraceElements;

// --------------------------- CONSTRUCTORS ---------------------------

    public AuthenticationException(String message) {
        this(message, HttpStatus.FORBIDDEN, null);
    }

    public AuthenticationException(String message, HttpStatus errorCode) {
        this(message, errorCode, null);
    }

    public AuthenticationException(String message, Throwable cause) {
        this(message, HttpStatus.FORBIDDEN, cause);
    }

    public AuthenticationException(String message, HttpStatus errorCode, Throwable cause) {
        this.message = message;
        this.cause = cause;
        this.httpStatusCode = errorCode;
        if (cause != null) {
            this.stackTraceElements = cause != null ? cause.getStackTrace() : null;
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
}
