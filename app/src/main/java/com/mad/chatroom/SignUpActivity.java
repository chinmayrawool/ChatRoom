package com.mad.chatroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail , editTextFname, editTextLname;
    private EditText editTextPassword, editTextConfirmPassword;
    private Button btnSignUp, btnCancel;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase db;
    DatabaseReference rootRef;
    String userUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail =(EditText)findViewById(R.id.et_emailSignUp);
        editTextPassword =(EditText)findViewById(R.id.et_passwordSignUp);
        editTextFname =(EditText)findViewById(R.id.et_fname_signup);
        editTextLname=(EditText)findViewById(R.id.et_lname_signup);
        editTextConfirmPassword =(EditText)findViewById(R.id.et_confirmpasswordSignUp);
        btnSignUp =(Button) findViewById(R.id.btn_signUp);
        btnCancel =(Button) findViewById(R.id.btn_cancel);
        //textViewLogin = (TextView)findViewById(R.id.textViewLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootRef = db.getReference();
        /*if(mAuth.getCurrentUser()!=null){
            //Intent to profile activity
            finish();
            Log.d("demo","Intent to profile activity");
            startActivity(new Intent(getApplicationContext(),ChatActivity.class));
        }
*/
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userUID = user.getUid();
                    // User is signed in
                    Log.d("demo", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("demo", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fName = editTextFname.getText().toString().trim();
                final String lName = editTextLname.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if(!(fName.equals("") || lName.equals("") || email.equals("") || password.equals("")) && password.equals(confirmPassword) ){
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been created", Toast.LENGTH_SHORT).show();
                                        User user = new User(fName,lName,email);

                                        rootRef.child("User").child(userUID).setValue(user);

                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));


                                    }
                                }
                            }).addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(SignUpActivity.this, "Enter correct details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.signOut();
        }

    }
}
