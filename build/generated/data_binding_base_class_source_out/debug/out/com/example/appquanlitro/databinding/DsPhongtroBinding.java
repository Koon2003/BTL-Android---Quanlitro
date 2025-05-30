// Generated by view binder compiler. Do not edit!
package com.example.appquanlitro.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appquanlitro.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DsPhongtroBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView dientich;

  @NonNull
  public final TextView giatien;

  @NonNull
  public final TextView id;

  @NonNull
  public final ImageView img1;

  @NonNull
  public final ImageView img2;

  @NonNull
  public final ImageView img3;

  @NonNull
  public final ImageView img4;

  @NonNull
  public final ImageView img5;

  @NonNull
  public final ImageButton imgsua;

  @NonNull
  public final ImageButton imgxoa;

  @NonNull
  public final TextView mota;

  @NonNull
  public final ImageView phong;

  @NonNull
  public final TextView tenphong;

  private DsPhongtroBinding(@NonNull RelativeLayout rootView, @NonNull TextView dientich,
      @NonNull TextView giatien, @NonNull TextView id, @NonNull ImageView img1,
      @NonNull ImageView img2, @NonNull ImageView img3, @NonNull ImageView img4,
      @NonNull ImageView img5, @NonNull ImageButton imgsua, @NonNull ImageButton imgxoa,
      @NonNull TextView mota, @NonNull ImageView phong, @NonNull TextView tenphong) {
    this.rootView = rootView;
    this.dientich = dientich;
    this.giatien = giatien;
    this.id = id;
    this.img1 = img1;
    this.img2 = img2;
    this.img3 = img3;
    this.img4 = img4;
    this.img5 = img5;
    this.imgsua = imgsua;
    this.imgxoa = imgxoa;
    this.mota = mota;
    this.phong = phong;
    this.tenphong = tenphong;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DsPhongtroBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DsPhongtroBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.ds_phongtro, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DsPhongtroBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dientich;
      TextView dientich = ViewBindings.findChildViewById(rootView, id);
      if (dientich == null) {
        break missingId;
      }

      id = R.id.giatien;
      TextView giatien = ViewBindings.findChildViewById(rootView, id);
      if (giatien == null) {
        break missingId;
      }

      id = R.id.id;
      TextView id_ = ViewBindings.findChildViewById(rootView, id);
      if (id_ == null) {
        break missingId;
      }

      id = R.id.img1;
      ImageView img1 = ViewBindings.findChildViewById(rootView, id);
      if (img1 == null) {
        break missingId;
      }

      id = R.id.img2;
      ImageView img2 = ViewBindings.findChildViewById(rootView, id);
      if (img2 == null) {
        break missingId;
      }

      id = R.id.img3;
      ImageView img3 = ViewBindings.findChildViewById(rootView, id);
      if (img3 == null) {
        break missingId;
      }

      id = R.id.img4;
      ImageView img4 = ViewBindings.findChildViewById(rootView, id);
      if (img4 == null) {
        break missingId;
      }

      id = R.id.img5;
      ImageView img5 = ViewBindings.findChildViewById(rootView, id);
      if (img5 == null) {
        break missingId;
      }

      id = R.id.imgsua;
      ImageButton imgsua = ViewBindings.findChildViewById(rootView, id);
      if (imgsua == null) {
        break missingId;
      }

      id = R.id.imgxoa;
      ImageButton imgxoa = ViewBindings.findChildViewById(rootView, id);
      if (imgxoa == null) {
        break missingId;
      }

      id = R.id.mota;
      TextView mota = ViewBindings.findChildViewById(rootView, id);
      if (mota == null) {
        break missingId;
      }

      id = R.id.phong;
      ImageView phong = ViewBindings.findChildViewById(rootView, id);
      if (phong == null) {
        break missingId;
      }

      id = R.id.tenphong;
      TextView tenphong = ViewBindings.findChildViewById(rootView, id);
      if (tenphong == null) {
        break missingId;
      }

      return new DsPhongtroBinding((RelativeLayout) rootView, dientich, giatien, id_, img1, img2,
          img3, img4, img5, imgsua, imgxoa, mota, phong, tenphong);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
