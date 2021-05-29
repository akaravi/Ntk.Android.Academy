package ntk.android.academy.adapter.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.activity.AboutUsActivity;
import ntk.android.academy.activity.BlogListActivity;
import ntk.android.academy.activity.MainActivity;
import ntk.android.academy.activity.NewsListActivity;
import ntk.android.base.activity.common.NotificationsActivity;
import ntk.android.base.activity.poling.PolingActivity;
import ntk.android.base.activity.ticketing.FaqActivity;
import ntk.android.base.activity.ticketing.TicketListActivity;
import ntk.android.base.dtomodel.theme.DrawerChildThemeDtoModel;
import ntk.android.base.room.NotificationStorageService;
import ntk.android.base.utill.FontManager;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private final List<DrawerChildThemeDtoModel> childs;
    private final Context context;
    private int Click;
    private final FlowingDrawer Drawer;

    public DrawerAdapter(Context context, List<DrawerChildThemeDtoModel> children, FlowingDrawer drawer) {
        this.childs = children;
        this.context = context;
        this.Drawer = drawer;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_drawer_child, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(childs.get(position).Icon, holder.Img);
        if (childs.get(position).Type == 1) {
            int n = new NotificationStorageService().getAllUnread(context).size();
            if (n != 0) {
                holder.Lbls.get(1).setText(String.valueOf(n));
                holder.Lbls.get(1).setVisibility(View.VISIBLE);
            }
        }
        holder.Lbls.get(0).setText(childs.get(position).Title);

        holder.Root.setOnClickListener(v -> {
            switch (childs.get(position).Id) {
                case 1:
                    ClickInbox();
                    break;
                case 2:
                    ClickNews();
                    break;
                case 3:
                    ClickPooling();
                    break;
                case 5:
                    ClickShare();
                    break;
                case 6:
                    ClickAbout();
                    break;
                case 7:
                    ClickContact();
                    break;
                case 8:
                    ClickFeedBack();
                    break;
                case 9:
                    ClickQuestion();
                    break;
                case 10:
                    ClickIntro();
                    break;
                case 11:
                    ClickBlog();
                    break;
            }
        });
    }

    private void ClickBlog() {
        context.startActivity(new Intent(context, BlogListActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    @Override
    public int getItemCount() {
        if (childs != null)
            return childs.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ImgRecyclerDrawerChild)
        ImageView Img;

        @BindViews({R.id.lblRecyclerDrawerChild, R.id.lblBadgeRecyclerDrawerChild})
        List<TextView> Lbls;

        @BindView(R.id.RootContainerRecyclerDrawerChild)
        RelativeLayout Root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbls.get(0).setTypeface(FontManager.T1_Typeface(context));
            Lbls.get(1).setTypeface(FontManager.T1_Typeface(context));
        }
    }

    private void ClickIntro() {
        ((MainActivity) context).onMainIntro();
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickInbox() {
        context.startActivity(new Intent(context, NotificationsActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickNews() {
        context.startActivity(new Intent(context, NewsListActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickPooling() {
        context.startActivity(new Intent(context, PolingActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickShare() {
        ((MainActivity) context).onInviteMethod();
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickAbout() {
        context.startActivity(new Intent(context, AboutUsActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickContact() {
        context.startActivity(new Intent(context, TicketListActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }

    private void ClickFeedBack() {
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
        ((MainActivity) context).onFeedbackClick();
    }

    private void ClickQuestion() {
        context.startActivity(new Intent(context, FaqActivity.class));
        if (Drawer != null) {
            Drawer.closeMenu(true);
        }
    }
}
