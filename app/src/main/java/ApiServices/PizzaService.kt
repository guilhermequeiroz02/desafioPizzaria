package ApiServices


import Models.Pizza
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface PizzaService {

    @GET ("v1/pizza")
    @Headers("Content-type:application/json")
    fun daPizza() : Call<List<Pizza>>
}