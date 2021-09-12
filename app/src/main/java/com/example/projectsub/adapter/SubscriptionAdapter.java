package com.example.projectsub.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsub.R;
import com.example.projectsub.model.Subscription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private List<Subscription> subscriptionsList;

    private Subscription mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private int lastPosition = -1;
    private Context context;

    public SubscriptionAdapter (List<Subscription> subscriptionsList, Context context) {
        this.subscriptionsList = subscriptionsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View subscriptionCard = layoutInflater.inflate(R.layout.subscription_item, parent, false);

        return new ViewHolder(subscriptionCard);
    }

    @Override
    public void onBindViewHolder(SubscriptionAdapter.ViewHolder holder, int position) {
        final Subscription mySub = subscriptionsList.get(position);

        holder.amountLayout.getBackground().setColorFilter(mySub.getColor(), PorterDuff.Mode.SRC_ATOP);
//        holder.subscriptionLayout.getBackground().setColorFilter(mySub.getColor(), PorterDuff.Mode.SRC_ATOP);
        holder.subscriptionName.setText(mySub.getName());
        holder.nextPay.setText(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(mySub.getNextPay()));
        holder.amount.setText(mySub.getAmount() + "€");
        holder.subscriptionSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if(position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return subscriptionsList.size();
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = subscriptionsList.get(position);
        mRecentlyDeletedItemPosition = position;
        subscriptionsList.remove(position);
        notifyItemRemoved(position);
//        showUndoSnackbar();
        deleteFromDatabase(mRecentlyDeletedItem);
    }

    private void deleteFromDatabase(Subscription subscription) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsub-9f668-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("users/"+user.getUid()+"/subscriptions");
        reference.child(subscription.getUuid()).removeValue();
    }

    // TODO https://stackoverflow.com/questions/26724964/how-to-animate-recyclerview-items-when-they-appear
//    @Override
//    public void onViewDetachedFromWindow(SubscriptionAdapter.ViewHolder holder)
//    {
//        super.onViewDetachedFromWindow(holder);
//    }

//    private void showUndoSnackbar() {
//        View view = mActivity.findViewById(R.id.coordinator_layout);
//        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
//                Snackbar.LENGTH_LONG);
//        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
//        snackbar.show();
//    }
//
//    private void undoDelete() {
//        mListItems.add(mRecentlyDeletedItemPosition,
//                mRecentlyDeletedItem);
//        notifyItemInserted(mRecentlyDeletedItemPosition);
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout subscriptionLayout;
        public ConstraintLayout amountLayout;

        public TextView subscriptionName;
        public TextView nextPay;
        public TextView amount;

        public ImageButton subscriptionSettingsButton;



        public ViewHolder(View itemView) {
            super(itemView);

            this.amountLayout = itemView.findViewById(R.id.amountLayout);
            this.subscriptionLayout = itemView.findViewById(R.id.subscriptionLayout);
            this.subscriptionName = itemView.findViewById(R.id.subscriptionName);
            this.nextPay = itemView.findViewById(R.id.nextPay);
            this.amount = itemView.findViewById(R.id.amount);
            this.subscriptionSettingsButton = itemView.findViewById(R.id.subscriptionSettings);
        }

    }
}
