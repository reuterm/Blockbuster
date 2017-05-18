package com.retuerm.android.blockbuster;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retuerm.android.blockbuster.utility.MovieTrailer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by max on 18/05/2017.
 */

public class MovieTrailerListAdapter extends RecyclerView.Adapter<MovieTrailerListAdapter.TrailerViewHolder> {

    public interface TrailerAdapterOnClickHandler {
        void onClick(MovieTrailer trailer);
    }

    private final TrailerAdapterOnClickHandler mOnClickHandler;
    private MovieTrailer[] mTrailerList;
    private Context mContext;


    public MovieTrailerListAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mOnClickHandler = clickHandler;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIDForListItem = R.layout.movie_trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIDForListItem, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        MovieTrailer trailer = mTrailerList[position];
        holder.mDisplayTrailer.setText(trailer.getName() + " (" + trailer.getType() + ")");
    }

    public void setTrailerList(MovieTrailer[] trailerList) {
        mTrailerList = trailerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTrailerList == null) return 0;
        return mTrailerList.length;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trailer_link) public TextView mDisplayTrailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieTrailer trailer = mTrailerList[getAdapterPosition()];
                    mOnClickHandler.onClick(trailer);
                }
            });
        }
    }
}
