package data;

import android.provider.BaseColumns;

public final class DatabaseContract {

    //constructor
    private DatabaseContract(){}

    public static final class DatabaseEntry implements BaseColumns {
        //name of column in Contact_Table
        public static final String KEY_ID = "contact_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_PHONE = "phone_number";

        //name of column in User_Table
        public static final String USER_ID = "user_id";
        public static final String FIRSTNAME = "FirstName";
        public static final String LASTNAME = "LastName";
        public static final String EMAIL = "Email";
        public static final String PHONENO = "PhoneNo";
        public static final String BALANCE = "Balance";

        //name of column in User_credentials
        public static final String ACCOUNT_NO = "Account_no";
        public static final String PASSWORD = "Password";

        //name of column in Transfers_Table
        public static final String TRANSFER_ID = "transfer_id";
        public static final String TRANSACTION_DATE = "Transaction_Date";
        public static final String SENDER = "Sender";
        public static final String RECEIVER = "Receiver";
        public static final String AMOUNTSENT = "AmountSent";

        //Name of Tables
        public static final String USER_CREDENTIALS_TABLE_NAME = "user_credentials";
        public static final String USER_TABLE_NAME = "User_Table";
        public static final String TRANSFERS_TABLE_NAME = "Transfers_Table";
        public static final String CONTACT_TABLE_NAME = "Contact";
    }

}
