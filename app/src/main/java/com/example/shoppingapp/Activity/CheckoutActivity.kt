package com.example.shoppingapp.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.shoppingapp.R
import com.example.shoppingapp.roomdb.AppDatabase
import com.example.shoppingapp.roomdb.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.json.JSONObject
import java.lang.Exception



class CheckoutActivity : AppCompatActivity(),PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_checkout)

        val checkout=Checkout()

        checkout.setKeyID("rzp_test_JiV9eoG0riym2j")
        val price=intent.getStringExtra("totalCost")
        //  options.put("amount",(price!!.toInt()*100).toString())
        //options.put("amount","500000")

        try{
            val options=JSONObject()
            options.put("name","ShoppingApp")
            options.put("description","Ecommerce application")
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color","#673AB7")
            options.put("currency","INR")
            options.put("amount",(price!!.toInt()*100).toString())
            options.put("prefill.email","singhgurparteek4@gmail.com")
            options.put("prefill.contact","6284575584")
            checkout.open(this,options)

        }catch (e:Exception){

            Toast.makeText(this,"Something went Wrong ",Toast.LENGTH_SHORT).show()

        }



    }

    override fun onPaymentSuccess(p0: String?) {

        Toast.makeText(this,"Payment success",Toast.LENGTH_SHORT).show()
        uploadData()

    }


    private fun uploadData() {
        val id=intent.getStringArrayListExtra("productId")
        for(currentId in id!!){
            fetchData(currentId)



        }
    }

    private fun fetchData(productId: String?) {

        val dao=AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

              lifecycleScope.launch(Dispatchers.IO) {
                  dao.deleteProduct(ProductModel(productId))
              }
                saveData(it.getString("productName"),
                    it.getString("productSP"),
                   productId)

            }

    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val data= hashMapOf<String,Any>()
        data["name"]=name!!
        data["price"]=price!!
        data["productId"]=productId
        data["status"]="Ordered"
        data["userId"]=preferences.getString("number","")!!

        val firestore1=Firebase.firestore.collection("allOrders")
        val key=firestore1.document().id
        data["orderId"] =key

        firestore1.document(key).set(data).addOnSuccessListener {

            Toast.makeText(this,"Ordered Placed",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }.addOnFailureListener {


            Toast.makeText(this,"Something WentWrong",Toast.LENGTH_SHORT).show()
        }
        }



    /*
private fun saveData(name: String?, price: String?, productId: String?) {
    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
    val data = hashMapOf<String, Any>()

    // Check for null values before adding to data map
    if (name != null && price != null && productId != null) {
        data["name"] = name
        data["price"] = price
        data["productId"] = productId
    } else {
        // Handle null values appropriately, such as logging or displaying an error message
        Toast.makeText(this, "Error: Null values encountered", Toast.LENGTH_SHORT).show()
        return
    }

    data["status"] = "Ordered"
    val userId = preferences.getString("number", "")
    if (userId != null) {
        data["userId"] = userId
    } else {
        // Handle null userId appropriately
        Toast.makeText(this, "Error: User ID not found", Toast.LENGTH_SHORT).show()
        return
    }

    val firestore1 = Firebase.firestore.collection("allOrders")

    // Use documentId to get a unique ID generated by Firestore
    val key = firestore1.document().id
    data["orderId"] = key
    firestore1.document(key).set(data).addOnSuccessListener {
        Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }.addOnFailureListener { e ->
        // Display a more informative error message
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

     */


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Error",Toast.LENGTH_SHORT).show()
    }


    }






