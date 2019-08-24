package com.amarlubovac.videochatapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/v1/stream/register")
    Call<StreamData> registerStream();

}
