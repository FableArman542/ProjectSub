package com.example.projectsub.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectsub.R;

import at.markushi.ui.CircleButtonNew;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSubscriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSubscriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSubscriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSubscriptionFragment newInstance(String param1, String param2) {
        AddSubscriptionFragment fragment = new AddSubscriptionFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private int lastId = -1;
    private int color = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);

        Toolbar toolbar = view.findViewById(R.id.add_sub_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                MainPageFragment fragment = new MainPageFragment();

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                                R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.main_fragment, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        setColorListeners(view);
        TextView subscriptionName = view.findViewById(R.id.sub_name);


        ImageButton checkButton = view.findViewById(R.id.check_subscription);


        return view;
    }

    private void setColorListeners (View view) {
        CircleButtonNew c11 = view.findViewById(R.id.c11);
        CircleButtonNew c12 = view.findViewById(R.id.c12);
        CircleButtonNew c13 = view.findViewById(R.id.c13);
        CircleButtonNew c14 = view.findViewById(R.id.c14);
        CircleButtonNew c15 = view.findViewById(R.id.c16);
        CircleButtonNew c16 = view.findViewById(R.id.c16);

        CircleButtonNew c21 = view.findViewById(R.id.c21);
        CircleButtonNew c22 = view.findViewById(R.id.c22);
        CircleButtonNew c23 = view.findViewById(R.id.c23);
        CircleButtonNew c24 = view.findViewById(R.id.c24);
        CircleButtonNew c25 = view.findViewById(R.id.c25);
        CircleButtonNew c26 = view.findViewById(R.id.c26);

        CircleButtonNew c31 = view.findViewById(R.id.c31);
        CircleButtonNew c32 = view.findViewById(R.id.c32);
        CircleButtonNew c33 = view.findViewById(R.id.c33);
        CircleButtonNew c34 = view.findViewById(R.id.c34);
        CircleButtonNew c35 = view.findViewById(R.id.c35);
        CircleButtonNew c36 = view.findViewById(R.id.c36);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastId != -1) view.findViewById(lastId).setBackgroundColor(0x00000000);
                v.setBackgroundColor(Color.argb(0.1f, 0f, 0f, 0f));
                lastId = v.getId();
                CircleButtonNew thisBtn = view.findViewById(lastId);
                color = thisBtn.getCurrentColor();
                Log.d("COLOR", "onClick: " + thisBtn.getCurrentColor());

            }
        };

        c11.setOnClickListener(listener);
        c12.setOnClickListener(listener);
        c13.setOnClickListener(listener);
        c14.setOnClickListener(listener);
        c15.setOnClickListener(listener);
        c16.setOnClickListener(listener);
        c21.setOnClickListener(listener);
        c22.setOnClickListener(listener);
        c23.setOnClickListener(listener);
        c24.setOnClickListener(listener);
        c25.setOnClickListener(listener);
        c26.setOnClickListener(listener);
        c31.setOnClickListener(listener);
        c32.setOnClickListener(listener);
        c33.setOnClickListener(listener);
        c34.setOnClickListener(listener);
        c35.setOnClickListener(listener);
        c36.setOnClickListener(listener);
    }
}