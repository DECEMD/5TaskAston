package com.example.fifthtaskaston.frags

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.fifthtaskaston.databinding.FragmentContactsDetailedBinding
import com.example.fifthtaskaston.model.Person
import com.example.fifthtaskaston.model.DataBase.listOfPersons
import com.example.fifthtaskaston.utils.Constants.FIRST_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.utils.Constants.SECOND_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.utils.Constants.THIRD_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.utils.OnContactsDataReturnable
import com.example.fifthtaskaston.utils.OnSaveButtonClickable

class ContactsDetailedFragment : Fragment(), OnContactsDataReturnable {
    private var listener: OnSaveButtonClickable? = null
    private lateinit var binding: FragmentContactsDetailedBinding
    private var person = Person()
    private var contactSubsequence: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSaveButtonClickable) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            person.name = it.getString(ARG_NAME).toString()
            person.surname = it.getString(ARG_SURNAME).toString()
            person.phoneNumber = it.getString(ARG_NUMBER).toString()
            contactSubsequence = it.getInt(CONTACT_SUBSEQUENCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsDetailedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillFields()
        setDataSaveButtonClickListener()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setDataSaveButtonClickListener() {
        binding.saveDateButton.setOnClickListener {
            restoreContactData(person)
            when (contactSubsequence) {
                FIRST_CONTACT_SUBSEQUENCE -> listOfPersons[1] =
                    Person(person.name, person.surname, person.phoneNumber)

                SECOND_CONTACT_SUBSEQUENCE -> listOfPersons[2] =
                    Person(person.name, person.surname, person.phoneNumber)

                THIRD_CONTACT_SUBSEQUENCE -> listOfPersons[3] =
                    Person(person.name, person.surname, person.phoneNumber)
            }
            softCloseKeyBoard(activity as Activity)
            listener?.onSaveContactChangedData()
        }
    }

    private fun fillFields() = with(binding) {
        editTextName.setText(person.name)
        editTextSurname.setText(person.surname)
        editTextPhone.setText(person.phoneNumber)
    }

    private fun softCloseKeyBoard(context: Activity) {
        val imm: InputMethodManager =
            context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun restoreContactData(person: Person) {
        person.name = binding.editTextName.text.toString()
        person.surname = binding.editTextSurname.text.toString()
        person.phoneNumber = binding.editTextPhone.text.toString()
    }

    companion object {
        fun newInstance(
            name: String,
            surname: String,
            phoneNumber: String,
            contactSubsequence: Int,
        ) = ContactsDetailedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_SURNAME, surname)
                    putString(ARG_NUMBER, phoneNumber)
                    putInt(CONTACT_SUBSEQUENCE, contactSubsequence)
                }
            }

        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_SURNAME = "ARG_SURNAME"
        private const val ARG_NUMBER = "ARG_NUMBER"
        private const val CONTACT_SUBSEQUENCE = "CONTACT_SUBSEQUENCE"
    }
}