package com.nlh.minishoping;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

    public void getImagetoImageView(Context c, ImageView iv) {
        Log.d("meow", "GetProducts: " + wrapped.imageLink);
        Ion.getDefault(c).getConscryptMiddleware().enable(false);
        Ion.with(c)
                .load(wrapped.imageLink)
                .withBitmap()
                .placeholder(R.drawable.hung)
                .error(R.drawable.icon)
                .animateLoad(R.anim.loading)
                .intoImageView(iv);
    }

    public int getPrice() {
        return wrapped.price;
    }

    private final Product wrapped;
}
