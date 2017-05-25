package william.miranda.marvel.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class used to Cache our images.
 * If we need to cache for current session, we may use the support v4 LruCache
 * but since we may use the app in the "offline mode", we will cache the images storing them
 * at Internal Storage folder.
 * The implementation will be simple and it will not worry about clear the stored images,
 * what should be done in order to dont overflow the device storage.
 */
public class ImageCache {

    /**
     * Convert the url to the local filename
     * @param url
     * @return
     */
    private static String getLocalNameFromUrl(String url) {
        return url.replace("http://", "").replace('/', '-');
    }

    /**
     * Save an image to file
     * @param context
     * @param url
     * @param bitmap
     * @return
     */
    public static boolean saveImage(Context context, String url, Bitmap bitmap) {
        //format the local name
        String imageName = getLocalNameFromUrl(url);
        File imageFile = new File(context.getCacheDir(), imageName);

        try {
            //create the file and save the bitmap as JPEG, 85% quality
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if an image file exists
     * @param context
     * @param url
     * @return
     */
    public static boolean imageExists(Context context, String url) {
        String imageName = getLocalNameFromUrl(url);
        File imageFile = new File(context.getCacheDir(), imageName);
        return imageFile.exists();
    }

    /**
     * Load an image from file
     * @param context
     * @param url
     * @return
     */
    public static Bitmap loadImage(Context context, String url) {
        String imageName = getLocalNameFromUrl(url);
        File imageFile = new File(context.getCacheDir(), imageName);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

}
