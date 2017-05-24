package code.android.enforeag.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}