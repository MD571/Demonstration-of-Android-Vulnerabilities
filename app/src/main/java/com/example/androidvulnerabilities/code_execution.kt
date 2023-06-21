package com.example.androidvulnerabilities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.androidvulnerabilities.databinding.ActivityCodeExecutionBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class code_execution : AppCompatActivity() {
    private lateinit var binding: ActivityCodeExecutionBinding
    //var djni: DivaJni? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCodeExecutionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=getIntent()
        val SL=intent.getStringExtra("SL").toString()
        if(SL=="high")
            binding.securityLevel.text="Security Level = High"
        else
            binding.securityLevel.text="Security Level = Low"
        binding.sear.setOnClickListener {
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
        /*if(SL=="high")
            binding.securityLevel.text="Security Level = High"
        else
            binding.securityLevel.text="Security Level = Low"
        binding.show.setOnClickListener{
            val cr: Cursor
            try {
                var id=binding.search1.text.toString()
                if(SL=="high") {
                    id= id.toIntOrNull().toString()
                    //val id=binding.search1.text.toString()
                    //Toast.makeText(this,cr.getString(0).toString(),Toast.LENGTH_LONG).show()
                }
                val strb = StringBuilder("")
                strb.append(id)
                //strb.append("User: (" + binding.search1.text.toString() + ") not found")
                //Toast.makeText(this, strb.toString(), Toast.LENGTH_SHORT).show()
                binding.viewtext.text=strb.toString()
            }
            catch(e:Exception) {
                Log.d("V-sql", "Error occurred while searching in database: " + e.message)
            }
        }*/

            //djni = DivaJni()


    }



}