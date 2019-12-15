package kirdmt.com.docsworkersvr.ui.add;

import android.content.Context;

import java.util.List;

import kirdmt.com.docsworkersvr.Data.ExcelData;
import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.ui.base.PresenterBase;
import kirdmt.com.docsworkersvr.ui.base.ViewBase;

public abstract class AddItemContract {


    interface View extends ViewBase {

         void startMain();
        // show message to user
        void showToast(int messageResId);

        Context getContext();

        void showProgress(String operationExplanation);

        void hideProgress();

        ExcelData getData();
    }

    interface Presenter extends PresenterBase<AddItemContract.View> {


    }
}
