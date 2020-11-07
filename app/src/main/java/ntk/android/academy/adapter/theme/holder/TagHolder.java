package ntk.android.academy.adapter.theme.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;

public class TagHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerTagRecyclerHome)
    public RecyclerView RvTag;

    public TagHolder(Context context , View view) {
        super(view);
        ButterKnife.bind(this, view);
        RvTag.setHasFixedSize(true);
    }
}
