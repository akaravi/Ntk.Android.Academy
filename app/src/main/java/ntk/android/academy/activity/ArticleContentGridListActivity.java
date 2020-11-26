package ntk.android.academy.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import java9.util.function.Function;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.activity.abstraction.AbstractionListActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.article.ArticleContentService;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;

public class ArticleContentGridListActivity extends AbstractionListActivity<ArticleContentModel> {

    @BindView(R.id.lblTitleActArticleContentList)
    TextView Lbl;

    @BindView(R.id.recyclerActArticleContentList)
    RecyclerView Rv;

    @Override
    public void afterInit() {
        super.afterInit();
        findViewById(R.id.imgSearch).setVisibility(View.GONE);
        request.RowPerPage = 16;
    }

    @Override
    protected RecyclerView.LayoutManager getRvLayoutManager() {
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

    @OnClick(R.id.imgBackArticleContentList)
    public void Back() {
        finish();
    }
}
