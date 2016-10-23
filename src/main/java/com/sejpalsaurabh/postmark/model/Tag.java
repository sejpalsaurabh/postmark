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
 *
 * Model Class for Retrieving Tag Triggers
 *
 * @author Saurabh Sejpal
 * @since 1.0
 * @version 1.0
 */
public class Tag {

    @SerializedName("ID")
    private long tagTriggerId;

    @SerializedName("MatchName")
    private String matchName;

    @SerializedName("TrackOpens")
    private boolean isTracked;

    public Tag() {
    }

    public long getTagTriggerId() {
        return tagTriggerId;
    }

    public void setTagTriggerId(long tagTriggerId) {
        this.tagTriggerId = tagTriggerId;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public boolean isTracked() {
        return isTracked;
    }

    public void setTracked(boolean tracked) {
        isTracked = tracked;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagTriggerId=" + tagTriggerId +
                ", matchName='" + matchName + '\'' +
                ", isTracked=" + isTracked +
                '}';
    }

    public void validateTag(Method method) throws PostmarkException {
        if (method.equals(Method.CREATE) || method.equals(Method.EDIT)) {
            if ("".equals(this.getMatchName()) || null == this.getMatchName() ) {
                throw new PostmarkException("Match Name can't be blank");
            }
        }
        if (method.equals(Method.EDIT)) {
            if (this.getTagTriggerId() == 0) {
                throw new PostmarkException("Tag Trigger Id can't be ZERO, please provide Tag Trigger ID for Editing the Tag.");
            }
        }
    }
}
