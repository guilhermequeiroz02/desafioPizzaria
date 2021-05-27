package ApiServices
import Models.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @POST("v1/signin")
    @Headers("Content-type:application/json")
    fun doLogin(@Body body: Map<String, String>) : Call<Login>
}