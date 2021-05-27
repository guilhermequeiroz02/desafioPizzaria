package Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import Models.Login
import ApiServices.LoginService
import ApiServices.NetworkUtils
import com.example.desafiopizzaria.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var enter: Button
    lateinit var user: EditText
    lateinit var password: EditText

    fun toast(){
        Toast.makeText(this, "Erro de conexão", Toast.LENGTH_SHORT).show()
    }

    fun invalidLogin(){
        Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
    }

    fun callScreen(){
        val intentPizza = Intent(this, ChoosePizza::class.java)
                startActivity(intentPizza)
                    finish()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enter = findViewById(R.id.btEnter)
        user = findViewById(R.id.etUser)
        password = findViewById(R.id.etPassword)

        enter.setOnClickListener {
            get()
        }
    }
    fun get(){
        val retrofitClient = NetworkUtils.getRetrofitInstance()

        val endpoint = retrofitClient.create(LoginService::class.java)
        var body = mapOf("email" to user.text.toString(), "password" to password.text.toString()) // Passar o email e senha que estão no edit text (editText.text)

        val callback = endpoint.doLogin(body)

        callback.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                toast()
            }

            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                // verificar response.body().accessToken != null ou response.code == 200 para abrir a próxima tela
                if (response.code() == 200){
                    callScreen()
                }else{
                    invalidLogin()
                }
            }
        })

    }
    }
