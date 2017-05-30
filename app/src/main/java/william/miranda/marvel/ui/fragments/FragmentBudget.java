package william.miranda.marvel.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import william.miranda.marvel.R;
import william.miranda.marvel.adapters.ComicAdapter;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.tasks.ComicsTask;

/**
 * Simple Fragment where the user insert the Budget
 * and we calculate the maximum "pages" the user can buy
 *
 * To accomplish this, we'll do the following:
 * We will consider only the Comics with PageCount > 0 AND Price > 0
 * For each Comic, we calculate the PricePerPage:
 *         PricePerPage = Price / PageCount
 * We will have a sorted list by PricePerPage.
 * Then we "buy" until the budget is over
 */
public class FragmentBudget extends Fragment {

    private EditText editBudget;
    private Button button;
    private TextView textComicsPurchased;
    private TextView textPagesPurchased;
    private TextView textRemainingBudget;

    private View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strBudget = editBudget.getText().toString();

            if (strBudget != null && !strBudget.isEmpty()) {
                float budgetAvailable = Float.valueOf(strBudget);
                calculate(budgetAvailable);
            }
        }
    };

    /**
     * Inflate the Layout and bind the Views
     * @param inflater              android inflater
     * @param container             container view
     * @param savedInstanceState    previous state
     * @return                      inflated root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        editBudget = (EditText) view.findViewById(R.id.edit_budget);
        textComicsPurchased = (TextView) view.findViewById(R.id.comics_purchased);
        textPagesPurchased = (TextView) view.findViewById(R.id.pages_purchased);
        textRemainingBudget = (TextView) view.findViewById(R.id.remaining_budget);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(calculateListener);

        return view;
    }

    private void calculate(float totalBudget) {
        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Comic> result = realm.where(Comic.class)
                .greaterThan("pageCount", 0)
                .greaterThan("price", 0f)
                .findAllSorted("pricePerPage", Sort.ASCENDING);

        List<Comic> availableList = result.subList(0, result.size());
        int comicsPurchased = 0;
        int pagesPurchased = 0;
        float budgetAvailable = totalBudget;

        //Buy comics until budget is over or until the list is empty
        for (Comic comic : availableList) {

            //if we have budget available, buy the comic
            if (budgetAvailable >= comic.getPrice()) {
                comicsPurchased++;
                pagesPurchased += comic.getPageCount();
                budgetAvailable -= comic.getPrice();
            }

            //if we dont, just go to next Comic, because the next one may be cheaper
        }

        //Set the result values
        textComicsPurchased.setText(getString(R.string.comics_purchased, comicsPurchased));
        textPagesPurchased.setText(getString(R.string.pages_purchased, pagesPurchased));
        textRemainingBudget.setText(getString(R.string.remaining_budget, budgetAvailable));
    }
}
