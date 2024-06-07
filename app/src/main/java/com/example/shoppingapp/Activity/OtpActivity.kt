package com.example.shoppingapp.Activity

import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityLoginBinding
import com.example.shoppingapp.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Button3.setOnClickListener{
            if(binding.UserOTP.text!!.isEmpty())
            {

                Toast.makeText(this,"Please Provide OTP",Toast.LENGTH_SHORT).show()
            }
            else{

                verifyUser(binding.UserOTP.text.toString())

            }

90
        }
    }

    private fun verifyUser(otp: String) {
        val credential=PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!,otp)
        signInWithPhoneAuthCredential(credential)

    }
    private fun signInWithPhoneAuthCredential(credential:PhoneAuthCredential){

        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful)
                {

                    val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor=preferences.edit()
                    editor.putString("number",intent.getStringExtra("number")!!)

                    editor.apply()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                    val user=task.result?.user
                }
                else
                {

                    if(task.exception is FirebaseAuthInvalidCredentialsException)
                    {

                        Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()

                    }

                }




            }



    }

}