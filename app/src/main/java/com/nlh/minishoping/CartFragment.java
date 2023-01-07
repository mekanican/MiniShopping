package com.nlh.minishoping;

import static com.nlh.minishoping.NotificationClass.CHANNEL_1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nlh.minishoping.Cart.CartMap;
import com.nlh.minishoping.Connector.ServerConnector;
import com.nlh.minishoping.DAO.GeneralInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    ArrayList<CartMap.Pair<GeneralInfo, Integer>> productList;
    ProductCartViewAdapter pcvAdapter;
    ListView lvProductList;
    BottomNavigationView bottomNavigationView;
    EditText etVoucher;

    public static double VAT = 1.08; // 8%
    public static int DEFAULT_SHIPPING_PRICE = 20000;
    String hashValue;

    double discount;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MainActivity mainActivity = (MainActivity) getActivity();
        hashValue = mainActivity.getHashValue();

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CartMap cartMapInstance = CartMap.getInstance();

        // Setup productList
        productList = new ArrayList<>();
        pcvAdapter = new ProductCartViewAdapter(productList, id -> {
            cartMapInstance.increaseItemQuantity((int) id);
        }, id -> {
            cartMapInstance.decreaseItemQuantity((int) id);
        });

        etVoucher = getActivity().findViewById(R.id.voucher_edit_text);

        lvProductList = getActivity().findViewById(R.id.cart_lv);
        lvProductList.setAdapter(pcvAdapter);

        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

        cartMapInstance.setCallback(() -> {
            productList.clear();
            CartMap.Pair<ArrayList<CartMap.Pair<GeneralInfo, Integer>>, Integer> pair =
                    cartMapInstance.generateArrayListWithTotal();
            productList.addAll(pair.x);
            bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(
                    cartMapInstance.getNumberOfItem()
            );
            update(pair.y);
            pcvAdapter.notifyDataSetChanged();
        });

        resetValue();

        // Handle deleteAll
        (getActivity().findViewById(R.id.delete_)).setOnClickListener(view1 -> {
            deleteAll(cartMapInstance);
        });


        // Handle proceed
        (getActivity().findViewById(R.id.proceed_)).setOnClickListener(view1 -> {
            String voucher = String.valueOf(etVoucher.getText());

            if (voucher == null) {
                discount = 0;
            } else {
                discount = ServerConnector.GetVoucherDiscount(voucher);
            }

            CartMap.Pair<ArrayList<CartMap.Pair<GeneralInfo, Integer>>, Integer> pair =
                    cartMapInstance.generateArrayListWithTotal();

            int total = pair.y;
            double totalAfterDiscount = 1.00 * total * (1 - discount / 100);
            double totalPayment = (totalAfterDiscount + DEFAULT_SHIPPING_PRICE) * VAT;
            Log.i("AFTER DISCOUNT", String.valueOf(totalAfterDiscount));

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_24);

            Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_1)
                    .setContentTitle("Đặt hàng thành công")
                    .setContentText("Tổng thanh toán là " + (int) totalPayment)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(3, notification);
            }

            //Log.i("CART MAP TO STRING", cartMapInstance.toString());

            String tempStr = cartMapInstance.toString();
            String str = "{\n\"hash\": \"" + hashValue + "\",\n";
            for (int i = 0; i < tempStr.length() - 2; i++) {
                str += tempStr.charAt(i);
            }

            str += "\n],\n";

            if (voucher != null) {
                str += "\"voucher\": " + "\"" + voucher + "\"\n}";
            }

            //Log.i("CART STRING", str);
            ServerConnector.Purchase(str);

            processCart();
            deleteAll(cartMapInstance);
        });
    }

    private void deleteAll(CartMap cartMapInstance) {
        productList.clear();
        cartMapInstance.clearCart();
        resetValue();
        bottomNavigationView.removeBadge(R.id.cart);
        pcvAdapter.notifyDataSetChanged();
    }

    private void processCart() {
        // TODO: passing the cartMap.toString -> Server / to next intent
        Intent intent = new Intent(getContext(), AddressActivity.class)
                .putExtra("hash", ((MainActivity) getActivity()).getHashValue());
        startActivity(intent);
    }

    private void setPriceByID(int ID, int price) {
        DecimalFormat formatter = new DecimalFormat("###,###,### 'VND'");
        ((TextView) getActivity().findViewById(ID))
                .setText(formatter.format(price));
    }


    private void resetValue() {
        setPriceByID(R.id.product_price, 0);
        setPriceByID(R.id.shipping_price, DEFAULT_SHIPPING_PRICE);
        setPriceByID(R.id.total_price, (int) (DEFAULT_SHIPPING_PRICE * VAT));
    }

    private void update(int total) {
        setPriceByID(R.id.product_price, total);
        setPriceByID(R.id.total_price, (int) Math.round((total + DEFAULT_SHIPPING_PRICE) * VAT));
    }
}