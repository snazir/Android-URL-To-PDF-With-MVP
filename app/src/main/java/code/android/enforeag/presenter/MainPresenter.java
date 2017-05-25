package code.android.enforeag.presenter;

import android.os.Handler;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;

import code.android.enforeag.DownloadFile;
import code.android.enforeag.DownloadFileUrlConnectionImpl;
import code.android.enforeag.HtmlToPdfApplication;
import code.android.enforeag.R;
import code.android.enforeag.model.ServerResponse;
import code.android.enforeag.network.HtmlToPdfService;
import code.android.enforeag.utils.Constants;
import code.android.enforeag.view.MainView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by thebr_000 on 23/05/2017.
 */

public class MainPresenter implements Presenter<MainView> {
    public static String TAG = "MainPresenter";
    private MainView mView;
    private Subscription subscription;
    private ServerResponse mResponse;
    private DownloadFile.Listener downloadListener;

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void handleGetPdfFromUrlButtonClick() {

        String enteredUrl = mView.getEnteredUrl();
        if (enteredUrl.isEmpty()) {
            mView.showNullUrlError(R.string.empty_url_message);
            return;
        }
        if (!URLUtil.isValidUrl(enteredUrl)) {
            mView.showInValidUrlError(R.string.invalid_url_message);
            return;
        }
        mView.showLoading();
        mView.disableButtonClick();
        downloadPdf(enteredUrl);
    }

    private void downloadPdf(String enteredUrl) {
        DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(mView.getContext(),
                new Handler(), downloadListener);
        downloadFile.download(enteredUrl,
                new File(mView.getContext().getExternalFilesDir("pdf"), Constants.PDF_NAME).
                        getAbsolutePath());

    }

    public void setDownloadListener(DownloadFile.Listener downloadListener) {
        this.downloadListener = downloadListener;
    }



    public void loadHtmlFromServer() {
        if (subscription != null) subscription.unsubscribe();
        HtmlToPdfApplication application = HtmlToPdfApplication.get(mView.getContext());
        HtmlToPdfService htmlToPdfService = application.geHtmlToPdfService();
        subscription = htmlToPdfService.getHtmlFromServer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ServerResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, mResponse.getHtmlCode());
                        if (!mResponse.getHtmlCode().isEmpty()) {
                           // Here is HTML FROM THR SERVER
                            mView.hideLoading();
                            mView.showToastHtml(mResponse.getHtmlCode());
                        } else {
                            mView.showNoHtmlFromServerMessage(R.string.text_empty_html);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading Html From Server ", error);
                        if (isHttp404(error)) {
                            mView.showNoHtmlFromServerMessage(R.string.error_from_server);
                        } else {
                            mView.showNoHtmlFromServerMessage(R.string.error_loading_html);
                        }
                    }

                    @Override
                    public void onNext(ServerResponse response) {
                        mResponse = response;
                    }
                });
    }
    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }


    public void handleGetPdfFromHtmlButtonClick() {
    }
}
