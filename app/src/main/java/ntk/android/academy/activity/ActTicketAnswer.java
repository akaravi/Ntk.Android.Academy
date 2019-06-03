package ntk.android.academy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.AdAttach;
import ntk.android.academy.adapter.AdTicketAnswer;
import ntk.android.academy.config.ConfigRestHeader;
import ntk.android.academy.config.ConfigStaticValue;
import ntk.android.academy.event.EvRemoveAttach;
import ntk.android.academy.utill.AppUtill;
import ntk.android.academy.utill.FontManager;
import ntk.base.api.ticket.interfase.ITicket;
import ntk.base.api.ticket.model.TicketingAnswer;
import ntk.base.api.ticket.model.TicketingAnswerListRequest;
import ntk.base.api.ticket.model.TicketingAnswerListResponse;
import ntk.base.api.ticket.model.TicketingAnswerSubmitRequest;
import ntk.base.api.ticket.model.TicketingAnswerSubmitResponse;
import ntk.base.api.utill.RetrofitManager;


public class ActTicketAnswer extends AppCompatActivity {

    @BindViews({R.id.recyclerAnswer,
            R.id.RecyclerAttachTicketAnswer})
    List<RecyclerView> Rvs;

    @BindView(R.id.lblTitleActTicketAnswer)
    TextView Lbl;

    @BindView(R.id.txtMessageActTicketAnswer)
    TextView txt;

    @BindView(R.id.btnSubmitActTicketAnswer)
    Button Btn;

    @BindView(R.id.mainLayoutActTicketAnswer)
    CoordinatorLayout layout;

    private ArrayList<TicketingAnswer> tickets = new ArrayList<>();
    private AdTicketAnswer adapter;
    private List<String> attaches = new ArrayList<>();
    private AdAttach AdAtach;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ticket_answer);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        txt.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Btn.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Lbl.setText("پاسخ تیکت شماره");
        Rvs.get(0).setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rvs.get(0).setLayoutManager(manager);

        adapter = new AdTicketAnswer(this, tickets);
        Rvs.get(0).setAdapter(adapter);
        adapter.notifyDataSetChanged();

        HandelData(1);

        Rvs.get(1).setHasFixedSize(true);
        Rvs.get(1).setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdAtach = new AdAttach(this, attaches);
        Rvs.get(1).setAdapter(AdAtach);
        AdAtach.notifyDataSetChanged();
    }

    private void HandelData(int i) {
        RetrofitManager retro = new RetrofitManager(this);
        ITicket iTicket = retro.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ITicket.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

        Observable<TicketingAnswerListResponse> Call = iTicket.GetTicketAnswerList(headers, new Gson().fromJson(getIntent().getExtras().getString("Request"), TicketingAnswerListRequest.class));
        Call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TicketingAnswerListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TicketingAnswerListResponse model) {
                        tickets.addAll(model.ListItems);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(ActTicketAnswer.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.imgBackActTicketAnswer)
    public void ClickBack() {
        finish();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.RippleAttachActTicketAnswer)
    public void ClickAttach() {
        TedRx2Permission.with(this)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        StorageChooser.Theme theme = new StorageChooser.Theme(getApplicationContext());
                        theme.setScheme(getResources().getIntArray(R.array.paranoid_theme));
                        StorageChooser chooser = new StorageChooser.Builder()
                                .withActivity(this)
                                .allowCustomPath(true)
                                .setType(StorageChooser.FILE_PICKER)
                                .disableMultiSelect()
                                .setTheme(theme)
                                .withMemoryBar(true)
                                .withFragmentManager(getFragmentManager())
                                .build();
                        chooser.show();
                        chooser.setOnSelectListener(this::UploadFile);
                    } else {
                    }
                }, throwable -> {
                });

    }

    private void UploadFile(String s) {
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
        RetrofitManager manager = new RetrofitManager(this);
        Observable<String> observable = manager.FileUpload(null, s, headers);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String url) {
                        String[] strs = s.split("/");
                        String FileName = strs[strs.length - 1];
                        attaches.add(FileName + " - " + url);
                        AdAtach.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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


    @Subscribe
    public void EventRemove(EvRemoveAttach event) {
        attaches.remove(event.GetPosition());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnSubmitActTicketAnswer)
    public void ClickSubmit() {
        if (txt.getText().toString().isEmpty()) {
            YoYo.with(Techniques.Tada).duration(700).playOn(txt);
        } else {
            if (AppUtill.isNetworkAvailable(this)) {
                TicketingAnswerSubmitRequest request = new TicketingAnswerSubmitRequest();
                request.HtmlBody = txt.getText().toString();
                request.LinkTicketId = getIntent().getLongExtra("TicketId", 0);
//                request.LinkFileIds = attachesSrc;
                RetrofitManager retro = new RetrofitManager(this);
                Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
                ITicket iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(ITicket.class);
                Observable<TicketingAnswerSubmitResponse> Call = iTicket.GetTicketAnswerSubmit(headers, request);
                Call.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<TicketingAnswerSubmitResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(TicketingAnswerSubmitResponse model) {
                                Toasty.success(ActTicketAnswer.this, "با موفقیت ثبت شد", Toasty.LENGTH_LONG, true).show();
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        init();
                                    }
                                }).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else {
                Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
            }
        }
    }
}