package Activitys

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import Models.Pizza
import com.example.desafiopizzaria.R
import com.example.desafiopizzaria.currencyFormat

class OrderCorfirm : AppCompatActivity() {

     var bitmap: Bitmap? = null
     var pizza: Pizza? = null
    lateinit var btnP: Button
    lateinit var btnM: Button
    lateinit var btnG: Button
    lateinit var btnFinish: Button
    lateinit var tvFlavor: TextView
    lateinit var tvPrice: TextView
    lateinit var rbStar: RatingBar
    lateinit var ivOrder: ImageView


    fun callLastScreen(){
        val lastIntent = Intent(this, FinishOrder::class.java)
        startActivity(lastIntent)
        finish()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_corfirm)

        pizza = intent.extras?.get("Pizza") as Pizza
        val bytes = intent.getByteArrayExtra("Image")
        bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes!!.count())

        btnP = findViewById(R.id.btSizeP)
        btnM = findViewById(R.id.btSizeM)
        btnG = findViewById(R.id.btSizeG)
        btnFinish = findViewById(R.id.btnBuy)
        tvFlavor = findViewById(R.id.tvFlavorOrder)
        tvPrice = findViewById(R.id.tvPrice2)
        rbStar = findViewById(R.id.rbNoteOrder)
        ivOrder = findViewById(R.id.ivOrder)

        btnFinish.setOnClickListener {
            callLastScreen()
        }

        btnP.setOnClickListener {
            btnColorG(btnP)
            btnColorW(btnG)
            btnColorW(btnM)
            tvPrice.text = pizza?.priceP?.currencyFormat()
        }

        btnM.setOnClickListener {
            btnColorG(btnM)
            btnColorW(btnP)
            btnColorW(btnG)
            tvPrice.text = pizza?.priceM?.currencyFormat()
        }

        btnG.setOnClickListener {
            btnColorG(btnG)
            btnColorW(btnP)
            btnColorW(btnM)
            tvPrice.text = pizza?.priceG?.currencyFormat()
        }


        tvPrice.text = pizza?.priceM?.currencyFormat()
        tvFlavor.text = pizza?.name
        rbStar.numStars = 5
        pizza?.rating?.let { rbStar.rating = it }
        btnColorG(btnM)
        bitmap.let { ivOrder.setImageBitmap(it) }


    }
    fun btnColorW(button: Button){
        val whiteColor = Color.parseColor("#F2F2F2")
        button.setBackgroundColor(whiteColor)
        val txBlack = Color.BLACK
        button.setTextColor(txBlack)
    }

    fun btnColorG(button: Button){
        val greenColor = Color.parseColor("#7AC64F")
        button.setBackgroundColor(greenColor)
        val txWhite = Color.WHITE
        button.setTextColor(txWhite)
    }
}