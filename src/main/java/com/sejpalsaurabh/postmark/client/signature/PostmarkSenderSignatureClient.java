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

package com.sejpalsaurabh.postmark.client.signature;

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
 *
 * This class will allow you to access functionality of <tt>Sender signatures API</tt>
 *
 * <p>
 * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html" target="_blank">Sender signatures API</a>
 * for more detail.
 *
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkSenderSignatureClient {

    private static Logger logger    =   Logger.getLogger(PostmarkSenderSignatureClient.class);

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
            logger.error("PostmarkSenderSignatureClient -> static -> account-key not found");
        }

    }

    public PostmarkSenderSignatureClient() {
        //Default Constructor
    }

    /**
     *
     * This method will give you List of SenderSignature(s), We are providing count and offset internally,
     *  so you don't need to provide that.
     *
     *  <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#sender-list"
     * target="_blank">List sender signatures</a>
     * for more detail.
     *
     * @return - List of SenderSignature
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see SenderSignature
     *
     */
    public List<SenderSignature> getAllSignatures() throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Account account = new Account();
            try {
                // Create get request to Postmark Account API endpoint
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + "?count=" + ProjectConstants.MAXIMUM_RESULT_COUNT + "&offset=" + ProjectConstants.OFFSET);

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> getAllSignatures response: " + response);
                    account = gsonBuilder.create().fromJson(response, Account.class);
                }
                catch (IOException ioException) {
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
            return account.getSenderSignatureList();
        } else {
            logger.error("account-key not found");
            return null;
        }
    }

    /**
     *
     * This method will give you SenderSignature Object using <tt>signatureId</tt>.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#sender-signature"
     * target="_blank">Get a sender signature’s details</a>
     * for more detail.
     *
     *
     * @param signatureId - You need to specify signature Id for which you want to get signature.
     * @return - SenderSignature Object - which has response parsed from API
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see SenderSignature
     */
    public SenderSignature getSignature(long signatureId) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            SenderSignature theResponse = new SenderSignature();

            try {
                // Create get request to Postmark Account API endpoint
                HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + signatureId);

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> getSignature response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, SenderSignature.class);
                }
                catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                    throw new PostmarkException(ioException);
                }
            }
            catch (Exception exception) {
                logger.error("There has been an error while getting a SenderSignature from Id : " + signatureId);
                logger.error(exception.getMessage());
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

    /**
     *
     *  This method will allow you to create SenderSignature,
     *  you must need to specify <tt>fromEmail</tt> and <tt>Name</tt> into It.
     *
     *  <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#create-signature"
     * target="_blank">Create a signature</a>
     * for more detail.
     *
     * @param senderSignature You must need to specify fromEmail and Name in senderSignature Object.
     * @return - SenderSignature Object - which has response parsed from API
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see SenderSignature
     */
    public SenderSignature createSignature(SenderSignature senderSignature) throws PostmarkException {

        senderSignature.validateSenderSignature(Method.CREATE);

        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            SenderSignature theResponse = new SenderSignature();
            try {

                // Create post request to Postmark API endpoint
                HttpPost method = new HttpPost(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES);

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                // Convert the message into JSON content
                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(senderSignature);
                logger.info("Create SenderSignature content: " + messageContents);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> createSignature response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, SenderSignature.class);
                }
                catch (IOException ioException) {
                    logger.error(ioException.getMessage());
                    throw new PostmarkException(ioException);
                }
            }
            catch (Exception exception) {
                logger.error("There has been an error while Creating a SenderSignature : ");
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }
            return theResponse;
        }else {
            logger.error("account-key not found");
            return null;
        }
    }

    /**
     *
     * This method will allow you to edit Sender Signature using senderSignatureId
     * You must need to specify <tt>name</tt> and <tt>signatureId</tt> in SenderSignature Object.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#edit-signature"
     * target="_blank">Edit a signature</a>
     * for more detail.
     *
     *
     * @param senderSignature You must need to specify fromEmail in senderSignature Object.
     * @return - SenderSignature Object - which has response parsed from API
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see SenderSignature
     */
    public SenderSignature editSignature(SenderSignature senderSignature) throws PostmarkException {

        senderSignature.validateSenderSignature(Method.EDIT);

        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient   =   HttpClientBuilder.create().build();
            SenderSignature theResponse = new SenderSignature();

            try {
                HttpPut method = new HttpPut(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + senderSignature.getSignatureId());

                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                senderSignature.setSignatureId(0);
                // Convert the message into JSON content
                Gson gson = gsonBuilder.create();
                String messageContents = gson.toJson(senderSignature);
                logger.info("Edit Server Contents : " + messageContents);

                // Add JSON as payload to post request
                StringEntity payload = new StringEntity(messageContents, "UTF-8");
                method.setEntity(payload);


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> Edit SenderSignature response: " + response);
                    theResponse = gsonBuilder.create().fromJson(response, SenderSignature.class);
                }
                catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            }
            catch (Exception exception) {
                logger.error("There has been an error while editing SenderSignature : " + senderSignature.toString());
                logger.error(exception.getMessage());
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

    /**
     *
     * This method will allow you to delete Sender Signature using <tt>Signature Id</tt>
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#delete-signature"
     * target="_blank">Delete a signature</a>
     * for more detail.
     *
     * @param signatureId - You need to specify signature Id for which you want to delete signature.
     * @return PostmarkResponse Object - which holds API Response - Generally status/error code and message
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see PostmarkResponse
     */
    public PostmarkResponse deleteSignature(long signatureId) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse = new PostmarkResponse();

            try {
                HttpDelete method = new HttpDelete(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + signatureId);
                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> deleteSignature response: " + response);
                    postmarkResponse = gsonBuilder.create().fromJson(response, PostmarkResponse.class);
                }
                catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }
            }
            catch (Exception exception) {
                logger.error("There has been an error while deleting signature : " + exception.getMessage());
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

    /**
     *
     * This method will allow you to resend the confirmation email using <tt>signatureId</tt>.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#resend-confirmation"
     * target="_blank">Resend a confirmation</a>
     * for more detail.
     *
     * @param signatureId - You need to specify signature Id for which you want to resend confirmation email.
     * @return PostmarkResponse Object - which holds API Response - Generally status/error code and message
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see PostmarkResponse
     */
    public PostmarkResponse resendConfirmation(long signatureId) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse = new PostmarkResponse();

            try {
                // Create post request to Postmark API endpoint
                HttpPost method = new HttpPost(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + signatureId + ProjectConstants.SEPARATOR + "resend");

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> resendConfirmation response: " + response);
                    postmarkResponse = gsonBuilder.create().fromJson(response, PostmarkResponse.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while Resending Confirmation for signature using SenderSignature Id : " + signatureId);
                logger.error(exception.getMessage());
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

    /**
     *
     * This method Will query DNS for your domain and attempt
     * to verify the SPF record contains the information for Postmark's servers.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#verify-spf"
     * target="_blank">Verify an SPF record</a>
     * for more detail.
     *
     * @param signatureId - You need to specify signature Id for which you want to verify SPF Record.
     * @return - SenderSignature Object - which has response parsed from API
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see SenderSignature
     */
    public SenderSignature verifySPFRecord(long signatureId) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            SenderSignature senderSignature = new SenderSignature();
            try {
                // Create post request to Postmark API endpoint
                HttpPost method = new HttpPost(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + signatureId + ProjectConstants.SEPARATOR + "verifyspf");

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> verifySPFRecord response: " + response);
                    senderSignature = gsonBuilder.create().fromJson(response, SenderSignature.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while Verifying SPF Record using SenderSignature Id : " + signatureId);
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }
            return senderSignature;

        } else {
            logger.error("account-key not found");
            return null;
        }
    }

    /**
     *
     * This method Will create a New DKIM key.
     * Until the DNS entries are confirmed, the new values will be in the <tt>DKIMPendingHost</tt>
     * and <tt>DKIMPendingTextValue</tt> fields. After the new DKIM value is verified in DNS,
     * the pending values will migrate to <tt>DKIMTextValue</tt> and <tt>DKIMPendingTextValue</tt>
     * and Postmark will begin to sign emails with the new DKIM key.
     *
     * <p>
     * You can check <a href="http://developer.postmarkapp.com/developer-api-signatures.html#request-dkim"
     * target="_blank">Request a new DKIM</a>
     * for more detail.
     *
     * @param signatureId - You need to specify signature Id for which you want to verify SPF Record.
     * @return PostmarkResponse Object - which holds API Response - Generally status/error code and message
     * @throws PostmarkException will catch other exceptions and rethrow it in form of PostmarkException
     * @since 1.0
     * @see PostmarkResponse
     */
    public PostmarkResponse requestNewDKIM(long signatureId) throws PostmarkException {
        if (ProjectConstants.isAccountToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            PostmarkResponse postmarkResponse = new PostmarkResponse();
            try {
                // Create post request to Postmark API endpoint
                HttpPost method = new HttpPost(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.SIGNATURES + ProjectConstants.SEPARATOR + signatureId + ProjectConstants.SEPARATOR + "requestnewdkim");

                // Add standard headers required by Postmark
                method.addHeader("Accept", "application/json");
                method.addHeader("Content-Type", "application/json; charset=utf-8");
                method.addHeader(ProjectConstants.POSTMARK_ACCOUNT_TOKEN, accountToken);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                try {
                    String response = httpClient.execute(method, responseHandler);
                    logger.info("PostmarkSenderSignatureClient -> requestNewDKIM response: " + response);
                    postmarkResponse = gsonBuilder.create().fromJson(response, PostmarkResponse.class);
                } catch (HttpResponseException httpResponseException) {
                    logger.error(httpResponseException.getMessage());
                    throw new PostmarkException(httpResponseException);
                }

            } catch (Exception exception) {
                logger.error("There has been an error while requesting New DKIM using SenderSignature Id : " + signatureId);
                logger.error(exception.getMessage());
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
