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

package com.sejpalsaurabh.postmark.client.triggers.tags;

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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

/**
 * <p>This class will allow you the access <tt>Tags Triggers API</tt>
 *
 * <p>
 * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html" target="_blank">Tag Triggers</a>
 * for more detail.
 *
 * @author Saurabh Sejpal
 * @see Tag
 * @since 1.0
 * @version 1.0
 */
public class PostmarkTagClient {

    private static Logger logger    =   Logger.getLogger(PostmarkTagClient.class);

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
            logger.error("PostmarkTagClient -> static -> server-key not found");
        }

    }

    public PostmarkTagClient() {
        //Default Constructor
    }

    /**
     *
     * This mehtod will allow you to create a Tag Trigger, you can specify two details for that
     * match name - String (Required) and Tracking Opens - Boolean
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#create-trigger" target="_blank">Create a Tag Trigger</a>
     * for more detail.
     *
     *
     * @param tag Tag Object with must have Match Name
     * @return Tag Object - which is Response from API - with specified match name
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see Tag
     * @since 1.0
     */
    public Tag createTagTrigger(Tag tag) throws PostmarkException {

        tag.validateTag(Method.CREATE);

        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Tag theResponse = new Tag();

            try {
                HttpPost method =   new HttpPost(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS);

                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(tag);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);

                //TODO : Validate Tag that must contain matchName and TrackOpens
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTagClient -> createTagTrigger response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, Tag.class);
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

            return theResponse;
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

    /**
     *
     * This method will allow to get a Trigger using Tag ID.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#get-single-trigger" target="_blank">Get a Tag Trigger</a>
     * for more detail.
     *
     * @param tagTriggerId Tag Trigger ID that you need to specify for getting that Tag
     * @return Tag Object - which is Response from API for specified Tag Trigger ID
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see Tag
     * @since 1.0
     */
    public Tag getTagTrigger(long tagTriggerId) throws PostmarkException {
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Tag tag = new Tag();

            try {
                HttpGet method  =   new HttpGet(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS
                        + ProjectConstants.SEPARATOR + tagTriggerId);
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTagClient -> getTagTrigger response: " + response);
                    tag = gsonBuilder.create().fromJson(response, Tag.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                } catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                }

            }
            catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return tag;
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

    /**
     * This method will allow to edit a Tag Trigger using Tag.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#edit-single-trigger" target="_blank">Edit a Tag Trigger</a>
     * for more detail.
     *
     * @param tag Tag Object with Must Included Match Name
     * @return Tag Object - which is Response from API after editing a Tag with Given Tag Id.
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see Tag
     * @since 1.0
     */
    public Tag editTagTrigger(Tag tag) throws PostmarkException {

        tag.validateTag(Method.EDIT);

        if (ProjectConstants.isServerToken) {
            HttpClient httpClient   =   HttpClientBuilder.create().build();
            Tag theResponse =   new Tag();
            try {
                HttpPut method = new HttpPut(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS
                        + ProjectConstants.SEPARATOR + tag.getTagTriggerId());

                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(tag);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTagClient -> editTagTrigger response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, Tag.class);
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
            return theResponse;
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

    /**
     *
     * This method will allow to delete a Tag Trigger using tagTriggerId.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#delete-single-trigger" target="_blank">Delete a Tag Trigger</a>
     * for more detail.
     *
     * @param tagTriggerId Tag Trigger ID that you need to specify for deleting that Tag
     * @return PostmarkResponse Object - will have API Code and Status Message
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see PostmarkResponse
     * @since 1.0
     */
    public PostmarkResponse deleteTagTrigger(long tagTriggerId) throws PostmarkException {
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient   =   HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse    =   new PostmarkResponse();

            try {
                HttpDelete method = new HttpDelete(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS
                        + ProjectConstants.SEPARATOR + tagTriggerId);

                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkTagClient -> deleteTagTrigger response: " + response);
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

    /**
     *
     * This method will allow you to search tag Triggers using match name provided.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#search-triggers" target="_blank">Search Tag Triggers</a>
     * for more detail.
     *
     * @param matchName String match name for searching the Tag
     * @return List of Tag Objects
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     */
    public List<Tag> searchTagTrigger(String matchName) throws PostmarkException {
        if (null != matchName && !"".equals(matchName)) {
            if (ProjectConstants.isServerToken) {
                HttpClient httpClient   =   HttpClientBuilder.create().build();
                Account account =   new Account();

                try {
                    HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT
                            + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS
                            + "?match_name=" + matchName + "&" + ProjectConstants.COUNT + "=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&" + ProjectConstants.OFFSET + "=" + ProjectConstants.OFFSET_COUNT);
                    method.addHeader("Accept", "application/json");
                    method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    account = generateSearchTagTriggerResponse(responseHandler, httpClient, method);
                }
                catch (Exception exception) {
                    logger.error(exception.getMessage());
                    throw new PostmarkException(exception);
                }
                finally {
                    httpClient.getConnectionManager().shutdown();
                }

                return account.getTagList();
            } else {
                logger.error("server-key not found");
                return null;
            }
        } else {
            logger.error("match-name can't be blank or null");
            return null;
        }
    }

    /**
     * This method will give you list of Tag Triggers for Given server.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-triggers-tags.html#search-triggers" target="_blank">Search Tag Triggers</a>
     * for more detail.
     *
     * @return List of Tags that you are getting for searching, List size will be the Maximum Result Count
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see Tag
     * @since 1.0
     */
    public List<Tag> searchTagTrigger() throws PostmarkException {
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient   =   HttpClientBuilder.create().build();
            Account account =   new Account();

            try {
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT
                        + ProjectConstants.TRIGGERS + ProjectConstants.SEPARATOR + ProjectConstants.TAGS
                        + "?" + ProjectConstants.COUNT + "=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&" + ProjectConstants.OFFSET + "=" + ProjectConstants.OFFSET_COUNT);
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                account = generateSearchTagTriggerResponse(responseHandler, httpClient, method);
            }
            catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return account.getTagList();
        } else {
            logger.error("server-key not found");
            return null;
        }
    }

    /**
     *
     * This method will internally used by {@link PostmarkTagClient#searchTagTrigger(String) searchTagTrigger(String)} and
     * {@link PostmarkTagClient#searchTagTrigger() searchTagTrigger()} for generating Model Data from Response String.
     *
     *
     * @param responseHandler List of String of ResponseHandler
     * @param httpClient HttpClient Object
     * @param method HTTPGet Method Object
     * @return Account Object - which will have the Count and Tag List
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @see Account
     * @since 1.0
     */
    private Account generateSearchTagTriggerResponse(ResponseHandler<String> responseHandler, HttpClient httpClient, HttpGet method) throws PostmarkException {
        try {
            String response = httpClient.execute(method, responseHandler);
            logger.info("PostmarkTagClient -> generateSearchTagTriggerResponse response: " + response);
            return gsonBuilder.create().fromJson(response, Account.class);
        } catch (HttpResponseException httpResponseException) {
            logger.error(httpResponseException.getMessage());
            throw new PostmarkException(httpResponseException);
        } catch (IOException ioException) {
            logger.error(ioException.getMessage());
            throw new PostmarkException(ioException);
        }
    }
}
