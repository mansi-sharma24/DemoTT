package com.example.demott.ViewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demott.Modal.DetailData
import com.example.demott.Modal.LoginModel
import com.example.demott.Modal.RegisterModal
import com.example.demott.Modal.UserDetails
import com.example.demott.Repository.ApiInterface
import com.example.demott.Utils.CommonUtils
import com.example.demott.Utils.RetrofitBaseUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class DemoViewModal  : ViewModel(){
    val apiInterface = RetrofitBaseUrl.getClient()?.create(ApiInterface::class.java)

    private var generateOtpMutable : MutableLiveData<LoginModel> = MutableLiveData()
    private var registerMutable : MutableLiveData<RegisterModal> = MutableLiveData()
    private var logoutMutable : MutableLiveData<LoginModel> = MutableLiveData()
    private var getUserMutable : MutableLiveData<UserDetails> = MutableLiveData()
    private var demoapiMutable : MutableLiveData<DetailData> = MutableLiveData()

    fun generateOtpLive (activity: Activity , phone : String): LiveData<LoginModel>{
        CommonUtils.showDialog(activity)
        apiInterface?.generateOtp(phone)?.enqueue(object: Callback<LoginModel>{
            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                CommonUtils.dismissDialog()
                CommonUtils.xToast(activity, t.message.toString())
            }

            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                CommonUtils.dismissDialog()
                if (response.body()!=null){
                    generateOtpMutable.postValue(response.body())
                }
                else{
                    CommonUtils.xToast(activity,"Technical Issue ")
                }
            }
        } )
        return generateOtpMutable
    }

    fun registerUserLive(activity: Activity,phone : String , otp : String , Latitude : String , Longitude : String): LiveData<RegisterModal>{
        CommonUtils.showDialog(activity)
        apiInterface?.registerUser(phone,otp,Latitude,Longitude)?.enqueue(object : Callback<RegisterModal>{
            override fun onResponse(call: Call<RegisterModal>, response: Response<RegisterModal>) {
                CommonUtils.dismissDialog()
                if (response.body()!=null){
                    registerMutable.postValue(response.body())
                }
                else{
                    CommonUtils.xToast(activity,"Technical Issue")
                }
            }

            override fun onFailure(call: Call<RegisterModal>, t: Throwable) {
                CommonUtils.dismissDialog()
                CommonUtils.xToast(activity,t.message.toString())
            }
        })
        return registerMutable
    }

    fun logoutLive(activity: Activity , auth_token: String): LiveData<LoginModel>{
        CommonUtils.showDialog(activity)
        apiInterface?.logout(auth_token)?.enqueue(object  : Callback<LoginModel>{
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                CommonUtils.dismissDialog()
                if (response.body()!=null){
                    logoutMutable.postValue(response.body())
                } else{
                    CommonUtils.xToast(activity,"Technical Issue")
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                CommonUtils.dismissDialog()
                CommonUtils.xToast(activity,t.message.toString())
            }
        })
        return logoutMutable
    }

    fun get_all_user(activity: Activity,auth_token: String): LiveData<UserDetails>{
        CommonUtils.showDialog(activity)
        apiInterface?.get_all_user(auth_token)?.enqueue(object : Callback<UserDetails>{
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                CommonUtils.dismissDialog()
                if (response.body()!=null){
                    getUserMutable.postValue(response.body())
                } else{
                    CommonUtils.xToast(activity,"Technical Issue")
                }
            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                CommonUtils.dismissDialog()
                CommonUtils.xToast(activity,t.message.toString())
            }
        })
        return getUserMutable
    }
 fun demoapi(activity: Activity): LiveData<DetailData>{
        CommonUtils.showDialog(activity)
        apiInterface?.demoapi()?.enqueue(object : Callback<DetailData>{
            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {
                CommonUtils.dismissDialog()
                if (response.body()!=null){
                    demoapiMutable.postValue(response.body())
                } else{
                    CommonUtils.xToast(activity,"Technical Issue")
                }
            }

            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                CommonUtils.dismissDialog()
                CommonUtils.xToast(activity,t.message.toString())
            }
        })
        return demoapiMutable
    }

}