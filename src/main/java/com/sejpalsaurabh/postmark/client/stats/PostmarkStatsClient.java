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

package com.sejpalsaurabh.postmark.client.stats;

import com.google.gson.GsonBuilder;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.DateTimeTypeAdapter;
import com.sejpalsaurabh.postmark.model.SkipMeExclusionStrategy;
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
import java.util.Date;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkStatsClient {

    private static Logger logger    =   Logger.getLogger(PostmarkStatsClient.class);

    private static String serverToken;
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new SkipMeExclusionStrategy(Boolean.class));
        gsonBuilder.disableHtmlEscaping();

        if (ProjectUtil.isAccountTokenThere()) {
            serverToken = new ProjectUtil().getPostMarkProperties().getProperty("server-key").trim();
        } else {
            logger.error("PostmarkStatsClient -> static -> server-key not found");
        }

    }

    public PostmarkStatsClient() {
        //Default Constructor
    }

    public String getOutBoundStats(String tag, Date fromDate, Date toDate) throws PostmarkException {
        StringBuilder outboundStatsBuilder = new StringBuilder();
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                String fromDateString =   ProjectConstants.POSTMARK_DATE_FORMATTER.format(fromDate);
                String toDateString =   ProjectConstants.POSTMARK_DATE_FORMATTER.format(toDate);
                boolean isValidFromDate = !"".equals(fromDateString);
                boolean isValidToDate = !"".equals(toDateString);
                boolean isValidTag = null != tag && !"".equals(tag);
                if (isValidFromDate && isValidToDate && isValidTag) {
                    // Create get request to Postmark Account API endpoint
                    HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.STATS
                            + "?fromdate=" + fromDateString + "&todate=" + toDateString + "&tag=" + tag);

                    // Add standard headers required by Postmark
                    method.addHeader("Accept", "application/json");
                    method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    try {
                        String response = httpClient.execute(method, responseHandler);
                        outboundStatsBuilder.append(response);
                        logger.info("PostmarkStatsClient -> getOutboundStats with tag response: " + response);
                    }
                    catch (IOException ioException) {
                        logger.error(ioException.getMessage());
                        throw new PostmarkException(ioException);
                    }
                } else {
                    logger.error("fromDate or toDate not found");
                    outboundStatsBuilder.append("fromDate or toDate not found");
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }

        } else {
            logger.error("server-key not found");
        }
        return outboundStatsBuilder.toString();
    }

    public String getOutBoundStats(Date fromDate, Date toDate) throws PostmarkException {
        StringBuilder outboundStatsBuilder = new StringBuilder();
        if (ProjectConstants.isServerToken) {
            HttpClient httpClient = HttpClientBuilder.create().build();

            try {
                String fromDateString =   ProjectConstants.POSTMARK_DATE_FORMATTER.format(fromDate);
                String toDateString =   ProjectConstants.POSTMARK_DATE_FORMATTER.format(toDate);
                if (!"".equals(fromDateString) && !"".equals(toDateString)) {
                    // Create get request to Postmark Account API endpoint
                    HttpGet method = new HttpGet(ProjectConstants.POSTMARK_ENDPOINT + ProjectConstants.SEPARATOR + ProjectConstants.STATS
                            + "?fromdate=" + fromDateString + "&todate=" + toDateString);

                    // Add standard headers required by Postmark
                    method.addHeader("Accept", "application/json");
                    method.addHeader(ProjectConstants.POSTMARK_SERVER_TOKEN, serverToken);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();

                    try {
                        String response = httpClient.execute(method, responseHandler);
                        outboundStatsBuilder.append(response);
                        logger.info("PostmarkStatsClient -> getOutboundStats response: " + response);
                    }
                    catch (IOException ioException) {
                        logger.error(ioException.getMessage());
                        throw new PostmarkException(ioException);
                    }
                } else {
                    logger.error("fromDate or toDate not found");
                    outboundStatsBuilder.append("fromDate or toDate not found");
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                throw new PostmarkException(exception);
            }

        } else {
            logger.error("server-key not found");
        }
        return outboundStatsBuilder.toString();
    }

}
