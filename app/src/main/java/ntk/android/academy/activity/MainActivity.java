package ntk.android.academy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.adapter.FragmentAdapter;
import ntk.android.academy.adapter.PagerAdapter;
import ntk.android.academy.adapter.drawer.DrawerAdapter;
import ntk.android.academy.adapter.toolbar.ToolbarAdapter;
import ntk.android.academy.event.toolbar.EVHamberMenuClick;
import ntk.android.academy.event.toolbar.EVSearchClick;
import ntk.android.academy.fragment.BmiFragment;
import ntk.android.academy.fragment.CommandFragment;
import ntk.android.academy.fragment.FavoriteFragment;
import ntk.android.academy.fragment.HomeFragment;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigation;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigationItem;
import ntk.android.base.activity.abstraction.AbstractMainActivity;
import ntk.android.base.dtomodel.theme.ThemeDtoModel;
import ntk.android.base.dtomodel.theme.ToolbarDtoModel;
import ntk.android.base.utill.FontManager;
import ntk.android.base.utill.prefrense.Preferences;

public class MainActivity extends AbstractMainActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.bottomNavMenu)
    AHBottomNavigation navigation;

    @BindView(R.id.ViewPagerContainer)
    PagerAdapter pager;

    @BindView(R.id.drawerlayout)
    FlowingDrawer drawer;

    @BindView(R.id.HeaderImageActMain)
    KenBurnsView Header;

    @BindView(R.id.RecyclerToolbarActMain)
    RecyclerView RvToolbar;

    @BindView(R.id.RecyclerDrawer)
    RecyclerView RvDrawer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        drawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
            }
        });
        navigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
        navigation.setBehaviorTranslationEnabled(false);
        navigation.setTitleTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        AHBottomNavigationItem BMI = new AHBottomNavigationItem("BMI", R.drawable.ic_one, R.color.colorMenu);
        AHBottomNavigationItem Favorite = new AHBottomNavigationItem("علاقه مندی", R.drawable.ic_two, R.color.colorMenu);
        AHBottomNavigationItem Home = new AHBottomNavigationItem("خانه", R.drawable.ic_three, R.color.colorMenu);
        AHBottomNavigationItem Command = new AHBottomNavigationItem("دستور پخت", R.drawable.ic_five, R.color.colorMenu);
        navigation.addItem(Command);
        navigation.addItem(Home);
        navigation.addItem(Favorite);
        navigation.addItem(BMI);

        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setCurrentItem(1);
        navigation.setTitleTextSize(20, 18);
        navigation.setTitleTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        navigation.setAccentColor(Color.parseColor("#f04d4d"));
        navigation.setInactiveColor(Color.parseColor("#030303"));
        navigation.setOnTabSelectedListener(this);
        navigation.setColored(false);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CommandFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new FavoriteFragment());
        adapter.addFragment(new BmiFragment());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(1, false);

        HandelToolbarDrawer();
    }

    private void HandelToolbarDrawer() {
        ThemeDtoModel theme = new Gson().fromJson(Preferences.with(this).UserInfo().theme(), ThemeDtoModel.class);
        if (theme == null) return;
        RvToolbar.setHasFixedSize(true);
        RvToolbar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<ToolbarDtoModel> toolbars = new ArrayList<>();
        toolbars.add(theme.Toolbar);
        ToolbarAdapter AdToobar = new ToolbarAdapter(this, toolbars);
        RvToolbar.setAdapter(AdToobar);
        AdToobar.notifyDataSetChanged();

        ImageLoader.getInstance().displayImage(theme.Toolbar.DrawerThemeDtoModel.HeaderImage, Header);

        RvDrawer.setHasFixedSize(true);
        RvDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter AdDrawer = new DrawerAdapter(this, theme.Toolbar.DrawerThemeDtoModel.Child, drawer);
        RvDrawer.setAdapter(AdDrawer);
        AdDrawer.notifyDataSetChanged();
    }
    

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        pager.setCurrentItem(position, false);
        return true;
    }

    @Subscribe
    public void EvClickSearch(EVSearchClick click) {
        startActivity(new Intent(this, ArticleSearchActivity.class));
    }

    @Subscribe
    public void EvClickMenu(EVHamberMenuClick click) {
        drawer.openMenu(false);
    }
    
}
