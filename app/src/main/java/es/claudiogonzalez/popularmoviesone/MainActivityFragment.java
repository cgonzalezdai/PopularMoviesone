package es.claudiogonzalez.popularmoviesone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

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
public class MainActivityFragment extends Fragment {

    private ArrayList<JSONObject> MoviesList = new ArrayList<>();
    private ImageAdapter mMoviesAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        createSpinner(rootView);
        createGridView(rootView);

        return rootView;
    }

    public void createGridView(View rootView) {
        mMoviesAdapter = new ImageAdapter(getActivity());
        GridView gridView = (GridView) rootView.findViewById(R.id.GridViewPosters);
        gridView.setAdapter(mMoviesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetail = new Intent(getActivity(), DetailActivity.class);
                try {
                    showDetail.putExtra("MovieId", MoviesList.get(position).getString("id"));
                    showDetail.putExtra("MoviePoster", MoviesList.get(position).getString("poster_path"));
                    showDetail.putExtra("MovieBackdrop", MoviesList.get(position).getString("backdrop_path"));
                    showDetail.putExtra("MovieTitle", MoviesList.get(position).getString("title"));
                    showDetail.putExtra("MovieOriginalTitle", MoviesList.get(position).getString("original_title"));
                    showDetail.putExtra("MovieRelease", MoviesList.get(position).getString("release_date"));
                    showDetail.putExtra("MovieVoteAverage", MoviesList.get(position).getString("vote_average"));
                    showDetail.putExtra("MovieOverview", MoviesList.get(position).getString("overview"));
                    if (MoviesList.get(position).has("poster_BASE64") && MoviesList.get(position).has("backdrop_BASE64")) {
                        showDetail.putExtra("offline", true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(showDetail);
            }
        });
    }

    public void createSpinner(View rootView) {
        Spinner spinner = (Spinner) rootView.findViewById(R.id.SpinnerSortBy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.find_by_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                ListMoviesOrderBy(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void ShowFavorites() {
        FavoritesDBQuery fq = new FavoritesDBQuery();
        JSONArray MoviesArray = fq.getFavorites(getContext());
        if (MoviesArray != null) {
            MoviesList.clear();
            for(int i = 0; i < MoviesArray.length(); i++) {
                try {
                    JSONObject movie = MoviesArray.getJSONObject(i);
                    MoviesList.add(movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mMoviesAdapter.notifyDataSetChanged();
        }
    }

    public void ListMoviesOrderBy(String item) {
        if (item.equals(getString(R.string.Favorites))) {
            ShowFavorites();
        } else {
            String query = "";
            if (item.equals(getString(R.string.HighestRated))) {
                query = "top_rated?";
            } else if (item.equals(getString(R.string.MostPopular))) {
                query = "popular?";
            }
            MovieDBTask LoadMovies = new MovieDBTask();
            LoadMovies.execute(query);
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return MoviesList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setAdjustViewBounds(true);
            } else {
                imageView = (ImageView) convertView;
            }
            if (MoviesList.get(position).has("poster_BASE64")) {
                functions f = new functions();
                try {
                    Bitmap image = f.base64ToBitmap(MoviesList.get(position).getString("poster_BASE64"));
                    imageView.setImageBitmap(image);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                String url = "";
                try {
                    url = "http://image.tmdb.org/t/p/w342/" + MoviesList.get(position).getString("poster_path");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Picasso.with(mContext)
                        .load(url)
                        .into(imageView);
            }
            return imageView;
        }

    }

    public class MovieDBTask extends AsyncTask<String,Void,JSONArray> {

        private final String LOG_TAG = MovieDBTask.class.getSimpleName();

        @Override
        protected void onPostExecute(JSONArray MoviesArray) {
            if (MoviesArray != null) {
                MoviesList.clear();
                for(int i = 0; i < MoviesArray.length(); i++) {
                    try {
                        JSONObject movie = MoviesArray.getJSONObject(i);
                        MoviesList.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mMoviesAdapter.notifyDataSetChanged();
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
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL + QUERY).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, api_key)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
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
