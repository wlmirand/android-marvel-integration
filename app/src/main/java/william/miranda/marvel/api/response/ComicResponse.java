package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Class to map the JSON Response from Comics API
 * We may omit the annotations if the json field matches the attribute name,
 * but lets keep it in order to keep the code uniform.
 */
public class ComicResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
