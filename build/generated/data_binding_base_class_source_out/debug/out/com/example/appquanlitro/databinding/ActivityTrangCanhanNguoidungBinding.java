// Generated by view binder compiler. Do not edit!
package com.example.appquanlitro.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public final class ActivityTrangCanhanNguoidungBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btndangxuat;

  @NonNull
  public final ImageButton btnhosothuetro;

  @NonNull
  public final ImageButton btnlichhenchoduyet;

  @NonNull
  public final ImageButton btnlichhendaduyet;

  @NonNull
  public final ImageButton btnlichhendahuy;

  @NonNull
  public final ImageButton btnthongtintaikhoan;

  @NonNull
  public final ImageButton btntiendien;

  @NonNull
  public final ImageButton btntiennuoc;

  @NonNull
  public final ImageButton btntienphong;

  @NonNull
  public final TextView cccd;

  @NonNull
  public final TextView hovaten;

  @NonNull
  public final TextView id;

  @NonNull
  public final TextView ngaysinh;

  @NonNull
  public final TextView quequan;

  @NonNull
  public final TextView sdt;

  @NonNull
  public final TextView tendn;

  @NonNull
  public final TextView textView;

  private ActivityTrangCanhanNguoidungBinding(@NonNull RelativeLayout rootView,
      @NonNull Button btndangxuat, @NonNull ImageButton btnhosothuetro,
      @NonNull ImageButton btnlichhenchoduyet, @NonNull ImageButton btnlichhendaduyet,
      @NonNull ImageButton btnlichhendahuy, @NonNull ImageButton btnthongtintaikhoan,
      @NonNull ImageButton btntiendien, @NonNull ImageButton btntiennuoc,
      @NonNull ImageButton btntienphong, @NonNull TextView cccd, @NonNull TextView hovaten,
      @NonNull TextView id, @NonNull TextView ngaysinh, @NonNull TextView quequan,
      @NonNull TextView sdt, @NonNull TextView tendn, @NonNull TextView textView) {
    this.rootView = rootView;
    this.btndangxuat = btndangxuat;
    this.btnhosothuetro = btnhosothuetro;
    this.btnlichhenchoduyet = btnlichhenchoduyet;
    this.btnlichhendaduyet = btnlichhendaduyet;
    this.btnlichhendahuy = btnlichhendahuy;
    this.btnthongtintaikhoan = btnthongtintaikhoan;
    this.btntiendien = btntiendien;
    this.btntiennuoc = btntiennuoc;
    this.btntienphong = btntienphong;
    this.cccd = cccd;
    this.hovaten = hovaten;
    this.id = id;
    this.ngaysinh = ngaysinh;
    this.quequan = quequan;
    this.sdt = sdt;
    this.tendn = tendn;
    this.textView = textView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTrangCanhanNguoidungBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTrangCanhanNguoidungBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_trang_canhan_nguoidung, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTrangCanhanNguoidungBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btndangxuat;
      Button btndangxuat = ViewBindings.findChildViewById(rootView, id);
      if (btndangxuat == null) {
        break missingId;
      }

      id = R.id.btnhosothuetro;
      ImageButton btnhosothuetro = ViewBindings.findChildViewById(rootView, id);
      if (btnhosothuetro == null) {
        break missingId;
      }

      id = R.id.btnlichhenchoduyet;
      ImageButton btnlichhenchoduyet = ViewBindings.findChildViewById(rootView, id);
      if (btnlichhenchoduyet == null) {
        break missingId;
      }

      id = R.id.btnlichhendaduyet;
      ImageButton btnlichhendaduyet = ViewBindings.findChildViewById(rootView, id);
      if (btnlichhendaduyet == null) {
        break missingId;
      }

      id = R.id.btnlichhendahuy;
      ImageButton btnlichhendahuy = ViewBindings.findChildViewById(rootView, id);
      if (btnlichhendahuy == null) {
        break missingId;
      }

      id = R.id.btnthongtintaikhoan;
      ImageButton btnthongtintaikhoan = ViewBindings.findChildViewById(rootView, id);
      if (btnthongtintaikhoan == null) {
        break missingId;
      }

      id = R.id.btntiendien;
      ImageButton btntiendien = ViewBindings.findChildViewById(rootView, id);
      if (btntiendien == null) {
        break missingId;
      }

      id = R.id.btntiennuoc;
      ImageButton btntiennuoc = ViewBindings.findChildViewById(rootView, id);
      if (btntiennuoc == null) {
        break missingId;
      }

      id = R.id.btntienphong;
      ImageButton btntienphong = ViewBindings.findChildViewById(rootView, id);
      if (btntienphong == null) {
        break missingId;
      }

      id = R.id.cccd;
      TextView cccd = ViewBindings.findChildViewById(rootView, id);
      if (cccd == null) {
        break missingId;
      }

      id = R.id.hovaten;
      TextView hovaten = ViewBindings.findChildViewById(rootView, id);
      if (hovaten == null) {
        break missingId;
      }

      id = R.id.id;
      TextView id_ = ViewBindings.findChildViewById(rootView, id);
      if (id_ == null) {
        break missingId;
      }

      id = R.id.ngaysinh;
      TextView ngaysinh = ViewBindings.findChildViewById(rootView, id);
      if (ngaysinh == null) {
        break missingId;
      }

      id = R.id.quequan;
      TextView quequan = ViewBindings.findChildViewById(rootView, id);
      if (quequan == null) {
        break missingId;
      }

      id = R.id.sdt;
      TextView sdt = ViewBindings.findChildViewById(rootView, id);
      if (sdt == null) {
        break missingId;
      }

      id = R.id.tendn;
      TextView tendn = ViewBindings.findChildViewById(rootView, id);
      if (tendn == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new ActivityTrangCanhanNguoidungBinding((RelativeLayout) rootView, btndangxuat,
          btnhosothuetro, btnlichhenchoduyet, btnlichhendaduyet, btnlichhendahuy,
          btnthongtintaikhoan, btntiendien, btntiennuoc, btntienphong, cccd, hovaten, id_, ngaysinh,
          quequan, sdt, tendn, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
