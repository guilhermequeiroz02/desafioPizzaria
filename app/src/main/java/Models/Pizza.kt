package Models

import android.graphics.Bitmap
import java.io.Serializable

data class Pizza (

    val id: String,
    val name: String,
    val imageUrl: String,
    var drawable: Bitmap?,
    val rating: Float,
    val priceP: Double,
    val priceM: Double,
    val priceG: Double
) : Serializable