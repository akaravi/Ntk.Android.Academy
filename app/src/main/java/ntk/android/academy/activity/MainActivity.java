package ntk.android.academy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
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
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.BuildConfig;
import ntk.android.academy.R;
import ntk.android.academy.adapter.FragmentAdapter;
import ntk.android.academy.adapter.PagerAdapter;
import ntk.android.academy.adapter.drawer.DrawerAdapter;
import ntk.android.academy.adapter.toolbar.ToolbarAdapter;
import ntk.android.academy.fragment.BmiFragment;
import ntk.android.academy.fragment.CommandFragment;
import ntk.android.academy.fragment.FavoriteFragment;
import ntk.android.academy.event.toolbar.EVHamberMenuClick;
import ntk.android.academy.event.toolbar.EVSearchClick;
import ntk.android.academy.fragment.HomeFragment;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigation;
import ntk.android.academy.library.ahbottomnavigation.AHBottomNavigationItem;
import ntk.android.base.activity.abstraction.AbstractMainActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.application.MainResponseDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.application.ApplicationAppService;
import ntk.android.base.utill.AppUtill;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.core.entity.CoreMain;
import ntk.android.base.api.baseModel.theme.Theme;
import ntk.android.base.api.baseModel.theme.Toolbar;
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
        Theme theme = new Gson().fromJson(Preferences.with(this).UserInfo().theme(), Theme.class);
        if (theme == null) return;
        RvToolbar.setHasFixedSize(true);
        RvToolbar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Toolbar> toolbars = new ArrayList<>();
        toolbars.add(theme.Toolbar);
        ToolbarAdapter AdToobar = new ToolbarAdapter(this, toolbars);
        RvToolbar.setAdapter(AdToobar);
        AdToobar.notifyDataSetChanged();

        ImageLoader.getInstance().displayImage(theme.Toolbar.Drawer.HeaderImage, Header);

        RvDrawer.setHasFixedSize(true);
        RvDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter AdDrawer = new DrawerAdapter(this, theme.Toolbar.Drawer.Child, drawer);
        RvDrawer.setAdapter(AdDrawer);
        AdDrawer.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        HandelData();
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
        startActivity(new Intent(this, ArticleSearchActivity.class));
    }

    @Subscribe
    public void EvClickMenu(EVHamberMenuClick click) {
        drawer.openMenu(false);
    }



    private void HandelData() {
        if (AppUtill.isNetworkAvailable(this)) {
            new ApplicationAppService(this).getResponseMain().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<MainResponseDtoModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<MainResponseDtoModel> mainCoreResponse) {
                            if(!mainCoreResponse.IsSuccess)
                            {
                                //BtnRefresh.setVisibility(View.VISIBLE);
                                Toasty.warning(MainActivity.this, "خطای سامانه مجددا تلاش کنید"+mainCoreResponse.ErrorMessage, Toasty.LENGTH_LONG, true).show();
                                return;
                            }
                            Preferences.with(MainActivity.this).appVariableInfo().setConfigapp(new Gson().toJson(mainCoreResponse.Item));
                            CheckUpdate();
                        }

                        @Override
                        public void onError(Throwable e) {

                            Toasty.warning(MainActivity.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();

                        }
                    });
        } else {
            CheckUpdate();
        }
    }


}
