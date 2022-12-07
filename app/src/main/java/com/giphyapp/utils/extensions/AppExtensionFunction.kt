package com.giphyapp.utils.extensions



import androidx.appcompat.widget.AppCompatImageView
import com.giphyapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.io.File

/**Load image with glide*/
fun AppCompatImageView.loadImage(img: String) {
    val signature = File(img)
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(signature.lastModified()))

    Glide.with(context).asGif()
        .load(img)
        .centerCrop()
        .dontTransform()
        .placeholder(R.drawable.loading_img)
        .apply(requestOptions).into(this)
}






