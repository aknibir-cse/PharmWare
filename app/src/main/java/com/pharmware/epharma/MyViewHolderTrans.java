package com.pharmware.epharma;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderTrans extends RecyclerView.ViewHolder {

    TextView trans, date, money, custname;
    ImageView delbtn;
    LinearLayout gone;
    CardView cv;

    public MyViewHolderTrans(View itemView) {
        super(itemView);

        cv = (CardView) itemView.findViewById(R.id.cv);

        trans = (TextView) itemView.findViewById(R.id.id);
        date = (TextView) itemView.findViewById(R.id.date);
        money = (TextView) itemView.findViewById(R.id.money);
        custname = (TextView) itemView.findViewById(R.id.custname);

        delbtn = (ImageView) itemView.findViewById(R.id.deletebutton);
    }
}
