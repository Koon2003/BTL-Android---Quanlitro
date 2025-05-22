package com.example.appquanlitro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class AnhChiTiet_Adapter extends RecyclerView.Adapter<AnhChiTiet_Adapter.ImageViewHolder> {
    private List<String> imagePaths;

    public AnhChiTiet_Adapter(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anh_slide_chitietphongtro, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ImageViewHolder holder, int position) {
        String path = imagePaths.get(position);
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Glide.with(holder.imageView.getContext())
                    .load(imgFile)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.img); // Hình ảnh mặc định
        }
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
