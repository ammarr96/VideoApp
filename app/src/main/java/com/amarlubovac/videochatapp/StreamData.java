package com.amarlubovac.videochatapp;

import com.google.gson.annotations.SerializedName;

public class StreamData {

    @SerializedName("streamId")
    String streamId;

    @SerializedName("customertoken")
    String customerToken;

    @SerializedName("operatortoken")
    String operatorToken;

    @SerializedName("created")
    String createDate;

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public String getOperatorToken() {
        return operatorToken;
    }

    public void setOperatorToken(String operatorToken) {
        this.operatorToken = operatorToken;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
