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

public class OSAdapter extends RecyclerView.Adapter<OSAdapter.OSViewHolder> {
    String OSS;
    private Context mContext;
    private ArrayList<OSItem> mOSList;
    private OnItemClickListener mListener;

    public OSAdapter(Context context, ArrayList<OSItem> osList) {
        mContext = context;
        mOSList = osList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public OSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.o_s_item, parent, false);
        return new OSViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OSViewHolder holder, int position) {
        OSItem currentItem = mOSList.get(position);
        String SalesOrder = currentItem.getNumber();
        String TotalNetAmount = currentItem.getAmount();
        String Currency = currentItem.getCurrency();
        String Status = currentItem.getStatus();
        holder.mTextViewNumber.setText("Order Number :  " + SalesOrder);
        holder.mTextViewAmount.setText("Total Net Amount :  " + TotalNetAmount + " " + Currency);


        if (Status.equals("A")) {
            OSS = "<font color='black'>Order Status :  A (Order Created)</font>";
            holder.mTextViewStatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        } else if (Status.equals("B")) {
            OSS = "<font color='black'>Order Status :  B (Delivery Created)</font>";
            holder.mTextViewStatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        } else if (Status.equals("C")) {
            OSS = "<font color='black'>Order Status :  C (Delivered)</font>";
            holder.mTextViewStatus.setText(Html.fromHtml(OSS), TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount() {
        return mOSList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class OSViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewNumber;
        public TextView mTextViewAmount;
        public TextView mTextViewStatus;

        public OSViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewNumber = itemView.findViewById(R.id.ordernum);
            mTextViewAmount = itemView.findViewById(R.id.orderprice);
            mTextViewStatus = itemView.findViewById(R.id.orderstatus);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
