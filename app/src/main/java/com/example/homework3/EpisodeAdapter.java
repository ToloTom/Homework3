package com.example.homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private List<Episode> episodes;

    public EpisodeAdapter(List<Episode> episodeList){
        this.episodes = episodeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View episodeView = inflater.inflate(R.layout.item_episode, parent, false);
        EpisodeAdapter.ViewHolder viewHolder = new EpisodeAdapter.ViewHolder(episodeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode episode = episodes.get(position);
        Picasso.get().load(episode.getImage_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolder(View view){
            super(view);

            imageView = view.findViewById(R.id.imageView_characters);
        }

    }
}
