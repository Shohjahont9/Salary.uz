package shohjahon.salaryuz.api

import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import shohjahon.salaryuz.api.reponses.DeleteUserResponse
import shohjahon.salaryuz.models.Bonus
import shohjahon.salaryuz.models.Ishchi
import shohjahon.salaryuz.models.Lavozim
import java.util.*
import kotlin.collections.ArrayList

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/cms/account/add")
    fun addUser(@Body ishchi: Ishchi): Call<Ishchi>

    @GET("/cms/account/check")
    fun getUser(): Call<ArrayList<Ishchi>>

    @Headers("Content-Type: application/json")
    @POST("/cms/position/add")
    fun addLavozim(@Body lavozim: Lavozim): Call<Lavozim>

    @GET("/cms/position/get_all")
    fun getLavozim(): Call<List<Lavozim>>

    @Headers("Content-Type: application/json")
    @POST("/cms/bonus/add")
    fun addBonus(@Body bonus: Bonus): Call<Bonus>

    @GET("/cms/bonus/get_all")
    fun getBonus(): Call<List<Bonus>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/cms/account/delete", hasBody = true)
    fun deleteWorker(
        @Field("name")name: String,
        @Field("surname")surname: String
    ): Call<DeleteUserResponse>

    @Headers("Content-Type: application/json")
    @DELETE("")
    fun deleteLavozim(@Body lavozim: Lavozim): Call<Lavozim>

    @Headers("Content-Type: application/json")
    @DELETE("")
    fun deleteBonus(@Body bonus: Bonus): Call<Bonus>


}