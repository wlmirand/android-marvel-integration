package william.miranda.marvel.storage.db;

import android.content.Context;

import java.util.List;

import william.miranda.marvel.model.Comic;

public abstract class BaseDAO<T> {

    protected Context mContext;
    protected DatabaseHelper dbHelper;
    protected String table;

    public BaseDAO(Context context) {
        mContext = context;
        dbHelper = new DatabaseHelper(mContext);
    }

    public abstract List<T> query();
    public abstract T query(int id);
    public abstract long insert(T data);
    public abstract void insertList(List<Comic> data);
    public abstract T update(T data);
    public abstract boolean delete(T data);
    public abstract boolean deleteList(List<T> list);
}
