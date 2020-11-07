package ntk.android.academy.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.CategoryAdapter;
import ntk.android.base.config.ConfigRestHeader;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.article.interfase.IArticle;
import ntk.android.base.api.article.model.ArticleCategoryRequest;
import ntk.android.base.api.article.model.ArticleCategoryResponse;
import ntk.android.base.config.RetrofitManager;

public class CommandFragment extends Fragment {

    @BindView(R.id.recyclerCategory)
    RecyclerView Rv;

    @BindView(R.id.RefreshFrCategory)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.lblProgressFrCategory)
    TextView LblProgress;

    @BindView(R.id.progressFrCategory)
    ProgressBar Progress;

    @BindView(R.id.rowProgressFrCategory)
    LinearLayout Loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_category, container, false);
        ButterKnife.bind(this, view);
        configStaticValue = new ConfigStaticValue(this.getContext());
        init();
        return view;
    }

    private ConfigStaticValue configStaticValue;

    private void init() {
        LblProgress.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Rv.setLayoutManager(manager);
        Loading.setVisibility(View.VISIBLE);

        HandelRest();

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            HandelRest();
            Refresh.setRefreshing(false);
        });
    }

    private void HandelRest() {
        RetrofitManager manager = new RetrofitManager(getContext());
        IArticle iArticle = manager.getCachedRetrofit(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());

        ArticleCategoryRequest request = new ArticleCategoryRequest();
        request.RowPerPage = 20;
        Observable<ArticleCategoryResponse> call = iArticle.GetCategoryList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleCategoryResponse articleCategoryResponse) {
                        CategoryAdapter adapter = new CategoryAdapter(getContext(), articleCategoryResponse.ListItems);
                        Rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Loading.setVisibility(View.GONE);
                        Rv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Loading.setVisibility(View.GONE);
                        Toasty.error(getContext(), "خطای سامانه مجددا تلاش کنیدِ", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
