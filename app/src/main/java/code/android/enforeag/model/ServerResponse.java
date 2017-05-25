package code.android.enforeag.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by thebr_000 on 25/05/2017.
 */

public class ServerResponse {

    private String status;
    private String format;

    @SerializedName("data")
    private String htmlCode;

    public ServerResponse() {
    }

    public String getStatus() {
        return status;
    }

    public String getFormat() {
        return format;
    }

    public String getHtmlCode() {
        return htmlCode;
    }
}
