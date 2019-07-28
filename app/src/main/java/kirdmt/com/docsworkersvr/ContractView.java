package kirdmt.com.docsworkersvr;

import android.app.ProgressDialog;

public interface ContractView {

    void showProgress(String operationExplanation);

    void hideProgress();

    void showToast(String message);

    void startMain();

    ExcelData getData();
}
