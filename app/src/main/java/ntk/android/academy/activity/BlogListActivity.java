package ntk.android.academy.activity;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.OnClick;
import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.academy.R;
import ntk.android.academy.adapter.BlogAdapter;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.blog.BlogContentModel;
import ntk.android.base.services.blog.BlogContentService;

public class BlogListActivity extends BaseFilterModelListActivity<BlogContentModel> {
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
