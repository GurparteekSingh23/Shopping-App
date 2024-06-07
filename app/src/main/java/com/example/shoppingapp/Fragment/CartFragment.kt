package com.example.shoppingapp.Fragment

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.Activity.AddressActivity
import com.example.shoppingapp.Activity.CategoryActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.CartAdapter
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.roomdb.AppDatabase
import com.example.shoppingapp.roomdb.ProductModel
import java.util.Collections.list


class CartFragment : Fragment() {

private lateinit var binding: FragmentCartBinding

private lateinit var list:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(layoutInflater)


        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val dao=AppDatabase.getInstance(requireContext()).productDao()
        list=ArrayList()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter=CartAdapter(requireContext(),it)
            list.clear()
            for(data in it){
                list.add(data.productId)


            }

            totalcost(it)
        }

        return binding.root
    }

    private fun totalcost(data: List<ProductModel>?){
        var total=0
        for(item in data!!)
        {
        total +=item.productSp!!.toInt()
        }
        binding.textView12.text="total item in cart is ${data.size}"
        binding.textView13.text="Total cost :$total"

        binding.checkout.setOnClickListener{

            val intent= Intent(context, AddressActivity::class.java)
            val b=Bundle()
            b.putStringArrayList("productId",list)
            b.putString("totalCost",total.toString())
            intent.putExtras(b)


            startActivity(intent)



        }




    }


}