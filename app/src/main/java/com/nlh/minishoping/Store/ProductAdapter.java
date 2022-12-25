package com.nlh.minishoping.Store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;
import com.nlh.minishoping.DAO.GeneralInfo;
import com.nlh.minishoping.R;

public class ProductAdapter extends PagedListAdapter<GeneralInfo, ProductAdapter.ProductViewHolder> {

    View.OnClickListener listener;

    public ProductAdapter(View.OnClickListener listener) {
        super(GeneralInfo.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        v.setOnClickListener(listener);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        GeneralInfo gi = getItem(position);
        if (gi != null) {
            holder.bindTo(gi);
        }
    }

    public static void getImageToImageView(ImageView iv, String imageLink) {
        Ion.with(iv)
                .placeholder(R.drawable.loading)
                .error(R.drawable.icon)
                .animateLoad(R.anim.loading)
                .load(imageLink);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        ImageView ivProductImage;
        TextView tvProductPrice;
        View iv;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            iv = itemView;
        }

        public void bindTo(GeneralInfo gi) {
            tvProductName.setText(gi.name);
            tvProductPrice.setText("" + (int) gi.price + " VND");
            // Server chua co nen sai tam link nhe :(
            getImageToImageView(ivProductImage, "https://lzd-img-global.slatic.net/g/ff/kf/Sfae58f8bc68b49d4b7f178737b293858g.jpg_400x400q80.jpg_.webp"); // gi.imageLink
        }
    }
}
