package com.retuerm.android.blockbuster;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retuerm.android.blockbuster.utility.MovieReview;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by max on 19/05/2017.
 */

public class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewListAdapter.ReviewViewHolder> {

    public interface ReviewAdapterOnClickHandler {
        void onClick(MovieReview review);
    }

    private final MovieReviewListAdapter.ReviewAdapterOnClickHandler mOnClickHandler;
    private MovieReview[] mReviewList;
    private Context mContext;

    public MovieReviewListAdapter(ReviewAdapterOnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIDForListItem = R.layout.movie_review_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIDForListItem, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.mDisplayReview.setText(mContext.getString(R.string.review_name_format, position+1));
    }

    public void setReviewList(MovieReview[] reviewList) {
        mReviewList = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mReviewList == null) return 0;
        return mReviewList.length;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_review_link) public TextView mDisplayReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieReview review = mReviewList[getAdapterPosition()];
                    mOnClickHandler.onClick(review);
                }
            });
        }
    }
}
