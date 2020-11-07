package ntk.android.academy.adapter.theme.holder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;

public class ButtonHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerButtonRecyclerHome)
    public RecyclerView Rv;

    public ButtonHolder(Context context, View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
