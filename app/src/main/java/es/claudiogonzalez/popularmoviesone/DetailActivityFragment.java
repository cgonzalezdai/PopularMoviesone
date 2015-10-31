package es.claudiogonzalez.popularmoviesone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private ArrayList<JSONObject> TrailerList = new ArrayList<>();
    private ArrayList<JSONObject> ReviewList = new ArrayList<>();
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private ShareActionProvider mShareActionProvider;
    private String TrailerKey;
    private Boolean offline;
    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String MovieId;
        String MovieBackdrop = "";
        String MovieBackdrop64 = "";
        String MoviePoster = "";
        String MoviePoster64 = "";
        String MovieTitle = "";
        String MovieRelease = "";
        String MovieVoteAverage = "";
        String MovieOverview = "";
        if (getResources().getBoolean(R.bool.dual_pane) && getArguments().size()>0) {
        //tablet
            Bundle bundle = getArguments();
            MovieId = bundle.getString("MovieId");
            offline = bundle.getBoolean("offline", false);
            if (offline) {
                FavoritesDBQuery fq = new FavoritesDBQuery();
                try {
                    JSONObject favoriteMovie = fq.getFavoriteMovie(getContext(), MovieId);
                    MovieBackdrop = favoriteMovie.getString("backdrop_path");
                    MovieBackdrop64 = favoriteMovie.getString("backdrop_BASE64");
                    MoviePoster = favoriteMovie.getString("poster_path");
                    MoviePoster64 = favoriteMovie.getString("poster_BASE64");
                    MovieTitle = favoriteMovie.getString("title");
                    MovieRelease =  "(" + favoriteMovie.getString("release_date") + ")";
                    MovieVoteAverage = favoriteMovie.getString("vote_average");
                    MovieOverview = favoriteMovie.getString("overview");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                MovieBackdrop = bundle.getString("MovieBackdrop");
                MovieBackdrop64 = "";
                MoviePoster = bundle.getString("MoviePoster");
                MoviePoster64 = "";
                MovieTitle = bundle.getString("MovieTitle");
                MovieRelease =  "(" + bundle.getString("MovieRelease") + ")";
                MovieVoteAverage = bundle.getString("MovieVoteAverage");
                MovieOverview = bundle.getString("MovieOverview");
            }
        } else {
        //phone
            Intent i = getActivity().getIntent();
            MovieId = i.getStringExtra("MovieId");
            offline = i.getBooleanExtra("offline", false);
            if (offline) {
                FavoritesDBQuery fq = new FavoritesDBQuery();
                try {
                    JSONObject favoriteMovie = fq.getFavoriteMovie(getContext(), MovieId);
                    MovieBackdrop = favoriteMovie.getString("backdrop_path");
                    MovieBackdrop64 = favoriteMovie.getString("backdrop_BASE64");
                    MoviePoster = favoriteMovie.getString("poster_path");
                    MoviePoster64 = favoriteMovie.getString("poster_BASE64");
                    MovieTitle = favoriteMovie.getString("title");
                    MovieRelease =  "(" + favoriteMovie.getString("release_date") + ")";
                    MovieVoteAverage = favoriteMovie.getString("vote_average");
                    MovieOverview = favoriteMovie.getString("overview");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                MovieBackdrop = i.getStringExtra("MovieBackdrop");
                MovieBackdrop64 = "";
                MoviePoster = i.getStringExtra("MoviePoster");
                MoviePoster64 = "";
                MovieTitle = i.getStringExtra("MovieTitle");
                MovieRelease = "(" + i.getStringExtra("MovieRelease") + ")";
                MovieVoteAverage = i.getStringExtra("MovieVoteAverage");
                MovieOverview = i.getStringExtra("MovieOverview");
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView backdrop = (ImageView) rootView.findViewById(R.id.imageViewBackdrop);
        createImageView(backdrop, offline, MovieBackdrop64, MovieBackdrop);

        ImageView poster = (ImageView) rootView.findViewById(R.id.imageViewPoster);
        createImageView(poster, offline, MoviePoster64, MoviePoster);

        Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
        backdrop.startAnimation(scale);

        ((TextView) rootView.findViewById(R.id.textViewTitle)).setText(MovieTitle);
        ((TextView) rootView.findViewById(R.id.textViewRelease)).setText(MovieRelease);
        ((TextView) rootView.findViewById(R.id.textViewRating)).setText(MovieVoteAverage);
        ((TextView) rootView.findViewById(R.id.textViewOverview)).setText(MovieOverview);

        createGridViewTrailer(rootView, MovieId);
        createGridViewReview(rootView, MovieId);
        createButtonFavorite(rootView, MovieId);


        ((ScrollView) rootView.findViewById(R.id.ScrollViewDetail)).smoothScrollTo(0, 0);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareTrailerIntent(TrailerKey));
    }

    private Intent createShareTrailerIntent(String key) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://youtu.be/" + key);
        return shareIntent;
    }

    public void createImageView(ImageView imageView, Boolean offline, String image64, String image) {
        imageView.setAdjustViewBounds(true);
        if (offline) {
            functions f = new functions();
            Bitmap bitmap = f.base64ToBitmap(image64);
            imageView.setImageBitmap(bitmap);
        } else {
            String url = "http://image.tmdb.org/t/p/w342/" + image;
            Picasso.with(getContext()).load(url).into(imageView);
        }
    }

    public void createGridViewTrailer(View rootView, String MovieId) {
        VideoDBTask LoadTrailer = new VideoDBTask();
        LoadTrailer.execute(MovieId, "/videos");
        mTrailerAdapter = new TrailerAdapter(getActivity(), TrailerList);
        GridView gridViewTrailer = (GridView) rootView.findViewById(R.id.GridViewTrailers);
        gridViewTrailer.setAdapter(mTrailerAdapter);
        gridViewTrailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    playTrailer(Uri.parse("https://youtu.be/" + TrailerList.get(position).getString("key")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createGridViewReview(View rootView, String MovieId) {
        VideoDBTask LoadReview = new VideoDBTask();
        LoadReview.execute(MovieId, "/reviews");
        mReviewAdapter = new ReviewAdapter(getActivity(), ReviewList);
        GridView gridViewReview = (GridView) rootView.findViewById(R.id.GridViewReviews);
        gridViewReview.setAdapter(mReviewAdapter);
        gridViewReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    playReview(Uri.parse(ReviewList.get(position).getString("url")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createButtonFavorite(View rootView, String MovieId) {
        final FavoritesDBQuery fq = new FavoritesDBQuery();
        final Button buttonFavorite = (Button) rootView.findViewById(R.id.buttonFavorite);
        final ImageView backdrop = (ImageView) rootView.findViewById(R.id.imageViewBackdrop);
        final ImageView poster = (ImageView) rootView.findViewById(R.id.imageViewPoster);

        if (fq.isFavoriteMovie(getContext(), MovieId)){
            buttonFavorite.setText(R.string.i_love_it);
        } else {
            buttonFavorite.setText(R.string.favorite);
        }
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MovieId, MovieBackdrop, MoviePoster, MovieTitle, MovieRelease, MovieVoteAverage, MovieOverview;
                if (getResources().getBoolean(R.bool.dual_pane)) {
                    //tablet
                    Bundle bundle = getArguments();
                    MovieId = bundle.getString("MovieId");
                    MovieBackdrop = bundle.getString("MovieBackdrop");
                    MoviePoster = bundle.getString("MoviePoster");
                    MovieTitle = bundle.getString("MovieTitle");
                    MovieRelease = bundle.getString("MovieRelease");
                    MovieVoteAverage = bundle.getString("MovieVoteAverage");
                    MovieOverview = bundle.getString("MovieOverview");
                } else {
                    //phone
                    Intent i = getActivity().getIntent();
                    MovieId = i.getStringExtra("MovieId");
                    MovieBackdrop = i.getStringExtra("MovieBackdrop");
                    MoviePoster = i.getStringExtra("MoviePoster");
                    MovieTitle = i.getStringExtra("MovieTitle");
                    MovieRelease = i.getStringExtra("MovieRelease");
                    MovieVoteAverage = i.getStringExtra("MovieVoteAverage");
                    MovieOverview = i.getStringExtra("MovieOverview");
                }
                if (fq.isFavoriteMovie(getContext(), MovieId)) {
                    fq.deleteFavorite(getContext(), MovieId);
                    buttonFavorite.setText(R.string.favorite);
                } else {
                    fq.insertFavorite(getContext(), MovieId, MovieBackdrop, MoviePoster, MovieTitle, MovieRelease, MovieVoteAverage, MovieOverview, backdrop, poster);
                    buttonFavorite.setText(R.string.i_love_it);
                }
            }
        });
    }

    public class ReviewAdapter extends ArrayAdapter<JSONObject> {
        public ReviewAdapter(Context context, ArrayList<JSONObject> reviews) {
            super(context, 0, reviews);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            JSONObject review = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
            }
            // Lookup view for data population
            TextView ra = (TextView) convertView.findViewById(R.id.reviewAuthor);
            TextView rc = (TextView) convertView.findViewById(R.id.reviewContent);
            // Populate the data into the template view using the data object
            try {
                ra.setText(review.getString("author"));
                rc.setText(review.getString("content"));
                if (review.getString("content").length()>=50) {
                    rc.setText(review.getString("content").substring(0,50).concat("..."));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void playReview(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public class TrailerAdapter extends ArrayAdapter<JSONObject> {
        public TrailerAdapter(Context context, ArrayList<JSONObject> trailers) {
            super(context, 0, trailers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            JSONObject trailer = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
            }
            // Lookup view for data population
            TextView tr = (TextView) convertView.findViewById(R.id.trailer);
            // Populate the data into the template view using the data object
            try {
                tr.setText(trailer.getString("name"));
                if (trailer.getString("name").length()>=15) {
                    tr.setText(trailer.getString("name").substring(0, 15).concat("..."));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void playTrailer(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent i = getActivity().getIntent();
        getActivity().setTitle(i.getStringExtra("MovieTitle"));
    }

    public class VideoDBTask extends AsyncTask<String,Void,JSONArray> {

        private final String LOG_TAG = VideoDBTask.class.getSimpleName();

        @Override
        protected void onPostExecute(JSONArray MoviesArray) {
            if (MoviesArray != null) {
                try {
                    if (MoviesArray.getJSONObject(0).has("key")){
                        TrailerList.clear();
                        for(int i = 0; i < MoviesArray.length(); i++) {
                            JSONObject movie = MoviesArray.getJSONObject(i);
                            TrailerList.add(movie);
                            if(i==0) {
                                TrailerKey = movie.getString("key");
                            }
                        }
                        mTrailerAdapter.notifyDataSetChanged();
                        if(mShareActionProvider != null) {
                            mShareActionProvider.setShareIntent(createShareTrailerIntent(TrailerKey));
                        }
                    } else  {
                        ReviewList.clear();
                        for(int i = 0; i < MoviesArray.length(); i++) {
                            JSONObject movie = MoviesArray.getJSONObject(i);
                            ReviewList.add(movie);
                        }
                        mReviewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        protected JSONArray doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            String JsonStr = null;
            String api_key = getResources().getString(R.string.api_key);
            BufferedReader reader = null;

            try {

                final String BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String QUERY = params[0];
                final String POSTQUERY = params[1];
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL + QUERY + POSTQUERY).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, api_key)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JsonStr = buffer.toString();
                Log.v(LOG_TAG,"message: " + JsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return this.getDataFromJson(JsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private JSONArray getDataFromJson(String JsonStr)
                throws JSONException {

            final String OWM_RESULTS = "results";
            JSONObject Json = new JSONObject(JsonStr);
            return Json.getJSONArray(OWM_RESULTS);

        }
    }
}
