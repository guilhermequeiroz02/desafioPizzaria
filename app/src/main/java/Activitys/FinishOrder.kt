package Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.desafiopizzaria.R

class FinishOrder : AppCompatActivity() {

    lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_order)

        back = findViewById(R.id.btBack)
        back.setOnClickListener {
            finish()
        }

    }
}
