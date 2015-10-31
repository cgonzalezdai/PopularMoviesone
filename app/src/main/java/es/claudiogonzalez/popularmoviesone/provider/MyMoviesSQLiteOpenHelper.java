package es.claudiogonzalez.popularmoviesone.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import es.claudiogonzalez.popularmoviesone.BuildConfig;
import es.claudiogonzalez.popularmoviesone.provider.favorites.FavoritesColumns;

public class MyMoviesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MyMoviesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "MyMovies.db";
    private static final int DATABASE_VERSION = 1;
    private static MyMoviesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final MyMoviesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE IF NOT EXISTS "
            + FavoritesColumns.TABLE_NAME + " ( "
            + FavoritesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoritesColumns.MOVIE_ID + " TEXT, "
            + FavoritesColumns.BACKDROP_PATH + " TEXT, "
            + FavoritesColumns.BACKDROP_BASE64 + " BLOB, "
            + FavoritesColumns.OVERVIEW + " TEXT, "
            + FavoritesColumns.RELEASE_DATE + " TEXT, "
            + FavoritesColumns.POSTER_PATH + " TEXT, "
            + FavoritesColumns.POSTER_BASE64 + " BLOB, "
            + FavoritesColumns.VOTE_AVERAGE + " TEXT, "
            + FavoritesColumns.TITLE + " TEXT, "
            + FavoritesColumns.ORIGINAL_TITLE + " TEXT "
            + " );";

    // @formatter:on

    public static MyMoviesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MyMoviesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MyMoviesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new MyMoviesSQLiteOpenHelper(context);
    }

    private MyMoviesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MyMoviesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MyMoviesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new MyMoviesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MyMoviesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MyMoviesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
