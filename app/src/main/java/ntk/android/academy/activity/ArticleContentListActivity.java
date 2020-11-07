package ntk.android.academy.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.config.ConfigRestHeader;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.article.interfase.IArticle;
import ntk.android.base.api.article.entity.ArticleContent;
import ntk.android.base.api.article.model.ArticleContentListRequest;
import ntk.android.base.api.article.model.ArticleContentResponse;
import ntk.android.base.config.RetrofitManager;

public class ArticleContentListActivity extends AppCompatActivity {

    @BindView(R.id.lblTitleActArticleContentList)
    TextView Lbl;

    @BindView(R.id.recyclerActArticleContentList)
    RecyclerView Rv;

    private String RequestStr;

    private EndlessRecyclerViewScrollListener scrollListener;
    private int TotalItem = 0;
    private ArticleGridAdapter adapter;
    private final List<ArticleContent> contents = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article_content_list);
        ButterKnife.bind(this);
        configStaticValue = new ConfigStaticValue(this);
        init();
    }

    private void init() {
        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Rv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        Rv.setLayoutManager(manager);
        adapter = new ArticleGridAdapter(this, contents);
        Rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RequestStr = getIntent().getExtras().getString("Request");
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalItem) {
                    HandelData((page + 1), new Gson().fromJson(RequestStr, ArticleContentListRequest.class));
                }
            }
        };
        Rv.addOnScrollListener(scrollListener);
        HandelData(1, new Gson().fromJson(RequestStr, ArticleContentListRequest.class));
    }

    private ConfigStaticValue configStaticValue;

    private void HandelData(int i, ArticleContentListRequest request) {
        RetrofitManager retro = new RetrofitManager(this);
        IArticle iArticle = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IArticle.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
        request.RowPerPage = 16;
        request.CurrentPageNumber = i;
        Observable<ArticleContentResponse> call = iArticle.GetContentList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArticleContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleContentResponse articleContentResponse) {
                        contents.addAll(articleContentResponse.ListItems);
                        adapter.notifyDataSetChanged();
                        TotalItem = articleContentResponse.TotalRowCount;
                        Rv.setItemViewCacheSize(contents.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(ArticleContentListActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.imgBackArticleContentList)
    public void Back() {
        finish();
    }
}
