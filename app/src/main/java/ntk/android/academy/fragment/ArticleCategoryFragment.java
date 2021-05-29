package ntk.android.academy.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java9.util.function.Function;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleCategoryAdapter;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.article.ArticleCategoryModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.fragment.common.BaseFilterModelFragment;
import ntk.android.base.services.article.ArticleCategoryService;
import ntk.android.base.utill.FontManager;

public class ArticleCategoryFragment extends
        BaseFilterModelFragment<ArticleCategoryModel> {
    @Override
    public Function<FilterModel, Observable<ErrorException<ArticleCategoryModel>>> getService() {
        return new ArticleCategoryService(getContext())::getAll;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new ArticleCategoryAdapter(getContext(), models);
    }
}
    

