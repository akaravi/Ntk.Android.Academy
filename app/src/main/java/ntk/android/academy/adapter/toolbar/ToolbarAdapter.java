package ntk.android.academy.adapter.toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.event.toolbar.EVHamberMenuClick;
import ntk.android.academy.event.toolbar.EVSearchClick;
import ntk.android.base.dtomodel.theme.ToolbarDtoModel;

//todo remove
public class ToolbarAdapter extends RecyclerView.Adapter<ToolbarAdapter.ViewHolder> {

    private final List<ToolbarDtoModel> toolbars;
    private final Context context;


    public ToolbarAdapter(Context context, List<ToolbarDtoModel> toolbar) {
        this.toolbars = toolbar;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.toolbar_theme, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(toolbars.get(position).HamberMenuThemeDtoModel.Image, holder.Imgs.get(0));
        ImageLoader.getInstance().displayImage(toolbars.get(position).SearchBox.Image, holder.Imgs.get(1));
        ImageLoader.getInstance().displayImage(toolbars.get(position).Cart.Image, holder.Imgs.get(2));
        holder.Imgs.get(0).setColorFilter(Color.parseColor(toolbars.get(position).HamberMenuThemeDtoModel.Color), PorterDuff.Mode.SRC_IN);
        holder.Imgs.get(1).setColorFilter(Color.parseColor(toolbars.get(position).SearchBox.Color), PorterDuff.Mode.SRC_IN);
        holder.Imgs.get(2).setColorFilter(Color.parseColor(toolbars.get(position).Cart.Color), PorterDuff.Mode.SRC_IN);
        holder.Container.setBackgroundColor(Color.parseColor(toolbars.get(position).BackgroundColor));
        holder.Line.setBackgroundColor(Color.parseColor(toolbars.get(position).ColorBelowLine));

        holder.Ripples.get(0).setOnClickListener(v -> EventBus.getDefault().post(new EVHamberMenuClick(true)));
        holder.Ripples.get(1).setOnClickListener(v -> EventBus.getDefault().post(new EVSearchClick(true)));


    }

    @Override
    public int getItemCount() {
        return  toolbars.size();
    }

   public  class ViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.RippleHamberRecyclerToolbar, R.id.RippleSearchRecyclerToolbar, R.id.RippleShoppingCartRecyclerToolbar})
        List<MaterialRippleLayout> Ripples;

        @BindViews({R.id.ImgHamberRecyclerToolbar, R.id.ImgSearchCartRecyclerToolbar, R.id.ImgShoppingCartRecyclerToolbar})
        List<ImageView> Imgs;

        @BindView(R.id.ContainerRecyclerToolbar)
        RelativeLayout Container;

        @BindView(R.id.LineRecyclerToolbar)
        View Line;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.findViewById(R.id.RippleShoppingCartRecyclerToolbar).setVisibility(View.GONE);
        }
    }

    }