package william.miranda.marvel.api.response;

import com.google.gson.annotations.SerializedName;

public class ComicDataWrapperResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private ComicDataContainerResponse data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public ComicDataContainerResponse getData() {
        return data;
    }
}
