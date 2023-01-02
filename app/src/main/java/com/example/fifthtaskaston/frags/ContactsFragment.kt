package com.example.fifthtaskaston.frags

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fifthtaskaston.databinding.FragmentContactsBinding
import com.example.fifthtaskaston.model.DataBase.listOfPersons
import com.example.fifthtaskaston.utils.Constants.FIRST_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.model.Person
import com.example.fifthtaskaston.utils.Constants.SECOND_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.utils.Constants.THIRD_CONTACT_SUBSEQUENCE
import com.example.fifthtaskaston.utils.OnCertainContactClickable

class ContactsFragment : Fragment() {
    private var listener: OnCertainContactClickable? = null
    private lateinit var binding: FragmentContactsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCertainContactClickable) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillContacts(listOfPersons)
        setUpListeners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun fillContacts(dataFromDb: MutableMap<Int,Person>) = with(binding){
        for (i in dataFromDb.keys){
            val tempListOfCertainContact = dataFromDb[i]
            when(i){
                FIRST_CONTACT_SUBSEQUENCE -> {
                    nameFirst.text = tempListOfCertainContact?.name
                    surnameFirst.text = tempListOfCertainContact?.surname
                    phoneFirst.text = tempListOfCertainContact?.phoneNumber
                }
                SECOND_CONTACT_SUBSEQUENCE -> {
                    nameSecond.text = tempListOfCertainContact?.name
                    surnameSecond.text = tempListOfCertainContact?.surname
                    phoneSecond.text = tempListOfCertainContact?.phoneNumber
                }
                THIRD_CONTACT_SUBSEQUENCE -> {
                    nameThird.text = tempListOfCertainContact?.name
                    surnameThird.text = tempListOfCertainContact?.surname
                    phoneThird.text = tempListOfCertainContact?.phoneNumber
                }
            }
        }
    }

    private fun setUpListeners() = with(binding) {
        val (name1, surname1, phoneNumber1) = listOfPersons.values.first()
        val (name2, surname2, phoneNumber2) = listOfPersons.values.elementAt(1)
        val (name3, surname3, phoneNumber3) = listOfPersons.values.last()

        firstContact.setOnClickListener {
            listener?.contactClick(name1, surname1, phoneNumber1, FIRST_CONTACT_SUBSEQUENCE)
        }
        secondContact.setOnClickListener {
            listener?.contactClick(name2, surname2, phoneNumber2, SECOND_CONTACT_SUBSEQUENCE)
        }
        thirdContact.setOnClickListener {
            listener?.contactClick(name3, surname3, phoneNumber3, THIRD_CONTACT_SUBSEQUENCE)
        }
    }

    companion object {
        fun newInstance() = ContactsFragment()
    }
}