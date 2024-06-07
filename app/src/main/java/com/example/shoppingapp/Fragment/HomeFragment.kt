package com.example.shoppingapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.adapter.CategoryAdapter
import com.example.shoppingapp.adapter.CategoryProductAdapter
import com.example.shoppingapp.adapter.ProductAdapter
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.model.AddProductModel
import com.example.shoppingapp.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        progressBar = binding.progressBar1
        progressBar.visibility = View.VISIBLE


        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if (preference.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)


        getcategories()
        getproducts()
        getSliderImage()

        progressBar.visibility = View.GONE

        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item").get().addOnSuccessListener {

            Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)


        }
    }

    private fun getproducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)

                }
                binding.productRecycler.adapter = ProductAdapter(requireContext(), list)


            }

    }

    private fun getcategories() {

        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)

                }
                binding.CategoryRecycler.adapter = CategoryAdapter(requireContext(), list)


            }
    }


}
