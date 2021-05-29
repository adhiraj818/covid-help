package com.adhiraj.covidhelp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adhiraj.covidhelp.R;

import java.util.ArrayList;

public class RecyclerViewAdapterTiffin extends RecyclerView.Adapter<RecyclerViewAdapterTiffin.ViewHolder> {

    private ArrayList<String> mproviderNames = new ArrayList<String>();
    private ArrayList<String> maddress = new ArrayList<String>();
    private ArrayList<String> mcontact = new ArrayList<String>();
    private Context mContext;

    public RecyclerViewAdapterTiffin(Context context, ArrayList<String> providerNames, ArrayList<String> address, ArrayList<String> contact){

        mContext = context;
        mproviderNames =providerNames;
        maddress =address;
        mcontact = contact;

    }

    @NonNull
    @Override
    public RecyclerViewAdapterTiffin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiffin_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.providername.setText(mproviderNames.get(position));
        holder.address.setText("Address/city : "+maddress.get(position));
        holder.contact.setText("Contact : "+mcontact.get(position));

    }

    @Override
    public int getItemCount() {
        return mproviderNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView providername , address , contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            providername = itemView.findViewById(R.id.provider_name);
            address = itemView.findViewById(R.id.address);
            contact = itemView.findViewById(R.id.contact);

        }
    }

}
