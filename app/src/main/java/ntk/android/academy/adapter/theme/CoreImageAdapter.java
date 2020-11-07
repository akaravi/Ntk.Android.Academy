package ntk.android.academy.adapter.theme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.activity.ArticleContentListActivity;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.baseModel.theme.ThemeChildConfig;

public class CoreImageAdapter extends RecyclerView.Adapter<CoreImageAdapter.ViewHolder> {

    private final List<ThemeChildConfig> childs;
    private final Context context;

    public CoreImageAdapter(Context context, List<ThemeChildConfig> list) {
        this.childs = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).build();
        ImageLoader.getInstance().displayImage(childs.get(position).Href, holder.Img, options);
        if (childs.get(position).Title != null) {
            holder.Lbl.setVisibility(View.VISIBLE);
            holder.Lbl.setText(childs.get(position).Title);
            holder.Lbl.setSelected(true);
        } else {
            holder.Lbl.setVisibility(View.GONE);
        }
        holder.Img.setOnClickListener(v -> {
            if (childs.get(position).ActionName.equals("WebClick")) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(childs.get(position).ActionRequest));
                context.startActivity(i);
            } else if (childs.get(position).ActionName.equals("ArticleContentList")) {
                Intent intent = new Intent(context, ArticleContentListActivity.class);
                intent.putExtra("Request", childs.get(position).ActionRequest);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ImageRecyclerImage)
        ImageView Img;

        @BindView(R.id.LblRecyclerImage)
        TextView Lbl;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbl.setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
        }
    }
}