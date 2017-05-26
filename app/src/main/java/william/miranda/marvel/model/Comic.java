package william.miranda.marvel.model;

import william.miranda.marvel.api.ImageWrapper;
import william.miranda.marvel.api.response.ComicResponse;

/**
 * Model for Comic entity
 */
public class Comic {

    private int id;
    private String title;
    private String description;
    private String format;
    private int pageCount;
    private String[] urls;
    private String thumbnailUrl;

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

        //Add urls from Response object to an array of String
        this.urls = new String[response.getUrls().length];
        for (int i=0 ; i<response.getUrls().length ; i++) {
            this.urls[i] = response.getUrls()[i].getUrl();
        }
    }

    /**
     * Constructor to create the POJO from the DB
     * @param id
     * @param title
     * @param description
     * @param format
     * @param pageCount
     * @param urls
     * @param thumbnailUrl
     */
    public Comic(int id, String title, String description, String format,
                 int pageCount, String[] urls, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.format = format;
        this.pageCount = pageCount;
        this.urls = urls;
        this.thumbnailUrl = thumbnailUrl;
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

    public String[] getUrls() {
        return urls;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
