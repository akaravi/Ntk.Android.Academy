package ntk.android.academy.activity;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleAdapter;
import ntk.android.academy.adapter.ArticleCommentAdapter;
import ntk.android.academy.adapter.TabArticleAdapter;
import ntk.android.base.Extras;
import ntk.android.base.activity.article.BaseArticleDetail2_2_Activity;
import ntk.android.base.entitymodel.article.ArticleCommentModel;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.article.ArticleContentOtherInfoModel;

public class NewArticleDetailActivity extends BaseArticleDetail2_2_Activity {
    @Override
    protected void initChild() {
        favoriteDrawableId = R.drawable.ic_fav_full;
        unFavoriteDrawableId = R.drawable.ic_fav;
    }

    @Override
    protected void onCalClick() {
        Toasty.warning(NewArticleDetailActivity.this, "موردی یافت نشد").show();
    }

    @Override
    protected void onPlayClick() {
        Toasty.warning(NewArticleDetailActivity.this, "موردی یافت نشد").show();
    }

    @Override
    protected void ClickGalley() {
        if (model.LinkFileIdsSrc != null && model.LinkFileIdsSrc.size() != 0) {
            String[] array;
            if (model.LinkFileIdsSrc != null)
                array = model.LinkFileIdsSrc.toArray(new String[0]);
            else
                array = new String[0];
            Intent intent = new Intent(this, PhotoGalleryActivity.class);
            intent.putExtra(Extras.EXTRA_FIRST_ARG, array);
            startActivity(intent);
        }
    }

    @Override
    public RecyclerView.Adapter createCommentAdapter(List<ArticleCommentModel> listItems) {
        return new ArticleCommentAdapter(this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<ArticleContentOtherInfoModel> info) {
        return new TabArticleAdapter(NewArticleDetailActivity.this, info);

    }

    @Override
    protected RecyclerView.Adapter createSimilarContentAdapter(List<ArticleContentModel> listItems) {
        return new ArticleAdapter(NewArticleDetailActivity.this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createSimilarCategoryAdapter(List<ArticleContentModel> listItems) {
        return new ArticleAdapter(NewArticleDetailActivity.this, listItems);
    }
}


