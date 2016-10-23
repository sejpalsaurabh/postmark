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

package com.sejpalsaurabh.postmark.client.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.Account;
import com.sejpalsaurabh.postmark.model.DateTimeTypeAdapter;
import com.sejpalsaurabh.postmark.model.Server;
import com.sejpalsaurabh.postmark.model.SkipMeExclusionStrategy;
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
 *
 * <p>This class will allow you the access <tt>Server API</tt>
 *
 * <p>
 * You can check <a href="http://developer.postmarkapp.com/developer-api-servers.html" target="_blank">Server API</a>
 * for more detail.
 *
 *
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkServerClient {

    private static Logger logger    =   Logger.getLogger(PostmarkServerClient.class);

    private static String accountToken;
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new SkipMeExclusionStrategy(Boolean.class));
        gsonBuilder.disableHtmlEscaping();

        if (ProjectUtil.isAccountTokenThere()) {
            accountToken = new ProjectUtil().getPostMarkProperties().getProperty("account-key").trim();
        } else {
            logger.error("PostmarkServerClient -> static -> account-key not found");
        }


    }

    public PostmarkServerClient() {
        //Default Constructor
    }

    public List<Server> getServers() throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Account account = new Account();
            try {
                // Create get request to Postmark Account API endpoint
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SERVERS + "?count=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&offset=" + ProjectConstants.OFFSET);

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkServerClient -> getServers response: " + response);
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
            return account.getServerList();
        } else {
            logger.error("account-key not found");
            return null;
        }
    }

    public Server getServer(long serverId) throws PostmarkException {
        Server server = new Server();
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                // Create get request to Postmark Account API endpoint
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SERVERS + ProjectConstants.SEPARATOR + serverId);
                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkServerClient ->  getServer response: " + response);
                    server = gsonBuilder.create().fromJson(response, Server.class);
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
            logger.error("account-key not found");
        }
        return server;
    }

    public Server createServer(Server server) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Server theResponse = new Server();
            try {

                // Create post request to Postmark API endpoint
                HttpPost method = new HttpPost(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SERVERS);

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);


                // Convert the message into JSON content
                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(server);
                logger.info("Create Server contents: " + messageContents);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkServerClient -> createServer response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, Server.class);

                    //TODO : Add Proper Error Handling for with API Error Codes
                    //TODO : Check : http://developer.postmarkapp.com/developer-api-overview.html#error-codes

                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while creating server : " + exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return theResponse;
        } else {
            logger.error("account-key not found");
            return null;
        }
    }

    public Server editServer(Server server) throws PostmarkException {
        //TODO : http://developer.postmarkapp.com/developer-api-servers.html#edit-server

        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Server theResponse = new Server();
            try {

                // Create post request to Postmark API endpoint
                HttpPut method = new HttpPut(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SERVERS + ProjectConstants.SEPARATOR + server.getServerId());

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                server.setServerId(0);
                // Convert the message into JSON content
                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(server);
                logger.info("Edit Server Contents : " + messageContents);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkServerClient -> Edit Server response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, Server.class);

                    //TODO : Add Proper Error Handling for with API Error Codes
                    //TODO : Check : http://developer.postmarkapp.com/developer-api-overview.html#error-codes

                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while editing server : " + exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }

            return theResponse;
        } else {
            logger.error("account-key not found");
            return null;
        }
    }

    public PostmarkResponse deleteServer(long serverId) throws PostmarkException {

        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse = new PostmarkResponse();

            try{
                HttpDelete method = new HttpDelete(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SERVERS + ProjectConstants.SEPARATOR + serverId);
                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkServerClient -> deleteServer response: " + response);
                    postmarkResponse = gsonBuilder.create().fromJson(response, PostmarkResponse.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while editing server : " + exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }
            return postmarkResponse;

        } else {
            logger.error("account-key not found");
            return null;
        }
    }

}
