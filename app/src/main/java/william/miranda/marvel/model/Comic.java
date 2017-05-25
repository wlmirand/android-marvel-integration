package william.miranda.marvel.model;

import william.miranda.marvel.api.ImageWrapper;
import william.miranda.marvel.api.response.ComicResponse;

/**
 * Model for Comic entity
 */
public class Comic {

    private int id;
    private String title;
    private String thumbnailUrl;

    /**
     * Constructor to create our POJO from the Response
     * @param response
     */
    public Comic(ComicResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.thumbnailUrl = new ImageWrapper(response.getThumbnail())
                .getUrl(ImageWrapper.ImageVariant.standard_large);
    }

    /**
     * Constructor to create the POJO from the DB
     * @param id
     * @param title
     * @param thumbnailUrl
     */
    public Comic(int id, String title, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
