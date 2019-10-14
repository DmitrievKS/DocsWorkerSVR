package kirdmt.com.docsworkersvr.Presenters;


import android.content.Context;

import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.ModelCallback;
import kirdmt.com.docsworkersvr.util.ConnectivityHelper;
import kirdmt.com.docsworkersvr.ExcelData;
import kirdmt.com.docsworkersvr.Models.Model;
import kirdmt.com.docsworkersvr.R;
import kirdmt.com.docsworkersvr.contractView.ContractView;


public class Presenter {

    ExcelData excelData;
    private ContractView view;
    private Model model;
    private Context context;
    int itemsInDataBase;

    //static final List<String> Houses = new ArrayList<String>();

    public Presenter(ContractView AddItem, Context context) {

        this.context = context;
        this.view = AddItem;

        model = new Model(context);

        saveDataFromBD();


    }

    private void saveDataFromBD() {

        itemsInDataBase = model.getCountSqlRows();

        if (itemsInDataBase > 0 & ConnectivityHelper.isConnectedToNetwork(context)) {

            view.showToast(context.getString(R.string.data_from_db_to_server));

            List<ExcelData> excelDataList = model.getSqlRows();

            while (itemsInDataBase > 0) {
                model.sendData(true, excelDataList.get(itemsInDataBase - 1), new ModelCallback() {
                    @Override
                    public void onCallBack(String response) {

                    }
                });
                itemsInDataBase--;
            }
        }

    }


    private boolean sendExcelData(ExcelData excelData) {

        if (ConnectivityHelper.isConnectedToNetwork(context) == false) {

            model.addSqlData(excelData);

            view.showToast(context.getString(R.string.no_internet_access));

            voidModel();
            view.startMain();

        } else {

            view.showProgress("Adding Item");

            model.sendData(false, excelData, new ModelCallback() {
                @Override
                public void onCallBack(String response) {

                    //Log.d("responseTAG", "result is: " + result);

                    if (response.equals("Success")) {

                        view.showToast(response);
                        view.hideProgress();

                    } else {

                        view.showToast(response);
                        view.hideProgress();

                    }

                    voidModel();
                    view.startMain();

                }
            });
        }

        return true;

    }

    //unusable
    public void attachView(ContractView AddItem, Context context) {

        this.context = context;
        view = AddItem;
    }

    public void detachView() {
        view = null;
    }

    public void voidModel() {
        model = null;
    }

    public void addItem() {

        excelData = view.getData();

        if (excelData != null) {
            sendExcelData(excelData);

        }


    }

}
