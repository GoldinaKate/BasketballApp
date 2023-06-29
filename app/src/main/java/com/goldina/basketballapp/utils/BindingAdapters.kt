package com.goldina.basketballapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import coil.load
import com.goldina.basketballapp.R
import com.goldina.basketballapp.models.response.Quarter

@BindingAdapter("app:score")
fun TextView.setScore(quarter: Quarter) {
        text =  "${quarter.score_home} - ${quarter.score_away}"
}

@BindingAdapter("app:scale")
fun ProgressBar.setScale(quarter: Quarter){
        max = quarter.score_home+quarter.score_away
}

@BindingAdapter("imageUrl","placeholder")
fun loadImage(view: ImageView, url: String?,placeHolder: Drawable) {
        if (!url.isNullOrEmpty()) {
                view.load(url) {
                        crossfade(true)
                        crossfade(1000)
                }
        }else{
                view.setImageDrawable(placeHolder)
                view.setPadding(30)
        }
}
