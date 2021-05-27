package Activitys

import ApiServices.NetworkUtils
import ApiServices.PizzaService
import Models.Pizza
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.desafiopizzaria.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.Serializable


class ChoosePizza : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: MyCustomAdapter

    fun receivePizza(pizza: Pizza){
        val intent = Intent(this, OrderCorfirm::class.java)
        val stream = ByteArrayOutputStream()
        pizza.drawable?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        intent.putExtra("Image", stream.toByteArray())
        pizza.drawable = null
        intent.putExtra("Pizza", pizza as Serializable)
        startActivity(intent)
    }


    fun reciveObj(Pizza: List<Pizza>){
        adapter.flavorsP.addAll(Pizza)
        adapter.notifyDataSetChanged()
    }

    fun error(){
        Toast.makeText(this, "Erro de conexão com o servidor!", Toast.LENGTH_SHORT).show()
    }

    fun get (){
        val retrofitClient = NetworkUtils.getRetrofitInstance()

        val endpoint = retrofitClient.create(PizzaService::class.java)
        val callback = endpoint.daPizza()

        callback.enqueue(object : Callback<List<Pizza>>{
            override fun onResponse(call: Call<List<Pizza>>, response: Response<List<Pizza>>) {
                if (response.code() == 200){
                    response.body()?.let { reciveObj(it) }
                } else {
                    error()
                }
            }

            override fun onFailure(call: Call<List<Pizza>>, t: Throwable) {
                error()
            }

        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_pizza)

        val search = findViewById<SearchView>(R.id.svFlavor)

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    adapter.isSearching = false
                    adapter.searchingText = ""
                }else{
                    adapter.isSearching = true
                    adapter.searchingText = newText
                }
                adapter.notifyDataSetChanged()
                return false
            }

        })


        listView = findViewById<ListView>(R.id.lvPizzas)

        adapter = MyCustomAdapter(this)

        listView.adapter = adapter

        get()

        listView.setOnItemClickListener { parent, view, position, id ->
            val item = listView.getItemAtPosition(position) as Pizza
            receivePizza(item.copy())
        }


    }
    private class MyCustomAdapter(context: Context): BaseAdapter() {


        private val mContext: Context

        var flavorsP = mutableListOf<Pizza>()
        var isSearching = false
        var searchingText: String = ""

        init {
            mContext = context
        }

        override fun getCount(): Int {
            if (isSearching == true){
                return flavorsP.filter { it.name.toLowerCase().contains(searchingText.toLowerCase()) }.count()
            }else {
                return flavorsP.count()
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            if (isSearching == true){
                return flavorsP.filter { it.name.toLowerCase().contains(searchingText.toLowerCase()) }[position]
            }
            return flavorsP[position]
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val pizza = getItem(position) as Pizza
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.custom_listview, parent, false)
            row.findViewById<TextView>(R.id.tvFlavor).text = pizza.name
            row.findViewById<TextView>(R.id.tvPrice).text = pizza.priceP.currencyFormat()
            val imageView = row.findViewById<ImageView>(R.id.ivPizza)
            Picasso.get().load(pizza.imageUrl).into(imageView)
            Picasso.get().load(pizza.imageUrl).into(imageView, object: com.squareup.picasso.Callback{
                override fun onSuccess() {
                    flavorsP[position].drawable = imageView.drawable.toBitmap()
                }
                override fun onError(e: Exception?) { }
            })

            val rating = row.findViewById<RatingBar>(R.id.rbNote)
            rating.numStars = 5
            rating.rating = pizza.rating
            return row
        }




    }
}