package ntk.android.academy.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.api.article.interfase.IArticle;
import ntk.android.base.api.article.model.ArticleContentFavoriteListRequest;
import ntk.android.base.api.article.model.ArticleContentFavoriteListResponse;
import ntk.android.base.config.ConfigRestHeader;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.config.RetrofitManager;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.article.ArticleContentService;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;
import ntk.android.base.utill.FontManager;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.RecyclerFav)
    RecyclerView Rv;

    @BindView(R.id.swipRefreshFrFav)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.lblProgressFrFav)
    TextView LblProgress;

    @BindView(R.id.progressFrFav)
    ProgressBar Progress;

    @BindView(R.id.rowProgressFrFav)
    LinearLayout Loading;

    List<ArticleContentModel> contents = new ArrayList<>();
    ArticleGridAdapter adapter;

    private final int TotalArticle = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_fav, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        LblProgress.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        Rv.setLayoutManager(manager);
        Loading.setVisibility(View.VISIBLE);

        adapter = new ArticleGridAdapter(getContext(), contents);
        Rv.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalArticle) {
                    HandleCategory((page + 1));
                }
            }
        };

        Rv.addOnScrollListener(scrollListener);
        HandleCategory(1);

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            contents.clear();
            HandleCategory(1);
            Refresh.setRefreshing(false);
        });
    }

    private void HandleCategory(int i) {
        RetrofitManager manager = new RetrofitManager(getContext());
        IArticle iArticle = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IArticle.class);

        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = 20;
        request.CurrentPageNumber = i;
        new ArticleContentService(getContext()).getFavoriteList(request).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ErrorException<ArticleContentModel> articleContentResponse) {
                        Loading.setVisibility(View.GONE);
                        contents.addAll(articleContentResponse.ListItems);
                        adapter.notifyDataSetChanged();
                        Rv.setItemViewCacheSize(contents.size());

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Loading.setVisibility(View.GONE);
                        Toasty.error(getContext(), "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();

                    }
                });
    }

}
