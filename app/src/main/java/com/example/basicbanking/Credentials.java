package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import data.DbHelper;

public class Credentials extends AppCompatActivity {
    private String accountNo;
    private String password;

    public Credentials(String accountNo, String  password) {
        this.accountNo = accountNo;
        this.password = password;
    }

    public Credentials() {
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        this.setTitle("Enter Your Credentials");
    }

    public void onButtonClick(View v) {
        DbHelper db = new DbHelper(Credentials.this);
        if (v.getId() == R.id.btnSign) {
            EditText a = findViewById(R.id.txtusername);
            String str = a.getText().toString();
            EditText b = findViewById(R.id.txtpassword);
            String pass = b.getText().toString();

            String password = db.searchPass(str);
            if (pass.equals(password)) {
                Intent i = new Intent(Credentials.this, MyAccount.class);
                startActivity(i);
            }
            else{
                Toast.makeText(this,"wrong password",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
