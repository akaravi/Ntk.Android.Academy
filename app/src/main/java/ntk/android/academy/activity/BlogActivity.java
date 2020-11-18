package ntk.android.academy.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.BlogAdapter;
import ntk.android.base.activity.BaseActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.blog.BlogContentModel;
import ntk.android.base.services.blog.BlogContentService;
import ntk.android.base.utill.AppUtill;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;
import ntk.android.base.utill.FontManager;

public class BlogActivity extends BaseActivity {

    @BindView(R.id.lblTitleActBlog)
    TextView LblTitle;

    @BindView(R.id.recyclerBlog)
    RecyclerView Rv;

    private int Total = 0;
    private final List<BlogContentModel> blogs = new ArrayList<>();
    private BlogAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        LblTitle.setText("مجلات آموزشی دورهمی");
        Rv.setHasFixedSize(true);
        LinearLayoutManager LMC = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rv.setLayoutManager(LMC);
        adapter = new BlogAdapter(this, blogs);
        Rv.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(LMC) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= Total) {
                    RestCall((page + 1));
                }
            }
        };
        Rv.addOnScrollListener(scrollListener);

        RestCall(1);
    }

    private void RestCall(int i) {
        if (AppUtill.isNetworkAvailable(this)) {
            switcher.showProgressView();
            FilterDataModel request = new FilterDataModel();
            request.RowPerPage = 20;
            request.CurrentPageNumber = i;
            //todo show loading
            new BlogContentService(this).getAll(request).
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<BlogContentModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<BlogContentModel> blogContentResponse) {
                            if (blogContentResponse.IsSuccess) {
                                blogs.addAll(blogContentResponse.ListItems);
                                Total = blogContentResponse.TotalRowCount;
                                adapter.notifyDataSetChanged();
                                if (Total > 0)
                                    switcher.showContentView();
                                else
                                    switcher.showEmptyView();

                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            switcher.showErrorView("خطای سامانه مجددا تلاش کنید", () -> init());
                        }
                    });
        } else {
            switcher.showErrorView("عدم دسترسی به اینترنت", () -> init());

        }
    }

    @OnClick(R.id.imgBackActBlog)
    public void ClickBack() {
        finish();
    }
}
