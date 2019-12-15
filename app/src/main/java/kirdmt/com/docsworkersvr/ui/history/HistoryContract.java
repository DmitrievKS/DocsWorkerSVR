package kirdmt.com.docsworkersvr.ui.history;

import android.content.Context;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.HistoryData;
import kirdmt.com.docsworkersvr.ui.base.PresenterBase;
import kirdmt.com.docsworkersvr.ui.base.ViewBase;


public class HistoryContract {

    interface View extends ViewBase {

        void showToast(int messageResId);

        Context getContext();

        void closeHistoryActivity();

        void fillRecycler(List<HistoryData> list);
    }

    interface Presenter extends PresenterBase<HistoryContract.View> {


    }

}
