package com.domain.test.contactmalapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.domain.test.contactmalapplication.diffpack.ContactDetailProvider;
import com.domain.test.contactmalapplication.models.ContactDetail;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ContactRecyclerViewAdapter adapter;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    private ArrayList<ContactDetail> contactDetailsList= new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        askForPermission();
        recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(contactDetailsList.size()>0 ) {
            // set up the RecyclerView
            adapter = new ContactRecyclerViewAdapter(this, contactDetailsList);
            recyclerView.setAdapter(adapter);
            boolean firstTime = Preferences.getBooleanPreference(SecondActivity.this, "firstTime");
            if(!firstTime){
                for(int i=0;i<contactDetailsList.size();i++) {
                    ContentValues values = new ContentValues();
                    values.put(ContactDetailProvider.NAME,contactDetailsList.get(i).getContactName());
                    values.put(ContactDetailProvider.NUMBER,contactDetailsList.get(i).getContactNum());

                    Uri uri = getContentResolver().insert(
                            ContactDetailProvider.CONTENT_URI, values);
                    Toast.makeText(getBaseContext(),
                            uri.toString(), Toast.LENGTH_LONG).show();
                    Preferences.setBooleanPreference(SecondActivity.this, "firstTime",true);
                }
            }
        }
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(SecondActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SecondActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                ActivityCompat.requestPermissions(SecondActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(SecondActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            getContactList();
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(getApplicationContext(),"please provide contact permission to use this app ", Toast.LENGTH_LONG).show();
                    askForPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void getContactList() {
        try{
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            ContactDetail contactDetail= new ContactDetail();
                            contactDetail.setContactName(name);
                            contactDetail.setContactNum(phoneNo);
                            contactDetailsList.add(contactDetail);
                        }
                        pCur.close();
                    }
                }
            }
            if(cur!=null){
                cur.close();
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Facing issue while accessing contacts", Toast.LENGTH_LONG).show();
        }
    }

}
