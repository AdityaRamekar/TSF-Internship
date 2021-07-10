package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.DbHelper;
import model.Contact;

public class MyAccount extends AppCompatActivity {

    public  String _id ;
    public  String firstname ;
    public  String lastname ;
    public  String email ;
    public  String phoneno ;
    public  String balance ;
    static public String myaccountuser; //to put in select query , to fetch users name e.g select user from user_table;
    static public String myaccountphno;
    static public String splitfirstname;
    static String myaccountarray[];

    public MyAccount(){
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    DbHelper db = new DbHelper(MyAccount.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        this.setTitle(MainActivity.username + "'s Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.nothing,R.anim.nothing);

    }
    @Override
    public void onResume(){
        super.onResume();
        TextView showbalance = findViewById(R.id.showbal);
        TextView history = findViewById(R.id.history);
        ListView contactlist = findViewById(R.id.contactlist);
        showbalance.setText("" + db.showBalance() + "/-");

        //----show contact to transfer money
        ArrayList<String> contacts = new ArrayList<>();
        List<Contact> allContacts = db.getAllContacts();

        for (Contact contact : allContacts) {
            if (contact.getPhoneNumber().equals(MainActivity.phno)) {
            } else
                contacts.add(contact.getName() + " \n(" + contact.getPhoneNumber() + ")");

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactlist.setAdapter(arrayAdapter);
        //----
        contactlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent appInfo = new Intent(MyAccount.this, SendMoney.class);
                myaccountuser = (String) (contactlist.getItemAtPosition(position));
                myaccountarray = myaccountuser.split("\n",2);
                splitfirstname = myaccountarray[0];
                myaccountphno = myaccountarray[1];
                Log.v("in listview","user click on"+ myaccountphno);
                startActivity(appInfo);
            }
        });
    }
    public void intentHistory(View view) {
        Intent intent = new Intent(MyAccount.this, History.class);
        startActivity(intent);
    }
}