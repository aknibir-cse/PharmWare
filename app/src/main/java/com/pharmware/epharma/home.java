package com.pharmware.epharma;

import static com.pharmware.epharma.MainActivity.fab;

import  androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class home extends Fragment {
    View v;
    ImageView i;
    Animation rotate;
    private DatabaseReference du;
    String s;
    private FirebaseAuth mAuth;
    String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home, container, false);
        getActivity().setTitle("Home");

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        du = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        du.keepSynced(true);
        MainActivity.flag = 0;
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        du.child("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                TextView tv = (TextView) v.findViewById(R.id.hometext);
                tv.setText(map.get("storename"));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        fab.hide();
    }

}
