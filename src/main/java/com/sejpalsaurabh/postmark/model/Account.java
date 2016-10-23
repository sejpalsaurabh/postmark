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

import java.util.List;

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class Account {

    @SerializedName("TotalCount")
    private long totalCount;

    @SerializedName("Servers")
    private List<Server> serverList;

    @SerializedName("SenderSignatures")
    private List<SenderSignature> senderSignatureList;

    @SerializedName("Templates")
    private List<Template> templateList;

    @SerializedName("InboundRules")
    private List<InboundRule> inboundRuleList;

    @SerializedName("Tags")
    private List<Tag> tagList;

    public Account() {
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public List<SenderSignature> getSenderSignatureList() {
        return senderSignatureList;
    }

    public void setSenderSignatureList(List<SenderSignature> senderSignatureList) {
        this.senderSignatureList = senderSignatureList;
    }

    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    public List<InboundRule> getInboundRuleList() {
        return inboundRuleList;
    }

    public void setInboundRuleList(List<InboundRule> inboundRuleList) {
        this.inboundRuleList = inboundRuleList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
