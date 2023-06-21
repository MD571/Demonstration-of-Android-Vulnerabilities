package com.example.androidvulnerabilities

class DivaJni {
    // Jni function
    // Returns 1 if the key specified by user is valid, 0 otherwise
    external fun access(key: String?): Int
    external fun initiateLaunchSequence(code: String?): Int

    companion object {
        private const val soName = "divan"

        init {
            System.loadLibrary("divan")
        }
    }
}