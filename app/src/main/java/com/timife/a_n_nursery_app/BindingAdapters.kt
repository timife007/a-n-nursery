package com.timife.a_n_nursery_app

import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


private const val BASE_URL = "https://res.cloudinary.com/iyanuashiri/"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageButton, imgUrl: String?) {
    imgUrl?.let {
        val imgButton = Uri.parse(BASE_URL).buildUpon().appendEncodedPath(it).build().toString()
        Glide.with(imgView.context)
            .load(imgButton)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

