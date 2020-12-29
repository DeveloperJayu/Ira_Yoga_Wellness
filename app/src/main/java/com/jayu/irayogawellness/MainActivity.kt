package com.jayu.irayogawellness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnLogIn : Button
    private lateinit var lblRegisterHere : TextView
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogIn = findViewById(R.id.btnLogIn)
        lblRegisterHere = findViewById(R.id.lblRegisterHere)
        mAuth = FirebaseAuth.getInstance()

        btnLogIn.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (email.isEmpty()){
                txtEmail.error = "Please Enter An Email"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                txtEmail.error = "Please Enter Valid Email"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            else if(password.isEmpty()){
                txtPassword.error = "Please Enter Password"
                txtPassword.requestFocus()
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "User Logged In Successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    task.exception?.message.let{error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lblRegisterHere.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}