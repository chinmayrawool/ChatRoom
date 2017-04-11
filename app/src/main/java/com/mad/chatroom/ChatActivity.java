package com.mad.chatroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


//"auth != null"
public class ChatActivity extends AppCompatActivity implements FirebaseMessageHandler.IhandlerMessage{
    ImageView Logout, SendMessage, SendPhoto;
    FirebaseAuth firebaseAuth;
    TextView tv_name;
    EditText messageText;
    //FirebaseHandler handler;
    User currentUser;
    String email;
    FirebaseDatabase db;
    DatabaseReference rootRef;
    LinearLayout chatslayout;
    String userUID;
    FirebaseAuth.AuthStateListener mAuthListener;
    //ValueEventListener v;
    String userDisplayName;
    FirebaseMessageHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Logout = (ImageView) findViewById(R.id.logout);
        SendMessage = (ImageView) findViewById(R.id.addMessage);
        SendPhoto = (ImageView) findViewById(R.id.addImage);
        tv_name = (TextView) findViewById(R.id.user_name);
        messageText = (EditText) findViewById(R.id.messageText);
        chatslayout = (LinearLayout) findViewById(R.id.linearChats);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("demo", "onAuthStateChanged:signed_in");
                    userDisplayName=user.getDisplayName();
                    final String uid = user.getUid();
                    handler.retrieveMessages();
                    //Log.d("DISPLAYNAME:",userDisplayName);
                    //tv_name.setText(userDisplayName+" "+uid);
                    // User is signed in
                } else {
                    // User is signed out
                    Log.d("demo", "onAuthStateChanged:signed_out");
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                // ...
            }
        };
        db = FirebaseDatabase.getInstance();
        rootRef = db.getReference();
        handler = new FirebaseMessageHandler(rootRef,this);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        /*ArrayList<User> users = new ArrayList<>();

        FirebaseUser user = firebaseAuth.getCurrentUser();*/

        if(user!=null) {
            userDisplayName=user.getDisplayName();
            final String uid = user.getUid();
            handler.retrieveMessages();
            //Log.d("DISPLAYNAME:",userDisplayName);
            tv_name.setText(userDisplayName+" "+uid);
        }else{
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }


        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rootRef.removeEventListener(v);
                String text = messageText.getText().toString().trim();
                Log.d("demo","Send message ");
                if(!text.equals("")){
                    String id = String.valueOf(UUID.randomUUID());
                    MessageObj messageObj = new MessageObj(id,text,userDisplayName, System.currentTimeMillis(), true);
                    Log.d("demo","UserID:");
                    rootRef.child("Message").child(id).setValue(messageObj);
                    Log.d("demo","Message"+messageObj.toString());
                }
            }
        });


    }


    /*@Override
    public void onReturnValues(ArrayList<User> users) {
        for(User u: users){
            if(u.getEmail().equals(email)){
                currentUser = new User(u.getfName(),u.getlName(), u.getEmail());
                break;
            }
        }
        tv_name.setText(currentUser.getfName()+" "+ currentUser.getlName());
    }*/

    @Override
    public void onReturnMessage(ArrayList<MessageObj> messages) {
        Log.d("demo","UserID:");
        Log.d("demo","dsdas"+messages.toString());
        View view1 = getLayoutInflater().inflate(R.layout.message,null);
        for(MessageObj m:messages){
            TextView textViewname = (TextView) view1.findViewById(R.id.messageName);
            textViewname.setText(m.getName());
            TextView textViewtext = (TextView) view1.findViewById(R.id.messageText);

            TextView textViewtime = (TextView) view1.findViewById(R.id.messageTime);
            textViewname.setText(""+m.getTime());
            ImageView imageViewI = (ImageView) view1.findViewById(R.id.messageImage);
            ImageView imageViewComment = (ImageView) view1.findViewById(R.id.commentButton);
            ImageView imageViewDelete= (ImageView) view1.findViewById(R.id.deleteButton);


            if(m.getTextFlag()){
                //true=message, false = image
                textViewtext.setText(m.getContent());

            }else{

            }
            final EditText input = new EditText(ChatActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            imageViewComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setTitle("Enter Comment")
                            .setView(input)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("demo","Cancel clicked");

                                }
                            }).setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("demo","OK clicked");

                        }
                    });

                    builder.create().show();

                }
            });
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }



    }

}
