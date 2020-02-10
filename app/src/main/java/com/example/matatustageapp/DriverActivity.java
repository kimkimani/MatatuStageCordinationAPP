package com.example.matatustageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.matatustageapp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;


public class DriverActivity extends AppCompatActivity {

    Button btnsignin, btnreg;
    RelativeLayout rootlayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        getSupportActionBar().setTitle("Driver");

        //init firebase

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        //init view
        btnsignin = (Button) findViewById(R.id.btnsignin);
        btnreg = (Button) findViewById(R.id.btnreg);
        rootlayout = (RelativeLayout) findViewById(R.id.rootlayout);

        //event
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRegisterDialog();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }

        });
    }



    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please use email to SIGN IN");
        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        final EditText edtEmail = login_layout.findViewById(R.id.email);
        final EditText edtpassword = login_layout.findViewById(R.id.password);



        dialog.setView(login_layout);
        //set button
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //setting disable button
                btnsignin.setEnabled(false);
                final AlertDialog waitingDialog=new SpotsDialog(DriverActivity.this);
                waitingDialog.show();




                //login
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtpassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(DriverActivity.this, Welcome.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootlayout,"failed"+e.getMessage(),Snackbar.LENGTH_SHORT)
                                .show();

                        // enabling button
                        btnsignin.setEnabled(true);
                    }
                });



            }});
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });


        dialog.show();

    }
    private void showRegisterDialog() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please use email to register");
        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.layout_reg, null);

        final EditText edtEmail = reg_layout.findViewById(R.id.email);
        final EditText edtpassword = reg_layout.findViewById(R.id.password);
        final EditText edtName = reg_layout.findViewById(R.id.name);
        final EditText edtphone = reg_layout.findViewById(R.id.phone);




        dialog.setView(reg_layout);
        //set button
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //register new user
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtpassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //saving user to database

                                User user=new User();
                                user.setEmail(edtEmail.getText().toString());
                                user.setPassword(edtpassword.getText().toString());
                                user.setName(edtName.getText().toString());
                                user.setPhone(edtphone.getText().toString());


                                //use email to key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootlayout,"register successful",Snackbar.LENGTH_SHORT)
                                                        .show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootlayout,"failed"+e.getMessage(), Snackbar.LENGTH_SHORT)
                                                        .show();


                                                // enabling button
                                                btnreg.setEnabled(true);
                                            }
                                        });


                            }

                        });
            }

        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    public  void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.ACCESSIBILITY_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void close(View view){
        finish();
    }
}
