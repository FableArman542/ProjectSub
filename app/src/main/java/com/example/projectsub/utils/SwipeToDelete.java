package com.example.projectsub.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsub.adapter.SubscriptionAdapter;

import org.jetbrains.annotations.NotNull;

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback {

    private SubscriptionAdapter adapter;
    private View coordinator;

    public SwipeToDelete(SubscriptionAdapter adapter, View coordinator) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.coordinator = coordinator;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.deleteItem(position, coordinator);
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        return false;
    }
}
