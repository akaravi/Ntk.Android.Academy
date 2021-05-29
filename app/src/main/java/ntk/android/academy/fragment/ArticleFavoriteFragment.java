package ntk.android.academy.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.fragment.common.BaseFilterModelFragment;
import ntk.android.base.services.article.ArticleContentService;

public class ArticleFavoriteFragment extends BaseFilterModelFragment<ArticleContentModel> {

    @Override
    public Function<FilterModel, Observable<ErrorException<ArticleContentModel>>> getService() {
        return new ArticleContentService(getContext())::getFavoriteList;
    }

    @Override
    protected RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new ArticleGridAdapter(getContext(), models);
    }


}
