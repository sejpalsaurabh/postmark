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

package com.sejpalsaurabh.postmark.client.triggers.rule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.*;
import com.sejpalsaurabh.postmark.response.PostmarkResponse;
import com.sejpalsaurabh.postmark.util.ProjectConstants;
import com.sejpalsaurabh.postmark.util.ProjectUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

/**
 *
 * <p>This class will allow you the access <tt>Inbound Rule Trigger API</tt>
 *
 * <p>
 * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-rules.html" target="_blank">Inbound Rule Triggers</a>
 * for more detail.
 *
 * @author Saurabh Sejpal
 * @since 1.0
 * @version 1.0
 * @see InboundRule
 *
 *
 */
public class PostmarkRuleClient {

    private static Logger logger    =   Logger.getLogger(PostmarkRuleClient.class);

    private static String serverToken;

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new SkipMeExclusionStrategy(Boolean.class));
        gsonBuilder.disableHtmlEscaping();

        if (ProjectConstants.isServerToken) {
            serverToken = new ProjectUtil().getPostMarkProperties().getProperty("server-key").trim();
        } else {
            logger.error("PostmarkRuleClient -> static -> server-key not found");
        }

    }

    public PostmarkRuleClient() {
        //Default Constructor
    }

    /**
     *  This method will give you List of InboundRule(s), We are providing count and offset internally,
     *  so you don't need to provide that.
     *
     *  <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-rules.html#list-triggers"
     * target="_blank">List Rule Triggers</a>
     * for more detail.
     *
     * @return List of InboundRule
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see InboundRule
     * @since 1.0
     */
    public List<InboundRule> getInboundRules() throws PostmarkException {

        if (ProjectConstants.isServerToken) {
            HttpClient httpClient   =   HttpClientBuilder.create().build();
            Account account =   new Account();

            try {
                HttpGet method  =   new HttpGet(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.INBOUND_RULES
                + "?" + ProjectConstants.COUNT + "=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&" + ProjectConstants.OFFSET + "=" + ProjectConstants.OFFSET_COUNT);
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkRuleClient -> getInboundRules response: " + response);
                    account = gsonBuilder.create().fromJson(response, Account.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                } catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                    throw new PostmarkException(ioException);
                }

            }
            catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return account.getInboundRuleList();
        } else {
            logger.error("server-key not found");
            return null;
        }

    }

    /**
     *
     * This method will create Rule using Rule String you have specified.
     *
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-rules.html#create-trigger"
     * target="_blank">Create Rule Triggers</a>
     * for more detail.
     *
     * @param   rule Must need to specify Rule String into InboundRule Object.
     * @return InboundRule Object - which has response parsed from API
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see InboundRule
     * @since 1.0
     */
    public InboundRule createInboundRule(InboundRule rule) throws PostmarkException {

        rule.validateRule(Method.CREATE);

        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            InboundRule inboundRule = new InboundRule();

            try {
                HttpPost method =   new HttpPost(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.INBOUND_RULES);

                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(rule);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkRuleClient -> createInboundRule response: " + response);
                    inboundRule = gsonBuilder.create().fromJson(response, InboundRule.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }
            }
            catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return inboundRule;
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

    /**
     *
     * This method will delete Inbound Rule Trigger using Rule Id
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-rules.html#create-trigger"
     * target="_blank">Create Rule Triggers</a>
     * for more detail.
     *
     * @param ruleId Id of Rule you want to delete
     * @return PostmarkResponse Object - which holds API Response - Generally status/error code and message
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see PostmarkResponse
     * @since 1.0
     */
    public PostmarkResponse deleteRule(long ruleId) throws PostmarkException {
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse   =    new PostmarkResponse();

            try {
                HttpDelete method =   new HttpDelete(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.INBOUND_RULES + ProjectConstants.SEPARATOR + ruleId);
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkRuleClient -> deleteRule response: " + response);
                    postmarkResponse = gsonBuilder.create().fromJson(response, PostmarkResponse.class);
                }
                catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }
            }
            catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return postmarkResponse;
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

}
