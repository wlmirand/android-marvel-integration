package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Map Url from JSON
 */
public class UrlResponse {

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
