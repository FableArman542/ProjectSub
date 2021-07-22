package com.example.projectsub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.projectsub.adapter.SubscriptionAdapter;
import com.example.projectsub.model.Subscription;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Color c = new Color();

        Subscription[] mySubscriptions = new Subscription[] {
                new Subscription("Spotify", "10/02", "16", Color.argb(255, 113,239,163)),
                new Subscription("Youtube", "12/09", "22", Color.argb(255,248, 95, 106))
        };

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        SubscriptionAdapter adapter = new SubscriptionAdapter(mySubscriptions);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}