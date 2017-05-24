package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

public class ComicDataContainerResponse {

    @SerializedName("offset")
    private int offset;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private ComicResponse[] results;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public ComicResponse[] getResults() {
        return results;
    }
}
