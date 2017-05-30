package william.miranda.marvel.model;

import android.net.Uri;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import william.miranda.marvel.api.response.CreatorSummaryResponse;

/**
 * Model for Creator (Author) entity
 */

public class Creator extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;
    private String role;

    /**
     * Empty constructor
     */
    public Creator() {

    }

    /**
     * Constructor to create our POJO from the Response
     * @param response
     */
    public Creator(CreatorSummaryResponse response) {
        this.name = response.getName();
        this.role = response.getRole();

        Uri uri = Uri.parse(response.getResourceURI());
        this.id = Integer.valueOf(uri.getLastPathSegment());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
