/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 https://www.spaceotechnologies.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.giphyapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDao {

    @Query("select * from wishlist")
    fun allUsers(): List<WhishlistBean>

    @Insert
    fun insert(repos: WhishlistBean)

    @Update
    fun update(repos: WhishlistBean)

    @Delete
    fun delete(repos: WhishlistBean)

    @Query("DELETE FROM wishlist")
    fun deleteAllBeans()

    @Query("SELECT * FROM wishlist")
    fun getAllBeans(): LiveData<List<WhishlistBean>>


    //0 = data is not exist in your table
    @Query("SELECT * FROM wishlist WHERE image = :image")
    fun isDataExist(image: String?): Int

    @Query("SELECT EXISTS(SELECT * FROM wishlist WHERE image = :image)")
    fun isRecordExistsUserId(image: String?): Boolean

    @Query("DELETE FROM wishlist WHERE image = :image")
    fun deleteByUrl(image: String)

}