package william.miranda.marvel;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import retrofit2.Response;
import william.miranda.marvel.api.ApiWrapper;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;
import william.miranda.marvel.storage.ImageCache;

import static org.junit.Assert.*;

/**
 * Class to UnitTests (that does not uses Context)
 */
public class UnitTest {

    /**
     * Test the Api Calls
     * @throws IOException
     */
    @Test
    public void testApi() throws IOException {
        //use random parameters
        Random random = new Random();
        int limit = random.nextInt(100);;
        int offset = random.nextInt(100);;

        //Make the Call
        Response<ComicDataWrapperResponse> response = ApiWrapper.getApi().getComics(limit, offset).execute();

        //Check if response was OK
        assertEquals(response.code(), 200);

        //get the body
        ComicDataWrapperResponse body = response.body();

        //check if the passed values are Ok
        assertEquals(limit, body.getData().getLimit());
        assertEquals(limit, body.getData().getResults().length);
        assertEquals(offset, body.getData().getOffset());
    }

    /**
     * Test our method to convert image URLs into local filenames
     * Since our method is private, we'll use Reflection to access it
     */
    @Test
    public void localImageNameTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //input url
        String imgUrl = "http://domain.net/api/endpoint/item/my_image.jpg";

        //expected file name
        String imgFile = "domain.net-api-endpoint-item-my_image.jpg";

        //use reflection to access the private method
        Class<ImageCache> clazz = ImageCache.class;
        Method method = clazz.getDeclaredMethod("getLocalNameFromUrl", String.class);
        method.setAccessible(true);
        String res = (String) method.invoke(null, imgUrl);

        //check if output is Ok
        assertEquals(res, imgFile);
    }
}
