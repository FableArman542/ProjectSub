package com.example.projectsub.adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsub.R;
import com.example.projectsub.model.Subscription;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private Subscription[] subscriptionsList;

    public SubscriptionAdapter (Subscription[] subscriptionsList) {
        this.subscriptionsList = subscriptionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View subscriptionCard = layoutInflater.inflate(R.layout.subscription_item, parent, false);

        return new ViewHolder(subscriptionCard);
    }

    @Override
    public void onBindViewHolder(SubscriptionAdapter.ViewHolder holder, int position) {
        final Subscription mySub = subscriptionsList[position];

        holder.subscriptionLayout.getBackground().setColorFilter(mySub.getColor(), PorterDuff.Mode.SRC_ATOP);
        holder.subscriptionName.setText(mySub.getName());
        holder.nextPay.setText(mySub.getNextPay());
        holder.amount.setText(mySub.getAmount() + "€");
    }

    @Override
    public int getItemCount() {
        return subscriptionsList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout subscriptionLayout;

        public TextView subscriptionName;
        public TextView nextPay;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);

            this.subscriptionLayout = itemView.findViewById(R.id.subscriptionLayout);
            this.subscriptionName = itemView.findViewById(R.id.subscriptionName);
            this.nextPay = itemView.findViewById(R.id.nextPay);
            this.amount = itemView.findViewById(R.id.amount);
        }
    }

}
