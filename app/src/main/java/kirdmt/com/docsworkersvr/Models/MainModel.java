package kirdmt.com.docsworkersvr.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kirdmt.com.docsworkersvr.CallBacks.HousesCallback;

public class MainModel {

    static final String FIREBASE_AUTH_TAG = "fireBaseAuthTag";

    private FirebaseDatabase database;
    private DatabaseReference housesRef;

    private static boolean fireBaseFirstStart = true;

    public MainModel() {

    }

    public void getHousesList(final HousesCallback callback) {


        database = FirebaseDatabase.getInstance();

        if (fireBaseFirstStart) {
            database.setPersistenceEnabled(true);
            fireBaseFirstStart = false;
        }

        housesRef = database.getReference("houses");
        housesRef.keepSynced(true);
        housesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                callback.onCallBack(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(FIREBASE_AUTH_TAG, "Failed to read value.", error.toException());
            }

        });

        //  }
    }
}
