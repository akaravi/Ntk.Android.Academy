package ntk.android.academy.activity;

import android.content.Intent;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import java9.util.function.Function;
import ntk.android.academy.R;
import ntk.android.academy.adapter.BlogAdapter;
import ntk.android.base.activity.BaseActivity;
import ntk.android.base.activity.abstraction.AbstractionListActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.blog.BlogContentModel;
import ntk.android.base.services.blog.BlogContentService;
import ntk.android.base.utill.AppUtill;
import ntk.android.base.utill.EndlessRecyclerViewScrollListener;
import ntk.android.base.utill.FontManager;

public class BlogListActivity extends AbstractionListActivity<BlogContentModel> {
    @Override
    public void afterInit() {
        super.afterInit();
        LblTitle.setText("مجلات آموزشی دورهمی");
    }
    @Override
    public Function<FilterDataModel, Observable<ErrorException<BlogContentModel>>> getService() {
        return new BlogContentService(this)::getAll;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BlogAdapter(this,models);
    }

    @OnClick(R.id.imgBackActBlog)
    public void ClickBack() {
        finish();
    }

    @Override
    public void ClickSearch() {
        startActivity(new Intent(this, BlogSearchActivity.class));
    }
}
