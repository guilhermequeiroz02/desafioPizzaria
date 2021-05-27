package ApiServices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object {

        /** Retorna uma Instância do Client Retrofit para Requisições
         * @param path Caminho Principal da API
         */
        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                    .baseUrl("https://p3teufi0k9.execute-api.us-east-1.amazonaws.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }
}