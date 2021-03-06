package com.rocktech.firebasedatabaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    DatabaseReference reference;
    TextInputLayout full_name, email, phone_no, password;
    TextView username, fullName;
   static String nameIntent, usernameIntent, emailIntent, phoneNoIntent, passwordIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //
        getUserDetails();
        reference = FirebaseDatabase.getInstance().getReference("users");

        //init();

    }

    private void getUserDetails() {
        init();
        Intent value = getIntent();
        if (value.hasExtra("phoneNo")){
            nameIntent = value.getStringExtra("name");
            usernameIntent = value.getStringExtra("username");
            emailIntent = value.getStringExtra("email");
            phoneNoIntent = value.getStringExtra("phoneNo");
            passwordIntent = value.getStringExtra("password");
            fullName.setText(nameIntent);
            username.setText(value.getStringExtra("username"));
            full_name.getEditText().setText(nameIntent);
            email.getEditText().setText(emailIntent);
            phone_no.getEditText().setText(phoneNoIntent);
            password.getEditText().setText(passwordIntent);
        }
        else {
            Toast.makeText(this, "Empty Intent", Toast.LENGTH_LONG).show();
        }

        //


    }

    private void init() {
        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        phone_no = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        fullName = findViewById(R.id.fullName);
    }
    //
    public  void update(View view){
        if (isValueChanged(nameIntent, full_name, "name") |
                isValueChanged(emailIntent, email, "email") |
                isValueChanged(phoneNoIntent, phone_no, "phoneNo") |
                isValueChanged(passwordIntent, password, "password")
                ){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this, "No changes was made", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValueChanged(String value, TextInputLayout txtValue, String child) {
        String valueHolder = txtValue.getEditText().getText().toString();
        if (!value.equals(valueHolder)){
            reference.child(phoneNoIntent).child(child).setValue(valueHolder);
            value = valueHolder;
            return true;
        }
        else {
            return false;
        }
    }


}
