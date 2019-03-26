package com.kc.splashpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kc.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {

    private final List<Photo> photoList;
    private Context mContext;
    private OnPhotoClickedListener mListener;

    public PhotoRecyclerAdapter(List<Photo> photos, Context context, OnPhotoClickedListener listener) {
        photoList = photos;
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Photo photo = photoList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.photoClicked(photoList.get(holder.getAdapterPosition()), (ImageView)v);
            }
        });

        Picasso.get()
                .load(photo.getUrls().getRegular())
                .resize(300, 300)
                .centerCrop()
                .into(holder.imageView);
    }

    public void addPhotos(List<Photo> photos){
        int lastCount = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }

    }

    public interface OnPhotoClickedListener {

        void photoClicked(Photo photo, ImageView imageView);

    }
}
