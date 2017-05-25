package william.miranda.marvel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import william.miranda.marvel.R;
import william.miranda.marvel.model.Comic;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private List<Comic> data;

    public ComicAdapter() {
        this.data = new ArrayList<>();
    }

    /**
     * Method used to create a new ViewHolder for an Entry
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout for a new "Item"
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comic, parent, false);

        //Create the associated ViewHolder
        return new ComicViewHolder(view);
    }

    /**
     * Method used to set data on the ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        Comic comic = data.get(position);
        holder.setData(comic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        private final TextView comicTitle;

        /**
         * Create a viewHolder and bind the Views
         * @param itemView
         */
        public ComicViewHolder(View itemView) {
            super(itemView);
            comicTitle = (TextView) itemView.findViewById(R.id.comic_title);
        }

        /**
         * Fill POJO data in the Views
         * @param comic
         */
        private void setData(Comic comic) {
            comicTitle.setText(comic.getTitle());
        }
    }

}
