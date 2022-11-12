package com.pharmware.epharma;

import static com.pharmware.epharma.MainActivity.fab;


import  androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class summary extends Fragment {
    View v;
    Button sample, log;
    private DatabaseReference du;
    String s;
    private FirebaseAuth mAuth;
    TextView t;
    String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.summary, container, false);
        getActivity().setTitle("Summary");
        com.pharmware.epharma.MainActivity.flag = 1;
        sample = (Button) v.findViewById(R.id.sample);
        log = (Button) v.findViewById(R.id.log);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        du = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        du.keepSynced(true);

        t = (TextView) v.findViewById(R.id.title);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.hide();
        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SampleBill.class);
                startActivity(i);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Log.class);
                startActivity(i);
            }
        });
        du.child("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };
                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                t.setText(map.get("storename"));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        du.child("Summary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("amt")) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
                    SimpleDateFormat fm = new SimpleDateFormat("MM");
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };
                    Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                    String date = (String) dataSnapshot.child("date").getValue();
                    String month = (String) dataSnapshot.child("month").getValue();
                    if (!format.format(c.getTime()).equals(date)) {
                        du.child("date").setValue(format.format(c.getTime()));
                        du.child("count").setValue("0");
                        du.child("amt").setValue("0.0");
                        ((TextView) (v.findViewById(R.id.todayamt))).setText("0.0");
                        ((TextView) (v.findViewById(R.id.todaycount))).setText("0");
                    } else if (!fm.format(c.getTime()).equals(month)) {
                        du.child("month").setValue(fm.format(c.getTime()));
                        du.child("monthcount").setValue("0");
                        du.child("monthamt").setValue("0.0");
                        ((TextView) (v.findViewById(R.id.mcount))).setText("0");
                        ((TextView) (v.findViewById(R.id.mamt))).setText("0.0");
                    } else {
                        ((TextView) (v.findViewById(R.id.todayamt))).setText(map.get("amt"));
                        ((TextView) (v.findViewById(R.id.todaycount))).setText(map.get("count"));
                        ((TextView) (v.findViewById(R.id.mcount))).setText(map.get("monthcount"));
                        ((TextView) (v.findViewById(R.id.mamt))).setText(map.get("monthamt"));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
}
