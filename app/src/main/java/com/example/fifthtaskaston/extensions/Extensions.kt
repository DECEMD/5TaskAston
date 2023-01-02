package com.example.fifthtaskaston.extensions

import android.view.View

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : View> View.find(idRes: Int) = unsafeLazy<T> { findViewById(idRes) }

