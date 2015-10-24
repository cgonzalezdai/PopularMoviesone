package es.claudiogonzalez.popularmoviesone;

/**
 * Created by Claudio on 11/10/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.claudiogonzalez.popularmoviesone.favoritesDB.FavoritesEntry;

public class FavoritesDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Favorites.db";

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_FAVORITES =
            "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " (" +
                    FavoritesEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoritesEntry.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_BACKDROP_BASE64 + BLOB_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_POSTER_BASE64 + BLOB_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_ORIGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                    FavoritesEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_FAVORITES =
            "DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_FAVORITES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
