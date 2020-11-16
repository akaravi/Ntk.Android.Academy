package ntk.android.academy.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.DetailPoolCategoryAdapter;
import ntk.android.base.config.ConfigRestHeader;
import ntk.android.base.config.ConfigStaticValue;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.polling.PollingContentModel;
import ntk.android.base.services.pooling.PollingContentService;
import ntk.android.base.utill.FontManager;
import ntk.android.base.api.pooling.interfase.IPooling;
import ntk.android.base.api.pooling.model.PoolingContentListRequest;
import ntk.android.base.api.pooling.model.PoolingContentListResponse;
import ntk.android.base.config.RetrofitManager;

public class PoolingDetailActivity extends AppCompatActivity {

    @BindView(R.id.lblTitleActDetailPooling)
    TextView LblTitle;

    @BindView(R.id.recyclerDetailPooling)
    RecyclerView Rv;

    private String RequestStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_pooling);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        LblTitle.setText(getIntent().getStringExtra("Title"));
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        RequestStr = getIntent().getExtras().getString("Request");

        HandelData(1, new Gson().fromJson(RequestStr, FilterDataModel.class));
    }

    private void HandelData(int i, FilterDataModel request) {
        new PollingContentService(this).getAll(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<PollingContentModel>>() {

                    @Override
                    public void onNext(@NonNull ErrorException<PollingContentModel> poolingContentListResponse) {
                        if (poolingContentListResponse.IsSuccess) {
                            DetailPoolCategoryAdapter adapter = new DetailPoolCategoryAdapter(PoolingDetailActivity.this, poolingContentListResponse.ListItems);
                            Rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(PoolingDetailActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.imgBackActDetailPooling)
    public void ClickBack() {
        finish();
    }
}
