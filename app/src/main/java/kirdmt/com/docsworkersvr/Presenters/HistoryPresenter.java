package kirdmt.com.docsworkersvr.Presenters;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.ModelHistoryCallback;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.Models.ModelHistory;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.contractView.ContractViewHistory;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;

public class HistoryPresenter {

    static final String PresenterTAG = "PresenterTAG";

    ContractViewHistory historyView;
    ModelHistory historyModel;
    Context historyContext;

    String authorHystoryPresenter;
    static final List<HistoryData> historyData = new ArrayList<HistoryData>();

    public HistoryPresenter(ModelHistory model, ContractViewHistory view, Context context) {

        historyModel = model;
        historyView = view;
        historyContext = context;

    }

    public void getHistoryData(int index, String author) {

        authorHystoryPresenter = author;

        if (!ConnectivityHelper.isConnectedToNetwork(historyContext)) {
            historyView.showToast(historyContext.getString(R.string.no_internet_access));
            historyView.closeHistoryActivity();

        } else if (index != 0) {

            index--;

            historyModel.getHistoryData(index, new ModelHistoryCallback() {
                @Override
                public void onCallBack(DataSnapshot snapshot) {

                    historyData.clear();

                    if(snapshot == null)
                    {
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

}
