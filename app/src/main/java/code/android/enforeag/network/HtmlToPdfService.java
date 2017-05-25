package code.android.enforeag.network;


import code.android.enforeag.model.ServerResponse;
import code.android.enforeag.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface HtmlToPdfService {

    @GET(Constants.Get_HTML_URL)
    Observable<ServerResponse> getHtmlFromServer();


    class Factory {
        public static HtmlToPdfService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(HtmlToPdfService.class);
        }
    }
}
