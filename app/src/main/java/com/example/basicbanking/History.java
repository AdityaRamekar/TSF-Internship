package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import data.DatabaseContract;
import data.DbHelper;

public class History extends AppCompatActivity {

    DbHelper db = new DbHelper(History.this);
    String receiver , amtsent, tdate,sender,receivername,sendername;
    ListView TransactionList,TransactionList1;
    ArrayList<String> TransactionArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.nothing,R.anim.nothing);

            String rupees = "\u20B9";
            SQLiteDatabase db1 = db.getReadableDatabase();
            TransactionList =  findViewById(R.id.transactionlist);
            Cursor cursor = db1.rawQuery("SELECT Receiver,AmountSent,Transaction_Date,Sender,ReceiverName,SenderName FROM " + DatabaseContract.DatabaseEntry.TRANSFERS_TABLE_NAME
                    + " WHERE Sender = " + MainActivity.phno, null);

            if (cursor.moveToFirst()) {

                do {
                    receiver = cursor.getString(0);
                    amtsent = cursor.getString(1);
                    tdate = cursor.getString(2);
                    sender = cursor.getString(3);
                    receivername = cursor.getString(4);
                    sendername = cursor.getString(5);
                    ArrayAdapter<String> TransactionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TransactionArrayList);
                    TransactionArrayAdapter.add( rupees + amtsent +"/- sent to " + receivername + receiver + "\n on " + tdate + " IST");
                    //TransactionArrayAdapter1.add( rupees + amtsent +"/- received from " + sendername + sender + "\n on " + tdate + " IST");
                    TransactionList.setAdapter(TransactionArrayAdapter);
                }
                while (cursor.moveToNext());
            }

            try {
                Log.v("in History", "receiver is " + receiver);
                Log.v("in History", "AmountSent is" + amtsent);
                Log.v("in History", "Transaction_Date is" + tdate);
                Log.v("in History", "status is" + sender);

            } finally {
                cursor.close();
            }
        }
    }
