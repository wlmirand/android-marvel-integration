package william.miranda.marvel.api;

import java.io.IOException;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to add the Authentication Query parameters to every request
 */
public class AuthInterceptor implements Interceptor {

    public AuthInterceptor() {

    }

    /**
     * According to documentation, we must pass the following parameters:
     * ?ts=1
     * &apikey=1234
     * &hash=ffd275c5130566a2916217b101f26150
     * where hash is md5(ts+privateKey+publicKey)
     * @param chain     Original request
     * @return          Modified request
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        //Authentication Data
        long timestamp = new Date().getTime();
        String publicKey = "54306733de0f5cd1418aa05a85fa062a";
        String privateKey = "5de1fabcda2ea08912bd8b09bca4321f50563655";
        String hash = ApiWrapper.md5(timestamp + privateKey + publicKey);

        //Add the parameters to our Http Request
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", String.valueOf(timestamp))
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash", hash)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        //Forward our request
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}
