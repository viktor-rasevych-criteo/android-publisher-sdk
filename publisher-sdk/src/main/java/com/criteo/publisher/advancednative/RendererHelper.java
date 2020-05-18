package com.criteo.publisher.advancednative;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.criteo.publisher.SafeRunnable;
import com.criteo.publisher.annotation.Incubating;
import com.criteo.publisher.concurrent.RunOnUiThreadExecutor;
import java.net.URL;

@Incubating(Incubating.NATIVE)
public class RendererHelper {

  @NonNull
  private final ImageLoader imageLoader;

  @NonNull
  private final RunOnUiThreadExecutor uiExecutor;

  public RendererHelper(
      @NonNull ImageLoader imageLoader,
      @NonNull RunOnUiThreadExecutor uiExecutor
  ) {
    this.imageLoader = imageLoader;
    this.uiExecutor = uiExecutor;
  }

  void preloadMedia(@NonNull URL url) {
    new SafeRunnable() {
      @Override
      public void runSafely() throws Throwable {
        imageLoader.preload(url);
      }
    }.run();
  }

  public void setMediaInView(CriteoMedia mediaContent, CriteoMediaView mediaView) {
    setMediaInView(mediaContent.getImageUrl(), mediaView.getImageView(), mediaView.getPlaceholder());
  }

  void setMediaInView(@NonNull URL url, @NonNull ImageView imageView, @Nullable Drawable placeholder) {
    uiExecutor.execute(new SafeRunnable() {
      @Override
      public void runSafely() throws Throwable {
        imageLoader.loadImageInto(url, imageView, placeholder);
      }
    });
  }

}