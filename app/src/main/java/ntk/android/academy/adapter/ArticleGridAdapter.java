package ntk.android.academy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.activity.ArticleDetailActivity;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.services.base.CmsApiScoreApi;
import ntk.android.base.utill.FontManager;

public class ArticleGridAdapter extends BaseRecyclerAdapter<ArticleContentModel, ArticleGridAdapter.ViewHolder> {

    private final Context context;

    public ArticleGridAdapter(Context context, List<ArticleContentModel> arrayList) {
        super(arrayList);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflate(viewGroup, R.layout.row_recycler_article_grid);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ArticleContentModel item = list.get(position);
        holder.LblName.setText(item.Title);
        holder.LblLike.setText(String.valueOf(item.ViewCount));

        loadImage(item.LinkMainImageIdSrc, holder.Img, holder.Progress);
        double rating = CmsApiScoreApi.CONVERT_TO_RATE(item.ViewCount, item.ScoreSumPercent);
        holder.Rate.setRating((float) rating);
        holder.Root.setOnClickListener(view -> {
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            Long Id = item.Id;
            intent.putExtra(Extras.EXTRA_FIRST_ARG, Id);
            context.startActivity(intent);
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblNameRowRecyclerMenu)
        TextView LblName;

        @BindView(R.id.lblLikeRowRecyclerMenu)
        TextView LblLike;

        @BindView(R.id.imgRowRecyclerMenu)
        ImageView Img;

        @BindView(R.id.ratingBarRowRecyclerMenu)
        RatingBar Rate;

        @BindView(R.id.rootMenu)
        CardView Root;

        @BindView(R.id.ProgressRecyclerMenu)
        ProgressBar Progress;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            LblName.setTypeface(FontManager.T1_Typeface(context));
            LblLike.setTypeface(FontManager.T1_Typeface(context));
            Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
