package william.miranda.marvel.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to manage the Retrofit interface.
 * Implemented as a Singleton to provide global access across the app
 * and to ensure the instantiation of only one Retrofit object.
 */
public class ApiWrapper {

    //API Base URL
    private static final String MARVEL_URL = "http://gateway.marvel.com/";

    //Singleton instance
    private static ApiWrapper instance;

    //Retrofit instance
    private MarvelApi api;

    //Constructor which will initialize the Retrofit interface
    private ApiWrapper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MARVEL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarvelApi.class);
    }

    /**
     * Create the Singleton if needed
     * @return Api interface instance
     */
    public static MarvelApi getApi() {
        if (instance == null) {
            instance = new ApiWrapper();
        }
        return instance.api;
    }
}
