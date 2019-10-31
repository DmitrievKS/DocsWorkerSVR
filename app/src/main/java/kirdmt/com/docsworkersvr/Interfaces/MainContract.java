package kirdmt.com.docsworkersvr.Interfaces;

import android.content.Context;

import java.util.List;

import kirdmt.com.docsworkersvr.CallBacks.HousesCallback;

public interface MainContract {

    interface View extends MvpView {

        // show message to user
        void showToast(int messageResId);

        void setHousesList(List<String> housesList);

        Context getContext();

        void progressDialogStart();

        void progressDialogCancel();
    }


    interface Presenter extends MvpPresenter<View> {


    }
}
