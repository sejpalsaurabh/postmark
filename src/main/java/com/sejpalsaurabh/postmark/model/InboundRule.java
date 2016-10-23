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

package com.sejpalsaurabh.postmark.model;

import com.google.gson.annotations.SerializedName;
import com.sejpalsaurabh.postmark.exception.PostmarkException;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class InboundRule {

    @SerializedName("ID")
    private long ruleId;

    @SerializedName("Rule")
    private String rule;

    public InboundRule() {
    }

    public InboundRule(long ruleId, String rule) {
        this.ruleId = ruleId;
        this.rule = rule;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InboundRule that = (InboundRule) o;

        if (ruleId != that.ruleId) return false;
        return rule.equals(that.rule);

    }

    @Override
    public int hashCode() {
        int result = (int) (ruleId ^ (ruleId >>> 32));
        result = 31 * result + rule.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "InboundRule{" +
                "ruleId=" + ruleId +
                ", rule='" + rule + '\'' +
                '}';
    }

    public void validateRule(Method method) throws PostmarkException {
        if (method.equals(Method.CREATE)) {
            if ("".equals(this.getRule()) || null == this.getRule()) {
                throw new PostmarkException("Rule Name can't be blank");
            }
            //TODO : Domain and Email Validation Needed
        }
    }
}
