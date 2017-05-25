package william.miranda.marvel.model;

import android.graphics.Bitmap;

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
     * We may use the same, but I dont think it's a good practice.
     * @param response
     */
    public Comic(ComicResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.thumbnailUrl = new ImageWrapper(response.getThumbnail())
                .getUrl(ImageWrapper.ImageVariant.standard_large);
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
