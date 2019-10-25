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
import kirdmt.com.docsworkersvr.Data.HistoryData;

public class ModelHistory {


    final static String fbValueTAG = "fbValueTAG";
    final static String fbValueTAGError = "fbValueTAGError";

    FirebaseDatabase database;
    DatabaseReference historyRef;

    static final List<HistoryData> historyData = new ArrayList<HistoryData>();

    public ModelHistory() {

        fireBaseInit();
    }

    public void getHistoryData(int houseIndex, final ModelHistoryCallback callback) {

        // Read from the database
        historyRef = database.getReference("history/house" + houseIndex);
        //myRef.keepSynced(true);


        historyRef.orderByChild("number").limitToLast(70).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()) {
                    callback.onCallBack(null);
                } else {

                    for (DataSnapshot snap : dataSnapshot.getChildren()) {

                        callback.onCallBack(dataSnapshot);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

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

}


