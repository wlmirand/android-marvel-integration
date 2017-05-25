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
        adapter = new ComicAdapter();
        new ComicsTask(100, 0, this).execute();
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    /**
     * Callback to handle new Data from ComicsTask
     * @param bodyResponse
     */
    @Override
    public void handleResult(ComicDataWrapperResponse bodyResponse) {
        if (bodyResponse.getCode() == ApiWrapper.HTTP_SUCCESS_CODE) {
            List<Comic> listComic = new ArrayList<>();
            //If Success, we iterate all ComicResponse and create our comic POJO
            for (ComicResponse item : bodyResponse.getData().getResults()) {
                listComic.add(new Comic(item));
            }

            adapter.swap(listComic);
        }
    }
}
