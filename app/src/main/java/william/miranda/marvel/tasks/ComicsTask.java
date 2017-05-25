package william.miranda.marvel.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Response;
import william.miranda.marvel.api.ApiWrapper;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;

public class ComicsTask extends AsyncTask<Void, Void, Response<ComicDataWrapperResponse>> {

    //Interface for Handle the Task Result
    public interface Callback {
        void handleResult(ComicDataWrapperResponse bodyResponse);
    }

    private final int limit;
    private final int offset;
    private final Callback caller;

    public ComicsTask(int limit, int offset, Callback caller) {
        this.limit = limit;
        this.offset = offset;
        this.caller = caller;
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
            caller.handleResult(response.body());
        }
    }
}