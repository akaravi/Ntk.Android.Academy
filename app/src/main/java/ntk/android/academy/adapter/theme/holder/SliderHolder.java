package ntk.android.academy.adapter.theme.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ss.com.bannerslider.views.BannerSlider;

public class SliderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerSliderRecyclerHome)
    public BannerSlider Slider;

    public SliderHolder(@NonNull View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
