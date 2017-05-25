package code.android.enforeag;

import android.app.Application;
import android.content.Context;

import code.android.enforeag.network.HtmlToPdfService;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class HtmlToPdfApplication extends Application {

    private HtmlToPdfService htmlToPdfService;
    private Scheduler defaultSubscribeScheduler;

    public static HtmlToPdfApplication get(Context context) {
        return (HtmlToPdfApplication) context.getApplicationContext();
    }

    public HtmlToPdfService geHtmlToPdfService() {
        if (htmlToPdfService == null) {
            htmlToPdfService = HtmlToPdfService.Factory.create();
        }
        return htmlToPdfService;
    }

    //For setting mocks during testing
    public void seHtmlToPdfService(HtmlToPdfService htmlToPdfService) {
        this.htmlToPdfService = htmlToPdfService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
