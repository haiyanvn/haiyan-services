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

package org.haiyanvn.restp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {
// ------------------------------ FIELDS ------------------------------

    @JsonProperty
    private String statusCode;
    @JsonProperty
    private String statusDescription;
    @JsonProperty
    private String errorMessage;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Properties {
        public static final String STATUS_CODE = "statusCode";
        public static final String STATUS_DESCRIPTION = "statusDescription";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String ADDITION_INFO = "additionInfo";
        public static final String OBJECT = "object";
    }
}
