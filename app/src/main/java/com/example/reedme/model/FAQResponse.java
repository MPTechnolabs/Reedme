package com.example.reedme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHRIYA17 on 29-Mar-16.
 */
public class FAQResponse {


    @SerializedName("Response")
    @Expose
    private String Response;
    @SerializedName("DeviceId")
    @Expose
    private String DeviceId;
    @SerializedName("DeviceType")
    @Expose
    private Object DeviceType;

    /**
     *
     * @return
     * The Response
     */
    public String getResponse() {
        return Response;
    }

    /**
     *
     * @param Response
     * The Response
     */
    public void setResponse(String Response) {
        this.Response = Response;
    }

    /**
     *
     * @return
     * The DeviceId
     */
    public String getDeviceId() {
        return DeviceId;
    }

    /**
     *
     * @param DeviceId
     * The DeviceId
     */
    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }

    /**
     *
     * @return
     * The DeviceType
     */
    public Object getDeviceType() {
        return DeviceType;
    }

    /**
     *
     * @param DeviceType
     * The DeviceType
     */
    public void setDeviceType(Object DeviceType) {
        this.DeviceType = DeviceType;
    }

}
