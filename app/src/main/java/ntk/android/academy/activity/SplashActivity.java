package ntk.android.academy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.BuildConfig;
import ntk.android.academy.R;
import ntk.android.base.activity.BaseSplashActivity;
import ntk.android.base.activity.IntroActivity;
import ntk.android.base.activity.RegisterActivity;
import ntk.android.base.config.ConfigRestHeader;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.utill.AppUtill;
import ntk.android.base.utill.EasyPreference;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.core.entity.CoreMain;
import ntk.android.base.api.core.interfase.ICore;
import ntk.android.base.api.core.entity.CoreTheme;
import ntk.android.base.api.core.model.MainCoreResponse;
import ntk.android.base.config.RetrofitManager;

public class SplashActivity extends BaseSplashActivity {
    
}
