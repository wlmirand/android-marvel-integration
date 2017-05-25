package william.miranda.marvel.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Task to download Images
 */
public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

    //Interface for Handle the Task Result
    public interface Callback {
        void handleResult(Bitmap result);
    }

    private final String url;
    private final Callback caller;

    public DownloadImageTask(String url, Callback caller) {
        this.url = url;
        this.caller = caller;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return downloadBitmapFromUrl(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (caller != null) {
            caller.handleResult(bitmap);
        }
    }

    private Bitmap downloadBitmapFromUrl(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException exception) {
            return null;
        }
    }
}
