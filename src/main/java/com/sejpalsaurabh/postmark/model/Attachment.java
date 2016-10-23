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

/**
 * @author Saurabh Sejpal
 * @version 1.0
 * @since 1.0
 */
public class Attachment {
    private String name;
    private String contentType;
    private String content;

    public Attachment() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "Attachment [name=" + this.name + ", contentType=" + this.contentType + ", content=" + this.content + "]";
    }

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.content == null?0:this.content.hashCode());
        result1 = 31 * result1 + (this.contentType == null?0:this.contentType.hashCode());
        result1 = 31 * result1 + (this.name == null?0:this.name.hashCode());
        return result1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            Attachment other = (Attachment)obj;
            if(this.content == null) {
                if(other.content != null) {
                    return false;
                }
            } else if(!this.content.equals(other.content)) {
                return false;
            }

            if(this.contentType == null) {
                if(other.contentType != null) {
                    return false;
                }
            } else if(!this.contentType.equals(other.contentType)) {
                return false;
            }

            if(this.name == null) {
                if(other.name != null) {
                    return false;
                }
            } else if(!this.name.equals(other.name)) {
                return false;
            }

            return true;
        }
    }
}
