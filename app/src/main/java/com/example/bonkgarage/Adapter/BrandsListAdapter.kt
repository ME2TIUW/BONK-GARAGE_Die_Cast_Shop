//package com.example.bonkgarage.Adapter
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.bonkgarage.Brands.BrandsListActivity
//import com.example.bonkgarage.CarsDetailsActivity
//import com.example.bonkgarage.R
//import com.google.firebase.firestore.FirebaseFirestore
//
//class BrandsListAdapter(private val listener: (HashMap<String, Any>) -> Unit) :
//    RecyclerView.Adapter<BrandsListAdapter.Holder>() {
//
//    private val allCars = mutableListOf<HashMap<String, Any>>() // All fetched cars
//    private val filteredCars = mutableListOf<HashMap<String, Any>>() // Filtered cars
//
//    // Get car data from Firestore on creation using initializer block
//    init {
//        val db = FirebaseFirestore.getInstance()
//        val carCollection = db.collection("car_models")
//        carCollection.get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val carData = document.data!!
//                    allCars.add(carData as HashMap<String, Any>)
//                    filterCars(allCars) // Initially filter all cars
//                    notifyItemInserted(filteredCars.size - 1) // Notify adapter of new item
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Firestore", "Error getting documents: $exception")
//            }
//    }
//
//    private fun filterCars(cars: List<HashMap<String, Any>>) {
//        filteredCars.clear()  // Clear existing filtered list before adding new ones
//        val targetBrands = listOf("tamiya", "minigt", "kaido", "tarmac")
//        for (carData in cars) {
//            val brand = carData["brand"]?.toString()?.lowercase() ?: ""
//            if (targetBrands.any { brand.contains(it) }) { // Check if brand contains any target brand
//                filteredCars.add(carData)
//                notifyItemInserted(filteredCars.size - 1) // Notify for each added item
//            }
//        }
//    }
//
//    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imgCar: ImageView = itemView.findViewById(R.id.imgCars)
//        val txtCarName: TextView = itemView.findViewById(R.id.txtCarName)
//
//        fun bind(carData: HashMap<String, Any>) {
//            val name = carData["name"] as String
//
//            txtCarName.text = name
//
//            val context = itemView.context
//            var imageResource = R.drawable.car_black  // Default image
//
//            when {
//                name.lowercase().contains("datsun") -> {
//                    imageResource = R.drawable.car_yellow
//                }
//                name.lowercase().contains("nismo") -> {
//                    imageResource = R.drawable.car_orange
//                }
//                name.lowercase().contains("supra") -> {
//                    imageResource = R.drawable.car_white
//                }
//                name.lowercase().contains("silvia") -> {
//                    imageResource = R.drawable.car_purple
//                }
//                name.lowercase().contains("nsx") -> {
//                    imageResource = R.drawable.car_red
//                }
//                name.lowercase().contains("Skyline") -> {
//                    imageResource = R.drawable.car_black
//                }
//                name.lowercase().contains("stagea") -> {
//                    imageResource = R.drawable.car_silver
//                }
//            }
//
//            Glide.with(context)
//                .load(imageResource)
//                .into(imgCar)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.list_brands_item, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val carData = filteredCars[position]  // Use filteredCars for filtered data
//        holder.bind(carData)
//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, CarsDetailsActivity::class.java)
//            intent.putExtra("carData", carData) // Pass car data to the activity
//            holder.itemView.context.startActivity(intent)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return filteredCars.size
//    }
//
//}

package com.example.bonkgarage.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bonkgarage.Brands.BrandsListActivity
import com.example.bonkgarage.CarsDetailsActivity
import com.example.bonkgarage.R
import com.google.firebase.firestore.FirebaseFirestore

class BrandsListAdapter(private val listener: (HashMap<String, Any>) -> Unit) :
    RecyclerView.Adapter<BrandsListAdapter.Holder>() {

    private val allCars = mutableListOf<HashMap<String, Any>>() // All fetched cars
    private val filteredCars = mutableListOf<HashMap<String, Any>>() // Filtered cars

    // Get car data from Firestore on creation using initializer block
    init {
        val db = FirebaseFirestore.getInstance()
        val carCollection = db.collection("car_models")
        carCollection.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val carData = document.data!!
                    Log.d("FilterDebug", "car data = $carData")  // Log extracted brand name
                    allCars.add(carData as HashMap<String, Any>)
                    filterCars(allCars) // Initially filter all cars
                    notifyItemRangeInserted(0, filteredCars.size)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: $exception")
            }
    }

    private fun filterCars(cars: List<HashMap<String, Any>>) {

        filteredCars.clear()  // Clear existing filtered list before adding new ones
        val targetBrands = listOf("tamiya", "minigt", "kaido", "tarmac")
        for (carData in cars) {
            val brand = carData["brand"]?.toString()?.trim()?.lowercase() ?: ""  // Trim spaces and lowercase
            Log.d("FilterDebug", "Brand: $brand")  // Log extracted brand name
            if (targetBrands.any { brand.contains(it) }) { // Check if brand contains any target brand
                filteredCars.add(carData)
            }
        }
            notifyItemRangeInserted(0, filteredCars.size)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCar: ImageView = itemView.findViewById(R.id.imgCars)
        val txtCarName: TextView = itemView.findViewById(R.id.txtCarName)

        fun bind(carData: HashMap<String, Any>) {
            val name = carData["name"] as String

            txtCarName.text = name

            val context = itemView.context
            var imageResource = R.drawable.car_black  // Default image

            when {
                name.lowercase().contains("datsun") -> {
                    imageResource = R.drawable.car_yellow
                }
                name.lowercase().contains("nismo") -> {
                    imageResource = R.drawable.car_orange
                }
                name.lowercase().contains("supra") -> {
                    imageResource = R.drawable.car_white
                }
                name.lowercase().contains("silvia") -> {
                    imageResource = R.drawable.car_purple
                }
                name.lowercase().contains("nsx") -> {
                    imageResource = R.drawable.car_red
                }
                name.lowercase().contains("Skyline") -> {
                    imageResource = R.drawable.car_black
                }
                name.lowercase().contains("stagea") -> {
                    imageResource = R.drawable.car_silver
                }
            }

            Glide.with(context)
                .load(imageResource)
                .into(imgCar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_brands_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val carData = filteredCars[position]  // Use filteredCars for filtered data
        holder.bind(carData)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CarsDetailsActivity::class.java)
            intent.putExtra("carData", carData) // Pass car data to the activity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredCars.size
    }

}

