package kirdmt.com.docsworkersvr.ui.history;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.Callback.FBSnapshotCallback;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.firebase.FireBaseWorker;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;

public class HistoryPresenter implements HistoryContract.Presenter {

    static final String PresenterTAG = "PresenterTAG";

    HistoryContract.View historyView;
    FireBaseWorker fireBaseWorker;
    Context historyContext;

    String authorHystoryPresenter;
    static final List<HistoryData> historyData = new ArrayList<HistoryData>();

    public HistoryPresenter(HistoryContract.View view, Context context) {

        historyView = view;
        historyContext = context;

    }

    public void getHistoryData(int housePosition, String houseName, String author) {

        authorHystoryPresenter = author;

        if (fireBaseWorker == null) {
            fireBaseWorker = new FireBaseWorker();
        }

        if (!ConnectivityHelper.isConnectedToNetwork(historyContext)) {
            historyView.showToast(R.string.no_internet_access);
            historyView.closeHistoryActivity();

        } else if (housePosition != 0) {

            fireBaseWorker.getHistoryData(houseName, new FBSnapshotCallback() {
                @Override
                public void onCallBack(DataSnapshot snapshot) {

                    historyData.clear();

                    if (snapshot == null) {
                        historyView.fillRecycler(historyData);
                        return;
                    }

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        if (authorHystoryPresenter.length() == 0) {

                            historyData.add(new HistoryData(snap.child("resident").getValue().toString(),
                                    snap.child("house").getValue().toString(),
                                    snap.child("author").getValue().toString(),
                                    snap.child("need").getValue().toString(),
                                    snap.child("date").getValue().toString(),
                                    snap.child("method").getValue().toString()
                            ));

                        } else if (authorHystoryPresenter.length() > 0 && snap.child("author").getValue().toString().equalsIgnoreCase(authorHystoryPresenter)) {

                            historyData.add(new HistoryData(snap.child("resident").getValue().toString(),
                                    snap.child("house").getValue().toString(),
                                    snap.child("author").getValue().toString(),
                                    snap.child("need").getValue().toString(),
                                    snap.child("date").getValue().toString(),
                                    snap.child("method").getValue().toString()
                            ));
                        }

                    }
                    historyView.fillRecycler(historyData);
                }
            });

        } else {
            historyData.clear();
            historyView.fillRecycler(historyData);
        }
    }

    @Override
    public void attachView(HistoryContract.View view) {

    }

    @Override
    public void detachView() {
        historyView = null;
    }

    @Override
    public void destroy() {

    }
}
