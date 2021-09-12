package com.example.projectsub.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;
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

    private static final String TAG = "SUB_ADAPTER";
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
        final Subscription mySubscription = subscriptionsList.get(position);
        boolean isClickable = false;

        holder.amountLayout.getBackground().setColorFilter(mySubscription.getColor(), PorterDuff.Mode.SRC_ATOP);
        holder.subscriptionName.setText(mySubscription.getName());
        holder.nextPay.setText(mySubscription.getNextPay());
        holder.amount.setText(mySubscription.getAmount() + "€");
        holder.finishDate.setText(mySubscription.getFinishDate());

        String desc = mySubscription.getDescription();
        if (desc != null && !desc.trim().isEmpty()) {
            isClickable = true;
            holder.description.setText(desc);
        }

        if (isClickable) {
            holder.subscriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition(holder.subscriptionLayout, new AutoTransition());
                    holder.descriptionLayout.setVisibility(holder.descriptionLayout.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });
        }

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

    public void deleteItem(int position, View coordinator) {
        mRecentlyDeletedItem = subscriptionsList.get(position);
        mRecentlyDeletedItemPosition = position;
        subscriptionsList.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar(coordinator);
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

    private void showUndoSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
        snackbar.show();
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    deleteFromDatabase(mRecentlyDeletedItem);
                }
            }
        });
    }

    private void undoDelete() {
        subscriptionsList.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout subscriptionLayout;
        public LinearLayout descriptionLayout;
        public ConstraintLayout amountLayout;

        public TextView subscriptionName;
        public TextView nextPay;
        public TextView amount;
        public TextView finishDate;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            this.amountLayout = itemView.findViewById(R.id.amountLayout);
            this.subscriptionLayout = itemView.findViewById(R.id.subscriptionLayout);
            this.subscriptionName = itemView.findViewById(R.id.subscriptionName);
            this.nextPay = itemView.findViewById(R.id.nextPay);
            this.amount = itemView.findViewById(R.id.amount);
            this.finishDate = itemView.findViewById(R.id.finishDateSub);
            this.description = itemView.findViewById(R.id.descriptionCard);
            this.descriptionLayout = itemView.findViewById(R.id.descriptionLayout);
        }

    }
}
