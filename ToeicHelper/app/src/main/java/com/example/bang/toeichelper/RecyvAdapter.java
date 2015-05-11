package com.example.bang.toeichelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bang.toeichelper.customprogress.CircularProgress;

/**
 * Created by BANG on 2015-01-27.
 */
public class RecyvAdapter extends RecyclerView.Adapter<RecyvAdapter.LoginedViewHolder> {

    private Context context;
    private int Count;
    private int[] nCardViewId;
    private int position;
    private TextView txtvTest;

    public RecyvAdapter(Context context, int Count, int[] nCardViewId) {
        this.context = context;
        this.Count = Count;
        this.nCardViewId = nCardViewId;
        position = 0;
    }

    @Override
    public LoginedViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(nCardViewId[position], viewGroup, false);

        if(position == 0){
            //Log.w("position", "if ok");
            view.findViewById(R.id.ripplelayLoginedToeicDate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ToeicdateActivity.class);
                    intent.putExtra("toeicdate_h", ((LoginedActivity)context).getToeicdate_handler());
                    context.startActivity(intent);
                }
            });
        }

        position++;

        return new LoginedViewHolder(view);
    }

    //이게 시작되는 조건이 화면에서 보였을때임
    @Override
    public void onBindViewHolder(LoginedViewHolder loginedViewHolder, int i) {
        //loginedViewHolder.txtvCardID.setText("ID = " + i);
        if(i == 0){
            ((LoginedActivity)context).executeTaskImportToeicDate(loginedViewHolder.txtvLoginedToeicdate2, loginedViewHolder.cprogressToeicDate);
        }
        loginedViewHolder.rootView.setRotationX(30);
        loginedViewHolder.rootView.animate().rotationX(0);
    }

    @Override
    public void onViewDetachedFromWindow(LoginedViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return this.Count;
    }

    public final static class LoginedViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        //0
        CircularProgress cprogressToeicDate;
        TextView txtvLoginedToeicdate1;
        TextView txtvLoginedToeicdate2;
        TextView txtvTest;

        public LoginedViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            txtvTest = (TextView) itemView.findViewById(R.id.txtvLoinedTest);
            //position == 0
            cprogressToeicDate = (CircularProgress) itemView.findViewById(R.id.cprogressToeicDate);
            txtvLoginedToeicdate1 = (TextView) itemView.findViewById(R.id.txtvLoginedToeicdate1);
            txtvLoginedToeicdate2 = (TextView) itemView.findViewById(R.id.txtvLoginedToeicdate2);
        }
    }
}
