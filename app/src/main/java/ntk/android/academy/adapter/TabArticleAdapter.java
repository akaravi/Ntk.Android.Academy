package ntk.android.academy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.article.ArticleContentOtherInfoModel;
import ntk.android.base.event.HtmlBodyEvent;
import ntk.android.base.utill.FontManager;

public class TabArticleAdapter extends BaseRecyclerAdapter<ArticleContentOtherInfoModel, TabArticleAdapter.ViewHolder> {

    private final Context context;

    public TabArticleAdapter(Context context, List<ArticleContentOtherInfoModel> arrayList) {
        super(arrayList);
        this.context = context;
        //no need to drawable
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_tab, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ArticleContentOtherInfoModel item = list.get(position);
        holder.Btn.setText(item.Title);
        if (item.TypeId == 0) {
            EventBus.getDefault().post(new HtmlBodyEvent(item.HtmlBody));
        }
        holder.Ripple.setOnClickListener(v -> EventBus.getDefault().post(new HtmlBodyEvent(item.HtmlBody)));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.BtnRecyclerTab)
        Button Btn;

        @BindView(R.id.RippleBtnRecyclerTab)
        MaterialRippleLayout Ripple;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Btn.setTypeface(FontManager.T1_Typeface(context));
        }
    }
}
