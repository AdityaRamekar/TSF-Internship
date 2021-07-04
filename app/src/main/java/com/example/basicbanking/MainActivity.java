package com.example.basicbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.DbHelper;
import model.Contact;

public class MainActivity extends AppCompatActivity  {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Select Your Account");
        DbHelper db = new DbHelper(MainActivity.this);

        //Creating a contact for db
//1
        Contact aditya = new Contact();
        aditya.setPhoneNumber("7020902520");
        aditya.setName("Aditya");
//2
        Contact shiv = new Contact();
        shiv.setPhoneNumber("7232564587");
        shiv.setName("Shiv");
//3
        Contact ram = new Contact();
        ram.setPhoneNumber("7025455450");
        ram.setName("Ram");
//4
        Contact param = new Contact();
        param.setPhoneNumber("7988568879");
        param.setName("param");
//5
        Contact akash = new Contact();
        akash.setPhoneNumber("7054654654");
        akash.setName("Akash");
//6
        Contact indra = new Contact();
        indra.setPhoneNumber("7878898989");
        indra.setName("Indra");
//7
        Contact govind = new Contact();
        govind.setPhoneNumber("7089798989");
        govind.setName("Govind");
//8
        Contact krushna = new Contact();
        krushna.setPhoneNumber("7115154556");
        krushna.setName("Krushna");
//9
        Contact kanha = new Contact();
        kanha.setPhoneNumber("7065656565");
        kanha.setName("Kanha");
//10
        Contact manmohan = new Contact();
        manmohan.setPhoneNumber("7798615654");
        manmohan.setName("Manmohan");
        //Adding a contact for db
        db.addContact(aditya);
        db.addContact(shiv);
        db.addContact(ram);
        db.addContact(param);
        db.addContact(akash);
        db.addContact(indra);
        db.addContact(govind);
        db.addContact(krushna);
        db.addContact(kanha);
        db.addContact(manmohan);
//--------------------
        Credentials adityacredentials = new Credentials();
        adityacredentials.setAccountNo("1");
        adityacredentials.setPassword("1");
        db.addCredentials(adityacredentials);
//--------------------Transfer adityamoney in user_table
        MyAccount adityamoney = new MyAccount();
        adityamoney.set_id("1");
        adityamoney.setBalance("100");
        adityamoney.setEmail("adityadramekar@gmail.com");
        adityamoney.setFirstname("aditya");
        adityamoney.setLastname("ramekar");
        adityamoney.setPhoneno("7020902520");
        db.addAccountMoney(adityamoney);
//--------------------Transfer shivamoney in user_table
        MyAccount shivmoney = new MyAccount();
        shivmoney.set_id("2");
        shivmoney.setBalance("0");
        shivmoney.setEmail("shivnamah@gmail.com");
        shivmoney.setFirstname("shiv");
        shivmoney.setLastname("namah");
        shivmoney.setPhoneno("7232564587");
        db.addAccountMoney(shivmoney);
//---------------------------------------------
        ArrayList<String> contacts = new ArrayList<>();
        listView =  findViewById(R.id.listView);
        //contactlist view in transferMoney
        // Get all contacts
        List<Contact> allContacts = db.getAllContacts();

         for(Contact contact: allContacts){

            contacts.add(contact.getName() + " \n(" + contact.getPhoneNumber() + ")");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (position == 0) {
                    Intent intent = new Intent(view.getContext(), Credentials.class);
                //    String message = "Select your Bank";
                 //   intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
            }
        });

    }

}