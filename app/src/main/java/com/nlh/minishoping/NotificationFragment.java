package com.nlh.minishoping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView productDiscount1;
    TextView productDiscount2;
    TextView productDiscount3;
    private String hashValue;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            getArguments().getString(ARG_PARAM1);
            getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MainActivity mainActivity = (MainActivity) getActivity();
        hashValue = Objects.requireNonNull(mainActivity).getHashValue();

        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productDiscount1 = requireActivity().findViewById(R.id.product_1);
        productDiscount1.setOnClickListener(view1 -> onProductDiscount1Clicked(view1, 2));

        productDiscount2 = requireActivity().findViewById(R.id.product_2);
        productDiscount2.setOnClickListener(view12 -> onProductDiscount1Clicked(view12, 3));

        productDiscount3 = requireActivity().findViewById(R.id.product_3);
        productDiscount3.setOnClickListener(view13 -> onProductDiscount1Clicked(view13, 4));
    }

    public void onProductDiscount1Clicked(View ignoredView, int id) {
        Intent intent = new Intent(getActivity(), ProductDetails.class);
        intent.putExtra("ID", id);
        intent.putExtra("HASH", hashValue);
        requireActivity().startActivity(intent);
    }
}