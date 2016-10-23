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


import com.sejpalsaurabh.postmark.client.triggers.rule.PostmarkRuleClient;
import com.sejpalsaurabh.postmark.exception.PostmarkException;
import com.sejpalsaurabh.postmark.model.InboundRule;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostmarkRuleClientTest {

    static PostmarkRuleClient postmarkRuleClient;

    static long ruleId;

    @BeforeClass
    public static void setUp() {
        postmarkRuleClient = new PostmarkRuleClient();
    }

    @Test
    public void createInboundRule() throws PostmarkException {
        InboundRule inboundRule = new InboundRule();
        inboundRule.setRule("someone@example.coms");
        inboundRule = postmarkRuleClient.createInboundRule(inboundRule);
        ruleId  =   inboundRule.getRuleId();
        System.out.println("======================= createInboundRule ======================");
        System.out.println(ruleId);
        System.out.println(inboundRule.toString());
        System.out.println("================================================================");
        getInboundRules();
    }

    @Test
    public void deleteInboundRule() throws PostmarkException {
        System.out.println("======================== deleteInboundRule ======================");
        if (0 != ruleId) {
            System.out.println(postmarkRuleClient.deleteRule(ruleId).toString());
        } else {
            assert false;
        }
        System.out.println("================================================================");
        getInboundRules();
    }

    public void getInboundRules() throws PostmarkException {
        List<InboundRule> inboundRules  =   postmarkRuleClient.getInboundRules();
        System.out.println("======================== getInboundRules ======================");
        for (InboundRule inboundRule : inboundRules) {
            System.out.println(inboundRule.toString());
        }
        System.out.println("================================================================");
    }



}
