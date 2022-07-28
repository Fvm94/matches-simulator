package com.fvm.matchessimulator.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fvm.matchessimulator.R;
import com.fvm.matchessimulator.data.MatchesAPI;
import com.fvm.matchessimulator.databinding.ActivityMainBinding;
import com.fvm.matchessimulator.domain.Match;
import com.fvm.matchessimulator.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesAPI;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setupHttpClient();
        setupMacthesList();
        setupMacthesRefresh();
        setupFloatingActionButton();

    }

    private void setupHttpClient() {

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://fvm94.github.io/matches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesAPI = retrofit.create(MatchesAPI.class);
    }

    private void setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener(view ->{

                view.animate().rotationBy(360).setDuration(750).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Random  random = new Random();
                        for (int i=0;i<matchesAdapter.getItemCount();i++){
                            Match match = matchesAdapter.getMatches().get(i);

                            match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getForce() + 1));
                            match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getForce() + 1));
                            matchesAdapter.notifyItemChanged(i);
                        }
                    }
                });

            }
        );
    }

    private void setupMacthesRefresh() {
        binding.srlMatches.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findMatchesFromApi();
            }
        });
    }

    private void setupMacthesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));

        findMatchesFromApi();
    }

    private void findMatchesFromApi() {
        binding.srlMatches.setRefreshing(true);
        matchesAPI.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if(response.isSuccessful()){
                    List<Match> matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                }
                else{
                    showErrorMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        Snackbar.make(binding.getRoot(),getString(R.string.api_generic_error),Snackbar.LENGTH_LONG).show();
    }
}
