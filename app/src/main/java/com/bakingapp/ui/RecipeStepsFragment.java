package com.bakingapp.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.bakingapp.AppConstants;
import com.bakingapp.POJO.RecipeSteps;
import com.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepsFragment} interface
 * to handle interaction events.
 * Use the {@link RecipeStepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepsFragment extends Fragment {

    private RecipeSteps recipeSteps;
    private ImageView imageView;
    private TextView stepsDesc;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    //    private ProgressBar exoplayerProgressBar;
    private boolean playerInitialised;
    private boolean isPlaying = true;
    private boolean isOnPaused = false;
    private long position;
    private ImageView prev;
    private ImageView next;
    private static boolean hideNexButton;

    private OnFragmentInteractionListener mListener;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecipeStepsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepsFragment newInstance(RecipeSteps param1) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.STEPS_INTENT, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeSteps = (RecipeSteps) getArguments().get(AppConstants.STEPS_INTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mListener != null) {
            mListener.setActionBar(getString(R.string.recipe_steps));
        }

        if (savedInstanceState != null) {
            recipeSteps = (RecipeSteps) savedInstanceState.getSerializable(AppConstants.STEPS_INTENT);
            position = savedInstanceState.getLong(AppConstants.EXO_POSITION);
            isPlaying = savedInstanceState.getBoolean(AppConstants.EXO_PAUSED);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_steps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsDesc = (TextView) view.findViewById(R.id.stepDesc);
        imageView = (ImageView) view.findViewById(R.id.stepsVideo);
//        exoplayerProgressBar = view.findViewById(R.id.exoPlayerProgressBar);
        simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);

        prev = (ImageView) view.findViewById(R.id.leftbutton);
        next = (ImageView) view.findViewById(R.id.rightButton);

        stepsDesc.setText(recipeSteps.getDescription());

        if (!recipeSteps.getVideoURL().equalsIgnoreCase("") && recipeSteps.getVideoURL() != null) {

            setupExoPlayer(Uri.parse(recipeSteps.getVideoURL()));

        } else if (!recipeSteps.getThumbnailURL().equalsIgnoreCase("") && recipeSteps.getThumbnailURL() != null) {
            String thumbailUrl = recipeSteps.getThumbnailURL();
            if (thumbailUrl.contains(".mp4")) {
                setupExoPlayer(Uri.parse(thumbailUrl));
            }

            if (thumbailUrl.contains("jpeg") || thumbailUrl.contains("jpg") || thumbailUrl.contains("png")) {
                Picasso.get().load(thumbailUrl).into(imageView);
                imageView.setVisibility(View.VISIBLE);

            }

        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
//            exoplayerProgressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

        }

        if (prev != null) {
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPreviousClicked(recipeSteps.getStepId());
                    hideNexButton = false;
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideNexButton = mListener.onNextClicked(recipeSteps.getStepId());
                }
            });

            setButtonsVisibility(hideNexButton);
        }
    }

    private void setButtonsVisibility(boolean hideNexButton) {
        if (recipeSteps.getStepId() == 0) {
            prev.setVisibility(View.INVISIBLE);
        } else {
            prev.setVisibility(View.VISIBLE);
        }

        if (hideNexButton) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
        }

    }

    private void setupExoPlayer(Uri mediaUri) {
        simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.loadvideo));
        initializePlayer(mediaUri);
        simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                if (playbackState == ExoPlayer.STATE_BUFFERING) {
////                    exoplayerProgressBar.setVisibility(View.VISIBLE);
//                } else {
//                    exoplayerProgressBar.setVisibility(View.INVISIBLE);
//                }

                if (playbackState == ExoPlayer.STATE_ENDED) {
                    simpleExoPlayerView.getPlayer().seekTo(0);
                    simpleExoPlayer.setPlayWhenReady(false);
                }
                if (playWhenReady && playbackState == simpleExoPlayer.STATE_READY) {
                    isPlaying = true;
                } else {
                    // player paused in any state
                    if (!isOnPaused) {
                        isPlaying = !isPlaying;
                    }
                }
                if (playbackState == simpleExoPlayer.STATE_ENDED) {
                    isPlaying = false;
                    simpleExoPlayer.setPlayWhenReady(isPlaying);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
    }

    private void initializePlayer(Uri mediaUri) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        String userAgent = Util.getUserAgent(getContext(), "letsbake");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);
        simpleExoPlayer.prepare(mediaSource);

        if (position > 0l) {
            simpleExoPlayer.seekTo(position);
        }
        playerInitialised = true;
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void setActionBar(String appTitle);

        void onPreviousClicked(int stepId);

        boolean onNextClicked(int stepId);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.getCurrentPosition();
            isOnPaused = true;
            isPlaying = false;
            simpleExoPlayer.setPlayWhenReady(isPlaying);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isOnPaused = false;
        if (playerInitialised) {
            simpleExoPlayer.setPlayWhenReady(isPlaying);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(AppConstants.STEPS_INTENT, recipeSteps);
        if (simpleExoPlayer != null) {
            outState.putLong(AppConstants.EXO_POSITION, simpleExoPlayer.getCurrentPosition());
            outState.putBoolean(AppConstants.EXO_PAUSED, isPlaying);
        }
    }

    public void setHideNextButtonVisibility(boolean visibility) {
        hideNexButton = visibility;
    }

}
