package es.claudiogonzalez.popularmoviesone.provider.favorites;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.claudiogonzalez.popularmoviesone.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favorites} table.
 */
public class FavoritesCursor extends AbstractCursor implements FavoritesModel {
    public FavoritesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavoritesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code movie_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieId() {
        String res = getStringOrNull(FavoritesColumns.MOVIE_ID);
        return res;
    }

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBackdropPath() {
        String res = getStringOrNull(FavoritesColumns.BACKDROP_PATH);
        return res;
    }

    /**
     * Get the {@code backdrop_base64} value.
     * Can be {@code null}.
     */
    @Nullable
    public byte[] getBackdropBase64() {
        byte[] res = getBlobOrNull(FavoritesColumns.BACKDROP_BASE64);
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOverview() {
        String res = getStringOrNull(FavoritesColumns.OVERVIEW);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReleaseDate() {
        String res = getStringOrNull(FavoritesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterPath() {
        String res = getStringOrNull(FavoritesColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code poster_base64} value.
     * Can be {@code null}.
     */
    @Nullable
    public byte[] getPosterBase64() {
        byte[] res = getBlobOrNull(FavoritesColumns.POSTER_BASE64);
        return res;
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVoteAverage() {
        String res = getStringOrNull(FavoritesColumns.VOTE_AVERAGE);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(FavoritesColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalTitle() {
        String res = getStringOrNull(FavoritesColumns.ORIGINAL_TITLE);
        return res;
    }
}
