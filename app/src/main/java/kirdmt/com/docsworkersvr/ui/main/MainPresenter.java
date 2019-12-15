package kirdmt.com.docsworkersvr.ui.main;

import android.content.Context;

public class  MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Context context;

    public MainPresenter(MainContract.View mainActivity) {

        this.view = mainActivity;
        context = view.getContext();

    }

    @Override
    public void attachView(MainContract.View view) {
    }

    @Override
    public void detachView() {
        context = null;
        view = null;
    }

    @Override
    public void destroy() {
    }
}
