package william.miranda.marvel.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import william.miranda.marvel.R;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.storage.db.ComicDAO;
import william.miranda.marvel.storage.db.tables.ComicTable;

public class ComicDetailActivity extends AppCompatActivity {

    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        int comicId = getIntent().getIntExtra(ComicTable.COLUMN_ID, -1);
        comic = new ComicDAO(this).query(comicId);

        ((TextView) findViewById(R.id.comic_title)).setText(comic.getTitle());
    }
}
