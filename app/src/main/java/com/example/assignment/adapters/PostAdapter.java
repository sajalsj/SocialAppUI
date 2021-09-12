package com.example.assignment.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.databinding.ItemPhotosBinding;
import com.example.assignment.model.Gallery;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final String TAG = PostAdapter.class.getSimpleName();
    private final Context context;
    private List<Gallery> userPosts;

    public PostAdapter(Context context, List<Gallery> userPosts) {
        this.context = context;
        this.userPosts = userPosts;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        ItemPhotosBinding photosBinding = ItemPhotosBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new ViewHolder(photosBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        Gallery gallery = userPosts.get(i);
        List<String> photosList = gallery.getPhotosList();
        viewHolder.postGL.post(() -> {
            int imagesCount = 0;
            int width = viewHolder.postGL.getWidth();
            for (String name : photosList) {
                imagesCount++;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 2, 400, 2);
                layoutParams.setMargins(1, 1, 1, 1);
                if (photosList.size() == 1) {
                    layoutParams = new LinearLayout.LayoutParams(viewHolder.postGL.getWidth(), 400, 2);
                }

                ImageView postImage = new ImageView(context);
                postImage.setScaleType(ImageView.ScaleType.FIT_XY);
                postImage.setLayoutParams(layoutParams);

                Glide.with(context)
                        .load(name)
                        .placeholder(R.drawable.ic_person)
                        .into(postImage);

                if (imagesCount > 3 && photosList.size() > 4) {
                    Bitmap bitmap = Bitmap.createBitmap(width / 2, 400, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    Paint paint = new Paint();
                    paint.setTextSize(38);
                    paint.setColor(Color.BLACK);
                    String imagesLeftTxt = context.getResources().getString(R.string.user_post_more_images_txt, photosList.size() - 3);
                    canvas.drawText(imagesLeftTxt, 50, 200, paint);
                    postImage.setImageBitmap(bitmap);
                    viewHolder.postGL.addView(postImage);
                    return;

                }
                viewHolder.postGL.addView(postImage);
            }

        });


    }

    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    public void setUserPosts(List<Gallery> galleries) {
        userPosts = galleries;
        this.notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final GridLayout postGL;

        public ViewHolder(ItemPhotosBinding photosBinding) {
            super(photosBinding.getRoot());
            postGL = photosBinding.postsGridLayout;
        }

    }


}
