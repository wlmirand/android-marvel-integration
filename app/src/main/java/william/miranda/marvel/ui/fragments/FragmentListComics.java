package william.miranda.marvel.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import william.miranda.marvel.R;
import william.miranda.marvel.adapters.ComicAdapter;
import william.miranda.marvel.api.ApiWrapper;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;
import william.miranda.marvel.api.response.ComicResponse;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.tasks.ComicsTask;
import william.miranda.marvel.ui.activities.ComicDetailActivity;

/**
 * Fragment to display a Comics list
 */
public class FragmentListComics extends Fragment implements ComicsTask.Callback {

    /**
     * Limit and offset to call the Api
     */
    private static final int LIMIT = 100;
    private static final int OFFSET = 0;

    private RecyclerView recyclerView;
    private ComicAdapter adapter;
    private ComicAdapter.OnItemClickListener clickListener = new ComicAdapter.OnItemClickListener() {
        @Override
        public void onItemClickListener(Comic comic) {

            //Create the Intent and pass the Comic ID as Extra...
            Intent intent = new Intent(getContext(), ComicDetailActivity.class);
            intent.putExtra("comic_id", comic.getId());

            //Open the detail activity
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Inflate the Layout and bind the Views
     * @param inflater              android inflater
     * @param container             container view
     * @param savedInstanceState    previous state
     * @return                      inflated root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_comics, container, false);

        //Create the empty Adapter
        adapter = new ComicAdapter(getContext(), clickListener);

        //Prepare the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Run the task to fetch comics from Api
        new ComicsTask(LIMIT, OFFSET, this).execute();

        return view;
    }

    /**
     * Callback to handle new Data from ComicsTask
     * @param responseBody
     */
    @Override
    public void handleResult(ComicDataWrapperResponse responseBody) {

        List<Comic> listComic;

        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();

        if (responseBody != null &&
                responseBody.getCode() == ApiWrapper.HTTP_SUCCESS_CODE) {
            //If Success, we iterate all ComicResponse and create our comic POJOs
            listComic = new ArrayList<>();
            for (ComicResponse item : responseBody.getData().getResults()) {
                //Add the object to a list
                Comic comic = new Comic(item);
                listComic.add(comic);

                //Persists the object if needed
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(comic);
                realm.commitTransaction();
            }
        } else {
            //Could not fetch data from Api, so we use the Local data if available
            RealmResults<Comic> comics = realm.where(Comic.class).findAll();
            listComic = comics.subList(0, comics.size());
        }

        //Fill the adapter with new Data
        adapter.swap(listComic);
    }
}
