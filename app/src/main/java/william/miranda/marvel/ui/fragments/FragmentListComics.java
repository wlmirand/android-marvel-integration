package william.miranda.marvel.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import william.miranda.marvel.R;
import william.miranda.marvel.adapters.ComicAdapter;
import william.miranda.marvel.api.ApiWrapper;
import william.miranda.marvel.api.response.ComicDataWrapperResponse;
import william.miranda.marvel.api.response.ComicResponse;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.storage.db.ComicDAO;
import william.miranda.marvel.tasks.ComicsTask;

/**
 * Fragment to display a Comics list
 */
public class FragmentListComics extends Fragment implements ComicsTask.Callback {

    private RecyclerView recyclerView;
    private ComicAdapter adapter;

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
        adapter = new ComicAdapter(getContext());

        //Prepare the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Run the task to fetch comics from Api
        new ComicsTask(100, 0, this).execute();

        return view;
    }

    /**
     * Callback to handle new Data from ComicsTask
     * @param responseBody
     */
    @Override
    public void handleResult(ComicDataWrapperResponse responseBody) {

        List<Comic> listComic;
        ComicDAO comicDAO = new ComicDAO(getContext());
        if (responseBody != null &&
                responseBody.getCode() == ApiWrapper.HTTP_SUCCESS_CODE) {
            //If Success, we iterate all ComicResponse and create our comic POJOs
            listComic = new ArrayList<>();
            for (ComicResponse item : responseBody.getData().getResults()) {
                listComic.add(new Comic(item));
            }

            //save the list on the DB
            comicDAO.insertList(listComic);
        } else {
            //Could not fetch data from Api, so we use the Local data
            listComic = comicDAO.query();
        }


        //Fill the adapter with new Data
        adapter.swap(listComic);
    }
}
