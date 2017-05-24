package code.android.enforeag.presenter;

import android.webkit.URLUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import code.android.enforeag.R;
import code.android.enforeag.view.MainView;

import static android.webkit.URLUtil.isValidUrl;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thebr_000 on 23/05/2017.
 */


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter;

    @Mock
    private MainView mView;


    @Before
    public void setUp() throws Exception {

        presenter = new MainPresenter();
        presenter.attachView(mView);

    }

    @Test
    public void should_Show_ErrorMessage_When_Url_IsEmpty() throws Exception {

        when(mView.getEnteredUrl()).thenReturn("");
        presenter.handleGetPdfButtonClick();

        verify(mView).showNullUrlError(R.string.empty_url_message);
    }

    @Test
    public void should_Show_ErrorMessage_When_Url_Is_Not_Valid() throws Exception {

        when(mView.getEnteredUrl()).thenReturn("training/testing/");
        presenter.handleGetPdfButtonClick();

        verify(mView).showInValidUrlError(R.string.invalid_url_message);
    }


}
