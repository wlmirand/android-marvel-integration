package william.miranda.marvel.api;

import retrofit2.Call;
import retrofit2.http.GET;
import william.miranda.marvel.api.response.ComicResponse;

public interface MarvelApi {

    @GET("/v1/public/comics")
    Call<ComicResponse> getComics();
}
