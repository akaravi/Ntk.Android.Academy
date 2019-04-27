package ntk.android.academy;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ntk.android.academy.model.theme.Drawer;
import ntk.android.academy.model.theme.DrawerChild;
import ntk.android.academy.model.theme.HamberMenu;
import ntk.android.academy.model.theme.SearchBox;
import ntk.android.academy.model.theme.ShoppingCart;
import ntk.android.academy.model.theme.Theme;
import ntk.android.academy.model.theme.ThemeChild;
import ntk.android.academy.model.theme.ThemeChildConfig;
import ntk.android.academy.model.theme.Toolbar;
import ntk.android.academy.utill.Constant;
import ntk.android.academy.utill.FontManager;
import ntk.base.api.article.model.ArticleContentListRequest;
import ntk.base.api.article.model.ArticleTagRequest;
import ntk.base.api.model.Filters;
import ntk.base.api.utill.NTKUtill;

public class Academy extends MultiDexApplication {

    public static boolean Inbox = false;
    public static String JsonThemeExmaple = "";

    @Override
    public void onCreate() {
        super.onCreate();

        if (!new File(getCacheDir(), "image").exists()) {
            new File(getCacheDir(), "image").mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(getCacheDir(), "image")))
                .diskCacheFileNameGenerator(imageUri -> {
                    String[] Url = imageUri.split("/");
                    return Url[Url.length];
                })
                .build();
        ImageLoader.getInstance().init(config);

        Toasty.Config.getInstance()
                .setToastTypeface(FontManager.GetTypeface(getApplicationContext(), FontManager.IranSans))
                .setTextSize(14).apply();

//        JsonThemeExmaple
        CreateJson();
    }

    private void CreateJson() {

        new Constant().SetMap();

        Drawer drawer = new Drawer();
        drawer.CircleImage = null;
        drawer.HeaderImage = "http://oco.ir/files/8539db25addc4102b901efde3b3b2b4c.jpg";
        drawer.Type = 1;
        List<DrawerChild> childs = new ArrayList<>();
        childs.add(new DrawerChild(1, 1, "صندوق پیام", "https://image.flaticon.com/icons/png/512/107/107822.png"));
        childs.add(new DrawerChild(2, 2, "اخبار", "https://image.flaticon.com/icons/png/512/31/31866.png"));
        childs.add(new DrawerChild(3, 2, "نظرسنجی", "https://image.flaticon.com/icons/png/512/120/120114.png"));
        childs.add(new DrawerChild(4, 2, "تنظیمات", "https://image.flaticon.com/icons/png/512/57/57047.png"));
        childs.add(new DrawerChild(5, 2, "دعوت از دوستان", "https://image.flaticon.com/icons/png/512/109/109534.png"));
        childs.add(new DrawerChild(6, 2, "درباره ما", "https://image.flaticon.com/icons/png/512/97/97849.png"));
        childs.add(new DrawerChild(7, 2, "تماس با ما", "https://image.flaticon.com/icons/png/512/13/13936.png"));
        childs.add(new DrawerChild(8, 2, "بازخورد", "https://image.flaticon.com/icons/png/512/87/87702.png"));
        childs.add(new DrawerChild(9, 2, "پرسش های متداول", "https://image.flaticon.com/icons/png/512/43/43392.png"));
        drawer.Child = childs;

//        HmberMenu
        HamberMenu HM = new HamberMenu();
        HM.Color = "#000000";
        HM.Image = "https://image.flaticon.com/icons/png/512/52/52045.png";

//        SearchBox
        SearchBox SB = new SearchBox();
        SB.Color = "#000000";
        SB.Image = "https://image.flaticon.com/icons/png/512/55/55369.png";

//        ShoppingCart
        ShoppingCart cart = new ShoppingCart();
        cart.Color = "#000000";
        cart.Image = "https://image.flaticon.com/icons/png/512/107/107831.png";

//        Toolbar
        Toolbar toolbar = new Toolbar();
        toolbar.Cart = cart;
        toolbar.Type = 1;
        toolbar.BackgroundColor = "#ffffff";
        toolbar.ColorBelowLine = "#f04d4d";
        toolbar.Drawer = drawer;
        toolbar.HamberMenu = HM;
        toolbar.SearchBox = SB;

        List<ThemeChild> themeChildren = new ArrayList<>();


        ArticleTagRequest tag_request = new ArticleTagRequest();
        tag_request.SortType = NTKUtill.Random_Sort;
        tag_request.SortColumn = "Id";
        themeChildren.add(new ThemeChild(1, 1, "ArticleTagList", null, null, new Gson().toJson(tag_request)));


        List<ThemeChildConfig> configs_sinfle_iamge = new ArrayList<>();
        configs_sinfle_iamge.add(new ThemeChildConfig(1, "دلمه کلم مجلسی ایرانی مناسب برای ناهار برای مجالس مهمانی", null, null, "", "https://ashpazidorehami.com/wp-content/uploads/2019/03/1546319980_B9eN7.jpg", "WebClick", "https://ashpazidorehami.com/%D8%AF%D9%84%D9%85%D9%87-%DA%A9%D9%84%D9%85-%D9%85%D8%AC%D9%84%D8%B3%DB%8C/"));
        configs_sinfle_iamge.add(new ThemeChildConfig(2, null, null, null, "", "https://ashpazidorehami.com/wp-content/uploads/2018/03/slider_bg1-1.jpg", "WebClick", "https://ashpazidorehami.com/"));
        themeChildren.add(new ThemeChild(2, 1, "CoreImage", configs_sinfle_iamge, null, null));


        List<ThemeChildConfig> confige_core_two_button = new ArrayList<>();
        confige_core_two_button.add(new ThemeChildConfig(1, "پیشنهاد وعده های روزانه", "#ff00ff", "#ffffff", "15", null, "WebClick", "https://ashpazidorehami.com/#"));
        confige_core_two_button.add(new ThemeChildConfig(2, "دستورات آشپزی فصل", "#ff00ff", "#ffffff", "15", null, "WebClick", "https://ashpazidorehami.com/#"));
        themeChildren.add(new ThemeChild(3, 1, "CoreButton", confige_core_two_button, null, null));


        ArticleContentListRequest content_list = new ArticleContentListRequest();
        content_list.SortType = NTKUtill.Descnding_Sort;
        content_list.SortColumn = "Id";
        List<Filters> filters = new ArrayList<>();
        Filters f = new Filters();
        f.IntValue1 = Long.parseLong("20195");
        f.PropertyName = "LinkCategoryId";
        filters.add(f);
        content_list.filters = filters;

        List<ThemeChildConfig> config_horizental_menu_one = new ArrayList<>();
        config_horizental_menu_one.add(new ThemeChildConfig(1, "آخرین دستورات آشپزی", null, "#000000", "15", null, null, null));
        config_horizental_menu_one.add(new ThemeChildConfig(2, "لیست کامل", null, "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        themeChildren.add(new ThemeChild(4, 1, "ArticleContentList", null, config_horizental_menu_one, new Gson().toJson(content_list)));

        List<ThemeChildConfig> config_horizental_menu_t = new ArrayList<>();
        config_horizental_menu_t.add(new ThemeChildConfig(1, "جدید ترین کلیپ های آشپزی", null, "#000000", "15", null, null, null));
        config_horizental_menu_t.add(new ThemeChildConfig(2, "لیست کامل", null, "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        themeChildren.add(new ThemeChild(4, 1, "ArticleContentList", null, config_horizental_menu_t, new Gson().toJson(content_list)));

        List<ThemeChildConfig> configs_iamge = new ArrayList<>();
        configs_iamge.add(new ThemeChildConfig(1, "غذاهای اصیل ایرانی در عطر و طعم رقیب ندارند. کوفته انواع مختلفی مثل کوفته کله گنجشکی، آذری، ترکی کوفته نخودچی و غیره دارد", null, null, null, "https://ashpazidorehami.com/wp-content/uploads/2019/03/%D8%AF%D8%B3%D8%AA%D9%88%D8%B1-%D9%BE%D8%AE%D8%AA-%DA%A9%D9%88%D9%81%D8%AA%D9%87-%D8%AA%D8%A8%D8%B1%DB%8C%D8%B2%DB%8C.jpg", "WebClick", "https://ashpazidorehami.com/%DA%A9%D9%88%D9%81%D8%AA%D9%87-%D8%AA%D8%A8%D8%B1%DB%8C%D8%B2%DB%8C/"));
        configs_iamge.add(new ThemeChildConfig(2, "وقتی هوا خنک می شود، یکی از بزرگ ترین لذت ها خوردن یک کاسه آش داغ و خوشمزه است. تا به حال طرز تهیه انواع آش ها را به شما آموزش دادیم.", null, null, null, "https://ashpazidorehami.com/wp-content/uploads/2019/03/%D8%B7%D8%B1%D8%B2-%D8%AA%D9%87%DB%8C%D9%87-%D8%A2%D8%B4-%D9%82%D9%84%DB%8C%D9%87-%DA%AF%DB%8C%D9%84%D8%A7%D9%86%DB%8C.jpg", "WebClick", "https://ashpazidorehami.com/%D8%A2%D8%B4-%D9%82%D9%84%DB%8C%D9%87-%DA%AF%DB%8C%D9%84%D8%A7%D9%86%DB%8C/"));
        themeChildren.add(new ThemeChild(5, 2, "CoreImage", configs_iamge, null, null));

        List<ThemeChildConfig> config_horizental_menu = new ArrayList<>();
        config_horizental_menu.add(new ThemeChildConfig(1, "مجبوب ترین دستورات ما", null, "#000000", "15", null, null, null));
        config_horizental_menu.add(new ThemeChildConfig(2, "لیست کامل", null, "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        themeChildren.add(new ThemeChild(4, 1, "ArticleContentList", null, config_horizental_menu, new Gson().toJson(content_list)));

        List<ThemeChildConfig> configs_slider = new ArrayList<>();
        configs_slider.add(new ThemeChildConfig(1, null, null, null, null, "https://ashpazidorehami.com/wp-content/uploads/2019/03/albaloo-polo2-848x477.jpg", "WebClick", "http://oco.ir"));
        configs_slider.add(new ThemeChildConfig(2, null, null, null, null, "https://ashpazidorehami.com/wp-content/uploads/2019/03/%D8%B4%DB%8C%D8%B1%DB%8C%D9%86%DB%8C-%DA%A9%D8%B4%D9%85%D8%B4%DB%8C.jpg", "ArticleContentList", new Gson().toJson(content_list)));
        themeChildren.add(new ThemeChild(5, 3, "CoreSlider", configs_slider, null, null));


        List<ThemeChildConfig> confige_core_four_button = new ArrayList<>();
        confige_core_four_button.add(new ThemeChildConfig(1, "مجلات و کتاب", "#ff00ff", "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        confige_core_four_button.add(new ThemeChildConfig(2, "نکات و فوت و فن", "#ff00ff", "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        confige_core_four_button.add(new ThemeChildConfig(3, "موزیک", "#ff00ff", "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        confige_core_four_button.add(new ThemeChildConfig(4, "مشاور تغذیه", "#ff00ff", "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list)));
        themeChildren.add(new ThemeChild(3, 2, "CoreButton", confige_core_four_button, null, null));


        ArticleContentListRequest content_list_2 = new ArticleContentListRequest();
        content_list_2.SortType = NTKUtill.Descnding_Sort;
        content_list_2.SortColumn = "Id";
        List<Filters> filters_2 = new ArrayList<>();
        Filters f_2 = new Filters();
        f_2.IntValue1 = Long.parseLong("20185");
        f_2.PropertyName = "LinkCategoryId";
        filters_2.add(f_2);
        content_list_2.filters = filters_2;

        List<ThemeChildConfig> config_horizental_menu_two = new ArrayList<>();
        config_horizental_menu_two.add(new ThemeChildConfig(1, "دستورات ارسالی کاربران", null, "#000000", "15", null, null, null));
        config_horizental_menu_two.add(new ThemeChildConfig(2, "لیست کامل", null, "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list_2)));
        themeChildren.add(new ThemeChild(4, 1, "ArticleContentList", null, config_horizental_menu_two, new Gson().toJson(content_list_2)));


        ArticleContentListRequest content_list_3 = new ArticleContentListRequest();
        content_list_3.SortType = NTKUtill.Descnding_Sort;
        content_list_3.SortColumn = "Id";
        List<Filters> filters_3 = new ArrayList<>();
        Filters f_3 = new Filters();
        f_3.IntValue1 = Long.parseLong("20189");
        f_3.PropertyName = "LinkCategoryId";
        filters_3.add(f_3);
        content_list_3.filters = filters_3;

        List<ThemeChildConfig> config_horizental_menu_three = new ArrayList<>();
        config_horizental_menu_three.add(new ThemeChildConfig(1, "عکس های ارسالی", null, "#000000", "15", null, null, null));
        config_horizental_menu_three.add(new ThemeChildConfig(2, "لیست کامل", null, "#ff0000", "15", null, "ArticleContentList", new Gson().toJson(content_list_3)));
        themeChildren.add(new ThemeChild(6, 1, "ArticleContentList", null, config_horizental_menu_three, new Gson().toJson(content_list_3)));


        Theme theme = new Theme();
        theme.Toolbar = toolbar;
        theme.Childs = themeChildren;

        JsonThemeExmaple = new Gson().toJson(theme);
        Log.i("JsonTheme", JsonThemeExmaple);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
