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

package com.sejpalsaurabh.postmark.client.templates;

import com.google.gson.GsonBuilder;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.Account;
import com.sejpalsaurabh.postmark.model.DateTimeTypeAdapter;
import com.sejpalsaurabh.postmark.model.SkipMeExclusionStrategy;
import com.sejpalsaurabh.postmark.model.Template;
import com.sejpalsaurabh.postmark.util.ProjectConstants;
import com.sejpalsaurabh.postmark.util.ProjectUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkTemplatesClient {

    private static Logger logger    =   Logger.getLogger(PostmarkTemplatesClient.class);

    private static String serverToken;
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new SkipMeExclusionStrategy(Boolean.class));
        gsonBuilder.disableHtmlEscaping();

        if (ProjectUtil.isServerTokenThere()) {
            serverToken = new ProjectUtil().getPostMarkProperties().getProperty("server-key").trim();
        } else {
            logger.error("PostmarkTemplatesClient -> static -> server-key not found");
        }

    }

    public PostmarkTemplatesClient() {
        //Default Constructor
    }

    public List<Template> getTemplates() throws PostmarkException {
        Account account = new Account();
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.TEMPLATES + "?count=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&offset=" + ProjectConstants.OFFSET);

                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTemplatesClient -> getTemplates response: " + response);
                    account = gsonBuilder.create().fromJson(response, Account.class);
                }
                catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                    throw new PostmarkException(ioException);
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }
        } else {
            logger.error("server-key not found");
        }
        return account.getTemplateList();
    }

    public Template getTemplate(long templateId) throws PostmarkException {
        Template template = new Template();
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.TEMPLATES + ProjectConstants.SEPARATOR + templateId);

                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTemplatesClient -> getTemplate response: " + response);
                    template = gsonBuilder.create().fromJson(response, Template.class);
                }
                catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                    throw new PostmarkException(ioException);
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

        } else {
            logger.error("server-key not found");
        }
        return template;
    }

}
