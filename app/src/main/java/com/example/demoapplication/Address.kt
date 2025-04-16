package com.example.demoapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Address : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var addAddressButton: Button
    private lateinit var addressAdapter: AddressAdapter

    // Sample data class for Address
    data class AddressModel(
        val id: String,
        val name: String,
        val line1: String,
        val line2: String?,
        val city: String,
        val state: String,
        val zip: String,
        val country: String,
        val phone: String?,
        var isDefault: Boolean = false
    )

    // Example list of addresses
    private val addresses = mutableListOf<AddressModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_address)

        // Initialize views
        recyclerView = findViewById(R.id.addressesRecyclerView)
        emptyStateText = findViewById(R.id.emptyStateText)
        addAddressButton = findViewById(R.id.addAddressButton)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up RecyclerView
        addressAdapter = AddressAdapter(
            addresses,
            onEditClick = { address -> editAddress(address) },
            onSetDefaultClick = { address -> setDefaultAddress(address) },
            onDeleteClick = { address -> deleteAddress(address) }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Address)
            adapter = addressAdapter
        }

        // Add address button click listener
        addAddressButton.setOnClickListener {
            navigateToAddAddress()
        }

        // Load addresses from your data source
        loadAddresses()
    }

    private fun loadAddresses() {
        // Here you would typically load addresses from your data source (API, database, etc.)
        // For this example, we'll just use some sample data

        // Clear existing addresses
        addresses.clear()

        // Add sample addresses (replace with your actual data loading logic)
        addresses.add(
            AddressModel(
                id = "1",
                name = "Home",
                line1 = "123 Main Street",
                line2 = "Apt 4B",
                city = "San Francisco",
                state = "CA",
                zip = "94105",
                country = "USA",
                phone = "555-123-4567",
                isDefault = true
            )
        )

        addresses.add(
            AddressModel(
                id = "2",
                name = "Work",
                line1 = "456 Business Ave",
                line2 = null,
                city = "San Francisco",
                state = "CA",
                zip = "94107",
                country = "USA",
                phone = "555-987-6543",
                isDefault = false
            )
        )

        // Update UI
        updateUI()
    }

    private fun updateUI() {
        if (addresses.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateText.visibility = View.GONE
            addressAdapter.notifyDataSetChanged()
        }
    }

    private fun editAddress(address: AddressModel) {
        // Navigate to edit address screen with the selected address
        // Intent to EditAddressActivity with address details as extras
        // For example:
        // val intent = Intent(this, EditAddressActivity::class.java)
        // intent.putExtra("ADDRESS_ID", address.id)
        // startActivity(intent)

        // For now, we'll just show a message
        android.widget.Toast.makeText(
            this,
            "Edit address: ${address.name}",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun setDefaultAddress(address: AddressModel) {
        // Update the default address
        addresses.forEach { it.isDefault = (it.id == address.id) }
        addressAdapter.notifyDataSetChanged()

        // Here you would also update this in your data source

        android.widget.Toast.makeText(
            this,
            "${address.name} is now your default address",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun deleteAddress(address: AddressModel) {
        // Remove the address from the list
        addresses.removeIf { it.id == address.id }
        updateUI()

        // Here you would also delete it from your data source

        android.widget.Toast.makeText(
            this,
            "Address deleted",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToAddAddress() {
        // Navigate to add address screen
        // For example:
        // val intent = Intent(this, AddAddressActivity::class.java)
        // startActivity(intent)

        // For now, we'll just show a message
        android.widget.Toast.makeText(
            this,
            "Add new address",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    // RecyclerView Adapter for Addresses
    inner class AddressAdapter(
        private val addresses: List<AddressModel>,
        private val onEditClick: (AddressModel) -> Unit,
        private val onSetDefaultClick: (AddressModel) -> Unit,
        private val onDeleteClick: (AddressModel) -> Unit
    ) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.addressName)
            val line1TextView: TextView = view.findViewById(R.id.addressLine1)
            val line2TextView: TextView = view.findViewById(R.id.addressLine2)
            val cityStateTextView: TextView = view.findViewById(R.id.addressCityState)
            val zipCountryTextView: TextView = view.findViewById(R.id.addressZipCountry)
            val phoneTextView: TextView = view.findViewById(R.id.addressPhone)
            val actionsMenu: ImageButton = view.findViewById(R.id.addressActionsMenu)
            val editButton: Button = view.findViewById(R.id.editAddressButton)
            val setDefaultButton: Button = view.findViewById(R.id.setDefaultButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_address, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = addresses.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val address = addresses[position]

            holder.nameTextView.text = address.name
            if (address.isDefault) {
                holder.nameTextView.text = "${address.name} (Default)"
            } else {
                holder.nameTextView.text = address.name
            }

            holder.line1TextView.text = address.line1

            if (address.line2.isNullOrEmpty()) {
                holder.line2TextView.visibility = View.GONE
            } else {
                holder.line2TextView.visibility = View.VISIBLE
                holder.line2TextView.text = address.line2
            }

            holder.cityStateTextView.text = "${address.city}, ${address.state}"
            holder.zipCountryTextView.text = "${address.zip}, ${address.country}"

            if (address.phone.isNullOrEmpty()) {
                holder.phoneTextView.visibility = View.GONE
            } else {
                holder.phoneTextView.visibility = View.VISIBLE
                holder.phoneTextView.text = address.phone
            }

            // Handle menu button click
            holder.actionsMenu.setOnClickListener { view ->
                showPopupMenu(view, address)
            }

            // Handle edit button click
            holder.editButton.setOnClickListener {
                onEditClick(address)
            }

            // Handle set default button click
            holder.setDefaultButton.apply {
                if (address.isDefault) {
                    text = "Default"
                    isEnabled = false
                } else {
                    text = "Set as Default"
                    isEnabled = true
                    setOnClickListener {
                        onSetDefaultClick(address)
                    }
                }
            }
        }

        private fun showPopupMenu(view: View, address: AddressModel) {
            val popup = PopupMenu(this@Address, view)
            popup.menuInflater.inflate(R.menu.address_actions_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onEditClick(address)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(address)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }
    }
}