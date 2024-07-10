package com.beyzanur.expiration_date_reminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName, editMail;
    private ProgressDialog mProgress;

    private FirebaseFirestore mFirestore;
    private DocumentReference mRef;
    private FirebaseUser mUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName =findViewById(R.id.profil_activity_editName);
        editMail = findViewById(R.id.profil_activity_editMail);


        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        mRef = mFirestore.collection("users").document(mUser.getUid());

        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userMail = documentSnapshot.getString("userMail");
                    String userName = documentSnapshot.getString("userName");

                    System.out.println(userMail);
                    System.out.println(userName);
                    editName.setText(userName);
                    editMail.setText(userMail);

                } else {


                }
            }
        });

    }

    public void btnLogout(View v){
        mProgress= new ProgressDialog(this);
        mProgress.setTitle("Exiting...");
        mProgress.show();
        progressAyar();
        Toast.makeText(ProfileActivity.this, "You have successfully logged out", Toast.LENGTH_SHORT).show();
        finish();

        startActivity(new Intent(ProfileActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    private void progressAyar(){
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
}