package com.rocktech.firebasedatabaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button signUp;
    ImageView image;
    TextView logo, slogan;
    TextInputLayout txtUsername, txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);
        signUp = findViewById(R.id.sign_up);
        image = findViewById(R.id.logo_image1);
        logo = findViewById(R.id.logo_text1);
        slogan = findViewById(R.id.slogan);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
//                Pair[] pairs  = new Pair[1];
//                pairs[0] = new Pair<View, String>(image, "logo_image1");
//                pairs[1] = new Pair<View, String>(logo, "logo_text1");
//                pairs[2] = new Pair<View, String>(slogan, "slogan");
//                pairs[0] = new Pair<View, String>(linearLayout, "form");
                //
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
//                startActivity(intent, options.toBundle());

            }
        });
    }
    private Boolean validateUsername(TextInputLayout text){
        String val = text.getEditText().getText().toString();

        if (val.isEmpty()){
            text.setError("Field cannot be empty");
            return false;
        }
        else {
            text.setError(null);
            text.setErrorEnabled(false);
            return true;
        }
    }
    public void loginUser(View view){
        if (!validateUsername(txtUsername) | !validateUsername(txtPassword)){
            return;
        }
        else {
            isUser();
        }
    }

    private void isUser() {
        UserModel model;
        final String username1 = txtUsername.getEditText().getText().toString().trim();
        final String password = txtPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        final Query checkUsername = reference.orderByChild("phoneNo").equalTo(username1);
        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    txtUsername.setError(null);
                    txtUsername.setErrorEnabled(false);
                    String passwordFromDb = snapshot.child(username1).child("password").getValue(String.class);
                    if (password.equals(passwordFromDb)){
                        txtPassword.setError(null);
                        txtPassword.setErrorEnabled(false);
                        String nameFromDb = snapshot.child(username1).child("name").getValue(String.class);
                        String usernameFromDb = snapshot.child(username1).child("username").getValue(String.class);
                        String phoneNoFromDb = snapshot.child(username1).child("phoneNo").getValue(String.class);
                        String emailFromDb = snapshot.child(username1).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        intent.putExtra("name", nameFromDb);
                        intent.putExtra("username", usernameFromDb);
                        intent.putExtra("phoneNo", phoneNoFromDb);
                        intent.putExtra("email", emailFromDb);
                        intent.putExtra("password", passwordFromDb);
                        startActivity(intent);
                    }
                    else if (passwordFromDb == null){
                        txtPassword.setError("No Password Detected");
                    }
                    else {
                        txtPassword.setError("Wrong Password");
                        txtPassword.requestFocus();
                    }
                }
                else {
                    txtUsername.setError("No Such User exist");
                    txtUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
