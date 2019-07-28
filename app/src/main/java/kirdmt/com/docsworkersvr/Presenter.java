package kirdmt.com.docsworkersvr;


import android.content.Context;
import android.util.Log;

import java.util.List;


public class Presenter {

    ExcelData excelData;
    private ContractView view;
    private Model model;
    private Context context;
    int itemsInDataBase;

    Presenter(ContractView AddItem, Context context) {

        this.context = context;
        this.view = AddItem;

        model = new Model(context);

        saveDataFromBD();

    }


    private void saveDataFromBD() {

        itemsInDataBase = model.getCountSqlRows();

        if (itemsInDataBase > 0 & ConnectivityHelper.isConnectedToNetwork(context)) {

            view.showToast("Данные записанные в БД отправляются на сервер.");

            List<ExcelData> excelDataList = model.getSqlRows();

            while (itemsInDataBase > 0) {
                model.sendData(excelDataList.get(itemsInDataBase - 1), new ModelCallback() {
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

            view.showToast("Отсутствует подключение к интернету, сохроняю в БД.");

            voidModel();
            view.startMain();

        } else {

            view.showProgress("Adding Item");

            model.sendData(excelData, new ModelCallback() {
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
