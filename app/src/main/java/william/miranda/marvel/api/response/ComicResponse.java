package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

import william.miranda.marvel.storage.db.tables.ComicTable;

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

    @SerializedName("description")
    private String description;

    @SerializedName("format")
    private String format;

    @SerializedName("pageCount")
    private int pageCount;

    @SerializedName("prices")
    private ComicPriceResponse[] prices;

    @SerializedName("urls")
    private UrlResponse[] urls;

    @SerializedName("thumbnail")
    private ImageResponse thumbnail;

    @SerializedName("images")
    private ImageResponse[] images;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public int getPageCount() {
        return pageCount;
    }

    public ComicPriceResponse[] getPriceResponse() {
        return prices;
    }

    public UrlResponse[] getUrls() {
        return urls;
    }

    public ImageResponse getThumbnail() {
        return thumbnail;
    }

    public ImageResponse[] getImages() {
        return images;
    }
}
