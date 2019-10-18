package kirdmt.com.docsworkersvr.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kirdmt.com.docsworkersvr.CallBacks.ModelHistoryCallback;
import kirdmt.com.docsworkersvr.CallBacks.ModelHousesCallback;


//UNUSED!
public class FireBaseWorker {

    final static String fbValueTAG = "fbValueTAG";
    final static String fbValueTAGError = "fbValueTAGError";

    FirebaseDatabase database;
    DatabaseReference historyRef, housesRef;


    public FireBaseWorker()
    {
        fireBaseInit();
    }

    public void fireBaseInit() {


        // Write a message to the database
        database = FirebaseDatabase.getInstance();

      /*  if (fireBaseFirstStart) {
            database.setPersistenceEnabled(true);
            fireBaseFirstStart = false;
        }*/

    }

    public void getHouses(final ModelHousesCallback callback) {
        // Read from the database

        housesRef = database.getReference("houses");
//        myRef.keepSynced(true);
        housesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                callback.onCallBack(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(fbValueTAGError, "Failed to read value.", error.toException());
            }
        });
    }

    public void getHistory(int houseIndex, final ModelHistoryCallback callback) {

        // Read from the database
        historyRef = database.getReference("history/house" + houseIndex);
        //myRef.keepSynced(true);

        historyRef.orderByChild("number").limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Log.d(fbValueTAG, "Im here. 1");

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    callback.onCallBack(dataSnapshot);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(fbValueTAGError, "Failed to read value.", error.toException());
            }
        });
    }

}

