package es.claudiogonzalez.popularmoviesone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.claudiogonzalez.popularmoviesone.favoritesDB.FavoritesEntry;

/**
 * Created by Claudio on 13/10/2015.
 */
public class FavoritesDBQuery {

    public JSONArray getFavorites(Context context) {

        FavoritesDBHelper helper = new FavoritesDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FavoritesEntry.COLUMN_NAME_MOVIE_ID,
                FavoritesEntry.COLUMN_NAME_TITLE,
                FavoritesEntry.COLUMN_NAME_ORIGINAL_TITLE,
                FavoritesEntry.COLUMN_NAME_RELEASE_DATE,
                FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE,
                FavoritesEntry.COLUMN_NAME_OVERVIEW,
                FavoritesEntry.COLUMN_NAME_POSTER_PATH,
                FavoritesEntry.COLUMN_NAME_POSTER_BASE64,
                FavoritesEntry.COLUMN_NAME_BACKDROP_PATH,
                FavoritesEntry.COLUMN_NAME_BACKDROP_BASE64
        };

        String selection = "";
        String[] selectionArgs = {};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE + " DESC";

        Cursor c = db.query(
                FavoritesEntry.TABLE_NAME,                // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        functions f = new functions();
        JSONArray Favorites = f.cur2Json(c);
        c.close();
        db.close();
        return Favorites;
    }

    public JSONObject getFavoriteMovie(Context context, String MovieId) throws JSONException {

        FavoritesDBHelper helper = new FavoritesDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FavoritesEntry.COLUMN_NAME_MOVIE_ID,
                FavoritesEntry.COLUMN_NAME_TITLE,
                FavoritesEntry.COLUMN_NAME_ORIGINAL_TITLE,
                FavoritesEntry.COLUMN_NAME_RELEASE_DATE,
                FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE,
                FavoritesEntry.COLUMN_NAME_OVERVIEW,
                FavoritesEntry.COLUMN_NAME_POSTER_PATH,
                FavoritesEntry.COLUMN_NAME_POSTER_BASE64,
                FavoritesEntry.COLUMN_NAME_BACKDROP_PATH,
                FavoritesEntry.COLUMN_NAME_BACKDROP_BASE64
        };

        String selection = "id=?";
        String[] selectionArgs = {MovieId};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = FavoritesEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor c = db.query(
                FavoritesEntry.TABLE_NAME,                // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        functions f = new functions();
        JSONObject Favorite = f.cur2Json(c).getJSONObject(0);
        c.close();
        db.close();
        return Favorite;
    }

    public boolean insertFavorite(Context context, String MovieId,String MovieBackdrop,String MoviePoster,String MovieTitle,String MovieOriginalTitle,String MovieRelease,String MovieVoteAverage,String MovieOverview,ImageView backdrop,ImageView poster) {
        // Gets the data repository in write mode
        functions f = new functions();
        FavoritesDBHelper helper = new FavoritesDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FavoritesEntry.COLUMN_NAME_MOVIE_ID, MovieId);
        values.put(FavoritesEntry.COLUMN_NAME_TITLE, MovieTitle);
        values.put(FavoritesEntry.COLUMN_NAME_ORIGINAL_TITLE, MovieOriginalTitle);
        values.put(FavoritesEntry.COLUMN_NAME_RELEASE_DATE, MovieRelease);
        values.put(FavoritesEntry.COLUMN_NAME_OVERVIEW, MovieOverview);
        values.put(FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE, MovieVoteAverage);
        values.put(FavoritesEntry.COLUMN_NAME_POSTER_PATH, MoviePoster);
        values.put(FavoritesEntry.COLUMN_NAME_POSTER_BASE64, f.ImageViewToBase64(poster));
        values.put(FavoritesEntry.COLUMN_NAME_BACKDROP_PATH, MovieBackdrop);
        values.put(FavoritesEntry.COLUMN_NAME_BACKDROP_BASE64, f.ImageViewToBase64(backdrop));

        // Insert the new row, returning true if the primary key value of the new row > 0
        return db.insert(FavoritesEntry.TABLE_NAME, null, values) > 0;

    }

    public boolean deleteFavorite(Context context, String MovieId) {
        FavoritesDBHelper helper = new FavoritesDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = FavoritesEntry.COLUMN_NAME_MOVIE_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { MovieId };
        // Issue SQL statement.
        return db.delete(FavoritesEntry.TABLE_NAME, selection, selectionArgs) > 0;
    }

    public boolean isFavoriteMovie(Context context, String MovieId) {

        FavoritesDBHelper helper = new FavoritesDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FavoritesEntry._ID,
                FavoritesEntry.COLUMN_NAME_MOVIE_ID,
                FavoritesEntry.COLUMN_NAME_TITLE,
                FavoritesEntry.COLUMN_NAME_ORIGINAL_TITLE,
                FavoritesEntry.COLUMN_NAME_RELEASE_DATE,
                FavoritesEntry.COLUMN_NAME_VOTE_AVERAGE,
                FavoritesEntry.COLUMN_NAME_OVERVIEW,
                FavoritesEntry.COLUMN_NAME_POSTER_PATH,
                FavoritesEntry.COLUMN_NAME_BACKDROP_PATH
        };

        String selection = "id=?";
        String[] selectionArgs = {MovieId};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = FavoritesEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor c = db.query(
                FavoritesEntry.TABLE_NAME,                // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        int resp = c.getCount();
        c.close();
        db.close();
        return resp > 0;
    }
}
