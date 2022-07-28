package com.fvm.matchessimulator.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fvm.matchessimulator.databinding.MatchItemBinding;
import com.fvm.matchessimulator.domain.Match;
import com.fvm.matchessimulator.ui.DetailActivity;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    public List<Match> getMatches() {
        return matches;
    }

    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchItemBinding binding = MatchItemBinding
                .inflate(LayoutInflater
                        .from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();

        Match match = matches.get(position);

        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.ivTeamPictureHome);
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivTeamPictureAway);
        holder.binding.tvTeamNameHome.setText(match.getHomeTeam().getName());
        holder.binding.tvTeamNameAway.setText(match.getAwayTeam().getName());
        if(match.getHomeTeam().getScore() !=null)
        holder.binding.tvTeamScoreHome.setText(String.valueOf(match.getHomeTeam().getScore()));
        if(match.getAwayTeam().getScore() != null)
        holder.binding.tvTeamScoreAway.setText(String.valueOf(match.getAwayTeam().getScore()));


        holder.itemView.setOnClickListener(view->{

            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra(DetailActivity.Extras.MATCH,match);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

}
