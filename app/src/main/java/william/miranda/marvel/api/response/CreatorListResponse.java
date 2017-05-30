package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Class to keep CreatorList from Api
 */
public class CreatorListResponse {

    @SerializedName("available")
    private int available;

    @SerializedName("returned")
    private int returned;

    @SerializedName("collectionURI")
    private String collectionURI;

    @SerializedName("items")
    private CreatorSummaryResponse[] items;

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public CreatorSummaryResponse[] getItems() {
        return items;
    }
}
