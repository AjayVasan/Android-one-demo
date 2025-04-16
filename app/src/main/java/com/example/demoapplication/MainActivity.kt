package com.example.demoapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var wishlistCountTextView: TextView
    private lateinit var ordersCountTextView: TextView
    private lateinit var profileImage: ShapeableImageView
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        userNameTextView = findViewById(R.id.userName)
        userEmailTextView = findViewById(R.id.userEmail)
        wishlistCountTextView = findViewById(R.id.wishlistCount)
        ordersCountTextView = findViewById(R.id.ordersCount)
        profileImage = findViewById(R.id.profileImage)

        val contactUsButton = findViewById<LinearLayout>(R.id.contactUsButton)
        val faqButton = findViewById<LinearLayout>(R.id.faqButton)
        val signOutButton = findViewById<MaterialButton>(R.id.signOutButton)
        val editProfileButton = findViewById<MaterialButton>(R.id.editProfileButton)

        // Set profile image
        profileImage.setImageResource(R.drawable.ic_profile_placeholder)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("User").child("CxQojTYpMC4XWazof9Eg")

        // Load user data
        loadUserData()

        // Load stats
        loadUserStats()

        // Set up option items
        setupOptionItems()

        // Set up buttons
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        contactUsButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // Only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf("mrajayvasan@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Agribazzar issue!")
                putExtra(Intent.EXTRA_TEXT, "Hello I have an issue in your app ")
            }

            val chooser = Intent.createChooser(emailIntent, "Send Email Using")

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
            }

        }

        faqButton.setOnClickListener {
            // Handle FAQs
//            val intent = Intent(this, FAQActivity::class.java)
//            startActivity(intent)
        }

        signOutButton.setOnClickListener {
            // Handle sign out
            // FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    private fun loadUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("Name").getValue(String::class.java)?.trim().takeUnless { it.isNullOrEmpty() } ?: "Root"
                val email = snapshot.child("Email").getValue(String::class.java)?.trim().takeUnless { it.isNullOrEmpty() } ?: "root@linux.com"

                userNameTextView.text = name
                userEmailTextView.text = email

                // Load profile image if available
                // val profileImageUrl = snapshot.child("ProfileImage").getValue(String::class.java)
                // if (!profileImageUrl.isNullOrEmpty()) {
                //     Glide.with(this@MainActivity)
                //         .load(profileImageUrl)
                //         .placeholder(R.drawable.ic_profile_placeholder)
                //         .error(R.drawable.ic_profile_placeholder)
                //         .circleCrop()
                //         .into(profileImage)
                // }
            }

            override fun onCancelled(error: DatabaseError) {
                userNameTextView.text = "Root"
                userEmailTextView.text = "root@linux.com"
            }
        })
    }

    private fun loadUserStats() {
        // Load wishlist count
        database.getReference("wishlists").child("CxQojTYpMC4XWazof9Eg")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val count = snapshot.childrenCount.toInt()
                    wishlistCountTextView.text = count.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    wishlistCountTextView.text = "0"
                }
            })

        // Load orders count
        database.getReference("orders").child("CxQojTYpMC4XWazof9Eg")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val count = snapshot.childrenCount.toInt()
                    ordersCountTextView.text = count.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    ordersCountTextView.text = "0"
                }
            })
    }

    private fun setupOptionItems() {
        val optionIds = listOf(
            R.id.option_wishlist,
            R.id.option_orders,
            R.id.option_addresses,
            R.id.option_settings,
            R.id.option_edit_profile
        )

        val optionTitles = listOf(
            "My Wishlist",
            "Order History",
            "Saved Addresses",
            "Account Settings",
            "Edit Profile"
        )

        val optionIcons = listOf(
            R.drawable.ic_wishlist,
            R.drawable.ic_orders,
            R.drawable.ic_location,
            R.drawable.ic_settings,
            R.drawable.ic_edit_profile
        )

        val targetActivities = listOf(
            Wishlist::class.java,
            Orders::class.java,
            Address::class.java,
            Settings::class.java,
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