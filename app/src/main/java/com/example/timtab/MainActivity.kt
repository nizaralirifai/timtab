package com.example.timtab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        btnSave.setOnClickListener {
            savedata()
            val intent = Intent (this, show::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        val hari = inputHari.text.toString()
        val mapel1 = inputMapel1.text.toString()
        val mapel2 = inputMapel2.text.toString()
        val mapel3 = inputMapel3.text.toString()

        val userId = ref.push().key.toString()
        val user = Users(userId,hari,mapel1,mapel2,mapel3)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            inputHari.setText("")
            inputMapel1.setText("")
            inputMapel2.setText("")
            inputMapel3.setText("")
        }
    }
}