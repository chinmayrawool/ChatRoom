package com.mad.chatroom;

import android.text.LoginFilter;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Chinmay Rawool on 4/10/2017.
 */

public class FirebaseMessageHandler {
    DatabaseReference db;
    ArrayList<MessageObj> messages = new ArrayList<MessageObj>();
    boolean saved = false;
    ChildEventListener childEventListener;
    IhandlerMessage handler;
    FirebaseMessageHandler(DatabaseReference db, IhandlerMessage handler){
        this.db = db;
        this.handler = handler;

    }


    FirebaseMessageHandler(DatabaseReference db){
        this.db = db;
    }


    public void retrieveMessages(){
        //
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                messages = getData(dataSnapshot);
                Log.d("demo", "onchildadded returned" + db.toString() + " " + saved);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                messages = getData(dataSnapshot);
                Log.d("demo", "onchildchanged returned" + saved);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        db.addChildEventListener(childEventListener);

        //db.child("City").child("favorite").orderByValue().equalTo(true);
        Log.d("demo","messages in retrieve details "+ messages.toString()+db.toString());
        Log.d("demo","Before return of messages");


    }

    private ArrayList<MessageObj> getData(DataSnapshot dataSnapshot){
        messages = new ArrayList<>();

        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            Log.d("firebase",ds.toString());
            //String content, String name, long time, Boolean textFlag
            MessageObj message = new MessageObj(ds.getValue(MessageObj.class).getContent(),ds.getValue(MessageObj.class).getName(),ds.getValue(MessageObj.class).getTime(),ds.getValue(MessageObj.class).getTextFlag());
            message.setComments(ds.getValue(MessageObj.class).getComments());
            Log.d("demo","In Handler, City:"+message.toString());
            messages.add(message);
        }
        handler.onReturnMessage(messages);
        return messages;
    }

    public interface IhandlerMessage{
        public void onReturnMessage(ArrayList<MessageObj> messages);
    }

    void removeHandler(){
        db.removeEventListener(childEventListener);
    }
}
