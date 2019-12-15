package kirdmt.com.docsworkersvr.ui.start;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.ExcelData;
import kirdmt.com.docsworkersvr.Callback.FBSnapshotCallback;
import kirdmt.com.docsworkersvr.Callback.FBStringCallback;
import kirdmt.com.docsworkersvr.firebase.FireBaseWorker;
import kirdmt.com.docsworkersvr.db.DbWorker;
import kirdmt.com.docsworkersvr.network.NetworkService;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;
import kirdmt.com.docsworkersvr.util.Constants;
import kirdmt.com.docsworkersvr.util.Support;

public final class StartPresenter implements StartContract.Presenter, NetworkService.NetworkServiceResponse {

    private static final String PRESENTER_TAG = "PresenterTAG";

    private StartContract.View view;
    private List<ExcelData> ExcelDataList;
    private Context context;
    private ArrayList<String> housesList;

    private NetworkService service;
    private DbWorker dbWorker;
    private FireBaseWorker fbWorker;

    StartPresenter(StartContract.View view) {

        attachView(view);
        this.context = this.view.getContext();

        if (ConnectivityHelper.isConnectedToNetwork(context)) {

            getHouseList();

        } else {

            this.view.startMainActivity();
        }

    }

    private void realizeStoredData() {

        try {

            //Log.d(PRESENTER_TAG, "ExcelDataList amount is: " + ExcelDataList.size());
            getSqlData();

            if (ExcelDataList == null) {

                view.startMainActivity();

            } else if (!ExcelDataList.isEmpty()) {

                sendServiceData((ArrayList<ExcelData>) ExcelDataList);
                sendFireBaseData(ExcelDataList);
                view.startMainActivity();
            }


        } catch (NullPointerException e) {
            Log.d(PRESENTER_TAG, "NullPointException is : " + e.getMessage());
        } catch (Exception e) {
            Log.d(PRESENTER_TAG, "Exception is : " + e.getMessage());
        }

    }

    private void sendServiceData(ArrayList<ExcelData> data) {

        service = new NetworkService(this);
        service.setData(data);
        service.execute();
    }

    private void getSqlData() {

        dbWorker = new DbWorker(context);
        dbWorker.DbInit();
        ExcelDataList = dbWorker.getSqlRows();

    }

    private void getHouseList() {

        try {

            if (fbWorker == null) {
                fbWorker = new FireBaseWorker();
            }

        } catch (NullPointerException e) {
            Log.d(PRESENTER_TAG, "NullPointException is : " + e.getMessage());
        } catch (Exception e) {
            Log.d(PRESENTER_TAG, "Exception is : " + e.getMessage());
        }

        fbWorker.getHousesList(new FBSnapshotCallback() {

            @Override
            public void onCallBack(DataSnapshot snapshot) {

                housesList = new ArrayList<String>();

                for (DataSnapshot snap : snapshot.getChildren()) {

                    housesList.add(snap.child("name").getValue().toString());
                    //Log.d(PRESENTER_TAG, "Value is: " + snap.child("name").getValue().toString());
                }

                view.setHouseList(housesList);
                realizeStoredData();
            }
        });

    }

    private void sendFireBaseData(final List<ExcelData> data) {

        try {
            if (fbWorker == null) {
                fbWorker = new FireBaseWorker();
            }
        } catch (NullPointerException e) {
            Log.d(PRESENTER_TAG, "NullPointException is : " + e.getMessage());
        } catch (Exception e) {
            Log.d(PRESENTER_TAG, "Exception is : " + e.getMessage());
        }

        fbWorker.getNewHistoryElementIndex(new FBStringCallback() {
            @Override
            public void onCallBack(String result) {

                //Log.d(PRESENTER_TAG, "Result is: " + result);

                int elementIndex = Integer.parseInt(result);
                final String date = Support.getCurrentMoscowTimeAndDate();

                for (int i = 0; i < data.size(); i++) {

                    fbWorker.insertFireBaseData(data.get(i), Constants.DB_DIRECTION, elementIndex, date);
                    elementIndex++;
                }

            }
        });

    }


    @Override
    public void attachView(StartContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

        //не успевают данные записаться в fireBase до того как объек обнулится.
       /* service = null;
        dbWorker = null;
        fbWorker = null;*/

        this.view = null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void processFinish(Integer result) {

    }
}
