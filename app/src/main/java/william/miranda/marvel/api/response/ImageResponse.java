package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Class to Map Image from JSON
 */
public class ImageResponse {

    @SerializedName("path")
    private String path;

    @SerializedName("extension")
    private String extension;

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }
}
