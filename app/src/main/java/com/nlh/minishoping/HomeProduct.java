package com.nlh.minishoping;

import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

// Decorator Pattern applied!
public class HomeProduct {
    public HomeProduct(Product p) {
        wrapped = p;
    }

    public int getId() {
        return wrapped.ID;
    }

    public String getName() {
        return wrapped.name;
    }

    public void getImageToImageView(ImageView iv) {
        Ion.with(iv)
                .placeholder(R.drawable.loading)
                .error(R.drawable.icon)
                .animateLoad(R.anim.loading)
                .load(wrapped.imageLink);
    }

    public int getPrice() {
        return wrapped.price;
    }

    public String getImageLink() {
        return wrapped.imageLink;
    }

    public String getCategory() {
        return wrapped.category;
    }

    public String getDescription() {
        return wrapped.description;
    }

    private final Product wrapped;
}
