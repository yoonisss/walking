package com.example.walking


import com.example.walking.model.*

import retrofit2.Call
import retrofit2.http.*

interface INetworkService {
    @POST("walking/user/join")
    fun doInsertUser(@Body user: User?): Call<User>

    @POST("login")
    fun login(@Body loginDto: LoginDto): Call<LoginDto>

    @GET("walking/user/oneUser")
    fun doGetOneUser(@Query("email") email: String?): Call<User>



    //meeting
    @GET("walking/meeting/list")
    fun doGetMeetingList(): Call<MeetingListModel>

    @GET("walking/meeting/oneMeeting")
    fun doGetOneMeeting(@Query("title") title:String?): Call<Meeting>

    @POST("walking/meeting/insert")
    fun doInsertMeeting(@Body meeting: Meeting?): Call<Meeting>

    @POST("walking/meeting/insertuserinmeeting")
    fun doInsertUserinmeeting(@Body userinmeeting: Userinmeeting?): Call<Userinmeeting>

    @GET("walking/meeting/oneUserinmeeting")
    fun doGetOneUserinmeeting(@Query("userinmeeting_val") userinmeeting_val:String?): Call<Userinmeeting>

    @GET("walking/meeting/chatMemberList")
    fun doGetChatMemberList(@Query("meeting_id") meeting_id:Int?): Call<List<User>>

}