package anu.g35.sharebooks.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Load the image using Glide
 * @author u7706346 Anbo Wu
 * @since 2024-05-04
 */

public class CommTools {

    /**
     *
     * @param imageView
     * @param url
     * @param errorDrawable
     */
    public static void loadImage(ImageView imageView, String url, int errorDrawable) {

        // Load the thumbnail image using Glide
        Glide.with(imageView)
                .load(url)
                .error(errorDrawable)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        e.logRootCauses("GLIDE_ERROR");
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}
