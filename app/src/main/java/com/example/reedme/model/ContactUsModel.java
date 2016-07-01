package com.example.reedme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHRIYA17 on 17-Mar-16.
 */
public class ContactUsModel {

    @SerializedName("ResponseCode")
    @Expose
    private Integer ResponseCode;
    @SerializedName("ResponseStatus")
    @Expose
    private String ResponseStatus;
    @SerializedName("ResponseMessage")
    @Expose
    private String ResponseMessage;

    /**
     *
     * @return
     * The ResponseCode
     */
    public Integer getResponseCode() {
        return ResponseCode;
    }

    /**
     *
     * @param ResponseCode
     * The ResponseCode
     */
    public void setResponseCode(Integer ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    /**
     *
     * @return
     * The ResponseStatus
     */
    public String getResponseStatus() {
        return ResponseStatus;
    }

    /**
     *
     * @param ResponseStatus
     * The ResponseStatus
     */
    public void setResponseStatus(String ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    /**
     *
     * @return
     * The ResponseMessage
     */
    public String getResponseMessage() {
        return ResponseMessage;
    }

    /**
     *
     * @param ResponseMessage
     * The ResponseMessage
     */
    public void setResponseMessage(String ResponseMessage) {
        this.ResponseMessage = ResponseMessage;
    }

}
