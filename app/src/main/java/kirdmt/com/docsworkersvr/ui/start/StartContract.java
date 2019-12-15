package kirdmt.com.docsworkersvr.ui.start;

import android.content.Context;

import java.util.ArrayList;

import kirdmt.com.docsworkersvr.ui.base.PresenterBase;
import kirdmt.com.docsworkersvr.ui.base.ViewBase;


abstract class StartContract {

    interface View extends ViewBase {

        Context getContext();

        void setHouseList(ArrayList<String> houseList);

        void startMainActivity();
    }


    interface Presenter extends PresenterBase<StartContract.View> {


    }
}

