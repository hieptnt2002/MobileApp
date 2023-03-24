package com.example.mobileapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.Model.Danhmuc;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class DanhmucAdapter extends RecyclerView.Adapter<DanhmucAdapter.ViewHolder> {
    ArrayList<Danhmuc> arrDanhmuc;
    Context context;

    public DanhmucAdapter(ArrayList<Danhmuc> arrDanhmuc, Context context) {
        this.arrDanhmuc = arrDanhmuc;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.row_category,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Danhmuc danhmuc = arrDanhmuc.get(position);
        holder.txtTen.setText(danhmuc.getTen());
//        Glide.with(context).load(danhmuc.getAnh()).into(holder.img);
        byte[] decode = Base64.decode(danhmuc.getAnh(),Base64.DEFAULT);
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        holder.img.setImageBitmap(imgBitmap);
    }

    @Override
    public int getItemCount() {
        return arrDanhmuc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.cate_ten);
            img = itemView.findViewById(R.id.cate_anh);
        }
    }
}
