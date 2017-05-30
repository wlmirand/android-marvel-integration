package william.miranda.marvel.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.realm.Realm;
import william.miranda.marvel.R;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.model.Creator;

/**
 * Activity to Display the Comic Detail
 * Depending on the Business Logic, we may implement this using Fragments
 */
public class ComicDetailActivity extends AppCompatActivity {

    /**
     * Comic to be displayed
     */
    private Comic comic;

    /**
     * Layout Views
     */
    private TextView textTitle;
    private TextView textDescription;
    private TextView textPageCount;
    private TextView textPrice;
    private TextView textCreators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        //Map the Layout Views
        textTitle = (TextView) findViewById(R.id.comic_title);
        textDescription = (TextView) findViewById(R.id.comic_description);
        textPageCount = (TextView) findViewById(R.id.comic_page_count);
        textPrice = (TextView) findViewById(R.id.comic_price);
        textCreators = (TextView) findViewById(R.id.comic_creators);

        //Get the Id from the Intent and look for it on the Db
        int comicId = getIntent().getIntExtra("comic_id", -1);

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        comic = realm.where(Comic.class).equalTo("id", comicId).findFirst();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (comic != null) {
            populateViews();
        }
    }

    /**
     * Populate the Layout Views with the Comic information
     */
    private void populateViews() {
        textTitle.setText(comic.getTitle());

        textDescription.setText(comic.getDescription() != null && !comic.getDescription().isEmpty() ?
                comic.getDescription() : getString(R.string.text_no_description));

        textPageCount.setText(comic.getPageCount() != 0 ?
                getString(R.string.text_page_count, comic.getPageCount()) :
                getString(R.string.text_no_page_count));

        textPrice.setText(comic.getPrice() != 0.0f ?
                getString(R.string.text_price, comic.getPrice()) :
                getString(R.string.text_no_price));

        if (comic.getCreators() != null && comic.getCreators().size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (Creator creator : comic.getCreators()) {
                sb.append(creator.getName()).append('\n');
            }
            textCreators.setText(getString(R.string.text_creators, sb.toString()));
        } else {
            textCreators.setText(R.string.text_no_creators);
        }
    }
}
