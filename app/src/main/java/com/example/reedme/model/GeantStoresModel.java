package com.example.reedme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHRIYA17 on 18-Mar-16.
 */
public class GeantStoresModel {
    @SerializedName("Response")
    @Expose
    private List<GeantStoresResponse> Response = new ArrayList<GeantStoresResponse>();

    /**
     *
     * @return
     * The Response
     */
    public List<GeantStoresResponse> getResponse() {
        return Response;
    }

    /**
     *
     * @param Response
     * The Response
     */
    public void setResponse(List<GeantStoresResponse> Response) {
        this.Response = Response;
    }

}
