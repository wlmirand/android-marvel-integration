package william.miranda.marvel.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import william.miranda.marvel.R;
import william.miranda.marvel.ui.fragments.FragmentBudget;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //We have only one menu item, so we dont need a switch (item.getId())
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)//so we can back
                .replace(R.id.frame_layout, new FragmentBudget())
                .commit();

        return true;
    }
}
