/*
 *
 *     The MIT License (MIT)
 *
 *     Copyright (c) 2016 Saurabh Sejpal
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *     documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *     the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 *     and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all copies or substantial
 *     portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *     TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *     THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *     CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *     IN THE SOFTWARE.
 *
 */

package com.sejpalsaurabh.postmark.util;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * This class will load properties from <tt>postmark.properties</tt>
 * Also check for the Account and Server Token.
 *
 * @author Saurabh Sejpal
 * @since 1.0
 * @version 1.0
 */
public class ProjectUtil {

    private static final String POSTMARK_FILE   =   "postmark.properties";

    private static final Properties POSTMARK_PROPERTIES   =   loadPostMarkProperties();

    /**
     *  This method will load Property Object
     * @return Properties Object Filled from postmark.properties
     */
    private static Properties loadPostMarkProperties() {
        InputStream inputStream =   ProjectUtil.class.getClassLoader().getResourceAsStream(POSTMARK_FILE);
        if(null != inputStream) {
            Properties properties   =   new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return properties;
        } else {
            return null;
        }
    }

    /**
     * Getter method for Postmark Properties
     * @return POSTMARK_PROPERTIES
     */
    public Properties getPostMarkProperties(){
        return POSTMARK_PROPERTIES;
    }

    /**
     *  This method will be useful to check that User of this lib have added server-key to the Property file
     * @return TRUE if serverToken is there
     */
    public static boolean isServerTokenThere() {
        if (null != POSTMARK_PROPERTIES) {
            if (null != POSTMARK_PROPERTIES.getProperty("server-key")
                    && !"".equals(POSTMARK_PROPERTIES.getProperty("server-key")))
            {
                return true;
            }
        }

        return false;
    }

    /**
     *  This method will be useful to check that User of this lib have added account-key to the Property file
     * @return TRUE if accountToken is there
     */
    public static boolean isAccountTokenThere() {
        if (null != POSTMARK_PROPERTIES) {
            if (null != POSTMARK_PROPERTIES.getProperty("account-key")
                    && !"".equals(POSTMARK_PROPERTIES.getProperty("account-key")))
            {
                return true;
            }
        }

        return false;
    }
}
