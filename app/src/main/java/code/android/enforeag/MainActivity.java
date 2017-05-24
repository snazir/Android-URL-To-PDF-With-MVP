package code.android.enforeag;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.android.enforeag.presenter.MainPresenter;
import code.android.enforeag.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView,DownloadFile.Listener {

    MainPresenter mPresenter;

    @BindView(R.id.et_url)
    EditText etUrl;

    @BindView(R.id.activity_pdf_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        hideLoading();

    }

    @OnClick(R.id.get_pdf_button)
    public void onButtonClick() {
        mPresenter.setDownloadListener(this);
        mPresenter.handleGetPdfButtonClick();

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
    public void showInValidUrlError(int resId) {
        etUrl.setError(getString(resId));
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        Toast.makeText(this, "Success "+ destinationPath, Toast.LENGTH_SHORT).show();
        Log.d("PDFPATH", destinationPath);
        hideLoading();

    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this, "Failure"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        hideLoading();

    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        Toast.makeText(this, progress+" / "+ total, Toast.LENGTH_SHORT).show();

    }
}
