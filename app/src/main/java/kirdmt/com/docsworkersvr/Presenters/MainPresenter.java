package kirdmt.com.docsworkersvr.Presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.HousesCallback;
import kirdmt.com.docsworkersvr.ExcelData;
import kirdmt.com.docsworkersvr.Interfaces.MainContract;
import kirdmt.com.docsworkersvr.Models.MainModel;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.contractView.ContractView;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;

public class MainPresenter {

    private MainContract.View view;
    private MainModel model;
    private Context context;

    private static List<String> housesList;

    public MainPresenter(MainContract.View mainActivity) {

        this.view = mainActivity;
        context = view.getContext();

        model = new MainModel();

        provideDataFromModel();

    }

    private void provideDataFromModel() {

        if (ConnectivityHelper.isConnectedToNetwork(context)) {

            view.progressDialogStart();
        }

        model.getHousesList(new HousesCallback() {
            @Override
            public void onCallBack(DataSnapshot snapshot) {

                housesList = new ArrayList<String>();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    housesList.add(snap.child("name").getValue().toString());

                    Log.w("FBTAG", "Value is: " + snap.child("name").getValue().toString());

                }

                view.setHousesList(housesList);

                try {

                    view.progressDialogCancel();

                } catch (Exception e) {
                    Log.d("progressDialogTagExcept", "Exception is: " + e.getMessage());
                }


            }
        });


    }
}
