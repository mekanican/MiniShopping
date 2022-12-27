package com.nlh.minishoping.DAO;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class GeneralInfo {
    // From product
    public int id;
    public String name;
    public float price;
    public String imageLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralInfo that = (GeneralInfo) o;

        if (id != that.id) return false;
        if (Float.compare(that.price, price) != 0) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(imageLink, that.imageLink);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (imageLink != null ? imageLink.hashCode() : 0);
        return result;
    }

    public static DiffUtil.ItemCallback<GeneralInfo> DIFF_CALLBACK = new DiffUtil.ItemCallback<GeneralInfo>() {
        @Override
        public boolean areItemsTheSame(@NonNull GeneralInfo oldItem, @NonNull GeneralInfo newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GeneralInfo oldItem, @NonNull GeneralInfo newItem) {
            return oldItem.equals(newItem);
        }
    };
}
