package ntk.android.academy.adapter.theme.holder;

import android.content.Context;
import android.graphics.PorterDuff;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.base.utill.FontManager;

public class ArticleHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerMenuRecyclerHome)
    public RecyclerView RvMenu;

    @BindViews({R.id.lblMenuRecyclerHome, R.id.lblAllMenuRecyclerHome})
    public List<TextView> Lbls;

    @BindView(R.id.ProgressRowHomeArticle)
    public ProgressBar Progress;

    public ArticleHolder(Context context, View view) {
        super(view);
        ButterKnife.bind(this, view);
        RvMenu.setHasFixedSize(true);
        Lbls.get(0).setTypeface(FontManager.T1_Typeface(context));
        Lbls.get(1).setTypeface(FontManager.T1_Typeface(context));
        Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

    }
}
