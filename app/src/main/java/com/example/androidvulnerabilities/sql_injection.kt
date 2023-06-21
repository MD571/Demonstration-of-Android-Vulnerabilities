package com.example.androidvulnerabilities

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidvulnerabilities.databinding.ActivitySqlInjectionBinding

class sql_injection : AppCompatActivity() {
    private lateinit var binding: ActivitySqlInjectionBinding
    private lateinit var mDB: SQLiteDatabase
    @SuppressLint("Recycle", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySqlInjectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=getIntent()
        val SL=intent.getStringExtra("SL").toString()
        if(SL=="high")
            binding.securityLevel.text="Security Level = High"
        else
            binding.securityLevel.text="Security Level = Low"
        try {
            mDB = openOrCreateDatabase("sqli", MODE_PRIVATE, null)
            mDB.execSQL("DROP TABLE IF EXISTS sqliuser;")
            mDB.execSQL("CREATE TABLE IF NOT EXISTS sqliuser(id INTEGER,user VARCHAR, password VARCHAR, credit_card VARCHAR);")
            mDB.execSQL("INSERT INTO sqliuser VALUES (1,'admin', 'passwd123', '1234567812345678');")
            mDB.execSQL("INSERT INTO sqliuser VALUES (2,'vinay', 'p@ssword', '1111222233334444');")
            mDB.execSQL("INSERT INTO sqliuser VALUES (3,'hemanth', 'password123', '5555666677778888');")

        }
        catch(e:Exception) {
            Log.d("Diva-sqli", "Error occurred while creating database for SQLI: " + e.message)
        }
        binding.sear.setOnClickListener{
            val cr: Cursor
            try {
                var id=binding.search1.text.toString()
                if(SL=="high") {
                    id= id.toIntOrNull().toString()
                    //val id=binding.search1.text.toString()
                    //Toast.makeText(this,cr.getString(0).toString(),Toast.LENGTH_LONG).show()
                }
                cr = mDB.rawQuery(
                    "SELECT * FROM sqliuser WHERE id='" + id + "'",
                    null
                )
                    val strb = StringBuilder("")
                if (cr != null && cr.count > 0) {
                    cr.moveToFirst()
                    do {
                        strb.append("""User: (${cr.getString(1)}) pass: (${cr.getString(2)}) Credit card: (${cr.getString(3)})""".trimIndent())
                    } while (cr.moveToNext())
                } else {
                    strb.append("User: (" + binding.search1.text.toString() + ") not found")
                }
                //Toast.makeText(this, strb.toString(), Toast.LENGTH_SHORT).show()
                binding.viewtext.text=strb.toString()
            }
            catch(e:Exception) {
                Log.d("V-sql", "Error occurred while searching in database: " + e.message)
            }
        }
    }
}