package com.jayu.irayogawellness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var btnRegister : Button
    private lateinit var lblLoginHere : TextView
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnRegister)
        lblLoginHere = findViewById(R.id.lblLogInHere)
        mAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
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

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
                else{
                    task.exception?.message.let{error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lblLoginHere.setOnClickListener {
            onBackPressed()
        }

    }
}