package code.android.enforeag.view;

/**
 * Created by thebr_000 on 23/05/2017.
 */

public interface MainView extends MvpView {

    String getEnteredUrl();

    void showNullUrlError(int resId);
    void showInValidUrlError(int resId);

    void showLoading();

    void hideLoading();

    void disableButtonClick();
    void enableButtonClick();

    void showNoHtmlFromServerMessage(int text_empty_html);

    void showToastHtml(String htmlCode);
}
