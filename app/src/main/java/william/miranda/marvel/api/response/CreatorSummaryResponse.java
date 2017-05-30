package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

public class CreatorSummaryResponse {
    @SerializedName("resourceURI")
    private String resourceURI;

    @SerializedName("name")
    private String name;

    @SerializedName("role")
    private String role;

    public String getResourceURI() {
        return resourceURI;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
