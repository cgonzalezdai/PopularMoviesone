package es.claudiogonzalez.popularmoviesone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        Intent i = getActivity().getIntent();
        String MovieBackdrop = i.getStringExtra("MovieBackdrop");
        String MovieTitle = i.getStringExtra("MovieTitle");
        String MovieRelease = i.getStringExtra("MovieRelease");
        String MovieVoteAverage = i.getStringExtra("MovieVoteAverage");
        String MovieOverview = i.getStringExtra("MovieOverview");

        ImageView poster = (ImageView) rootView.findViewById(R.id.imageViewPoster);
        poster.setAdjustViewBounds(true);
        String url = "http://image.tmdb.org/t/p/w500/" + MovieBackdrop;
        Picasso.with(getContext()).load(url).into(poster);

        ((TextView) rootView.findViewById(R.id.textViewTitle)).setText(MovieTitle);
        ((TextView) rootView.findViewById(R.id.textViewRelease)).setText("(" + MovieRelease + ")");
        ((TextView) rootView.findViewById(R.id.textViewRating)).setText(MovieVoteAverage);
        ((TextView) rootView.findViewById(R.id.textViewOverview)).setText(MovieOverview);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent i = getActivity().getIntent();
        getActivity().setTitle(i.getStringExtra("MovieTitle"));
    }
}
