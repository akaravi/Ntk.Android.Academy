package ntk.android.academy.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.academy.R;
import ntk.android.academy.adapter.ArticleAdapter;
import ntk.android.academy.adapter.ArticleCommentAdapter;
import ntk.android.academy.adapter.TabArticleAdapter;
import ntk.android.base.Extras;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.application.MainResponseDtoModel;
import ntk.android.base.dtomodel.core.ScoreClickDtoModel;
import ntk.android.base.entitymodel.article.ArticleCommentModel;
import ntk.android.base.entitymodel.article.ArticleContentModel;
import ntk.android.base.entitymodel.article.ArticleContentOtherInfoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.ErrorExceptionBase;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.event.HtmlBodyEvent;
import ntk.android.base.services.article.ArticleCommentService;
import ntk.android.base.services.article.ArticleContentOtherInfoService;
import ntk.android.base.services.article.ArticleContentService;
import ntk.android.base.utill.AppUtill;
import ntk.android.base.utill.FontManager;
import ntk.android.base.utill.prefrense.Preferences;

public class PrevArticleDetailActivity extends AppCompatActivity {

    @BindView(R.id.progressDetail)
    ProgressBar Progress;

    @BindView(R.id.rowProgressDetail)
    LinearLayout Loading;

    @BindViews({R.id.lblTitleDetail,
            R.id.lblNameCommandDetail,
            R.id.lblKeySeenDetail,
            R.id.lblValueSeenDetail,
            R.id.lblPhotoExtraDetail,
            R.id.lblCalDetail,
            R.id.lblTimerOne,
            R.id.lblTimerTwo,
            R.id.lblTimerThree,
            R.id.lblTimerFour,
            R.id.lblTimerFive,
            R.id.lblTimerSix,
            R.id.lblMenuDetail,
            R.id.lblMenuTwoDetail,
            R.id.lblCommentDetail,
            R.id.lblProgressDetail
    })
    List<TextView> Lbls;

    @BindView(R.id.imgHeaderDetail)
    ImageView ImgHeader;

    @BindView(R.id.recyclerMenuDetail)
    RecyclerView RvSimilarArticle;

    @BindView(R.id.recyclerMenuTwoDetail)
    RecyclerView RvSimilarCategory;

    @BindView(R.id.WebViewBodyDetail)
    WebView webView;

    @BindView(R.id.recyclerTabDetail)
    RecyclerView RvTab;

    @BindView(R.id.recyclerCommentDetail)
    RecyclerView RvComment;

    @BindView(R.id.ratingBarDetail)
    RatingBar Rate;

    @BindView(R.id.PageDetail)
    LinearLayout Page;


    @BindView(R.id.mainLayoutDetail)
    CoordinatorLayout layout;


    private ErrorException<ArticleContentModel> model;
    long Id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base2_detail_activity);
        ButterKnife.bind(this);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        for (TextView tv : Lbls) {
            tv.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        }
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        RvTab.setHasFixedSize(true);
        RvTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        Id = getIntent().getExtras().getLong(Extras.EXTRA_FIRST_ARG);
        HandelDataContent();
        Loading.setVisibility(View.VISIBLE);

        RvComment.setHasFixedSize(true);
        RvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RvSimilarArticle.setHasFixedSize(true);
        RvSimilarArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        RvSimilarCategory.setHasFixedSize(true);
        RvSimilarCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        Rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (!fromUser) return;

                if (AppUtill.isNetworkAvailable(PrevArticleDetailActivity.this)) {
                    ScoreClickDtoModel request = new ScoreClickDtoModel();
                    request.Id = Id;
                    if (rating == 0.5) {
                        request.ScorePercent = 10;
                    }
                    if (rating == 1) {
                        request.ScorePercent = 20;
                    }
                    if (rating == 1.5) {
                        request.ScorePercent = 30;
                    }
                    if (rating == 2) {
                        request.ScorePercent = 40;
                    }
                    if (rating == 2.5) {
                        request.ScorePercent = 50;
                    }
                    if (rating == 3) {
                        request.ScorePercent = 60;
                    }
                    if (rating == 3.5) {
                        request.ScorePercent = 70;
                    }
                    if (rating == 4) {
                        request.ScorePercent = 80;
                    }
                    if (rating == 4.5) {
                        request.ScorePercent = 90;
                    }
                    if (rating == 5) {
                        request.ScorePercent = 100;
                    }
                    new ArticleContentService(PrevArticleDetailActivity.this).scoreClick(request)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new NtkObserver<ErrorExceptionBase>() {

                                @Override
                                public void onNext(@NonNull ErrorExceptionBase response) {
                                    Loading.setVisibility(View.GONE);
                                    if (response.IsSuccess) {
                                        Toasty.success(PrevArticleDetailActivity.this, "نظر شمابا موفقیت ثبت گردید").show();
                                    } else {
                                        Toasty.warning(PrevArticleDetailActivity.this, response.ErrorMessage).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Loading.setVisibility(View.GONE);
                                    Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            init();
                                        }
                                    }).show();
                                }

                            });
                } else {
                    Loading.setVisibility(View.GONE);
                    Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            init();
                        }
                    }).show();
                }
            }
        });
    }


    private void HandelDataContent() {
        if (AppUtill.isNetworkAvailable(this)) {
            new ArticleContentService(this).getOne(Id).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<ArticleContentModel> articleContentResponse) {
                            model = articleContentResponse;
                            if (model.Item != null) {
                                SetData(model);
                                HandelSimilary(Id);
                                HandelSimilaryCategory(Id);
                                if (Id > 0) {
                                    HandelDataContentOtherInfo(Id);
                                    HandelDataComment(Id);
                                }
                            }
                            Loading.setVisibility(View.GONE);
                            Page.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Loading.setVisibility(View.GONE);
                            Toasty.warning(PrevArticleDetailActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void HandelSimilary(long id) {
        if (AppUtill.isNetworkAvailable(this)) {
            FilterModel Request = new FilterModel();
            FilterDataModel f = new FilterDataModel();
            f.PropertyName = "LinkContentId";
            f.setIntValue(Id);
            Request.addFilter(f);

            new ArticleContentService(this).getAllWithSimilarsId(id, Request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<ArticleContentModel> response) {
                            if (response.ListItems.size() == 0) {
                                findViewById(R.id.RowSimilaryDetail).setVisibility(View.GONE);
                            } else {
                                ArticleAdapter adapter = new ArticleAdapter(PrevArticleDetailActivity.this, response.ListItems);
                                RvSimilarArticle.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                findViewById(R.id.RowSimilaryDetail).setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void HandelSimilaryCategory(long id) {
        if (AppUtill.isNetworkAvailable(this)) {
            FilterModel request = new FilterModel();
            new ArticleContentService(this).getAllWithCategoryUsedInContent(id, request).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<ArticleContentModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<ArticleContentModel> response) {
                            if (response.ListItems.size() == 0) {
                                findViewById(R.id.RowSimilaryCategoryDetail).setVisibility(View.GONE);
                            } else {
                                findViewById(R.id.RowSimilaryCategoryDetail).setVisibility(View.VISIBLE);
                                ArticleAdapter adapter = new ArticleAdapter(PrevArticleDetailActivity.this, response.ListItems);
                                RvSimilarCategory.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void HandelDataComment(long ContentId) {
        if (AppUtill.isNetworkAvailable(this)) {
            FilterModel Request = new FilterModel();
            FilterDataModel f = new FilterDataModel();
            f.PropertyName = "LinkContentId";
            f.setIntValue(ContentId);
            Request.addFilter(f);
            new ArticleCommentService(this).getAll(Request).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NtkObserver<ErrorException<ArticleCommentModel>>() {
                        @Override
                        public void onNext(@NonNull ErrorException<ArticleCommentModel> model) {
                            if (model.IsSuccess) {
                                if (model.ListItems.size() == 0) {
                                    findViewById(R.id.RowCommentDetail).setVisibility(View.GONE);
                                } else {
                                    ArticleCommentAdapter adapter = new ArticleCommentAdapter(PrevArticleDetailActivity.this, model.ListItems);
                                    RvComment.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    findViewById(R.id.RowCommentDetail).setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toasty.warning(PrevArticleDetailActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void HandelDataContentOtherInfo(long ContentId) {

        FilterModel Request = new FilterModel();
        FilterDataModel f = new FilterDataModel();
        f.PropertyName = "LinkContentId";
        f.setIntValue(ContentId);
        Request.addFilter(f);
        new ArticleContentOtherInfoService(this).getAll(Request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new NtkObserver<ErrorException<ArticleContentOtherInfoModel>>() {
                    @Override
                    public void onNext(@NonNull ErrorException<ArticleContentOtherInfoModel> articleContentOtherInfoResponse) {
                        SetDataOtherinfo(articleContentOtherInfoResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.warning(PrevArticleDetailActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                    }

                });


    }

    private void SetDataOtherinfo(ErrorException<ArticleContentOtherInfoModel> model) {
        if (model.ListItems == null || model.ListItems.size() == 0) {
            findViewById(R.id.RowTimeDetail).setVisibility(View.GONE);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.weight = 3;
            return;
        }
        findViewById(R.id.RowTimeDetail).setVisibility(View.GONE);
        List<ArticleContentOtherInfoModel> Info = new ArrayList<>();
        ArticleContentOtherInfoModel i = new ArticleContentOtherInfoModel();
        i.Title = "طرز تهیه";
        i.TypeId = 0;
        i.HtmlBody = this.model.Item.Body;
        Info.add(i);

        for (ArticleContentOtherInfoModel ai : model.ListItems) {
            switch (ai.TypeId) {
                case 21:
                    Lbls.get(7).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(6).setText(Html.fromHtml(ai.HtmlBody));
                    findViewById(R.id.RowTimeDetail).setVisibility(View.VISIBLE);
                    break;
                case 22:
                    Lbls.get(9).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(8).setText(Html.fromHtml(ai.HtmlBody));
                    findViewById(R.id.RowTimeDetail).setVisibility(View.VISIBLE);
                    break;
                case 23:
                    Lbls.get(11).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(10).setText(Html.fromHtml(ai.HtmlBody));
                    findViewById(R.id.RowTimeDetail).setVisibility(View.VISIBLE);
                    break;
                default:
                    Info.add(ai);
                    break;
            }
        }
        TabArticleAdapter adapter = new TabArticleAdapter(PrevArticleDetailActivity.this, Info);
        RvTab.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void SetData(ErrorException<ArticleContentModel> model) {
        double rating = 0.0;
        int sumClick = model.Item.ViewCount;
        if (model.Item.ViewCount == 0) sumClick = 1;
        if (model.Item.ScoreSumPercent / sumClick > 0 && model.Item.ScoreSumPercent / sumClick <= 10) {
            rating = 0.5;
        } else if (model.Item.ScoreSumPercent / sumClick > 10 && model.Item.ScoreSumPercent / sumClick <= 20) {
            rating = 1.0;
        } else if (model.Item.ScoreSumPercent / sumClick > 20 && model.Item.ScoreSumPercent / sumClick <= 30) {
            rating = 1.5;
        } else if (model.Item.ScoreSumPercent / sumClick > 30 && model.Item.ScoreSumPercent / sumClick <= 40) {
            rating = 2.0;
        } else if (model.Item.ScoreSumPercent / sumClick > 40 && model.Item.ScoreSumPercent / sumClick <= 50) {
            rating = 2.5;
        } else if (model.Item.ScoreSumPercent / sumClick > 50 && model.Item.ScoreSumPercent / sumClick <= 60) {
            rating = 3.0;
        } else if (model.Item.ScoreSumPercent / sumClick > 60 && model.Item.ScoreSumPercent / sumClick <= 70) {
            rating = 3.5;
        } else if (model.Item.ScoreSumPercent / sumClick > 70 && model.Item.ScoreSumPercent / sumClick <= 80) {
            rating = 4.0;
        } else if (model.Item.ScoreSumPercent / sumClick > 80 && model.Item.ScoreSumPercent / sumClick <= 90) {
            rating = 4.5;
        } else if (model.Item.ScoreSumPercent / sumClick > 90) {
            rating = 5.0;
        }
        Rate.setRating((float) rating);
        ImageLoader.getInstance().displayImage(model.Item.LinkMainImageIdSrc, ImgHeader);
        Lbls.get(0).setText(model.Item.Title);
        Lbls.get(1).setText(model.Item.Title);
        Lbls.get(3).setText(String.valueOf(model.Item.ViewCount));
        if (model.Item.Favorited) {
            ((ImageView) findViewById(R.id.imgHeartDetail)).setImageResource(R.drawable.ic_fav_full);
        }
    }

    @OnClick(R.id.imgBackDetail)
    public void ClickBack() {
        finish();
    }

    @OnClick(R.id.RowGalleryDetail)
    public void ClickGalley() {
        if (model.Item.LinkFileIdsSrc != null && model.Item.LinkFileIdsSrc.size() != 0) {
            String[] array;
            if (model.Item.LinkFileIdsSrc != null)
                array = model.Item.LinkFileIdsSrc.toArray(new String[0]);
            else
                array = new String[0];
            Intent intent = new Intent(this, PhotoGalleryActivity.class);
            intent.putExtra(Extras.EXTRA_FIRST_ARG, array);
            startActivity(intent);
        }
    }

    @Subscribe
    public void EventHtmlBody(HtmlBodyEvent event) {
        webView.loadData("<html dir=\"rtl\" lang=\"\"><body>" + event.GetMessage() + "</body></html>", "text/html; charset=utf-8", "UTF-8");
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

    @OnClick(R.id.imgCommentDetail)
    public void ClickCommentAdd() {
        if (AppUtill.isNetworkAvailable(this)) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.dialog_comment_add);

            TextView Lbl = dialog.findViewById(R.id.lblTitleDialogAddComment);
            Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            EditText[] Txt = new EditText[2];

            Txt[0] = dialog.findViewById(R.id.txtNameDialogAddComment);
            Txt[0].setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Txt[1] = dialog.findViewById(R.id.txtContentDialogAddComment);
            Txt[1].setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Button Btn = dialog.findViewById(R.id.btnSubmitDialogCommentAdd);
            Btn.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Btn.setOnClickListener(v -> {
                if (Txt[0].getText().toString().isEmpty()) {
                    Toast.makeText(PrevArticleDetailActivity.this, "لطفا مقادیر را وارد نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    if (Txt[1].getText().toString().isEmpty()) {
                        Toast.makeText(PrevArticleDetailActivity.this, "لطفا مقادیر را وارد نمایید", Toast.LENGTH_SHORT).show();
                    } else {
                        ArticleCommentModel add = new ArticleCommentModel();
                        add.Writer = Txt[0].getText().toString();
                        add.Comment = Txt[1].getText().toString();
                        add.LinkContentId = Id;


                        new ArticleCommentService(this).add(add).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new NtkObserver<ErrorException<ArticleCommentModel>>() {
                                    @Override
                                    public void onNext(@NonNull ErrorException<ArticleCommentModel> e) {
                                        if (e.IsSuccess) {
                                            HandelDataComment(Id);
                                            dialog.dismiss();
                                            Toasty.success(PrevArticleDetailActivity.this, "نظر شما با موفقیت ثبت شد").show();
                                        } else {
                                            Toasty.warning(PrevArticleDetailActivity.this, "لطفا مجددا تلاش کنید").show();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                init();
                                            }
                                        }).show();
                                    }
                                });
                    }
                }
            });
            dialog.show();
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgFavDetail)
    public void ClickFav() {
        if (!model.Item.Favorited) {
            Fav();
        } else {
            UnFav();
        }
    }

    private void Fav() {
        if (AppUtill.isNetworkAvailable(this)) {


            new ArticleContentService(this).addFavorite(Id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NtkObserver<ErrorExceptionBase>() {
                        @Override
                        public void onNext(@NonNull ErrorExceptionBase e) {
                            if (e.IsSuccess) {
                                Toasty.success(PrevArticleDetailActivity.this, "با موفقیت ثبت شد").show();
                                model.Item.Favorited = !model.Item.Favorited;
                                if (model.Item.Favorited) {
                                    ((ImageView) findViewById(R.id.imgHeartDetail)).setImageResource(R.drawable.ic_fav_full);
                                } else {
                                    ((ImageView) findViewById(R.id.imgHeartDetail)).setImageResource(R.drawable.ic_fav);
                                }
                            } else {
                                Toasty.error(PrevArticleDetailActivity.this, e.ErrorMessage, Toast.LENGTH_LONG, true).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    init();
                                }
                            }).show();
                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void UnFav() {
        if (AppUtill.isNetworkAvailable(this)) {
            new ArticleContentService(this).removeFavorite(Id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NtkObserver<ErrorExceptionBase>() {
                        @Override
                        public void onNext(@NonNull ErrorExceptionBase e) {
                            if (e.IsSuccess) {
                                Toasty.success(PrevArticleDetailActivity.this, "با موفقیت ثبت شد").show();
                                model.Item.Favorited = !model.Item.Favorited;
                                if (model.Item.Favorited) {
                                    ((ImageView) findViewById(R.id.imgHeartDetail)).setImageResource(R.drawable.ic_fav_full);
                                } else {
                                    ((ImageView) findViewById(R.id.imgHeartDetail)).setImageResource(R.drawable.ic_fav);
                                }
                            } else {
                                Toasty.error(PrevArticleDetailActivity.this, e.ErrorMessage, Toast.LENGTH_LONG, true).show();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    init();
                                }
                            }).show();
                        }

                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgShareDetail)
    public void ClickShare() {
//        String st = Preferences.with(this).appVariableInfo().configapp();
        MainResponseDtoModel mcr = new Gson().fromJson("", MainResponseDtoModel.class);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String message = model.Item.Title + "\n" + model.Item.Description + "\n";
        if (model.Item.Body != null) {
            message = message + Html.fromHtml(model.Item.Body
                    .replace("<p>", "")
                    .replace("</p>", ""));
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, message + "\n\n\n" + this.getString(R.string.app_name) + "\n" + "لینک دانلود:" + "\n" + mcr.AppUrl);
        shareIntent.setType("text/txt");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.startActivity(Intent.createChooser(shareIntent, "به اشتراک گزاری با...."));
    }

    @OnClick(R.id.playDetail)
    public void onPlayClick() {
        Toasty.warning(PrevArticleDetailActivity.this, "موردی یافت نشد").show();
    }

    @OnClick(R.id.calDetail)
    public void onCalClick() {
        Toasty.warning(PrevArticleDetailActivity.this, "موردی یافت نشد").show();
    }
}
