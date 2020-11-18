package ntk.android.academy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.activity.ArticleContentListActivity;
import ntk.android.base.api.article.model.ArticleContentListRequest;
import ntk.android.base.entitymodel.article.ArticleContentTagModel;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.coremodulemain.CoreModuleTagModel;
import ntk.android.base.utill.FontManager;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private final List<CoreModuleTagModel> arrayList;
    private final Context context;

    public TagAdapter(Context context, List<CoreModuleTagModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_circle, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Lbl.setText(arrayList.get(position).Title);
        holder.Lbl.setOnClickListener(view -> {
            Intent intent = new Intent(context, ArticleContentListActivity.class);
            FilterDataModel request = new FilterDataModel();
            List<Long> Tags = new ArrayList<>();
            Tags.add(arrayList.get(position).Id);
            //todo karavi Tag biad too filtermodel
            intent.putExtra("Request", new Gson().toJson(request));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblTitleCategory)
        TextView Lbl;

        @BindView(R.id.rooCategory)
        LinearLayout Root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbl.setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
        }
    }
}
