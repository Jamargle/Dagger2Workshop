package com.mobileasone.dagger2workshop.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageViewWithImagePath
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    private var imageFilePath: String? = null

    fun setImageByImagePath(context: Context, imagePath: String) {
        imageFilePath = imagePath

        Glide.with(context)
                .load(imagePath)
                .into(this)
    }

    fun getFilePath() = imageFilePath

}
