package ntk.android.academy.activity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntk.android.academy.R;
import ntk.android.academy.adapter.NewsCommentAdapter;
import ntk.android.academy.adapter.TabNewsAdapter;
import ntk.android.base.activity.news.BaseNewsDetailActivity;
import ntk.android.base.entitymodel.news.NewsCommentModel;
import ntk.android.base.entitymodel.news.NewsContentOtherInfoModel;

public class NewsDetailActivity extends BaseNewsDetailActivity {


    @Override
    protected void initChild() {
         favoriteDrawableId = R.drawable.ic_fav_full;
         unFavoriteDrawableId = R.drawable.ic_fav;
    }

    @Override
    public RecyclerView.Adapter createCommentAdapter(List<NewsCommentModel> listItems) {
        return new NewsCommentAdapter(this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<NewsContentOtherInfoModel> info) {
        return new TabNewsAdapter(NewsDetailActivity.this, info);
    }


}
