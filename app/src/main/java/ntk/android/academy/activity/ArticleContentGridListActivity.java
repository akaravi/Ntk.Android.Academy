package ntk.android.academy.activity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.article.ArticleContentService;

public class ArticleContentGridListActivity extends BaseFilterModelListActivity<ArticleContentModel> {
    @Override
    public void afterInit() {
        super.afterInit();
        findViewById(R.id.imgSearch).setVisibility(View.GONE);
        request.RowPerPage = 16;
    }

    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }


    @Override
    public Function<FilterDataModel, Observable<ErrorException<ArticleContentModel>>> getService() {
        return new ArticleContentService(this)::getAll;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new ArticleGridAdapter(this, models);
    }

    @Override
    public void ClickSearch() {

    }


}
