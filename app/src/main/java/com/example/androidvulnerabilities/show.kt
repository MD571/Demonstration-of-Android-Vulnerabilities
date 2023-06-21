package com.example.androidvulnerabilities

import android.util.Log
import android.widget.Toast


class show {
    init {
        Log.d(show::class.java.name, "MyClass: constructor called.")
    }

    fun doSomething() {
        Log.d(show::class.java.name, "MyClass: doSomething() called.")
    }
}