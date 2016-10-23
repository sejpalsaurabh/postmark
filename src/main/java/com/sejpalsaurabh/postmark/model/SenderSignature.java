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
public class SenderSignature {

    @SerializedName("ID")
    private long signatureId;

    @SerializedName("Domain")
    private String domain;

    @SerializedName("EmailAddress")
    private String email;

    @SerializedName("ReplyToEmailAddress")
    private String replyToEmail;

    @SerializedName("Name")
    private String name;

    @SerializedName("Confirmed")
    private boolean isSignatureConfirmed;

    @SerializedName("SPFVerified")
    private boolean isSPFVerified;

    @SerializedName("SPFHost")
    private String SPFHost;

    @SerializedName("SPFTextValue")
    private String SPFTextValue;

    @SerializedName("DKIMVerified")
    private boolean isDKIMVerified;

    @SerializedName("WeakDKIM")
    private boolean isWeakDKIM;

    @SerializedName("DKIMHost")
    private String DKIMHost;

    @SerializedName("DKIMTextValue")
    private String DKIMTextValue;

    @SerializedName("DKIMPendingHost")
    private String DKIMPendingHost;

    @SerializedName("DKIMPendingTextValue")
    private String DKIMPendingTextValue;

    @SerializedName("DKIMRevokedHost")
    private String DKIMRevokedHost;

    @SerializedName("DKIMRevokedTextValue")
    private String DKIMRevokedTextValue;

    @SerializedName("SafeToRemoveRevokedKeyFromDNS")
    private boolean isSafeToRemoveRevokedKeyFromDNS;

    @SerializedName("DKIMUpdateStatus")
    private String DKIMUpdateStatus;

    @SerializedName("ReturnPathDomain")
    private String ReturnPathDomain;

    @SerializedName("ReturnPathDomainVerified")
    private boolean isReturnPathDomainVerified;

    @SerializedName("ReturnPathDomainCNAMEValue")
    private String ReturnPathDomainCNAMEValue;

    @SerializedName("FromEmail")
    private String fromEmail;


    public long getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(long signatureId) {
        this.signatureId = signatureId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReplyToEmail() {
        return replyToEmail;
    }

    public void setReplyToEmail(String replyToEmail) {
        this.replyToEmail = replyToEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSignatureConfirmed() {
        return isSignatureConfirmed;
    }

    public void setSignatureConfirmed(boolean signatureConfirmed) {
        isSignatureConfirmed = signatureConfirmed;
    }

    public boolean isSPFVerified() {
        return isSPFVerified;
    }

    public void setSPFVerified(boolean SPFVerified) {
        isSPFVerified = SPFVerified;
    }

    public String getSPFHost() {
        return SPFHost;
    }

    public void setSPFHost(String SPFHost) {
        this.SPFHost = SPFHost;
    }

    public String getSPFTextValue() {
        return SPFTextValue;
    }

    public void setSPFTextValue(String SPFTextValue) {
        this.SPFTextValue = SPFTextValue;
    }

    public boolean isDKIMVerified() {
        return isDKIMVerified;
    }

    public void setDKIMVerified(boolean DKIMVerified) {
        isDKIMVerified = DKIMVerified;
    }

    public boolean isWeakDKIM() {
        return isWeakDKIM;
    }

    public void setWeakDKIM(boolean weakDKIM) {
        isWeakDKIM = weakDKIM;
    }

    public String getDKIMHost() {
        return DKIMHost;
    }

    public void setDKIMHost(String DKIMHost) {
        this.DKIMHost = DKIMHost;
    }

    public String getDKIMTextValue() {
        return DKIMTextValue;
    }

    public void setDKIMTextValue(String DKIMTextValue) {
        this.DKIMTextValue = DKIMTextValue;
    }

    public String getDKIMPendingHost() {
        return DKIMPendingHost;
    }

    public void setDKIMPendingHost(String DKIMPendingHost) {
        this.DKIMPendingHost = DKIMPendingHost;
    }

    public String getDKIMPendingTextValue() {
        return DKIMPendingTextValue;
    }

    public void setDKIMPendingTextValue(String DKIMPendingTextValue) {
        this.DKIMPendingTextValue = DKIMPendingTextValue;
    }

    public String getDKIMRevokedHost() {
        return DKIMRevokedHost;
    }

    public void setDKIMRevokedHost(String DKIMRevokedHost) {
        this.DKIMRevokedHost = DKIMRevokedHost;
    }

    public String getDKIMRevokedTextValue() {
        return DKIMRevokedTextValue;
    }

    public void setDKIMRevokedTextValue(String DKIMRevokedTextValue) {
        this.DKIMRevokedTextValue = DKIMRevokedTextValue;
    }

    public boolean isSafeToRemoveRevokedKeyFromDNS() {
        return isSafeToRemoveRevokedKeyFromDNS;
    }

    public void setSafeToRemoveRevokedKeyFromDNS(boolean safeToRemoveRevokedKeyFromDNS) {
        isSafeToRemoveRevokedKeyFromDNS = safeToRemoveRevokedKeyFromDNS;
    }

    public String getDKIMUpdateStatus() {
        return DKIMUpdateStatus;
    }

    public void setDKIMUpdateStatus(String DKIMUpdateStatus) {
        this.DKIMUpdateStatus = DKIMUpdateStatus;
    }

    public String getReturnPathDomain() {
        return ReturnPathDomain;
    }

    public void setReturnPathDomain(String returnPathDomain) {
        ReturnPathDomain = returnPathDomain;
    }

    public boolean isReturnPathDomainVerified() {
        return isReturnPathDomainVerified;
    }

    public void setReturnPathDomainVerified(boolean returnPathDomainVerified) {
        isReturnPathDomainVerified = returnPathDomainVerified;
    }

    public String getReturnPathDomainCNAMEValue() {
        return ReturnPathDomainCNAMEValue;
    }

    public void setReturnPathDomainCNAMEValue(String returnPathDomainCNAMEValue) {
        ReturnPathDomainCNAMEValue = returnPathDomainCNAMEValue;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void validateSenderSignature(Method method) throws PostmarkException {
        if (method.equals(Method.CREATE)) {
            if ("".equals(this.getFromEmail()) || null == this.getFromEmail() ) {
                throw new PostmarkException("From Email is Mandatory");
            }
        }
        if (method.equals(Method.CREATE) || method.equals(Method.EDIT)) {
            if ("".equals(this.getName()) || null == this.getName()) {
                throw new PostmarkException("Name is Mandatory");
            }
        }

        if (method.equals(Method.EDIT)) {
            if (0 == this.getSignatureId()) {
                throw new PostmarkException("SenderSignature Id is Mandatory");
            }
        }

    }
}
