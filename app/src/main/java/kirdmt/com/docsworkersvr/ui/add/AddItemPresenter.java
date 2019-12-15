package kirdmt.com.docsworkersvr.ui.add;


import android.content.Context;
import android.util.Log;

import kirdmt.com.docsworkersvr.Callback.FBStringCallback;
import kirdmt.com.docsworkersvr.db.DbWorker;
import kirdmt.com.docsworkersvr.firebase.FireBaseWorker;
import kirdmt.com.docsworkersvr.network.NetworkService;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;
import kirdmt.com.docsworkersvr.Data.ExcelData;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.util.Constants;
import kirdmt.com.docsworkersvr.util.Support;


public class AddItemPresenter implements AddItemContract.Presenter, NetworkService.NetworkServiceResponse {

    private static final String PRESENTER_TAG = "PresenterTAG";

    private AddItemContract.View view;
    private Context context;

    private NetworkService service;
    private DbWorker dbWorker;
    private FireBaseWorker fbWorker;

    private ExcelData excelData;

    public AddItemPresenter(AddItemContract.View AddItem, Context context) {

        this.context = context;
        this.view = AddItem;

    }

    private boolean sendExcelData(ExcelData excelData) {

        if (ConnectivityHelper.isConnectedToNetwork(context) == false) {

            sendRoomData(excelData);

            view.showToast(R.string.no_internet_access);

            view.startMain();

        } else {

            sendNetworkData(excelData);

        }

        return true;

    }

    private void sendRoomData(ExcelData excelData) {

        dbWorker = new DbWorker(context);
        dbWorker.DbInit();
        dbWorker.addSqlData(excelData);
    }

    private void sendNetworkData(ExcelData excelData) {

        view.showProgress(context.getString(R.string.add_item));

        service = new NetworkService(this);
        service.setData(excelData);
        service.execute();
    }

    private void sendFireBaseData(final ExcelData excelData) {

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

                int elementIndex = Integer.parseInt(result);
                final String date = Support.getCurrentMoscowTimeAndDate();

                fbWorker.insertFireBaseData(excelData, Constants.DIRECT_DIRECTION, elementIndex, date);

            }
        });
    }

    //unusable
    public void attachView(AddItemContract.View AddItem, Context context) {

        this.context = context;
        view = AddItem;
    }

    @Override
    public void attachView(AddItemContract.View view) {

    }

    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {

    }

    public void addItem() {

        excelData = view.getData();

        if (excelData != null) {
            sendExcelData(excelData);

        }

    }

    //Here you will receive the result fired from async class
    //of onPostExecute(result) method.
    @Override
    public void processFinish(Integer result) {

        Log.d(PRESENTER_TAG, "result is: " + result);

        if (result == Constants.CORRECT_RESPONSE_CODE) {

            sendFireBaseData(excelData);

        } else {

            sendRoomData(excelData);

        }

        view.hideProgress();
        view.startMain();

    }
}
