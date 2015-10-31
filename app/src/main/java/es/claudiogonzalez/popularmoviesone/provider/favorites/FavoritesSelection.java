package es.claudiogonzalez.popularmoviesone.provider.favorites;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import es.claudiogonzalez.popularmoviesone.provider.base.AbstractSelection;

/**
 * Selection for the {@code favorites} table.
 */
public class FavoritesSelection extends AbstractSelection<FavoritesSelection> {
    @Override
    protected Uri baseUri() {
        return FavoritesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavoritesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavoritesCursor query(Context context) {
        return query(context, null);
    }


    public FavoritesSelection id(long... value) {
        addEquals("favorites." + FavoritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection idNot(long... value) {
        addNotEquals("favorites." + FavoritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection orderById(boolean desc) {
        orderBy("favorites." + FavoritesColumns._ID, desc);
        return this;
    }

    public FavoritesSelection orderById() {
        return orderById(false);
    }

    public FavoritesSelection movieId(String... value) {
        addEquals(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection movieIdNot(String... value) {
        addNotEquals(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection movieIdLike(String... value) {
        addLike(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection movieIdContains(String... value) {
        addContains(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection movieIdStartsWith(String... value) {
        addStartsWith(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection movieIdEndsWith(String... value) {
        addEndsWith(FavoritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavoritesSelection orderByMovieId(boolean desc) {
        orderBy(FavoritesColumns.MOVIE_ID, desc);
        return this;
    }

    public FavoritesSelection orderByMovieId() {
        orderBy(FavoritesColumns.MOVIE_ID, false);
        return this;
    }

    public FavoritesSelection backdropPath(String... value) {
        addEquals(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection backdropPathNot(String... value) {
        addNotEquals(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection backdropPathLike(String... value) {
        addLike(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection backdropPathContains(String... value) {
        addContains(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection backdropPathStartsWith(String... value) {
        addStartsWith(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection backdropPathEndsWith(String... value) {
        addEndsWith(FavoritesColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavoritesSelection orderByBackdropPath(boolean desc) {
        orderBy(FavoritesColumns.BACKDROP_PATH, desc);
        return this;
    }

    public FavoritesSelection orderByBackdropPath() {
        orderBy(FavoritesColumns.BACKDROP_PATH, false);
        return this;
    }

    public FavoritesSelection backdropBase64(byte[]... value) {
        addEquals(FavoritesColumns.BACKDROP_BASE64, value);
        return this;
    }

    public FavoritesSelection backdropBase64Not(byte[]... value) {
        addNotEquals(FavoritesColumns.BACKDROP_BASE64, value);
        return this;
    }


    public FavoritesSelection orderByBackdropBase64(boolean desc) {
        orderBy(FavoritesColumns.BACKDROP_BASE64, desc);
        return this;
    }

    public FavoritesSelection orderByBackdropBase64() {
        orderBy(FavoritesColumns.BACKDROP_BASE64, false);
        return this;
    }

    public FavoritesSelection overview(String... value) {
        addEquals(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection overviewNot(String... value) {
        addNotEquals(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection overviewLike(String... value) {
        addLike(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection overviewContains(String... value) {
        addContains(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection overviewStartsWith(String... value) {
        addStartsWith(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection overviewEndsWith(String... value) {
        addEndsWith(FavoritesColumns.OVERVIEW, value);
        return this;
    }

    public FavoritesSelection orderByOverview(boolean desc) {
        orderBy(FavoritesColumns.OVERVIEW, desc);
        return this;
    }

    public FavoritesSelection orderByOverview() {
        orderBy(FavoritesColumns.OVERVIEW, false);
        return this;
    }

    public FavoritesSelection releaseDate(String... value) {
        addEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateNot(String... value) {
        addNotEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateLike(String... value) {
        addLike(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateContains(String... value) {
        addContains(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection orderByReleaseDate(boolean desc) {
        orderBy(FavoritesColumns.RELEASE_DATE, desc);
        return this;
    }

    public FavoritesSelection orderByReleaseDate() {
        orderBy(FavoritesColumns.RELEASE_DATE, false);
        return this;
    }

    public FavoritesSelection posterPath(String... value) {
        addEquals(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection posterPathNot(String... value) {
        addNotEquals(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection posterPathLike(String... value) {
        addLike(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection posterPathContains(String... value) {
        addContains(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection posterPathStartsWith(String... value) {
        addStartsWith(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection posterPathEndsWith(String... value) {
        addEndsWith(FavoritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavoritesSelection orderByPosterPath(boolean desc) {
        orderBy(FavoritesColumns.POSTER_PATH, desc);
        return this;
    }

    public FavoritesSelection orderByPosterPath() {
        orderBy(FavoritesColumns.POSTER_PATH, false);
        return this;
    }

    public FavoritesSelection posterBase64(byte[]... value) {
        addEquals(FavoritesColumns.POSTER_BASE64, value);
        return this;
    }

    public FavoritesSelection posterBase64Not(byte[]... value) {
        addNotEquals(FavoritesColumns.POSTER_BASE64, value);
        return this;
    }


    public FavoritesSelection orderByPosterBase64(boolean desc) {
        orderBy(FavoritesColumns.POSTER_BASE64, desc);
        return this;
    }

    public FavoritesSelection orderByPosterBase64() {
        orderBy(FavoritesColumns.POSTER_BASE64, false);
        return this;
    }

    public FavoritesSelection voteAverage(String... value) {
        addEquals(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection voteAverageNot(String... value) {
        addNotEquals(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection voteAverageLike(String... value) {
        addLike(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection voteAverageContains(String... value) {
        addContains(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection voteAverageStartsWith(String... value) {
        addStartsWith(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection voteAverageEndsWith(String... value) {
        addEndsWith(FavoritesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavoritesSelection orderByVoteAverage(boolean desc) {
        orderBy(FavoritesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public FavoritesSelection orderByVoteAverage() {
        orderBy(FavoritesColumns.VOTE_AVERAGE, false);
        return this;
    }

    public FavoritesSelection title(String... value) {
        addEquals(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection titleNot(String... value) {
        addNotEquals(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection titleLike(String... value) {
        addLike(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection titleContains(String... value) {
        addContains(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection titleStartsWith(String... value) {
        addStartsWith(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection titleEndsWith(String... value) {
        addEndsWith(FavoritesColumns.TITLE, value);
        return this;
    }

    public FavoritesSelection orderByTitle(boolean desc) {
        orderBy(FavoritesColumns.TITLE, desc);
        return this;
    }

    public FavoritesSelection orderByTitle() {
        orderBy(FavoritesColumns.TITLE, false);
        return this;
    }

    public FavoritesSelection originalTitle(String... value) {
        addEquals(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection originalTitleNot(String... value) {
        addNotEquals(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection originalTitleLike(String... value) {
        addLike(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection originalTitleContains(String... value) {
        addContains(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection originalTitleStartsWith(String... value) {
        addStartsWith(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection originalTitleEndsWith(String... value) {
        addEndsWith(FavoritesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavoritesSelection orderByOriginalTitle(boolean desc) {
        orderBy(FavoritesColumns.ORIGINAL_TITLE, desc);
        return this;
    }

    public FavoritesSelection orderByOriginalTitle() {
        orderBy(FavoritesColumns.ORIGINAL_TITLE, false);
        return this;
    }
}
