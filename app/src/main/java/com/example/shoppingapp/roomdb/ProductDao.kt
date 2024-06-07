package com.example.shoppingapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
   suspend fun insertProduct(productModel: ProductModel)

     @Delete
  suspend   fun deleteProduct(productModel: ProductModel)

    @Query("Select * FROM products")
    fun getAllProducts():LiveData<List<ProductModel>>


    @Query("Select * FROM products WHERE productId=:id")
    fun isExit(id:String):ProductModel



}