package com.example.demoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileImage = findViewById<ImageView>(R.id.profileImage)
        profileImage.setImageResource(R.drawable.ic_profile_placeholder)

        val optionIds = listOf(
//            R.id.option_cart,
            R.id.option_wishlist,
            R.id.option_orders,
            R.id.option_settings,
            R.id.option_addresses,
            R.id.option_edit_profile
        )

        val optionTitles = listOf(
//            "Cart",
            "Wishlist",
            "Orders",
            "Account Settings",
            "Addresses",
            "Edit Profile"
        )

        val optionIcons = listOf(
//            R.drawable.ic_cart,
            R.drawable.ic_wishlist,
            R.drawable.ic_orders,
            R.drawable.ic_settings,
            R.drawable.ic_location,
            R.drawable.ic_edit_profile
        )

        val targetActivities = listOf(
            Wishlist::class.java,
            Orders::class.java,
            Settings::class.java,
            Address::class.java,
            EditProfile::class.java
        )

        for (i in optionIds.indices) {
            val itemView = findViewById<View>(optionIds[i])
            val iconView = itemView.findViewById<ImageView>(R.id.optionIcon)
            val titleView = itemView.findViewById<TextView>(R.id.optionTitle)

            iconView.setImageResource(optionIcons[i])
            titleView.text = optionTitles[i]

            // Set click listener to launch respective activity
            itemView.setOnClickListener {
                val intent = Intent(this, targetActivities[i])
                startActivity(intent)
            }
        }
    }
}
