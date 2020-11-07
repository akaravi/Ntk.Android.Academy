package ntk.android.academy.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.adapter.theme.AdHome;
import ntk.android.academy.library.AnimatedRecyclerView;
import ntk.android.base.utill.EasyPreference;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.baseModel.theme.Theme;

public class FrHome extends Fragment {

    @BindView(R.id.lblProgressFrHome)
    TextView LblProgress;

    @BindView(R.id.rowProgressFrHome)
    LinearLayout Loading;

    @BindView(R.id.progressFrHome)
    ProgressBar Progress;

    @BindView(R.id.swipRefreshFrHome)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.RecyclerHome)
    AnimatedRecyclerView RvHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        Loading.setVisibility(View.VISIBLE);
        RvHome.setVisibility(View.GONE);
        LblProgress.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        RvHome.setHasFixedSize(true);
        RvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Refresh.setOnRefreshListener(() -> {
            Refresh.setRefreshing(false);
            Loading.setVisibility(View.VISIBLE);
            SetDataHome();
        });
        SetDataHome();
    }

    private void SetDataHome() {
        Theme theme = new Gson().fromJson(EasyPreference.with(getContext()).getString("Theme", ""), Theme.class);
        if (theme == null) return;
        AdHome adapter = new AdHome(getContext(), theme.Childs);
        RvHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RvHome.scheduleLayoutAnimation();
        RvHome.setItemViewCacheSize(theme.Childs.size());
        Loading.setVisibility(View.GONE);
        RvHome.setVisibility(View.VISIBLE);
    }
}
