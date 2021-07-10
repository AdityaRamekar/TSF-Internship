package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.nothing,R.anim.nothing);
    }
    public void onClick(View v) {
        amount = enteramount.getText().toString();
        String pseudobal = db.showBalance();
        String a[] = pseudobal.split("\n", 2);
        int bal = Integer.parseInt(a[1]);
        int amt = Integer.parseInt(amount);
        try {
            if (amount.equals("0")) {
                Toast.makeText(SendMoney.this,"Enter minimum ₹1",Toast.LENGTH_SHORT).show();
            } else {
                if (bal >= amt) {
                    db.updateBalance(SendMoney.this);
                    Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
                    openactivity_contents();
                } else {
                    Toast.makeText(this, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception e) { e.getMessage();}
    }
    public void openactivity_contents(){
        Intent intent = new Intent(this,successful.class);
        startActivity(intent);
        finish();
    }

}