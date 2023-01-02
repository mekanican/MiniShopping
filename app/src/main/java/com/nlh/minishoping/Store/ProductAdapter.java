package com.nlh.minishoping.Store;

import static com.nlh.minishoping.Connector.ServerConnector.HOST_NAME;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;
import com.nlh.minishoping.Connector.ServerConnector;
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
            String image = HOST_NAME + gi.imageLink;
            Log.i("Image link", image);
            // Server chua co nen sai tam link nhe :(
            getImageToImageView(ivProductImage, image); // gi.imageLink
        }
    }
}
