package com.vatsyayan.huntformoview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vatsyayan.huntformovie.R;
import com.vatsyayan.huntformoview.web_service.LoadingScreen;
import com.vatsyayan.huntformoview.web_service.ServiceCallBack;
import com.vatsyayan.huntformoview.web_service.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServiceCallBack {

    private Toolbar mToolbar;
    private Context mContext;
    private List<Movie> mMoviesList;
    private EditText etMovieQuery;
    private RecyclerView rvMovies;
    private TextView tvEmptyMessage;

    @Override
    public void success(ServiceHandler.RequestId serviceName, Object data) {
        LoadingScreen.dismissProgressDialog();
        if (data != null) {
            processFetchMoviesResponse(data);
        }
    }

    @Override
    public void error(ServiceHandler.RequestId serviceName, String message) {

    }

    private void processFetchMoviesResponse(Object data) {
        mMoviesList.clear();
        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject movieJsonObject = results.getJSONObject(i);
                Movie movie = new Movie(movieJsonObject.optString("original_title"), movieJsonObject.optString("release_date"),
                        movieJsonObject.optString("overview"), jsonObject.optString("popularity"));
                movie.setLanguage(getLanguage(movieJsonObject.optString("original_language")));
                movie.setImageURL("https://image.tmdb.org/t/p/w500/"+movieJsonObject.optString("poster_path"));
                mMoviesList.add(movie);
            }
            MoviesRecyclerAdapter eventsAdapter = new MoviesRecyclerAdapter(mContext, mMoviesList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            rvMovies.setLayoutManager(linearLayoutManager);
            rvMovies.setHasFixedSize(true);
            rvMovies.setAdapter(eventsAdapter);

            tvEmptyMessage.setVisibility(View.GONE);
            rvMovies.setVisibility(View.VISIBLE);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private String getLanguage(String original_language) {
       // String language;
       /* switch (original_language) {
            case "en":
                language = "English";
                break;
            case "te":
                language = "Telugu";
                break;
            case "ta":
                language = "Tamil";
                break;
            default:
                language = "English";
                break;
        }*/
        Locale locale = new Locale(original_language);
        return locale.getDisplayLanguage();
    }

    private void fetchMovies(String movieTitle) {
        LoadingScreen.showProgressDialog(mContext, "Searching for movies..!!");
        ServiceHandler serviceHandler = new ServiceHandler();
        serviceHandler.setmContext(mContext);
        serviceHandler.setmServiceCallBack(this);
        serviceHandler.addParameters("query", movieTitle);
        serviceHandler.addParameters("api_key", "ba5899ad0ce1b3a6f74664f2947c84ac");
        serviceHandler.setmRequestId(ServiceHandler.RequestId.FETCH_MOVIES);
        serviceHandler.makeRequest(ServiceHandler.REQUEST_METHOD_GET);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mMoviesList = new ArrayList<>();
        setUpToolbar();
        setUpUI();
    }

    private void setUpUI() {
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        tvEmptyMessage = (TextView) findViewById(R.id.tv_empty);
        if (mMoviesList.size() == 0) {
            tvEmptyMessage.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.GONE);
        } else {
            tvEmptyMessage.setVisibility(View.GONE);
            rvMovies.setVisibility(View.VISIBLE);
        }
        etMovieQuery = (EditText) findViewById(R.id.et_search);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMovieQuery.getText().toString().equals("")) {
                    try {
                        String encodedURL = URLEncoder.encode(etMovieQuery.getText().toString(), "UTF-8");
                        fetchMovies(encodedURL);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.app_name);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.primary));
        }
    }
}
