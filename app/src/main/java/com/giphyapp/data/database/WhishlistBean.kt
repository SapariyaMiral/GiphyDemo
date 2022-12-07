package com.giphyapp.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WhishlistBean(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var image: String,

) {
    @Ignore
    constructor(
         image: String
    ) : this(0, image)
}