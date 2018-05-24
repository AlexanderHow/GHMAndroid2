package com.td.fr.unice.polytech.ghmandroid.NF.Helpers;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import com.td.fr.unice.polytech.ghmandroid.NF.Contact;
import com.td.fr.unice.polytech.ghmandroid.NF.ContextAndRoleHolder;
import com.td.fr.unice.polytech.ghmandroid.NF.HolderMatchingUser;
import com.td.fr.unice.polytech.ghmandroid.NF.Repository.UserRepository;

import java.util.ArrayList;

public class MessageSender extends AsyncTask<ContextAndRoleHolder, Void, ArrayList<Contact>> {
    private String textMessage;

    @Override
    protected ArrayList<Contact> doInBackground(ContextAndRoleHolder... params) {
        this.textMessage=params[0].getTextMessage();
        ArrayList<String> matchingNames = HolderMatchingUser.getInstance().getMatchingNames();
        return this.getPhoneContacts(params[0].getContextFromHolder(), matchingNames);
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> res) {
        Log.d("MESSENDER", "onPost Name: Hello there");
        SmsManager smsManager = SmsManager.getDefault();
        for( Contact c : res){
            //smsManager.sendMultimediaMessage(); with image
            String toSend = this.textMessage + "\n To " + c.getName();
            Log.d("MESSENDER", "onPost Finally: Hello "+toSend +"____" + c.getPhoneNo());
            ArrayList<String> parts = smsManager.divideMessage(toSend);
            smsManager.sendMultipartTextMessage(c.getPhoneNo(),null,parts,null,null);
        }
    }

    private ArrayList<Contact> getPhoneContacts(Context context, ArrayList<String> namesOfRole) {
        Log.d("MESSENDER", "getPhoneContact: Hello there");
        ArrayList<Contact> contactList = new ArrayList<Contact>();

        String phoneNumber = null;
        String name = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Iterate every contact in the phone
        if (cursor!=null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                Log.d("MESSENDER", "getPhoneContact Name: Hello "+name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    if (phoneCursor != null) {
                        phoneCursor.moveToNext();
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        Log.d("MESSENDER", "getPhoneContact Name: Hello "+phoneNumber);
                        phoneCursor.close();
                    }
                }

                if(name!=null && phoneNumber!=null){
                    Contact contactToAdd = new Contact(name,phoneNumber);
                    if(contactToAdd.matchWithNames(namesOfRole)){
                        contactList.add(contactToAdd);
                    }
                }

                name=null;
                phoneNumber=null;
            }
            cursor.close();
        }

        return contactList;
    }
}
