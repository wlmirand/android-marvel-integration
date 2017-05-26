package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

public class ComicPriceResponse {

    @SerializedName("type")
    private String type;

    @SerializedName("price")
    private float price;

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }
}
