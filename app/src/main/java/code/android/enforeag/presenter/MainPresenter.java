package code.android.enforeag.presenter;

import android.os.Handler;
import android.webkit.URLUtil;

import java.io.File;

import code.android.enforeag.DownloadFile;
import code.android.enforeag.DownloadFileUrlConnectionImpl;
import code.android.enforeag.R;
import code.android.enforeag.utils.Constants;
import code.android.enforeag.view.MainView;

/**
 * Created by thebr_000 on 23/05/2017.
 */

public class MainPresenter implements Presenter<MainView> {
    public static String TAG = "MainPresenter";
    private MainView mView;
    private DownloadFile.Listener downloadListener;

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void handleGetPdfButtonClick() {

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
}
