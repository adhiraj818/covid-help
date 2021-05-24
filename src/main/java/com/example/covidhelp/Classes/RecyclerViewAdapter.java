package com.example.covidhelp.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> mhospNames = new ArrayList<String>();
    private ArrayList<String> mbeds_count = new ArrayList<String>();
    private ArrayList<String> moxygen = new ArrayList<String>();
    private ArrayList<String> mcontact = new ArrayList<String>();
    private ArrayList<String> mdate = new ArrayList<String>();

    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> hospNames ,ArrayList<String> beds_count, ArrayList<String> oxygen , ArrayList<String> contact , ArrayList<String> date){
        mContext = context;
        mhospNames = hospNames;
        mbeds_count = beds_count;
        moxygen = oxygen;
        mcontact = contact;
        mdate = date;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beds_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.hospname.setText(mhospNames.get(position));
        holder.beds_count.setText("Beds Available : "+mbeds_count.get(position));
        holder.oxygen.setText("oxygen Available : "+moxygen.get(position));
        holder.contact.setText("Contact no : "+mcontact.get(position));
        holder.date.setText("date : "+mdate.get(position));

        holder.hospname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mhospNames.get(position));
                Toast.makeText(mContext, mhospNames.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mhospNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hospname , beds_count , oxygen , contact ,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hospname = itemView.findViewById(R.id.hosp_name);
            beds_count = itemView.findViewById(R.id.beds_count);
            oxygen = itemView.findViewById(R.id.oxygen_avail);
            contact = itemView.findViewById(R.id.contact);
            date = itemView.findViewById(R.id.date);

        }
    }
}
