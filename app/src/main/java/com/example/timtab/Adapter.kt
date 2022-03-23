package com.example.timtab

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class Adapter (val mCtx: Context, val layoutResId: Int, val list: List<Users> )
    : ArrayAdapter<Users>(mCtx,layoutResId,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textHari = view.findViewById<TextView>(R.id.textHari)
        val textMapel1 = view.findViewById<TextView>(R.id. textMapel1)
        val textMapel2 = view.findViewById<TextView>(R.id. textMapel2)
        val textMapel3 = view.findViewById<TextView>(R.id. textMapel3)

        val textUpdate = view.findViewById<TextView>(R.id.TextUpdate)
        val textDelete = view.findViewById<TextView>(R.id.TextDelete)


        val user = list[position]

        textHari.text = user.hari
        textMapel1.text = user.mapel1
        textMapel2.text = user.mapel2
        textMapel3.text = user.mapel3

        textUpdate.setOnClickListener {
            showUpdateDialog(user)
        }
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }

        return view

    }

    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, show::class.java)
        context.startActivity(intent)

    }

    private fun showUpdateDialog(user: Users) { val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textHari = view.findViewById<EditText>(R.id.inputHari)
        val textMapel1 = view.findViewById<EditText>(R.id.inputMapel1)
        val textMapel2 = view.findViewById<EditText>(R.id.inputMapel2)
        val textMapel3 = view.findViewById<EditText>(R.id.inputMapel3)

        textHari.setText(user.hari)
        textMapel1.setText(user.mapel1)
        textMapel2.setText(user.mapel2)
        textMapel3.setText(user.mapel3)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")

            val hari = textHari.text.toString().trim()

            val mapel1 = textMapel1.text.toString().trim()

            val mapel2 = textMapel2.text.toString().trim()

            val mapel3 = textMapel3.text.toString().trim()

            if (hari.isEmpty()){
                textHari.error = "please enter hari"
                textHari.requestFocus()
                return@setPositiveButton
            }

            if (mapel1.isEmpty()){
                textMapel1.error = "please enter mapel 1"
                textMapel1.requestFocus()
                return@setPositiveButton
            }

            if (mapel2.isEmpty()){
                textMapel2.error = "please enter mapel 2"
                textMapel2.requestFocus()
                return@setPositiveButton
            }

            if (mapel3.isEmpty()){
                textMapel3.error = "please enter mapel 3"
                textMapel3.requestFocus()
                return@setPositiveButton
            }

            val user = Users(user.id,hari,mapel1,mapel2,mapel3)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

    }