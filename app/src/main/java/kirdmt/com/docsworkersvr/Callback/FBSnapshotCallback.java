package kirdmt.com.docsworkersvr.Callback;

import com.google.firebase.database.DataSnapshot;

public interface FBSnapshotCallback {

    void onCallBack(DataSnapshot snapshot);
}
