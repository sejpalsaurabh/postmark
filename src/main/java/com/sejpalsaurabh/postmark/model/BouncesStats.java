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
public class BouncesStats {

    @SerializedName("InactiveMails")
    private long inActiveMails;

    @SerializedName("Bounces")
    List<Bounces> bounces;

    public long getInActiveMails() {
        return inActiveMails;
    }

    public void setInActiveMails(long inActiveMails) {
        this.inActiveMails = inActiveMails;
    }

    public List<Bounces> getBounces() {
        return bounces;
    }

    public void setBounces(List<Bounces> bounces) {
        this.bounces = bounces;
    }

    @Override
    public String toString() {
        return "BouncesStats{" +
                "inActiveMails=" + inActiveMails +
                ", bounces=" + bounces.toString() +
                '}';
    }
}
