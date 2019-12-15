package kirdmt.com.docsworkersvr.ui.base;

public interface PresenterBase<V extends ViewBase> {

    void attachView(V view);

    void detachView();

    void destroy();
}
