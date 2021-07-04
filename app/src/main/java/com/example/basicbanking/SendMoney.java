package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DbHelper;

public class SendMoney extends AppCompatActivity {

    DbHelper db = new DbHelper(SendMoney.this);
    Button  proceed ;
    EditText enteramount ;
    public  String amount ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        this.setTitle("SEND MONEY");
        proceed = findViewById(R.id.proceed);
        enteramount = findViewById(R.id.enteramount);

    }
    public void onClick(View v){
        amount = enteramount.getText().toString();
        db.insertMoney(SendMoney.this);
        //Intent intent = new Intent(v.getContext(), R.drawable.successful);
        Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
        //startActivity(intent);
        //do here minus in bank balance
    }

}