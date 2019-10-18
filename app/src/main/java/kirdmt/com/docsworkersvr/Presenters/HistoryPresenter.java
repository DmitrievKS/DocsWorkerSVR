package kirdmt.com.docsworkersvr.Presenters;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.ModelHistoryCallback;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.Models.ModelHistory;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.contractView.ContractViewHistory;

public class HistoryPresenter {

    static final String PresenterTAG = "PresenterTAG";

    //static final List<String> Houses = new ArrayList<String>();

    ContractViewHistory historyView;
    ModelHistory historyModel;
    Context historyContext;

    static final List<HistoryData> historyData = new ArrayList<HistoryData>();

    public HistoryPresenter(ModelHistory model, ContractViewHistory view, Context context) {

        historyModel = model;
        historyView = view;
        historyContext = context;

    }

    public void getHistoryData(int index) {

        if (!ConnectivityHelper.isConnectedToNetwork(historyContext)) {
            historyView.showToast(historyContext.getString(R.string.no_internet_access));
            historyView.closeHistoryActivity();

        } else if (index != 0) {
            index--;

            historyModel.getHistoryData(index, new ModelHistoryCallback() {
                @Override
                public void onCallBack(DataSnapshot snapshot) {

                    historyData.clear();

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        historyData.add(new HistoryData(snap.child("resident").getValue().toString(),
                                snap.child("house").getValue().toString(),
                                snap.child("author").getValue().toString(),
                                snap.child("need").getValue().toString(),
                                snap.child("date").getValue().toString(),
                                snap.child("method").getValue().toString()
                        ));
                        //snap.child("number").getValue()));

                    }
                    historyView.fillRecycler(historyData);

                }
            });

        } else {
            historyData.clear();
            historyView.fillRecycler(historyData);
        }
    }


    private void getHouses()
    {
         /*  if(ConnectivityHelper.isConnectedToNetwork(context)) {

            historyModel.fireBaseInit();

            historyModel.getHouses(new ModelHousesCallback() {
                @Override
                public void onCallBack(DataSnapshot snapshot) {

                    Houses.clear();

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        Houses.add(snap.child("name").getValue().toString());

                    }

                    historyView.createHousesSpinner(Houses);

                }

            });
        } else
        {
            historyView.showToast(context.getString(R.string.no_internet_access));
            historyView.closeHistoryActivity();
        }*/
    }

}
