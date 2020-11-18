package ntk.android.academy.activity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleAdapter;
import ntk.android.base.activity.BaseActivity;
import ntk.android.base.api.utill.NTKUtill;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.Filters;
import ntk.android.base.services.article.ArticleContentService;
import ntk.android.base.utill.FontManager;

public class ArticleSearchActivity extends BaseActivity {

    @BindView(R.id.txtSearchActSearch)
    EditText Txt;

    @BindView(R.id.recyclerSearch)
    RecyclerView Rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_search_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new GridLayoutManager(this, 2));

        Txt.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Txt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                Search();
                return true;
            }
            return false;
        });

    }

    private void Search() {

        FilterDataModel request = new FilterDataModel();
        ntk.android.base.entitymodel.base.Filters ft = new ntk.android.base.entitymodel.base.Filters();
        ft.PropertyName = "Title";
        ft.StringValue = Txt.getText().toString();
        ft.ClauseType = NTKUtill.ClauseType_Or;
        ft.SearchType = NTKUtill.Search_Type_Contains;
        request.addFilter(ft);

        Filters fd = new Filters();
        fd.PropertyName = "Description";
        fd.StringValue = Txt.getText().toString();
        fd.ClauseType = NTKUtill.ClauseType_Or;
        fd.SearchType = NTKUtill.Search_Type_Contains;
        request.addFilter(fd);

        Filters fb = new Filters();
        fb.PropertyName = "Body";
        fb.StringValue = Txt.getText().toString();
        fb.ClauseType = NTKUtill.ClauseType_Or;
        fb.SearchType = NTKUtill.Search_Type_Contains;
        request.addFilter(fb);

        switcher.showProgressView();
        new ArticleContentService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<ArticleContentModel> response) {
                        if (response.IsSuccess) {
                            if (response.ListItems.size() != 0) {
                                ArticleAdapter adArticle = new ArticleAdapter(ArticleSearchActivity.this, response.ListItems);
                                Rv.setAdapter(adArticle);
                                adArticle.notifyDataSetChanged();
                            } else {
                                switcher.showEmptyView();
                            }
                        } else {
                            switcher.showErrorView(response.ErrorMessage, () -> init());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        switcher.showErrorView("خطا در دسترسی به سامانه", () -> init());

                    }
                });
    }

    @OnClick(R.id.imgBackActSearch)
    public void ClickBack() {
        finish();
    }
}
