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

package org.haiyanvn.restp.service;

import com.google.gson.Gson;
import org.haiyanvn.restp.utils.LDAPUtils;
import org.haiyanvn.restp.utils.SH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class LDAPServiceAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LDAPServiceAdapter.class);

    public String doLoginLDAP(String userName, String password) throws NamingException {
        logger.debug("Logging user: ", userName);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://172.16.10.251:3268/DC=gemvietnam,DC=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, new String("GEMVIETNAM" + "\\" + userName));
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put("java.naming.ldap.attributes.binary", "objectSid objectGUID");

        DirContext ctx = null;
        NamingEnumeration results = null;
        Map<String, String> displayResults = new HashMap<String, String>();
        try {
            ctx = new InitialDirContext(env);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search("", "(&(objectClass=user)(sAMAccountName=" + userName + "))", controls);
            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                NamingEnumeration<? extends Attribute> attributesAll = attributes.getAll();
                while (attributesAll.hasMore()) {
                    Attribute attribute = attributesAll.next();

                    if (attribute.getID().equals("objectSid")) {
                        String decodeSID = LDAPUtils.decodeSID((byte[]) attribute.get());
                        System.out.println(attribute.getID() + " = " + decodeSID);
                        displayResults.put(attribute.getID(), decodeSID);

                    } else if (attribute.getID().equals("objectGUID")) {
                        String guid = LDAPUtils.convertToDashedString((byte[]) attribute.get());
                        System.out.println(attribute.getID() + " = " + guid);
                        displayResults.put(attribute.getID(), guid);
                    } else {
                        System.out.println(attribute.getID() + " = " + attribute.get());
                        displayResults.put(attribute.getID(), attribute.get().toString());
                    }

                }
            }
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }

        return SH.get(Gson.class).toJson(displayResults);
    }
}
