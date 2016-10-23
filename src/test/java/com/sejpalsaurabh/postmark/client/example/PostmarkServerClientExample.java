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

package com.sejpalsaurabh.postmark.client.example;



import com.sejpalsaurabh.postmark.client.server.PostmarkServerClient;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.Server;

import java.util.List;

/**
 *
 * This class will demonstrate functionality of Postmark Server API.
 *
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkServerClientExample {

    public static void main(String[] args) throws PostmarkException {
        PostmarkServerClient postmarkServerClient = new PostmarkServerClient();
        /* Getting All Server List */
        getAllServers(postmarkServerClient);

        /* Get a Server using Server Id */
        long serverId   =   1680960;
        getServer(postmarkServerClient, serverId);

    }

    public static  void getAllServers(PostmarkServerClient postmarkServerClient) throws PostmarkException {
        try {
            List<Server> serverList = postmarkServerClient.getServers();
            if (null != serverList) {
                for (Server server : serverList) {
                    System.out.println(server.toString());
                }
            }
        } catch (PostmarkException postmarkException) {
            postmarkException.printStackTrace();
        }
    }

    public static void getServer(PostmarkServerClient postmarkServerClient, long serverId) throws PostmarkException {
        try {
            Server server = postmarkServerClient.getServer(serverId);
            System.out.println(server.toString());
        } catch (PostmarkException postmarkException) {
            postmarkException.printStackTrace();
        }
    }

    public static void createServer(PostmarkServerClient postmarkServerClient, Server server) throws PostmarkException {
        System.out.println(postmarkServerClient.createServer(server).toString());

    }

    public static void editServer(PostmarkServerClient postmarkServerClient, Server server) throws PostmarkException {
        System.out.println(postmarkServerClient.editServer(server).toString());
    }

    public static void deleteServer(PostmarkServerClient postmarkServerClient, long serverId) throws PostmarkException {
        System.out.println(postmarkServerClient.deleteServer(serverId).toString());
    }

}
