package com.rocktech.firebasedatabaseauth;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Button regBtn, loginBtn;
    ImageView image;
    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;


    //
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.userName);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phoneNumber);
        regPassword = findViewById(R.id.password);
        //
        regBtn = findViewById(R.id.reg_user);
        loginBtn = findViewById(R.id.login);
        //
//        regBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
    public void registerUser(View view){

        if (! validateName() | !validateEmail() | !validateUsername() | !validatePhone() | !validatePassword()){
            return;
        }
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        //
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        UserModel userModel = new UserModel(name, username, email, phoneNo, password);
        //
        reference.child(phoneNo).setValue(userModel);
    }
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            return true;
        }
    }
    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String regExp = "(?=\\w+$)";
        if (val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()>=15){
            regUsername.setError("Username is too long");
            return false;
        }
//        else if (!val.matches(regExp)){
//            regUsername.setError("White space aren't allowed");
//            return false;
//        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String regExp = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(regExp)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();
        if (val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }
        else {
            regPhone.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String regPass = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=.*[0-9]).{4,}$";
        if (val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(regPass)){
            regPassword.setError("Password is too weak");
            return false;
        }
        else {
            regPassword.setError(null);
            return true;
        }
    }
}
