package com.example.semesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button becomeDonorButton;
    private Button findDonorButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        becomeDonorButton = findViewById(R.id.become_donor_button);
        findDonorButton = findViewById(R.id.find_donor_button);

        becomeDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonorRegistrationForm();
            }
        });

        findDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user is signed in
                if (mAuth.getCurrentUser() == null) {
                    // Handle user not signed in
                    Toast.makeText(RegisterActivity.this, "Please sign in to access this feature", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                Toast.makeText(RegisterActivity.this, "Find Donor button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //...

    private void showDonorRegistrationForm() {
        DonorRegistrationFormFragment fragment = new DonorRegistrationFormFragment();
        fragment.show(getSupportFragmentManager(), "donor_registration_form");
    }
}