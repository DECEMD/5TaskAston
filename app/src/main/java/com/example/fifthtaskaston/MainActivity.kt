package com.example.fifthtaskaston

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fifthtaskaston.frags.ContactsDetailedFragment
import com.example.fifthtaskaston.frags.ContactsFragment
import com.example.fifthtaskaston.utils.OnCertainContactClickable
import com.example.fifthtaskaston.utils.OnSaveButtonClickable

class MainActivity : AppCompatActivity(), OnCertainContactClickable, OnSaveButtonClickable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (isTablet(this)) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.tablet_container_contacts_list, ContactsFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, ContactsFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun isTablet(context: Context): Boolean {
        return context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    override fun contactClick(
        name: String,
        surname: String,
        phoneNumber: String,
        contactSubsequence: Int,
    ) {
        if (isTablet(this)) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.tablet_container_contact_details,
                    ContactsDetailedFragment.newInstance(name,
                        surname,
                        phoneNumber,
                        contactSubsequence))
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,
                    ContactsDetailedFragment.newInstance(name,
                        surname,
                        phoneNumber,
                        contactSubsequence))
                .commit()
        }
    }

    override fun onSaveContactChangedData() {
        if (isTablet(this)){
            supportFragmentManager.beginTransaction()
                .replace(R.id.tablet_container_contacts_list, ContactsFragment.newInstance())
                .commit()
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}