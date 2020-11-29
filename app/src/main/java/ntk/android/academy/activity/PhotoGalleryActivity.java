package ntk.android.academy.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.academy.R;
import ntk.android.academy.library.scrollgallery.MediaInfo;
import ntk.android.academy.library.scrollgallery.ScrollGalleryView;
import ntk.android.academy.library.scrollgallery.loader.DefaultImageLoader;
import ntk.android.base.Extras;

public class PhotoGalleryActivity extends AppCompatActivity {

    @BindView(R.id.scroll_gallery_view)
    ScrollGalleryView Gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_gallery);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String[] links = getIntent().getExtras().getStringArray(Extras.EXTRA_FIRST_ARG);
        List<MediaInfo> infos = new ArrayList<>(links.length);
        for (String url : links) infos.add(MediaInfo.mediaLoader(new DefaultImageLoader(url)));
        Gallery
                .setThumbnailSize(250)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Gallery.clearGallery();
    }
}
