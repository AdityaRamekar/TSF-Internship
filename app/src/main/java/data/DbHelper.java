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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Contact;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Banking.db";

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
                + DatabaseContract.DatabaseEntry.PHONENO + " INTEGER NOT NULL, "
                + DatabaseContract.DatabaseEntry.BALANCE + " INTEGER NOT NULL);";


        String SQL_CREATE_ENTRIES2 =  "CREATE TABLE " + DatabaseContract.DatabaseEntry.TRANSFERS_TABLE_NAME + " ("
                + DatabaseContract.DatabaseEntry.TRANSFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseContract.DatabaseEntry.TRANSACTION_DATE + " DATE, "
                + DatabaseContract.DatabaseEntry.RECEIVER + " TEXT, "
                + DatabaseContract.DatabaseEntry.SENDER + " TEXT, "
                + DatabaseContract.DatabaseEntry.AMOUNTSENT + " TEXT);";

        String SQL_CREATE_ENTRIES3 = "CREATE TABLE " + DatabaseContract.DatabaseEntry.CONTACT_TABLE_NAME + " ("
                +DatabaseContract.DatabaseEntry.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +DatabaseContract.DatabaseEntry.KEY_NAME + " TEXT NOT NULL, "
                +DatabaseContract.DatabaseEntry.KEY_PHONE + " TEXT NOT NULL); ";

        String SQL_CREATE_ENTRIES4 = "CREATE TABLE " + DatabaseContract.DatabaseEntry.USER_CREDENTIALS_TABLE_NAME + " ("
                +DatabaseContract.DatabaseEntry.ACCOUNT_NO + " TEXT NOT NULL PRIMARY KEY, "
                +DatabaseContract.DatabaseEntry.PASSWORD + " TEXT NOT NULL); ";

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES3);
        db.execSQL(SQL_CREATE_ENTRIES4);


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
        Log.d("contact","Successfully inserted");
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
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

//    public int updateContact(Contact contact){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.DatabaseEntry.KEY_NAME, contact.getName());
//        values.put(DatabaseContract.DatabaseEntry.KEY_PHONE, contact.getPhoneNumber());
//
//        //Lets update now
//        return db.update(DatabaseContract.DatabaseEntry.CONTACT_TABLE_NAME, values, DatabaseContract.DatabaseEntry.KEY_ID + "=?",
//                new String[]{String.valueOf(contact.getId())});
//    }

    //---------above code for contact db------------
    public void addCredentials(Credentials credentials){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.ACCOUNT_NO, credentials.getAccountNo());
        values.put(DatabaseContract.DatabaseEntry.PASSWORD,credentials.getPassword());

        db.insert(DatabaseContract.DatabaseEntry.USER_CREDENTIALS_TABLE_NAME,null,values);
        Log.d("contact","Successfully inserted");
        db.close();
    }

    public String searchPass(String uname){
        SQLiteDatabase db =  this.getReadableDatabase();
        String query = "SELECT ACCOUNT_NO, PASSWORD FROM " + DatabaseContract.DatabaseEntry.USER_CREDENTIALS_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a,b;
        b = "not found";
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);

                if (a.equals(uname)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
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
        values.put(DatabaseContract.DatabaseEntry.RECEIVER, "SHIV");
        values.put(DatabaseContract.DatabaseEntry.SENDER, "ADITYA");
        values.put(DatabaseContract.DatabaseEntry.AMOUNTSENT, sendMoney.amount);

        db.insert(DatabaseContract.DatabaseEntry.TRANSFERS_TABLE_NAME,null,values);
        db.close();
}
    //----------------above code to put data in user_table--------------
    public String showBalance(){

        SQLiteDatabase db = this.getReadableDatabase();
        // Generate the query to read from the database
       // String select = "SELECT * FROM " + DatabaseContract.DatabaseEntry.USER_TABLE_NAME;
        String[] projection ={DatabaseContract.DatabaseEntry.USER_ID,DatabaseContract.DatabaseEntry.BALANCE };
        String selection = DatabaseContract.DatabaseEntry.USER_ID + "=1";
        Cursor cursor = db.query(DatabaseContract.DatabaseEntry.USER_TABLE_NAME,projection,selection,null,null,null,null);
       // Cursor cursor = db.rawQuery("SELECT Balance FROM " + DatabaseContract.DatabaseEntry.USER_TABLE_NAME + "WHERE _ID = 1", null);
        cursor.moveToFirst();
        int bal = cursor.getInt(1);
        String rupees = "\u20B9";
        try {
        return "Balance " + rupees + "\n" + bal;
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
    }


    //----------above code for currentbalance in usertable db------------
