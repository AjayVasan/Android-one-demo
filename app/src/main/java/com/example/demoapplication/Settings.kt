package com.example.demoapplication

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupNotificationSettings()
        setupPrivacySettings()
        setupApplicationSettings()
    }

    private fun setupNotificationSettings() {
        val emailSwitch = findViewById<SwitchCompat>(R.id.emailNotificationsSwitch)
        val pushSwitch = findViewById<SwitchCompat>(R.id.pushNotificationsSwitch)
        val smsSwitch = findViewById<SwitchCompat>(R.id.smsNotificationsSwitch)

        emailSwitch.setOnCheckedChangeListener { _, isChecked ->
            // In a real app, save this setting to SharedPreferences or a backend
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Email notifications $status", Toast.LENGTH_SHORT).show()
        }

        pushSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Push notifications $status", Toast.LENGTH_SHORT).show()
        }

        smsSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "SMS notifications $status", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPrivacySettings() {
        val changePasswordLayout = findViewById<LinearLayout>(R.id.changePasswordLayout)
        val privacyPolicyLayout = findViewById<LinearLayout>(R.id.privacyPolicyLayout)

        changePasswordLayout.setOnClickListener {
            // In a real app, launch change password screen
            Toast.makeText(this, "Change password feature coming soon", Toast.LENGTH_SHORT).show()
        }

        privacyPolicyLayout.setOnClickListener {
            // In a real app, show privacy policy
            Toast.makeText(this, "Privacy policy feature coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupApplicationSettings() {
        val darkModeSwitch = findViewById<SwitchCompat>(R.id.darkModeSwitch)
        val clearCacheLayout = findViewById<LinearLayout>(R.id.clearCacheLayout)
        val logoutLayout = findViewById<LinearLayout>(R.id.logoutLayout)

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // In a real app, implement dark mode toggle
            val mode = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Dark mode $mode", Toast.LENGTH_SHORT).show()
        }

        clearCacheLayout.setOnClickListener {
            // In a real app, clear app cache
            Toast.makeText(this, "Cache cleared", Toast.LENGTH_SHORT).show()
        }

        logoutLayout.setOnClickListener {
            // In a real app, implement logout functionality
            Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show()
            // Redirect to login screen
            // val intent = Intent(this, LoginActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}