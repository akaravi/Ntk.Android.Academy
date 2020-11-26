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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleCategoryAdapter;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.article.ArticleCategoryModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.article.ArticleCategoryService;
import ntk.android.base.utill.FontManager;

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

        FilterDataModel request = new FilterDataModel();
        request.RowPerPage = 20;
        new ArticleCategoryService(getContext()).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<ArticleCategoryModel>>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ErrorException<ArticleCategoryModel> articleCategoryResponse) {
                        ArticleCategoryAdapter adapter = new ArticleCategoryAdapter(getContext(), articleCategoryResponse.ListItems);
                        Rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Loading.setVisibility(View.GONE);
                        Rv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Loading.setVisibility(View.GONE);
                        Toasty.error(getContext(), "خطای سامانه مجددا تلاش کنیدِ", Toasty.LENGTH_LONG, true).show();

                    }
                });
    }

}
