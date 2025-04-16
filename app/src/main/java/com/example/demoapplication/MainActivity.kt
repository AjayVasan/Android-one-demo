package com.example.demoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View references
        userNameTextView = findViewById(R.id.userName)
        userEmailTextView = findViewById(R.id.userEmail)
        val profileImage = findViewById<ImageView>(R.id.profileImage)
        profileImage.setImageResource(R.drawable.ic_profile_placeholder)

        // Initialize Firebase reference to the user node
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("User").child("CxQojTYpMC4XWazof9Eg")

        // Fetch name and email from Firebase
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("Name").getValue(String::class.java)?.trim().takeUnless { it.isNullOrEmpty() } ?: "Root"
                val email = snapshot.child("Email").getValue(String::class.java)?.trim().takeUnless { it.isNullOrEmpty() } ?: "root@linux.com"

                userNameTextView.text = name
                userEmailTextView.text = email
            }

            override fun onCancelled(error: DatabaseError) {
                // Show fallback values if there's an error
                userNameTextView.text = "Root"
                userEmailTextView.text = "root@linux.com"
            }
        })

        // List setup (same as before)
        val optionIds = listOf(
            R.id.option_wishlist,
            R.id.option_orders,
            R.id.option_settings,
            R.id.option_addresses,
            R.id.option_edit_profile
        )

        val optionTitles = listOf(
            "Wishlist",
            "Orders",
            "Account Settings",
            "Addresses",
            "Edit Profile"
        )

        val optionIcons = listOf(
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

            itemView.setOnClickListener {
                val intent = Intent(this, targetActivities[i])
                startActivity(intent)
            }
        }
    }
}
