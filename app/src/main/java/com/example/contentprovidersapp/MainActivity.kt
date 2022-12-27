package com.example.contentprovidersapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickAddDetails(view: View) {
        val values = ContentValues()
        values.put(
            InterfaceToContentProvider.name,
            (findViewById<View>(R.id.textName) as EditText).text.toString()
        )
        contentResolver.insert(InterfaceToContentProvider.CONTENT_URI, values)
        Toast.makeText(baseContext, "New Record Inserted", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("Range")
    fun onClickShowDetails(view: View) {
        val resultView = findViewById<View>(R.id.res) as TextView
        val cursor = contentResolver.query(
            Uri.parse(InterfaceToContentProvider.CONTENT_URI.toString()),
            null, null, null, null
        )

        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append(
                    """
      
    ${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}
    """.trimIndent()
                )
                cursor.moveToNext()
            }
            resultView.text = strBuild
        } else {
            resultView.text = "No Records Found"
        }
    }
}