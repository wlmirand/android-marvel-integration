package william.miranda.marvel.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import william.miranda.marvel.api.response.UrlResponse;
import william.miranda.marvel.model.Comic;
import william.miranda.marvel.storage.db.tables.ComicTable;

public class ComicDAO extends BaseDAO<Comic> {

    public ComicDAO(Context context) {
        super(context);
        table = ComicTable.TABLE_NAME;
    }

    /**
     * Get all Comics from the DB
     * @return
     */
    @Override
    public List<Comic> query() {
        List<Comic> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            list.add(makeFromCursor(cursor));
        }

        db.close();
        return list;
    }

    /**
     * Get a Comic By ID from DB
     * @param id
     * @return
     */
    @Override
    public Comic query(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = ComicTable.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);

        //Returns the first entry if found
        while (cursor.moveToNext()) {
            return makeFromCursor(cursor);
        }

        db.close();
        return null;
    }

    /**
     * Insert a Comic
     * @param data
     * @return
     */
    @Override
    public long insert(Comic data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = makeContentValues(data);
        long id = db.insert(table, null, contentValues);

        db.close();
        return id;
    }

    /**
     * Insert a List of Comics
     * @param list
     */
    @Override
    public void insertList(List<Comic> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Comic comic : list) {
            ContentValues contentValues = makeContentValues(comic);
            db.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }

        db.close();
    }

    /**
     * Update a Comic
     * @param data
     * @return
     */
    @Override
    public Comic update(Comic data) {
        String id = String.valueOf(data.getId());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = makeContentValues(data);

        db.update(table, contentValues, ComicTable.COLUMN_ID + " = ?", new String[] { id });
        db.close();
        return data;
    }

    /**
     * Delete a Comic
     * @param data
     * @return
     */
    @Override
    public boolean delete(Comic data) {
        String id = String.valueOf(data.getId());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int res = db.delete(table, ComicTable.COLUMN_ID + " = ?", new String[] { id });
        return res != 0;
    }

    /**
     * Delete a List of Comics
     * @param list
     * @return
     */
    @Override
    public boolean deleteList(List<Comic> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        String[] ids = new String[list.size()];
        for (int i=0 ; i<list.size() ; i++) {
            ids[i] = String.valueOf(list.get(i).getId());
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = String.format(ComicTable.COLUMN_ID + " in (%s)",
                new Object[] { TextUtils.join(",", Collections.nCopies(ids.length, "?")) });
        int res = db.delete(table, whereClause, ids);
        return res != 0;
    }

    /**
     * Create the content values from a Comic
     * @param data
     * @return
     */
    private static ContentValues makeContentValues(Comic data) {
        ContentValues cv = new ContentValues();
        cv.put(ComicTable.COLUMN_ID, data.getId());
        cv.put(ComicTable.COLUMN_TITLE, data.getTitle());
        cv.put(ComicTable.COLUMN_DESCRIPTION, data.getDescription());
        cv.put(ComicTable.COLUMN_FORMAT, data.getFormat());
        cv.put(ComicTable.COLUMN_PAGE_COUNT, data.getPageCount());
        cv.put(ComicTable.COLUMN_THUMBNAIL_URL, data.getThumbnailUrl());

        //Add formatted Urls
        cv.put(ComicTable.COLUMN_URLS, convertUrlsToString(data.getUrls()));

        return cv;
    }

    /**
     * Create a Comic from the Cursor
     * @param cursor
     * @return
     */
    private static Comic makeFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ComicTable.COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndex(ComicTable.COLUMN_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(ComicTable.COLUMN_DESCRIPTION));
        String format = cursor.getString(cursor.getColumnIndex(ComicTable.COLUMN_FORMAT));
        int pageCount = cursor.getInt(cursor.getColumnIndex(ComicTable.COLUMN_PAGE_COUNT));
        String urls = cursor.getString(cursor.getColumnIndex(ComicTable.COLUMN_URLS));
        String thumbnailUrl = cursor.getString(cursor.getColumnIndex(ComicTable.COLUMN_THUMBNAIL_URL));

        //parse the URLs from the DB to Array of String
        String[] urlArray = urls != null ? urls.split("\\|") : null;

        return new Comic(id, title, description, format, pageCount, urlArray, thumbnailUrl);
    }

    private static String convertUrlsToString(String[] urls) {
        if (urls == null || urls.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < urls.length; i++) {

            String url = urls[i];
            sb.append(url);

            if (i < urls.length - 1) {
                sb.append('|');
            }
        }
        return sb.toString();
    }
}
