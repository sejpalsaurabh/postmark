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

import com.sejpalsaurabh.postmark.client.triggers.rule.PostmarkRuleClient;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.InboundRule;

import java.util.List;

/**
 *
 * This class will demonstrate functionality of Postmark Inbound Rule API.
 *
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class PostmarkRuleClientExample {

    public static void main(String[] args) throws PostmarkException {
        PostmarkRuleClient postmarkRuleClient = new PostmarkRuleClient();

        long ruleId = createInboundRule(postmarkRuleClient);

        getInboundRules(postmarkRuleClient);

        deleteRule(postmarkRuleClient, ruleId);

        getInboundRules(postmarkRuleClient);

    }

    public static void getInboundRules(PostmarkRuleClient postmarkRuleClient) throws PostmarkException {
        List<InboundRule> inboundRules  =   postmarkRuleClient.getInboundRules();
        for (InboundRule inboundRule : inboundRules) {
            System.out.println(inboundRule.toString());
        }

    }

    public static long createInboundRule(PostmarkRuleClient postmarkRuleClient) throws PostmarkException {
        InboundRule inboundRule = new InboundRule();
        inboundRule.setRule("someone@example.coms");
        inboundRule = postmarkRuleClient.createInboundRule(inboundRule);
        System.out.println(inboundRule.toString());
        return inboundRule.getRuleId();
    }

    public static void deleteRule(PostmarkRuleClient postmarkRuleClient, long ruleId) throws PostmarkException {
        System.out.println(postmarkRuleClient.deleteRule(ruleId).toString());
    }

}
