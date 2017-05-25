package william.miranda.marvel.storage.db.tables;

import android.database.sqlite.SQLiteDatabase;

public class ComicTable {
    public static final String TABLE_NAME = "comics";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_THUMBNAIL_URL + " text not null"
            + ");";

    public static void createTable(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Since we have only this version, we recreate the DB, but later we may just Alter table
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        createTable(database);
    }
}