package com.example.projectsub.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.projectsub.BlankFragmentForTests;
import com.example.projectsub.R;
import com.example.projectsub.adapter.SubscriptionAdapter;
import com.example.projectsub.model.Subscription;
import com.example.projectsub.utils.SwipeToDelete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainPageFragment extends Fragment {

    FirebaseUser user;
    ImageButton addButton;

    private SubscriptionAdapter adapter;
    private List<Subscription> mSubscriptions;

    // Used to saved data
    private SharedPreferences mPrefs;
    private List<Subscription> savedSubs;

    ItemTouchHelper.SimpleCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // TODO if user not logged in redirect to login page
            Log.e("AUTH", "User not logged in");
        }

//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2021);
//        cal.set(Calendar.MONTH, 9-1);
//        cal.set(Calendar.DAY_OF_MONTH, 12);
//        Date date = cal.getTime();
//
//        Calendar cal1 = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2021);
//        cal.set(Calendar.MONTH, 9-1);
//        cal.set(Calendar.DAY_OF_MONTH, 12);
//        Date date1 = cal.getTime();

        // Firebase testing

//        DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsub-9f668-default-rtdb.europe-west1.firebasedatabase.app")
//                        .getReference("users");
//        String rr = reference.child(user.getUid()).child("subscriptions").push().getKey();
//        reference.child(user.getUid()).child("subscriptions").child(rr).setValue(new Subscription("Spotify", date, "16", Color.argb(255, 29, 185, 84)));
//
//        String rr1 = reference.child(user.getUid()).child("subscriptions").push().getKey();
//        reference.child(user.getUid()).child("subscriptions").child(rr1).setValue(new Subscription("Youtube", date1, "22", Color.argb(255,248, 95, 106)));

        // Firebase testing

//        Subscription[] mySubscriptions = new Subscription[] {
//                new Subscription("Spotify", date, "16", Color.argb(255, 29, 185, 84)),
//                new Subscription("Youtube", date1, "22", Color.argb(255,248, 95, 106))
//        };

        // Swipe to delete
        callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        savedSubs = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        readSubscriptions();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new SubscriptionAdapter(mSubscriptions, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addButton = view.findViewById(R.id.addSubscriptionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                AddSubscriptionFragment fragment = new AddSubscriptionFragment();

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.main_fragment, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        return view;
    }

    private void getNextDate(Date date) {

        Calendar calNow = Calendar.getInstance();
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        // The same day every month
        

        // The same day of the week every week
        // The same day every year

    }

    private void readSubscriptions() {

        // Getting the list of subscriptions
        DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsub-9f668-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("users/"+user.getUid()+"/subscriptions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                mSubscriptions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Subscription subscription = snapshot.getValue(Subscription.class);

                    savedSubs.add(subscription);
                    mSubscriptions.add(subscription);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                // TODO
            }
        });
    }
}