package com.example.shoppingapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.CategoryAdapter
import com.example.shoppingapp.adapter.CategoryProductAdapter
import com.example.shoppingapp.adapter.ProductAdapter
import com.example.shoppingapp.model.AddProductModel
import com.example.shoppingapp.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        getproducts(intent.getStringExtra("cat"))





    }

    private fun getproducts(category: String?) {


        val list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for( doc in it.documents)
                {
                    val data=doc.toObject(AddProductModel::class.java)
                    list.add(data!!)

                }
                val recyclerview=findViewById<RecyclerView>(R.id.recyclerView)
               recyclerview.adapter=CategoryProductAdapter(this,list)


            }

    }


}