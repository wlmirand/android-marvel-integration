package william.miranda.marvel.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to manage the Retrofit interface.
 * Implemented as a Singleton to provide global access across the app
 * and to ensure the instantiation of only one Retrofit object.
 */
public class ApiWrapper {

    //Http constants
    public static final int HTTP_SUCCESS_CODE = 200;

    //API Base URL
    private static final String MARVEL_URL = "http://gateway.marvel.com/";

    //Singleton instance
    private static ApiWrapper instance;

    //Retrofit instance
    private MarvelApi api;

    //Constructor which will initialize the Retrofit interface
    private ApiWrapper() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MARVEL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
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

    public static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
