package com.example.semesterproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonorAdapter extends BaseAdapter {
    private ArrayList<Donor> donors;
    private LayoutInflater inflater;

    public DonorAdapter(Context context, ArrayList<Donor> donors) {
        this.donors = donors;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return donors.size();
    }

    @Override
    public Object getItem(int position) {
        return donors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_donor, parent, false);
        }

        Donor donor = donors.get(position);

        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView emailTextView = view.findViewById(R.id.email_text_view);
        TextView bloodTypeTextView = view.findViewById(R.id.blood_type_text_view);
        TextView addressTextView = view.findViewById(R.id.address_text_view);
        TextView phoneNumberTextView = view.findViewById(R.id.phone_number_text_view);
        ImageView profileImageView = view.findViewById(R.id.profile_image_view);

        nameTextView.setText(donor.getName());
        emailTextView.setText(donor.getEmail());
        bloodTypeTextView.setText(donor.getBloodType());
        addressTextView.setText(donor.getAddress());
        phoneNumberTextView.setText(donor.getPhoneNumber());

        // Load the profile picture URL using Picasso
        Picasso.get()
                .load(donor.getProfilePictureUrl())
                .placeholder(R.drawable.default_profile_picture)
                .error(R.drawable.default_profile_picture)
                .into(profileImageView);

        return view;
    }

    public void updateData(ArrayList<Donor> donors) {
        this.donors = donors;
        notifyDataSetChanged();
    }
}