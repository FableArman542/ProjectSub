package com.example.projectsub.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.projectsub.R;
import com.example.projectsub.adapter.SubscriptionAdapter;
import com.example.projectsub.model.Subscription;

public class MainPageFragment extends Fragment {

    ImageButton addButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        Subscription[] mySubscriptions = new Subscription[] {
                new Subscription("Spotify", "10/02", "16", Color.argb(255, 29, 185, 84)),
                new Subscription("Youtube", "12/09", "22", Color.argb(255,248, 95, 106))
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SubscriptionAdapter adapter = new SubscriptionAdapter(mySubscriptions);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addButton = view.findViewById(R.id.addSubscriptionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                AddSubscriptionFragment fragment = new AddSubscriptionFragment();

                manager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        return view;
    }
}