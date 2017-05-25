package william.miranda.marvel.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import william.miranda.marvel.R;
import william.miranda.marvel.ui.fragments.FragmentListComics;

/**
 * App Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, new FragmentListComics())
                .commit();
    }
}
