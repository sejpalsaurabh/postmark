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
public class Server {

    @SerializedName("ID")
    private long serverId;

    @SerializedName("Name")
    private String serverName;

    @SerializedName("ApiTokens")
    private List<String> apiTokens;

    @SerializedName("ServerLink")
    private String serverLink;

    @SerializedName("Color")
    private Color serverColor;

    @SerializedName("SmtpApiActivated")
    private boolean isSMTPActivated;

    @SerializedName("RawEmailEnabled")
    private boolean isRAWEmailEnabled;

    @SerializedName("InboundAddress")
    private String inboundEmailAddress;

    @SerializedName("InboundHookUrl")
    private String inboundHookURL;

    @SerializedName("BounceHookUrl")
    private String bounceHookURL;

    @SerializedName("OpenHookUrl")
    private String openHookURL;

    @SerializedName("PostFirstOpenOnly")
    private String postFirstOpenOnly;

    @SerializedName("InboundDomain")
    private String inboundDomain;

    @SerializedName("InboundHash")
    private String inboundHash;

    @SerializedName("InboundSpamThreshold")
    private long inboundSpamThreshold;

    @SerializedName("TrackOpens")
    private boolean isTrackingEnabled;

    public Server() {
    }

    public Server(long serverId, String serverName, List<String> apiTokens, String serverLink, Color serverColor, boolean isSMTPActivated, boolean isRAWEmailEnabled, String inboundEmailAddress, String inboundHookURL, String bounceHookURL, String openHookURL, String postFirstOpenOnly, String inboundDomain, String inboundHash, long inboundSpamThreshold, boolean isTrackingEnabled) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.apiTokens = apiTokens;
        this.serverLink = serverLink;
        this.serverColor = serverColor;
        this.isSMTPActivated = isSMTPActivated;
        this.isRAWEmailEnabled = isRAWEmailEnabled;
        this.inboundEmailAddress = inboundEmailAddress;
        this.inboundHookURL = inboundHookURL;
        this.bounceHookURL = bounceHookURL;
        this.openHookURL = openHookURL;
        this.postFirstOpenOnly = postFirstOpenOnly;
        this.inboundDomain = inboundDomain;
        this.inboundHash = inboundHash;
        this.inboundSpamThreshold = inboundSpamThreshold;
        this.isTrackingEnabled = isTrackingEnabled;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<String> getApiTokens() {
        return apiTokens;
    }

    public void setApiTokens(List<String> apiTokens) {
        this.apiTokens = apiTokens;
    }

    public String getServerLink() {
        return serverLink;
    }

    public void setServerLink(String serverLink) {
        this.serverLink = serverLink;
    }

    public Color getServerColor() {
        return serverColor;
    }

    public void setServerColor(Color serverColor) {
        this.serverColor = serverColor;
    }

    public boolean isSMTPActivated() {
        return isSMTPActivated;
    }

    public void setSMTPActivated(boolean SMTPActivated) {
        isSMTPActivated = SMTPActivated;
    }

    public boolean isRAWEmailEnabled() {
        return isRAWEmailEnabled;
    }

    public void setRAWEmailEnabled(boolean RAWEmailEnabled) {
        isRAWEmailEnabled = RAWEmailEnabled;
    }

    public String getInboundEmailAddress() {
        return inboundEmailAddress;
    }

    public void setInboundEmailAddress(String inboundEmailAddress) {
        this.inboundEmailAddress = inboundEmailAddress;
    }

    public String getInboundHookURL() {
        return inboundHookURL;
    }

    public void setInboundHookURL(String inboundHookURL) {
        this.inboundHookURL = inboundHookURL;
    }

    public String getBounceHookURL() {
        return bounceHookURL;
    }

    public void setBounceHookURL(String bounceHookURL) {
        this.bounceHookURL = bounceHookURL;
    }

    public String getOpenHookURL() {
        return openHookURL;
    }

    public void setOpenHookURL(String openHookURL) {
        this.openHookURL = openHookURL;
    }

    public String getPostFirstOpenOnly() {
        return postFirstOpenOnly;
    }

    public void setPostFirstOpenOnly(String postFirstOpenOnly) {
        this.postFirstOpenOnly = postFirstOpenOnly;
    }

    public String getInboundDomain() {
        return inboundDomain;
    }

    public void setInboundDomain(String inboundDomain) {
        this.inboundDomain = inboundDomain;
    }

    public String getInboundHash() {
        return inboundHash;
    }

    public void setInboundHash(String inboundHash) {
        this.inboundHash = inboundHash;
    }

    public long getInboundSpamThreshold() {
        return inboundSpamThreshold;
    }

    public void setInboundSpamThreshold(long inboundSpamThreshold) {
        this.inboundSpamThreshold = inboundSpamThreshold;
    }

    public boolean isTrackingEnabled() {
        return isTrackingEnabled;
    }

    public void setTrackingEnabled(boolean trackingEnabled) {
        isTrackingEnabled = trackingEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        if (serverId != server.serverId) return false;
        if (isSMTPActivated != server.isSMTPActivated) return false;
        if (isRAWEmailEnabled != server.isRAWEmailEnabled) return false;
        if (inboundSpamThreshold != server.inboundSpamThreshold) return false;
        if (isTrackingEnabled != server.isTrackingEnabled) return false;
        if (!serverName.equals(server.serverName)) return false;
        if (apiTokens != null ? !apiTokens.equals(server.apiTokens) : server.apiTokens != null) return false;
        if (serverLink != null ? !serverLink.equals(server.serverLink) : server.serverLink != null) return false;
        if (serverColor != server.serverColor) return false;
        if (!inboundEmailAddress.equals(server.inboundEmailAddress)) return false;
        if (inboundHookURL != null ? !inboundHookURL.equals(server.inboundHookURL) : server.inboundHookURL != null)
            return false;
        if (bounceHookURL != null ? !bounceHookURL.equals(server.bounceHookURL) : server.bounceHookURL != null)
            return false;
        if (openHookURL != null ? !openHookURL.equals(server.openHookURL) : server.openHookURL != null) return false;
        if (postFirstOpenOnly != null ? !postFirstOpenOnly.equals(server.postFirstOpenOnly) : server.postFirstOpenOnly != null)
            return false;
        if (inboundDomain != null ? !inboundDomain.equals(server.inboundDomain) : server.inboundDomain != null)
            return false;
        return inboundHash != null ? inboundHash.equals(server.inboundHash) : server.inboundHash == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (serverId ^ (serverId >>> 32));
        result = 31 * result + serverName.hashCode();
        result = 31 * result + (apiTokens != null ? apiTokens.hashCode() : 0);
        result = 31 * result + (serverLink != null ? serverLink.hashCode() : 0);
        result = 31 * result + serverColor.hashCode();
        result = 31 * result + (isSMTPActivated ? 1 : 0);
        result = 31 * result + (isRAWEmailEnabled ? 1 : 0);
        result = 31 * result + inboundEmailAddress.hashCode();
        result = 31 * result + (inboundHookURL != null ? inboundHookURL.hashCode() : 0);
        result = 31 * result + (bounceHookURL != null ? bounceHookURL.hashCode() : 0);
        result = 31 * result + (openHookURL != null ? openHookURL.hashCode() : 0);
        result = 31 * result + (postFirstOpenOnly != null ? postFirstOpenOnly.hashCode() : 0);
        result = 31 * result + (inboundDomain != null ? inboundDomain.hashCode() : 0);
        result = 31 * result + (inboundHash != null ? inboundHash.hashCode() : 0);
        result = 31 * result + (int) (inboundSpamThreshold ^ (inboundSpamThreshold >>> 32));
        result = 31 * result + (isTrackingEnabled ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Server{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", apiTokens=" + apiTokens +
                ", serverLink='" + serverLink + '\'' +
                ", serverColor=" + serverColor +
                ", isSMTPActivated=" + isSMTPActivated +
                ", isRAWEmailEnabled=" + isRAWEmailEnabled +
                ", inboundEmailAddress='" + inboundEmailAddress + '\'' +
                ", inboundHookURL='" + inboundHookURL + '\'' +
                ", bounceHookURL='" + bounceHookURL + '\'' +
                ", openHookURL='" + openHookURL + '\'' +
                ", postFirstOpenOnly='" + postFirstOpenOnly + '\'' +
                ", inboundDomain='" + inboundDomain + '\'' +
                ", inboundHash='" + inboundHash + '\'' +
                ", inboundSpamThreshold=" + inboundSpamThreshold +
                ", isTrackingEnabled=" + isTrackingEnabled +
                '}';
    }

}
