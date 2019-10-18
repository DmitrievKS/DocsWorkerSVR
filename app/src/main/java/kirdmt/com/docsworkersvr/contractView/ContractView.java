package kirdmt.com.docsworkersvr.contractView;

import android.app.ProgressDialog;

import java.util.List;

import kirdmt.com.docsworkersvr.ExcelData;

public interface ContractView {

    void showProgress(String operationExplanation);

    void hideProgress();

    void showToast(String message);

    void startMain();

    ExcelData getData();
}
