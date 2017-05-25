package william.miranda.marvel.api;

import william.miranda.marvel.api.response.ImageResponse;

/**
 * Class used to create Image full paths from Api ImageResponse
 */
public class ImageWrapper {

    //Enum for all types of images
    public enum ImageVariant {
        portrait_small,         //50x75px
        portrait_medium,        //100x150px
        portrait_xlarge,        //150x225px
        portrait_fantastic,     //168x252px
        portrait_uncanny,       //300x450px
        portrait_incredible,    //216x324px
        standard_small,         //65x45px
        standard_medium,        //100x100px
        standard_large,         //140x140px
        standard_xlarge,        //200x200px
        standard_fantastic,     //250x250px
        standard_amazing,       //180x180px
        landscape_small,        //120x90px
        landscape_medium,       //175x130px
        landscape_large,        //190x140px
        landscape_xlarge,       //270x200px
        landscape_amazing,      //250x156px
        landscape_incredible    //464x261px
    }

    private ImageResponse imageResponse;

    public ImageWrapper(ImageResponse imageResponse) {
        this.imageResponse = imageResponse;
    }

    /**
     * Return a complete URL based on Variant
     * We can generate this based on screen density to download only images for current device
     * @return
     */
    public String getUrl(ImageVariant variant) {
        return new StringBuilder()
                .append(imageResponse.getPath())
                .append('/')
                .append(variant.toString())
                .append('.')
                .append(imageResponse.getExtension())
                .toString();
    }
}
