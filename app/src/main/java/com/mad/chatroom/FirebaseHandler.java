package com.mad.chatroom;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Chinmay Rawool on 4/10/2017.
 */

public class FirebaseHandler {
    /*DatabaseReference db;
    ArrayList<User> users = new ArrayList<User>();
    boolean saved = false;
    ChildEventListener childEventListener;
    Ihandler handler;
    FirebaseHandler(DatabaseReference db, Ihandler handler){
        this.db = db;
        this.handler = handler;

    }


    FirebaseHandler(DatabaseReference db){
        this.db = db;
    }


    public ArrayList<User> retrieveUsers(){
        //
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                users = getData(dataSnapshot);
                Log.d("demo", "onchildadded returned" + db.toString() + " " + saved);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                users = getData(dataSnapshot);
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
        Log.d("demo","users in retrieve details "+ users.toString()+db.toString());
        Log.d("demo","Before return of users");

        return users;
    }

    private ArrayList<User> getData(DataSnapshot dataSnapshot){
        users = new ArrayList<>();

        for (DataSnapshot ds: dataSnapshot.getChildren()) {

            User city = new User(ds.getValue(User.class).getfName(),ds.getValue(User.class).getlName(),ds.getValue(User.class).getEmail());
            Log.d("demo","In Handler, City:"+city.toString());
            users.add(city);
        }
        handler.onReturnValues(users);
        return users;
    }

    public interface Ihandler{
        public void onReturnValues(ArrayList<User> users);
    }

    void removeHandler(){
        db.removeEventListener(childEventListener);
    }*/
}
