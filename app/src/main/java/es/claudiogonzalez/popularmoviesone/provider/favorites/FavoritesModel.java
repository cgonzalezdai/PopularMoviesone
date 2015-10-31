package es.claudiogonzalez.popularmoviesone.provider.favorites;

import android.support.annotation.Nullable;

import es.claudiogonzalez.popularmoviesone.provider.base.BaseModel;

/**
 * my favorite movies.
 */
public interface FavoritesModel extends BaseModel {

    /**
     * Get the {@code movie_id} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieId();

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBackdropPath();

    /**
     * Get the {@code backdrop_base64} value.
     * Can be {@code null}.
     */
    @Nullable
    byte[] getBackdropBase64();

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOverview();

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleaseDate();

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterPath();

    /**
     * Get the {@code poster_base64} value.
     * Can be {@code null}.
     */
    @Nullable
    byte[] getPosterBase64();

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVoteAverage();

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalTitle();
}
