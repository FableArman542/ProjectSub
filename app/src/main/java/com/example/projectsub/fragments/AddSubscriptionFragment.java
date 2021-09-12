package com.example.projectsub.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsub.R;
import com.example.projectsub.model.Subscription;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

import at.markushi.ui.CircleButtonNew;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubscriptionFragment} factory method to
 * create an instance of this fragment.
 */
public class AddSubscriptionFragment extends Fragment {

    private int lastId = -1;
    private int color = 0;

    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);

        Button button = view.findViewById(R.id.back_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        setColorListeners(view);

        // --------------------------------------------------------------------- \\

                user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // TODO if user not logged in redirect to login page
            Log.e("AUTH", "User not logged in");
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 9-1);
        cal.set(Calendar.DAY_OF_MONTH, 12);
        Date date = cal.getTime();

        EditText subscriptionName = view.findViewById(R.id.sub_name);
        EditText amount = view.findViewById(R.id.amountAdd);
        EditText description = view.findViewById(R.id.descriptionAdd);

        Button checkButton = view.findViewById(R.id.check_subscription);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Composing a new subscription
                Subscription subscription = new Subscription(subscriptionName.getText().toString(),
                                                                date,
                                                                amount.getText().toString(),
                                                                color,
                                                                "uuid",
                                                                "monthly");
                addNewSubscription(subscription, view);
                goBack();
            }
        });

        return view;
    }

    private void goBack() {
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

    private void addNewSubscription(Subscription subscription, View view) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsub-9f668-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("users/"+user.getUid()+"/subscriptions");
        String uuid = reference.push().getKey();
        subscription.setUuid(uuid);
        reference.child(uuid).setValue(subscription)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(view.getContext(), "Subscription successfully added!", Toast.LENGTH_SHORT);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(view.getContext(), "Subscription not added!", Toast.LENGTH_SHORT);
                    }
                });
    }

    private void setColorListeners (View view) {
        CircleButtonNew c11 = view.findViewById(R.id.c11);
        CircleButtonNew c12 = view.findViewById(R.id.c12);
        CircleButtonNew c13 = view.findViewById(R.id.c13);
        CircleButtonNew c14 = view.findViewById(R.id.c14);
        CircleButtonNew c15 = view.findViewById(R.id.c15);
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (lastId != -1) view.findViewById(lastId).setBackgroundColor(0x00000000);
                v.setBackgroundColor(Color.argb(0.1f, 0f, 0f, 0f));
                lastId = v.getId();
                CircleButtonNew thisBtn = view.findViewById(lastId);
                color = thisBtn.getCurrentColor();
                Log.d("ADD", "onClick: " + thisBtn.getCurrentColor());

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