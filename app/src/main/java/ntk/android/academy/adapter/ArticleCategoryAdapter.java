package ntk.android.academy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.activity.ArticleContentGridListActivity;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.article.ArticleCategoryModel;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.utill.FontManager;

public class ArticleCategoryAdapter extends BaseRecyclerAdapter<ArticleCategoryModel, ArticleCategoryAdapter.ViewHolder> {

    private final Context context;

    public ArticleCategoryAdapter(Context context, List<ArticleCategoryModel> arrayList) {
        super(arrayList);
        this.context = context;
        drawable = R.drawable.article_place_holder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflate(viewGroup, R.layout.row_recycler_category);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ArticleCategoryModel item = list.get(position);
        holder.LblName.setText(item.Title);
        loadImage(item.LinkMainImageIdSrc, holder.Img, holder.Progress);

        if (item.Children.size() == 0) {
            holder.ImgDrop.setVisibility(View.GONE);
        }
        holder.Img.setOnClickListener(view -> {
            FilterModel request = new FilterModel();
            FilterDataModel f = new FilterDataModel();
            f.PropertyName = "LinkCategoryId";
            f.setIntValue(item.Id);
            request.addFilter(f);
            Intent intent = new Intent(context, ArticleContentGridListActivity.class);
            //todo get base on category
            intent.putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(request));
            context.startActivity(intent);
        });
        holder.ImgDrop.setOnClickListener(view -> {
            if (holder.Rv.getVisibility() == View.GONE) {
                holder.ImgArrow.setRotation(180);
                ArticleCategoryAdapter adapter = new ArticleCategoryAdapter(context, item.Children);
                holder.Rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                holder.Rv.setVisibility(View.VISIBLE);
            } else {
                holder.Rv.setAdapter(null);
                holder.Rv.setVisibility(View.GONE);
                holder.ImgArrow.setRotation(0);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblRowRecyclerCategory)
        TextView LblName;

        @BindView(R.id.imgRowRecyclerCategory)
        ImageView Img;

        @BindView(R.id.imgArrow)
        ImageView ImgArrow;

        @BindView(R.id.recyclerSubCategory)
        RecyclerView Rv;

        @BindView(R.id.imgArrowRecyclerCategory)
        MaterialRippleLayout ImgDrop;

        @BindView(R.id.ProgressRecyclerCategory)
        ProgressBar Progress;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            LblName.setTypeface(FontManager.T1_Typeface(context));
            Rv.setHasFixedSize(true);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
            Rv.setLayoutManager(manager);
            Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
        }
    }
}
