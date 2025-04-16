package com.example.demoapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class EditProfile : AppCompatActivity() {
    private lateinit var firstNameInput: TextInputEditText
    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var changePhotoButton: ImageButton
    private lateinit var profileImage: CircleImageView

    annotation class CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        supportActionBar?.hide() // Already using Toolbar

        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        saveButton = findViewById(R.id.saveChangesButton)
        changePhotoButton = findViewById(R.id.changePhotoButton)
        profileImage = findViewById(R.id.profileImage)

        saveButton.setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                // TODO: Save to database or SharedPreferences
            }
        }

        changePhotoButton.setOnClickListener {
            Toast.makeText(this, "Change photo clicked", Toast.LENGTH_SHORT).show()
            // TODO: Open gallery/camera intent here
        }
    }
}