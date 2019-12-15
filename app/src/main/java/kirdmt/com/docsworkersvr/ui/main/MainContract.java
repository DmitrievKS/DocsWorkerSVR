package kirdmt.com.docsworkersvr.ui.main;

import android.content.Context;

import java.util.List;

import kirdmt.com.docsworkersvr.ui.base.PresenterBase;
import kirdmt.com.docsworkersvr.ui.base.ViewBase;

abstract class MainContract {

    interface View extends ViewBase {

        void showToast(int messageResId);

        Context getContext();

    }

    interface Presenter extends PresenterBase<View> {


    }
}
