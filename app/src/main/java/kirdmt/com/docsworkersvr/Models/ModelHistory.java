package kirdmt.com.docsworkersvr.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.ModelHistoryCallback;
import kirdmt.com.docsworkersvr.CallBacks.ModelHousesCallback;
import kirdmt.com.docsworkersvr.Data.HistoryData;

public class ModelHistory {


    final static String fbValueTAG = "fbValueTAG";
    final static String fbValueTAGError = "fbValueTAGError";

    FirebaseDatabase database;
    DatabaseReference historyRef, housesRef;

    //static final List<String> Houses = new ArrayList<String>();
    static final List<HistoryData> historyData = new ArrayList<HistoryData>();

    public ModelHistory() {

        fireBaseInit();
    }

    public void getHistoryData(int houseIndex, final ModelHistoryCallback callback) {

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
                //Log.w(fbValueTAGError, "Failed to read value.", error.toException());
            }
        });
    }

    public void fireBaseInit() {


        // Write a message to the database
        database = FirebaseDatabase.getInstance();

      /*  if (fireBaseFirstStart) {
            database.setPersistenceEnabled(true);
            fireBaseFirstStart = false;
        }*/

    }


    //UNUSED
    public void getHouses(final ModelHousesCallback callback) {
     /*   // Read from the database

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
        });*/
    }
}


