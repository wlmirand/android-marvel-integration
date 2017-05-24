package william.miranda.marvel.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;

public interface MarvelApi {

    @GET("/v1/public/comics")
    Call<ComicDataWrapperResponse> getComics(@Query("limit") int limit, @Query("offset") int offset);
}
