package com.example.ffc;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OSDAdapter extends RecyclerView.Adapter<OSDAdapter.OSDViewHolder> {
    private Context xContext;
    private ArrayList<OSDItem> xExampleList;
    String OSS;


    public OSDAdapter(Context context, ArrayList<OSDItem> aexampleList) {
        xContext = context;
        xExampleList = aexampleList;
    }

    @NonNull
    @Override
    public OSDAdapter.OSDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(xContext).inflate(R.layout.osd_item, parent, false);
        return new OSDAdapter.OSDViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OSDAdapter.OSDViewHolder holder, int position) {
        OSDItem currItem = xExampleList.get(position);
        String Item = currItem.getItem();
        String Quantity = currItem.getQuantity();
        String Amount = currItem.getAmount();
        String Status = currItem.getStatus();
        String Matnum = currItem.getMnum();
        String Itnum = currItem.getInum();
        String Weight = currItem.getWeight();
        String Currency = currItem.getCurrency();


        holder.xTextViewItem.setText("Item #" + Itnum + " :  " + Item + " (" + Matnum + ")");
        holder.xTextViewQuantity.setText("Quantity :  " + Quantity + " " + Weight);
        holder.xTextViewAmount.setText("Amount :  " + Amount + " " + Currency);

        if (Status.equals("A")) {
            OSS = "<font color='black'>Order Status :  </font><font color='#1A73E8'> Order Created</font>";
            holder.xTextViewstatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        } else if (Status.equals("B")) {
            OSS = "<font color='black'>Order Status :  </font><font color='#FFBF00'> Delivery Created</font>";
            holder.xTextViewstatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        } else if (Status.equals("C")) {
            OSS = "<font color='black'>Order Status :  </font><font color='#027C4F'> Delivered</font>";
            holder.xTextViewstatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        }

    }

    @Override
    public int getItemCount() {
        return xExampleList.size();
    }


    public class OSDViewHolder extends RecyclerView.ViewHolder {
        public TextView xTextViewItem;
        public TextView xTextViewQuantity;
        public TextView xTextViewAmount;
        public TextView xTextViewstatus;

        public OSDViewHolder(@NonNull View itemView) {
            super(itemView);

            xTextViewItem = itemView.findViewById(R.id.item);
            xTextViewQuantity = itemView.findViewById(R.id.quantity);
            xTextViewAmount = itemView.findViewById(R.id.amount);
            xTextViewstatus = itemView.findViewById(R.id.status);


        }
    }
}
