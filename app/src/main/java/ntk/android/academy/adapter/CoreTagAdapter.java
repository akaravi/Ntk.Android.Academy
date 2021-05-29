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
import ntk.android.academy.activity.ArticleContentGridListActivity;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.coremodulemain.CoreModuleTagModel;
import ntk.android.base.utill.FontManager;

public class CoreTagAdapter extends BaseRecyclerAdapter<CoreModuleTagModel,CoreTagAdapter.ViewHolder> {

     private final Context context;

    public CoreTagAdapter(Context context, List<CoreModuleTagModel> arrayList) {
        super(arrayList);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflate(viewGroup,R.layout.row_recycler_circle);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        CoreModuleTagModel item = list.get(position);
        holder.Lbl.setText(item.Title);
        holder.Lbl.setOnClickListener(view -> {
            Intent intent = new Intent(context, ArticleContentGridListActivity.class);
            FilterDataModel request = new FilterDataModel();
            List<Long> Tags = new ArrayList<>();
            Tags.add(item.Id);
            //todo karavi Tag biad too filtermodel
            intent.putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(request));
            context.startActivity(intent);
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblTitleCategory)
        TextView Lbl;

        @BindView(R.id.rooCategory)
        LinearLayout Root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbl.setTypeface(FontManager.T1_Typeface(context));
        }
    }
}
