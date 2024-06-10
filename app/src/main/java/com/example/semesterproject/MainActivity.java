package com.example.semesterproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView donorListView;
    private DonorAdapter donorAdapter;
    private ArrayList<Donor> donors;
    private EditText searchEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views after setContentView
        donorListView = findViewById(R.id.donor_list_view);
        searchEditText = findViewById(R.id.search_edit_text);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Check if user is signed in
        if (mAuth.getCurrentUser() == null) {
            // Handle user not signed in
            Toast.makeText(this, "Please sign in to access this feature", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        donors = new ArrayList<>();
        donorAdapter = new DonorAdapter(this, donors);
        donorListView.setAdapter(donorAdapter);

        mDatabase.child("donors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Donor donor = dataSnapshot.getValue(Donor.class);
                donors.add(donor);
                donorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                searchDonors(s.toString());
            }
        });

        donorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donor donor = donors.get(position);
                showDonorInfoPopup(donor);
            }
        });
    }

    private void searchDonors(String query) {
        ArrayList<Donor> searchedDonors = new ArrayList<>();
        for (Donor donor : donors) {
            if (donor.getName().toLowerCase().contains(query.toLowerCase()) ||
                    donor.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                    donor.getBloodType().toLowerCase().contains(query.toLowerCase()) ||
                    donor.getAddress().toLowerCase().contains(query.toLowerCase()) ||
                    donor.getPhoneNumber().toLowerCase().contains(query.toLowerCase())) {
                searchedDonors.add(donor);
            }
        }
        donorAdapter.updateData(searchedDonors);
    }

    private void showDonorInfoPopup(Donor donor) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.donor_info_popup_layout, null);

        TextView nameTextView = popupView.findViewById(R.id.name_text_view);
        TextView emailTextView = popupView.findViewById(R.id.email_text_view);
        TextView bloodTypeTextView = popupView.findViewById(R.id.blood_type_text_view);
        TextView addressTextView = popupView.findViewById(R.id.address_text_view);
        TextView phoneNumberTextView = popupView.findViewById(R.id.phone_number_text_view);

        nameTextView.setText(donor.getName());
        emailTextView.setText(donor.getEmail());
        bloodTypeTextView.setText(donor.getBloodType());
        addressTextView.setText(donor.getAddress());
        phoneNumberTextView.setText(donor.getPhoneNumber());

        Button copyTextButton = popupView.findViewById(R.id.btn);
        copyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Donor Info", getDonorInfoText(donor));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Get the screen width and height
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        // Show the popup in the center of the screen
        popupWindow.showAtLocation(donorListView, Gravity.NO_GRAVITY, screenWidth / 3, screenHeight / 3);
    }

    private String getDonorInfoText(Donor donor) {
        return "Name: " + donor.getName() + "\n" +
                "Email: " + donor.getEmail() + "\n" +
                "Blood Type: " + donor.getBloodType() + "\n" +
                "Address: " + donor.getAddress() + "\n" +
                "Phone Number: " + donor.getPhoneNumber();
    }
}