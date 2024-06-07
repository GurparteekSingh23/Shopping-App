package com.example.shoppingapp.adapter

import android.content.Context

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.AllOrdersItemLayoutBinding
import com.example.shoppingapp.model.AllOrderModel

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore



class AllOrderAdapter(val list: ArrayList<AllOrderModel>, val context: Context):RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {
    inner class AllOrderViewHolder(val binding: AllOrdersItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
      return AllOrderViewHolder(AllOrdersItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
         holder.binding.productTitle.text=list[position].name
         holder.binding.productPrice.text=list[position].price

    when(list[position].status){
        "Ordered"->{
            holder.binding.productStatus.text="Ordered"
        }
        "Dispatched"->{
            holder.binding.productStatus.text="Dispatched"
        }
        "Delivered"->{
            holder.binding.productStatus.text="Delivered"

        }

        "Canceled"->{
            holder.binding.productStatus.text="Cancelled"
        }
    }

    }



}