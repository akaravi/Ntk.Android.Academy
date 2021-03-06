package ntk.android.academy.adapter.theme.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;

public class ImageHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerImageRecyclerHome)
    public RecyclerView Rv;

    public ImageHolder(@NonNull View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
