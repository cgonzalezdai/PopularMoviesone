package es.claudiogonzalez.popularmoviesone.provider.favorites;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.claudiogonzalez.popularmoviesone.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favorites} table.
 */
public class FavoritesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavoritesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavoritesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavoritesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FavoritesContentValues putMovieId(@Nullable String value) {
        mContentValues.put(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesContentValues putMovieIdNull() {
        mContentValues.putNull(FavoritesColumns.MOVIE_ID);
        return this;
    }

    public FavoritesContentValues putBackdropPath(@Nullable String value) {
        mContentValues.put(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesContentValues putBackdropPathNull() {
        mContentValues.putNull(FavoritesColumns.BACKDROP_PATH);
        return this;
    }

    public FavoritesContentValues putBackdropBase64(@Nullable String value) {
        mContentValues.put(FavoritesColumns.BACKDROP_BASE64, value);
        return this;
    }

    public FavoritesContentValues putBackdropBase64Null() {
        mContentValues.putNull(FavoritesColumns.BACKDROP_BASE64);
        return this;
    }

    public FavoritesContentValues putOverview(@Nullable String value) {
        mContentValues.put(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesContentValues putOverviewNull() {
        mContentValues.putNull(FavoritesColumns.OVERVIEW);
        return this;
    }

    public FavoritesContentValues putReleaseDate(@Nullable String value) {
        mContentValues.put(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesContentValues putReleaseDateNull() {
        mContentValues.putNull(FavoritesColumns.RELEASE_DATE);
        return this;
    }

    public FavoritesContentValues putPosterPath(@Nullable String value) {
        mContentValues.put(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesContentValues putPosterPathNull() {
        mContentValues.putNull(FavoritesColumns.POSTER_PATH);
        return this;
    }

    public FavoritesContentValues putPosterBase64(@Nullable String value) {
        mContentValues.put(FavoritesColumns.POSTER_BASE64, value);
        return this;
    }

    public FavoritesContentValues putPosterBase64Null() {
        mContentValues.putNull(FavoritesColumns.POSTER_BASE64);
        return this;
    }

    public FavoritesContentValues putVoteAverage(@Nullable String value) {
        mContentValues.put(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesContentValues putVoteAverageNull() {
        mContentValues.putNull(FavoritesColumns.VOTE_AVERAGE);
        return this;
    }

    public FavoritesContentValues putTitle(@Nullable String value) {
        mContentValues.put(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesContentValues putTitleNull() {
        mContentValues.putNull(FavoritesColumns.TITLE);
        return this;
    }

    public FavoritesContentValues putOriginalTitle(@Nullable String value) {
        mContentValues.put(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesContentValues putOriginalTitleNull() {
        mContentValues.putNull(FavoritesColumns.ORIGINAL_TITLE);
        return this;
    }
}
