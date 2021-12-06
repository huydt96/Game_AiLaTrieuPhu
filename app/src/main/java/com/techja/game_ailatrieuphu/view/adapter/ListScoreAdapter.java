package com.techja.game_ailatrieuphu.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techja.game_ailatrieuphu.R;
import com.techja.game_ailatrieuphu.db.entities.HighScore;

import java.util.List;


public class ListScoreAdapter extends RecyclerView.Adapter<ListScoreAdapter.ScoreHolder> {
    private static final String TAG = ListScoreAdapter.class.getName();
    private final Context context;
    private final List<HighScore> listHighScore;

    public ListScoreAdapter(Context context, List<HighScore> listHighScore) {
        this.context = context;
        this.listHighScore = listHighScore;
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_score, parent, false);
        return new ScoreHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
        HighScore highScore = listHighScore.get(position);
        Log.i(TAG, highScore.toString());
        if (highScore == null) return;
        @SuppressLint("DefaultLocale") String stringScore = String.format("%,d", highScore.scoreUser);
        if (position == 0) {
            bindDataScoreTop(holder, highScore, R.color.purple_light, R.drawable.rank_1, R.color.orange, stringScore);
        } else if (position == 1) {
            bindDataScoreTop(holder, highScore, R.color.green_light, R.drawable.rank_2, R.color.green, stringScore);
        } else if (position == 2) {
            bindDataScoreTop(holder, highScore, R.color.blue, R.drawable.rank_3, R.color.purple_light, stringScore);
        } else {
            holder.tvNumber.setText(String.format("%s", position + 1));
            holder.tvName.setText(highScore.name);
            holder.tvScore.setText(String.format("%s VNĐ", stringScore));
        }
    }

    private void bindDataScoreTop(ScoreHolder holder, HighScore highScore, int colorItem, int icRank, int colorName, String stringScore) {
        holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(colorItem,holder.itemView.getResources().newTheme()));
        holder.tvNumber.setBackgroundResource(icRank);
        holder.tvName.setTextColor(holder.tvName.getResources().getColor(colorName,holder.tvName.getResources().newTheme()));
        holder.tvName.setText(highScore.name);
        holder.tvScore.setText(String.format("%s VNĐ", stringScore));
    }


    @Override
    public int getItemCount() {
        return listHighScore.size();
    }

    public static class ScoreHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvName;
        TextView tvScore;

        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvName = itemView.findViewById(R.id.tv_name);
            tvScore = itemView.findViewById(R.id.tv_score_user);
        }
    }
}
