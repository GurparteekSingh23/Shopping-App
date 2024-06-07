package com.example.shoppingapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityRegisterBinding
import com.example.shoppingapp.databinding.LayoutCartItemBinding
import com.example.shoppingapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityRegisterBinding.inflate(layoutInflater)
        binding.Button4.setOnClickListener{


            openLogin()


        }
        binding.Button3.setOnClickListener{
            ValidateUser()


        }
        setContentView(binding.root)
    }

    private fun ValidateUser() {
        if(binding.username.text!!.isEmpty() || binding.userNumber.text!!.isEmpty())
        {

            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()


        }
        else{
            storeData()
        }

    }

    private fun storeData() {
        val builder=AlertDialog.Builder(this)
            .setTitle("Loading....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putString("number",binding.userNumber.text.toString())
        editor.putString("name",binding.username.text.toString())
        editor.apply()


        val data=UserModel(userName=binding.username.text.toString(),userPhoneNumber=binding.userNumber.text.toString())

        Firebase.firestore.collection("users").document(binding.userNumber.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User Registered",Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener{
                builder.dismiss()
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()

            }


    }

    private fun openLogin(){

        startActivity(Intent(this,LoginActivity::class.java))
        finish()


    }
}