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

package org.haiyanvn.restp.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SH {
// ------------------------------ FIELDS ------------------------------

    private static final Logger logger = LoggerFactory.getLogger(SH.class);
    private static Map<Class, Object> singletonMap = new HashMap<Class, Object>();

    private SH() {
        // Default constructor is set as private, so no instance
        // could be make, guarantee the singleton instance
        // exist within the application.
    }

    public static <T> T get(Class<T> clazz) {
        T instance = getIfAvailable(clazz);
        if (instance == null) {
            try {
                instance = clazz.newInstance();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return instance;
    }

    public static <T> T getIfAvailable(Class<T> clazz) {
        return (T) singletonMap.get(clazz);
    }

    public static void add(Object singleton) {
        add(singleton, false);
    }

    public static void add(Object singleton, boolean forceUpdate) {
        if (!singletonMap.containsKey(singleton.getClass()) || forceUpdate) {
            singletonMap.put(singleton.getClass(), singleton);
        }
    }

    public static void add(Object singleton, Class type) {
        if (!singletonMap.containsKey(singleton.getClass())) {
            singletonMap.put(type, singleton);
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static void add(Object singleton, Class type, boolean forceUpdate) {
        if (!singletonMap.containsKey(singleton.getClass()) || forceUpdate) {
            singletonMap.put(type, singleton);
        }
    }

    public static void remove(Class clazz) {
        if (singletonMap.get(clazz) != null) {
            singletonMap.remove(clazz);
        }
    }
}
