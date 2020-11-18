package ntk.android.academy.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleGridAdapter;
import ntk.android.base.api.article.model.ArticleContentListRequest;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.services.article.ArticleContentService;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;
import ntk.android.base.utill.FontManager;

public class ArticleContentListActivity extends AppCompatActivity {

    @BindView(R.id.lblTitleActArticleContentList)
    TextView Lbl;

    @BindView(R.id.recyclerActArticleContentList)
    RecyclerView Rv;

    private String RequestStr;

    private EndlessRecyclerViewScrollListener scrollListener;
    private int TotalItem = 0;
    private ArticleGridAdapter adapter;
    private final List<ArticleContentModel> contents = new ArrayList<ArticleContentModel>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_article_content_list);
        ButterKnife.bind(this);
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
                    HandelData((page + 1), new Gson().fromJson(RequestStr, FilterDataModel.class));
                }
            }
        };
        Rv.addOnScrollListener(scrollListener);
        HandelData(1, new Gson().fromJson(RequestStr, FilterDataModel.class));
    }


    private void HandelData(int i, FilterDataModel request) {
        request.RowPerPage = 16;
        request.CurrentPageNumber = i;
        new ArticleContentService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {

                    @Override
                    public void onNext(@NonNull ErrorException<ArticleContentModel> articleContentResponse) {
                        contents.addAll(articleContentResponse.ListItems);
                        adapter.notifyDataSetChanged();
                        TotalItem = articleContentResponse.TotalRowCount;
                        Rv.setItemViewCacheSize(contents.size());
                    }


                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(ArticleContentListActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }
                });
    }

    @OnClick(R.id.imgBackArticleContentList)
    public void Back() {
        finish();
    }
}
