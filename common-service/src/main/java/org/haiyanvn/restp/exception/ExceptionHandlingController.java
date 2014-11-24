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

import org.haiyanvn.restp.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Central exception handling controller.
 * TODO: Support response type difference base on request
 */
@ControllerAdvice
public class ExceptionHandlingController {
// ------------------------------ FIELDS ------------------------------

    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

// -------------------------- OTHER METHODS --------------------------

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView authenticationError(AuthenticationException serviceException,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, serviceException.getHttpStatusCode().value());
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, serviceException.getHttpStatusCode().getReasonPhrase());
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, serviceException.getMessage());

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }

    @ExceptionHandler({AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView authorizationError(AuthorizationException serviceException,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, serviceException.getHttpStatusCode().value());
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, serviceException.getHttpStatusCode().getReasonPhrase());
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, serviceException.getMessage());

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ModelAndView handlingBindException(BindException ex) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, HttpStatus.BAD_REQUEST);
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, HttpStatus.BAD_REQUEST.getReasonPhrase());

        String errorMessage = "";
        for (ObjectError objectError : ex.getAllErrors()) {
            errorMessage += objectError.getDefaultMessage() + "\n";
        }
        if (errorMessage.length() > 0) {
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        }
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, errorMessage);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ModelAndView handlingValidateException(ValidateException ex) {
        logger.error("=====Error====", ex);
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, HttpStatus.BAD_REQUEST);
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, ex.getMessage());

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }

    @ExceptionHandler({ServiceException.class})
    public ModelAndView serviceError(ServiceException serviceException,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        if (null != serviceException.getHttpStatusCode()) {
            response.setStatus(serviceException.getHttpStatusCode().value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, serviceException.getHttpStatusCode().value());
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, serviceException.getHttpStatusCode().getReasonPhrase());
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, serviceException.getMessage());
        errorMap.put(ErrorDTO.Properties.ADDITION_INFO, serviceException.getAdditionInfo());
        errorMap.put(ErrorDTO.Properties.OBJECT, serviceException.getObject());
        logger.error("=====Error====", serviceException);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serviceError(Exception exception,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        logger.error("=====Error====", exception);
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put(ErrorDTO.Properties.STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMap.put(ErrorDTO.Properties.STATUS_DESCRIPTION, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorMap.put(ErrorDTO.Properties.ERROR_MESSAGE, exception.getMessage() != null ? exception.getMessage() : exception.toString());

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        return new ModelAndView(jsonView, errorMap);
    }
}
