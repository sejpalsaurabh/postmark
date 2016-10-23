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

package com.sejpalsaurabh.postmark.client.junit;

import com.sejpalsaurabh.postmark.client.bounces.PostmarkBounceClient;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkTemplateClientTest {

    static PostmarkBounceClient postmarkBounceClient;

    @BeforeClass
    public static void setUp() {
        postmarkBounceClient    =   new PostmarkBounceClient();
    }

    @Test
    public void getDelivaryStatsTest() throws PostmarkException {
        System.out.println(postmarkBounceClient.getDelivaryStats().toString());
    }

}
