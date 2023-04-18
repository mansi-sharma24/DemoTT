package com.example.demott.Repository

import com.example.demott.Modal.DetailData
import com.example.demott.Modal.LoginModel
import com.example.demott.Modal.RegisterModal
import com.example.demott.Modal.UserDetails
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("generate-otp")
    fun generateOtp(@Field("phone") phone : String): Call<LoginModel>

    @FormUrlEncoded
    @POST("login-register-user")
    fun registerUser(@Field("phone") phone : String,
    @Field("otp") otp : String,
    @Field("Latitude")  lat : String,
    @Field("Longitude")long : String): Call<RegisterModal>

    @GET("log-out")
    fun logout(@Header("user_login_token") user_login_token : String) : Call<LoginModel>

    @GET("get-all-users")
    fun get_all_user(@Header("user_login_token") user_login_token : String) : Call<UserDetails>

      @GET("44670")
    fun demoapi() : Call<DetailData>


}