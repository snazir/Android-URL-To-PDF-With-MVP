package code.android.enforeag;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.android.enforeag.presenter.MainPresenter;
import code.android.enforeag.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, DownloadFile.Listener {

    MainPresenter mPresenter;

    @BindView(R.id.et_url)
    EditText etUrl;

    @BindView(R.id.activity_pdf_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.get_pdf_from_link_button)
    Button mGetPdfFromUrlButton;

    @BindView(R.id.get_pdf_from_link_button)
    Button mGetPdfFromHtmlButton;

    @BindView(R.id.pdfView)
    PDFView mPdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        hideLoading();

    }

    @OnClick(R.id.get_pdf_from_link_button)
    public void onPdfFromUrlButtonClick() {
        mPresenter.setDownloadListener(this);
        mPresenter.handleGetPdfFromUrlButtonClick();

    }

    @OnClick(R.id.get_pdf_from_html_button)
    public void onPdfFromHtmlButtonClick() {
        mPresenter.handleGetPdfFromHtmlButtonClick();

    }


    private void loadPDF(String localPath) {
        mPdfView.fromUri(Uri.parse(localPath))

//        pdfView.fromFile(File)

//        pdfView.fromAsset(String)
//                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
//                .enableSwipe(true)

                .enableDoubletap(true)
                .swipeVertical(true)
                .defaultPage(1)
                .showMinimap(false)
//                .onDraw(onDrawListener)
//                .onLoad(onLoadCompleteListener)
//                .onPageChange(onPageChangeListener)
//                .onError(onErrorListener)
                .enableAnnotationRendering(false)
                .password(null)
                .showPageWithAnimation(true)
                .load();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getEnteredUrl() {
        return etUrl.getText().toString().trim();
    }

    @Override
    public void showNullUrlError(int resId) {
        etUrl.setError(getString(resId));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableButtonClick() {
        mGetPdfFromUrlButton.setEnabled(false);
        mGetPdfFromHtmlButton.setEnabled(false);
    }

    @Override
    public void enableButtonClick() {
        mGetPdfFromUrlButton.setEnabled(true);
        mGetPdfFromHtmlButton.setEnabled(true);
    }

    @Override
    public void showNoHtmlFromServerMessage(int text_empty_html) {

    }

    @Override
    public void showToastHtml(String htmlCode) {
        Toast.makeText(this, htmlCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInValidUrlError(int resId) {
        etUrl.setError(getString(resId));
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        Toast.makeText(this, "Success " + destinationPath, Toast.LENGTH_SHORT).show();
        Log.d("PDFPATH", destinationPath);
        hideLoading();
        loadPDF(destinationPath);

    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this, "Failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();

    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        Toast.makeText(this, progress + " / " + total, Toast.LENGTH_SHORT).show();

    }
}
