package ntk.android.academy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.Academy;
import ntk.android.academy.R;
import ntk.android.academy.adapter.AdFragment;
import ntk.android.academy.adapter.AdPager;
import ntk.android.academy.adapter.drawer.AdDrawer;
import ntk.android.academy.adapter.toolbar.AdToobar;
import ntk.android.academy.event.toolbar.EVHamberMenuClick;
import ntk.android.academy.event.toolbar.EVSearchClick;
import ntk.android.academy.fragment.FrBmi;
import ntk.android.academy.fragment.FrCommand;
import ntk.android.academy.fragment.FrFav;
import ntk.android.academy.fragment.FrHome;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigation;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigationItem;
import ntk.android.academy.model.theme.Theme;
import ntk.android.academy.model.theme.Toolbar;
import ntk.android.academy.utill.FontManager;

public class ActMain extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.bottomNavMenu)
    AHBottomNavigation navigation;

    @BindView(R.id.ViewPagerContainer)
    AdPager pager;

    @BindView(R.id.drawerlayout)
    FlowingDrawer drawer;

    @BindView(R.id.HeaderImageActMain)
    KenBurnsView Header;

    @BindView(R.id.RecyclerToolbarActMain)
    RecyclerView RvToolbar;

    @BindView(R.id.RecyclerDrawer)
    RecyclerView RvDrawer;

    private long lastPressedTime;
    private static final int PERIOD = 2000;

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
        navigation.setTitleTextSize( 18, 16);
        navigation.setTitleTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        navigation.setAccentColor(Color.parseColor("#f04d4d"));
        navigation.setInactiveColor(Color.parseColor("#030303"));
        navigation.setOnTabSelectedListener(this);
        navigation.setColored(false);

        AdFragment adapter = new AdFragment(getSupportFragmentManager());
        adapter.addFragment(new FrCommand());
        adapter.addFragment(new FrHome());
        adapter.addFragment(new FrFav());
        adapter.addFragment(new FrBmi());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(1, false);

        HandelToolbarDrawer();
    }

    private void HandelToolbarDrawer() {
        Theme theme = new Gson().fromJson(Academy.JsonThemeExmaple, Theme.class);

        RvToolbar.setHasFixedSize(true);
        RvToolbar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Toolbar> toolbars = new ArrayList<>();
        toolbars.add(theme.Toolbar);
        AdToobar AdToobar = new AdToobar(this, toolbars);
        RvToolbar.setAdapter(AdToobar);
        AdToobar.notifyDataSetChanged();

        ImageLoader.getInstance().displayImage(theme.Toolbar.Drawer.HeaderImage, Header);

        RvDrawer.setHasFixedSize(true);
        RvDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdDrawer AdDrawer = new AdDrawer(this, theme.Toolbar.Drawer.Child, drawer);
        RvDrawer.setAdapter(AdDrawer);
        AdDrawer.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        pager.setCurrentItem(position, false);
        return true;
    }

    @Subscribe
    public void EvClickSearch(EVSearchClick click) {
        startActivity(new Intent(this, ActSearch.class));
    }

    @Subscribe
    public void EvClickMenu(EVHamberMenuClick click) {
        drawer.openMenu(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "برای خروج مجددا کلید بازگشت را فشار دهید",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;
    }
}
