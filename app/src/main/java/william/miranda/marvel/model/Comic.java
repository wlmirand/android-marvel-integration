package william.miranda.marvel.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import william.miranda.marvel.api.ImageWrapper;
import william.miranda.marvel.api.response.ComicResponse;
import william.miranda.marvel.api.response.CreatorSummaryResponse;

/**
 * Model for Comic entity
 */

public class Comic extends RealmObject {

    @PrimaryKey
    private int id;

    private String title;
    private String description;
    private String format;
    private int pageCount;
    private float price;
    private String thumbnailUrl;
    private RealmList<Creator> creators;
    private float pricePerPage;

    /**
     * Empty constructor
     */
    public Comic() {

    }

    /**
     * Constructor to create our POJO from the Response
     * @param response
     */
    public Comic(ComicResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.description = response.getDescription();
        this.format = response.getFormat();
        this.pageCount = response.getPageCount();
        this.thumbnailUrl = new ImageWrapper(response.getThumbnail())
                .getUrl(ImageWrapper.ImageVariant.standard_large);

        /* Consulting the API, I just found one price for a Comic,
            but the API documentation returns an array of Prices, so
            we will get only the first Price if it exists
         */
        if (response.getPrices() != null && response.getPrices().length != 0) {
            this.price = response.getPrices()[0].getPrice();
        }

        this.pricePerPage = pageCount != 0 ? price/pageCount : 0;

        //Get the Creators
        if (response.getCreators() != null &&
                response.getCreators().getItems() != null &&
                response.getCreators().getItems().length != 0) {

            this.creators = new RealmList<>();
            for (CreatorSummaryResponse sr : response.getCreators().getItems()) {
                this.creators.add(new Creator(sr));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Comic)) {
            return false;
        }

        Comic comic = (Comic) obj;
        return comic.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }

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

    public float getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public RealmList<Creator> getCreators() {
        return creators;
    }

    public float getPricePerPage() {
        return pricePerPage;
    }
}
