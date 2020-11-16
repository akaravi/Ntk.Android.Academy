package ntk.android.academy.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.PoolCategoryAdapter;
import ntk.android.base.activity.BaseActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.polling.PollingCategoryModel;
import ntk.android.base.services.pooling.PollingCategoryService;
import ntk.android.base.utill.FontManager;

public class PoolingActivity extends BaseActivity {

    @BindView(R.id.lblTitleActPooling)
    TextView LblTitle;

    @BindView(R.id.recyclerPooling)
    RecyclerView Rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pooling);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        new PollingCategoryService(this).getAll(new FilterDataModel())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<PollingCategoryModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<PollingCategoryModel> poolingCategoryResponse) {
                        if (poolingCategoryResponse.IsSuccess) {
                            PoolCategoryAdapter adapter = new PoolCategoryAdapter(PoolingActivity.this, poolingCategoryResponse.ListItems);
                            Rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (adapter.getItemCount() > 0)
                                switcher.showContentView();
                            else
                                switcher.showEmptyView();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(PoolingActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                    }

                });
    }


    @OnClick(R.id.imgBackActPooling)
    public void ClickBack() {
        finish();
    }
}
