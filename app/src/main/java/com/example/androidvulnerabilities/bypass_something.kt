package com.example.androidvulnerabilities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidvulnerabilities.databinding.ActivityBypassSomethingBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi

class bypass_something : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks {
    companion object {
        const val PREFS_NAME = "MyPrefsFile"
        const val USERNAME_KEY = "username"
        const val LOGIN_TIME_KEY = "login_time"
        const val LOGIN_TIMEOUT = 10000 // 5 minutes in milliseconds 300000
    }
    private var sitekey="6Lfsu54lAAAAAGIUnrbnnoKUBBQS-8jToPEhue_W"
    private lateinit var googleapiclinet:GoogleApiClient
    private var check=0
    private lateinit var binding: ActivityBypassSomethingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBypassSomethingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=getIntent()
        val SL=intent.getStringExtra("SL").toString()
        if(SL=="high")
            binding.securityLevel.text="Security Level = High"
        else
            binding.securityLevel.text="Security Level = Low"
        binding.cb.setTextColor(Color.BLACK)
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        if(SL!="low")binding.captcha.isVisible=true
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if(SL=="low") {
                if (isValidCredentialsl(username, password)) {
                    // Redirect the user to the home screen
                    startHomeActivity()
                } else {
                    // Display an error message
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                // Perform login

                if (isValidCredentialsh(username, password)) {
                    // Save the username and login time to shared preferences
                    /*val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(USERNAME_KEY, username)
                    editor.putLong(LOGIN_TIME_KEY, System.currentTimeMillis())
                    editor.apply()*/

                    // Enable the View Data button
                    binding.viewd.isVisible = true
                    binding.viewd.isEnabled = true
                } else {
                    // Display an error message if the credentials are invalid
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
        googleapiclinet= GoogleApiClient.Builder(this)
            .addApi(SafetyNet.API)
            .addConnectionCallbacks(this@bypass_something)
            .build()
        googleapiclinet.connect()
        binding.cb.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(binding.cb.isChecked){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleapiclinet,sitekey)
                        .setResultCallback(object:ResultCallback<SafetyNetApi.RecaptchaTokenResult>{
                            override fun onResult(p0: SafetyNetApi.RecaptchaTokenResult) {
                                val status=p0.status
                                if((status!=null)&&status.isSuccess){
                                    //Display Succeful Message
                                    //Toast.makeText(this,"Successfully Verified...",Toast.LENGTH_LONG).show()
                                    check=1
                                    binding.cb.setPaintFlags(binding.cb.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                                    binding.cb.setTextColor(Color.GREEN)
                                }
                            }

                        })
                }
                else{
                    binding.cb.setTextColor(Color.BLACK)
                }
            }

        })
        binding.viewd.setOnClickListener {
            // Check if the user is logged in and their session has not expired
            if (check==1) {
                // Start the ViewDataActivity if the session is valid
                startHomeActivity()
            } else {
                // Redirect the user to the login screen if the session has expired
                Toast.makeText(this, "Check the recaptcha and Try again.", Toast.LENGTH_SHORT).show()

            }
        }

        /*
        binding.login.setOnClickListener {
            val clazz: String = binding.search1.text.toString()
            if(SL=="low") {
                val r = Runtime.getRuntime()
                val process = r.exec(clazz)
                val sb = StringBuilder()
                var line: String?

                val br = BufferedReader(InputStreamReader(process.inputStream))
                line = br.readLine()

                while (line != null) {
                    sb.append(line)
                    line = br.readLine()
                }
                br.close()
                binding.viewtext.text = sb.toString()
            }
            else{
                binding.viewtext.text=binding.search1.text.toString()
            }
            //Toast.makeText(this,process.errorStream.toString(),Toast.LENGTH_LONG).show()
        }
    //file:///data/user/0/com.example.androidvulnerabilities/uinfo855253251507726730tmp*/
    }
    private fun isValidCredentialsh(username: String, password: String): Boolean {
        // Check if username and password match
        // In production, this should be done securely on a server
        return username == "admin" && password == "password"
    }
    private fun isValidCredentialsl(username: String, password: String): Boolean {
        // Insecure implementation of authentication
        return true
    }
    private fun isSessionValid(): Boolean {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // Check if the username and login time are stored in shared preferences
        if (!sharedPreferences.contains(USERNAME_KEY) || !sharedPreferences.contains(LOGIN_TIME_KEY)) {
            return false
        }
        val username = sharedPreferences.getString(USERNAME_KEY, null)
        val loginTime = sharedPreferences.getLong(LOGIN_TIME_KEY, 0)
        // Check if the session has expired
        if (System.currentTimeMillis() - loginTime > LOGIN_TIMEOUT) {
            // Clear the stored username and login time from shared preferences
            val editor = sharedPreferences.edit()
            editor.remove(USERNAME_KEY)
            editor.remove(LOGIN_TIME_KEY)
            editor.apply()
            return false
        }
        return true
    }
    private fun startHomeActivity() {
        val i=Intent(this,denial_of_service::class.java)
        i.putExtra("db","Welcome to Dashbord")
        startActivity(i)
        finish()
    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }


}