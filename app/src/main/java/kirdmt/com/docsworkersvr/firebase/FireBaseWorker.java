package kirdmt.com.docsworkersvr.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kirdmt.com.docsworkersvr.Data.ExcelData;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.Callback.FBSnapshotCallback;
import kirdmt.com.docsworkersvr.Callback.FBStringCallback;


final public class FireBaseWorker {

    static final String FIREBASE_TAG = "fireBaseTag";

    private FirebaseDatabase database;
    private DatabaseReference housesRef;

    private static boolean fireBaseFirstStart = true;

    public FireBaseWorker() {

        database = FirebaseDatabase.getInstance();
        //y setting this value to true, the data will be persisted to on-device (disk) storage and will thus be available again when the app is restarted
        //database.setPersistenceEnabled(true);
    }

    public void getNewHistoryElementIndex(final FBStringCallback callback) {

        DatabaseReference ref = database.getReference("history").child("logs");
        //ref = ref.child("house");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                callback.onCallBack(String.valueOf(dataSnapshot.getChildrenCount() + 1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getHistoryData(String houseName, final FBSnapshotCallback callback) {

        DatabaseReference historyRef = database.getReference("history/logs");
        //myRef.keepSynced(true);

        //historyRef.orderByChild("number").limitToLast(70).addValueEventListener(new ValueEventListener() {
        historyRef.orderByChild("house").equalTo(houseName).limitToLast(70).addValueEventListener(new ValueEventListener() {

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

    public void getHousesList(final FBSnapshotCallback callback) {

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
                Log.w(FIREBASE_TAG, "Failed to read value.", error.toException());
            }

        });
        // Failed to read value

        //  }
    }

    public void insertFireBaseData(final ExcelData excelData, final String dataDirection, int elementIndex, String date) {

        //Log.d(FIREBASE_TAG, "Data in worker is: " + excelData.getAdded());

        final DatabaseReference logsRef = database.getReference().child("history").child("logs");

        logsRef.child("historyElement" + elementIndex)
                .setValue(new HistoryData(excelData.getName(),
                        excelData.getAdded(),
                        excelData.getResponsible(),
                        excelData.getNeed(),
                        date,
                        dataDirection,
                        elementIndex));


    }



}

