package ntk.android.academy.activity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntk.android.academy.R;
import ntk.android.academy.adapter.BlogCommentAdapter;
import ntk.android.academy.adapter.TabBlogAdapter;
import ntk.android.base.activity.blog.BaseBlogDetail_1_Activity;
import ntk.android.base.entitymodel.blog.BlogCommentModel;
import ntk.android.base.entitymodel.blog.BlogContentOtherInfoModel;

public class BlogDetailActivity extends BaseBlogDetail_1_Activity {



    @Override
    public RecyclerView.Adapter createCommentAdapter(List<BlogCommentModel> listItems) {
        return new BlogCommentAdapter(this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<BlogContentOtherInfoModel> info) {
        return new TabBlogAdapter(this, info);
    }
}