package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

import androidx.annotation.Nullable;

import com.example.basicbanking.Credentials;
import com.example.basicbanking.MyAccount;
import com.example.basicbanking.SendMoney;
import com.example.basicbanking.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Contact;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Banking.db";
    String bal,pseudophno ;
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =  "CREATE TABLE " + DatabaseContract.DatabaseEntry.USER_TABLE_NAME + " ("
                + DatabaseContract.DatabaseEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.DatabaseEntry.FIRSTNAME + " TEXT NOT NULL, "
                + DatabaseContract.DatabaseEntry.LASTNAME + " TEXT NOT NULL, "
                + DatabaseContract.DatabaseEntry.EMAIL + " TEXT, "
                + DatabaseContract.DatabaseEntry.PHONENO + " TEXT NOT NULL, "
                + DatabaseContract.DatabaseEntry.BALANCE + " INTEGER NOT NULL);";

        String SQL_CREATE_ENTRIES2 =  "CREATE TABLE " + DatabaseContract.DatabaseEntry.TRANSFERS_TABLE_NAME + " ("
                + DatabaseContract.DatabaseEntry.TRANSFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.DatabaseEntry.TRANSACTION_DATE + " DATE, "
                + DatabaseContract.DatabaseEntry.RECEIVER + " TEXT, "
                + DatabaseContract.DatabaseEntry.SENDER + " TEXT, "
                + DatabaseContract.DatabaseEntry.AMOUNTSENT + " TEXT, "
                + DatabaseContract.DatabaseEntry.SENDERNAME + " INTEGER NOT NULL, "
                + DatabaseContract.DatabaseEntry.RECEIVERNAME + " INTEGER NOT NULL);";

        String SQL_CREATE_ENTRIES3 = "CREATE TABLE " + DatabaseContract.DatabaseEntry.CONTACT_TABLE_NAME + " ("
                +DatabaseContract.DatabaseEntry.KEY_NAME + " TEXT NOT NULL, "
                +DatabaseContract.DatabaseEntry.KEY_PHONE + " TEXT PRIMARY KEY NOT NULL, " +
                " UNIQUE (phone_number) ON CONFLICT REPLACE); ";

//        String SQL_CREATE_ENTRIES4 = "CREATE TABLE " + DatabaseContract.DatabaseEntry.USER_CREDENTIALS_TABLE_NAME + " ("
//                +DatabaseContract.DatabaseEntry.ACCOUNT_NO + " TEXT NOT NULL PRIMARY KEY, "
//                +DatabaseContract.DatabaseEntry.PASSWORD + " TEXT NOT NULL); ";

        String SQL_CREATE_ENTRIES5 =  "CREATE TABLE " + DatabaseContract.DatabaseEntry.MY_ACCOUNT_TABLE_NAME + " ("
                + DatabaseContract.DatabaseEntry.ACCOUNT_NO + " TEXT PRIMARY KEY, "
                + DatabaseContract.DatabaseEntry.PASSWORD + " TEXT NOT NULL, "
                + DatabaseContract.DatabaseEntry.PHONENO + " TEXT NOT NULL, "
                + DatabaseContract.DatabaseEntry.BALANCE + " INTEGER NOT NULL); ";
//
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
      //  db.execSQL(SQL_CREATE_ENTRIES4);
        db.execSQL(SQL_CREATE_ENTRIES5);
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341231','1111','7020902520',500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341232','2222','7232564587',1500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341233','3333','7025455450',1500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341234','4444','7988568879',1500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341235','5555','7054654654',500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341236','6666','7878898989',1500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341237','7777','7089798989',500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341238','8888','7115154556',1500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341239','9999','7065656565',500)");
        db.execSQL("INSERT or replace INTO My_account (Account_no, Password, PhoneNo, Balance) VALUES('1234123412341230','1000','7798615654',1500)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //---------------

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.KEY_NAME,contact.getName());
        values.put(DatabaseContract.DatabaseEntry.KEY_PHONE,contact.getPhoneNumber());
        db.insert(DatabaseContract.DatabaseEntry.CONTACT_TABLE_NAME,null,values);
        db.close();
    }

    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + DatabaseContract.DatabaseEntry.CONTACT_TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
          if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
               // contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(0));
                contact.setPhoneNumber(cursor.getString(1));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

    //---------above code for contact db------------
    public void addCredentials(Credentials credentials){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.ACCOUNT_NO, credentials.getAccountNo());
        values.put(DatabaseContract.DatabaseEntry.PASSWORD,credentials.getPassword());

        db.insert(DatabaseContract.DatabaseEntry.USER_CREDENTIALS_TABLE_NAME,null,values);
        db.close();
    }

    public String searchPass(String uname){
        SQLiteDatabase db =  this.getReadableDatabase();
        String query = "SELECT Account_no, Password, PhoneNo FROM " + DatabaseContract.DatabaseEntry.MY_ACCOUNT_TABLE_NAME + " WHERE PhoneNo = " + MainActivity.phno ;
        Cursor cursor = db.rawQuery(query,null);
        String a,b;
        b = "not found";
        if(cursor.moveToFirst()) {
            String c =cursor.getString(2);
            if (c.equals(MainActivity.phno)) {
                do {
                    a = cursor.getString(0);
                    if (a.equals(uname)) {//here do something to match valid phno and accno
                        b = cursor.getString(1);
                        break;
                    }
                }
                while (cursor.moveToNext());
            }
        }
        return b;
    }
    //----------above code for Credentials db-----------
    public void addAccountMoney(MyAccount myAccount){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.USER_ID, myAccount.get_id());
        values.put(DatabaseContract.DatabaseEntry.FIRSTNAME, myAccount.getFirstname());
        values.put(DatabaseContract.DatabaseEntry.LASTNAME, myAccount.getLastname());
        values.put(DatabaseContract.DatabaseEntry.EMAIL, myAccount.getEmail());
        values.put(DatabaseContract.DatabaseEntry.PHONENO, myAccount.getPhoneno());
        values.put(DatabaseContract.DatabaseEntry.BALANCE, myAccount.getBalance());

        db.insert(DatabaseContract.DatabaseEntry.USER_TABLE_NAME,null,values);
        db.close();
    }
//----------------above code to put data in user_table--------------
    public void insertMoney(SendMoney sendMoney){
        //here continue....
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.TRANSACTION_DATE, dateFormat.format(now));
        values.put(DatabaseContract.DatabaseEntry.RECEIVER, MyAccount.myaccountphno);
        values.put(DatabaseContract.DatabaseEntry.SENDER, MainActivity.phno);
        values.put(DatabaseContract.DatabaseEntry.AMOUNTSENT, sendMoney.amount);
        values.put(DatabaseContract.DatabaseEntry.SENDERNAME, MainActivity.username);
        values.put(DatabaseContract.DatabaseEntry.RECEIVERNAME, MyAccount.splitfirstname);

        db.insert(DatabaseContract.DatabaseEntry.TRANSFERS_TABLE_NAME,null,values);
        db.close();
}
    public void updateBalance(SendMoney sendMoney){
        SQLiteDatabase db = this.getWritableDatabase();
        String update1 = "UPDATE My_account SET Balance = Balance + "+sendMoney.amount+ " WHERE PhoneNo = " +MyAccount.myaccountphno;
        String update2 = "UPDATE My_account SET Balance = Balance - "+sendMoney.amount+" WHERE PhoneNo = " +MainActivity.phno;
        db.execSQL(update1);
        db.execSQL(update2);
        insertMoney(sendMoney);
    }
    //----------------above code to put data in user_table--------------
    public String showBalance(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT PhoneNo,Balance FROM " + DatabaseContract.DatabaseEntry.MY_ACCOUNT_TABLE_NAME
                + " WHERE PhoneNo = " + MainActivity.phno ,null);

        if(cursor.moveToFirst()){
                pseudophno = cursor.getString(0);
            do {
                    bal = cursor.getString(1);
            }
            while (cursor.moveToNext());
        }
        String rupees = "\u20B9";
        try {
         Log.v("in db","balance is "+ bal);
            Log.v("in db","pseudophno"+ pseudophno);
            Log.v("in listview","user click on"+ MainActivity.phno);
        return "Balance " + rupees + "\n" + bal;
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
    }


    //----------above code for currentbalance in usertable db------------