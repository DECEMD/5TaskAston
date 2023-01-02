package com.example.fifthtaskaston.utils

import com.example.fifthtaskaston.model.Person

interface OnContactsDataReturnable {
    fun restoreContactData(person: Person)
}