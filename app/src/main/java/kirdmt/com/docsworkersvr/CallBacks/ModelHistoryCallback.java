package kirdmt.com.docsworkersvr.CallBacks;

import com.google.firebase.database.DataSnapshot;

public interface ModelHistoryCallback {
    void onCallBack(DataSnapshot snapshot);
}