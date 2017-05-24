package william.miranda.marvel.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;
import william.miranda.marvel.api.ApiWrapper;
import william.miranda.marvel.api.ImageWrapper;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;
import william.miranda.marvel.api.response.ImageResponse;

public class ComicsTask extends AsyncTask<Void, Void, Response<ComicDataWrapperResponse>> {

    private int limit;
    private int offset;

    public ComicsTask(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    protected Response<ComicDataWrapperResponse> doInBackground(Void... params) {
        try {
            return ApiWrapper.getApi().getComics(limit, offset).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Response<ComicDataWrapperResponse> response) {
        if (response.code() == ApiWrapper.HTTP_SUCCESS_CODE) {
            //Send the response body to the UI
        }
    }
}