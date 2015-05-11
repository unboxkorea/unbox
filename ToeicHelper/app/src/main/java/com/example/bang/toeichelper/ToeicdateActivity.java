package com.example.bang.toeichelper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.usage.UsageEvents;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bang.toeichelper.customdlg.ToeicdateAddDlg;
import com.example.bang.toeichelper.mydata.TOEICDATE;
import com.example.bang.toeichelper.mydata.TOEICDATE_handler;
import com.example.bang.toeichelper.myfragment.ToeicdateFragment;
import com.google.android.gms.games.event.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by BANG on 2015-02-25.
 */
public class ToeicdateActivity extends ActionBarActivity implements View.OnClickListener{

    private ViewPager vpager;
    private ToeicdatePagerAdapter adapter;

    private LinearLayout layYBMHome;

    private TOEICDATE_handler toeicdate_handler;


    private int callID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeicdate);

        Intent intent = getIntent();
        toeicdate_handler = (TOEICDATE_handler)intent.getSerializableExtra("toeicdate_h");

        setActionBar();

        Init();
    }

    public void Init(){
        layYBMHome = (LinearLayout) findViewById(R.id.layToeicdateYBMHome);

        vpager = (ViewPager) findViewById(R.id.vpagerToeicdate);

        adapter = new ToeicdatePagerAdapter(getSupportFragmentManager(), toeicdate_handler.getSize());
        vpager.setAdapter(adapter);

        setViewPagerTrans();

        layYBMHome.setOnClickListener(ToeicdateActivity.this);
    }

    //vpager 넘김 효과
    public void setViewPagerTrans(){

        vpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final ToeicdateFragment fragment = (ToeicdateFragment) adapter.getItem(0);
                LinearLayout lay = fragment.getLayTh();

                if(lay == null){
                    Toast.makeText(ToeicdateActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == layYBMHome.getId()){
            //YBM 홈페이지 띄우기
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://appexam.ybmsisa.com/toeic/info/receipt_schedule.asp"));
            startActivity(intent);
        }
    }

    public void setActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background_black));
        actionBar.setTitle("토익 시험 일정");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toeicdate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_plus){

            //Toast.makeText(ToeicdateActivity.this, toeicdate_handler.getSize() + " man", Toast.LENGTH_SHORT).show();

            ToeicdateAddDlg dialog = new ToeicdateAddDlg(ToeicdateActivity.this);


            //현재 페이지에 따라 TOEICDATE 따오기
            TOEICDATE toeicdate = toeicdate_handler.getToeicDateByKey(vpager.getCurrentItem());
            //문자열 형태로 만들어줌
            String strDate = String.format("%d.%d.%d/09:20:00", toeicdate.getnExamYear(), toeicdate.getnExamMonth(), toeicdate.getnExamDay());
            //long형으로 데이터 포맷 해주기
            long lDate = 0;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd/HH:mm:ss");
            try {
                Date date = simpleDateFormat.parse(strDate);
                lDate = date.getTime();
                //Log.w("lDate", lDate + " 임");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dialog.setLongDate(lDate);
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Intent getSupportParentActivityIntent() {

        this.finish();

        return super.getSupportParentActivityIntent();
    }




    public class ToeicdatePagerAdapter extends FragmentPagerAdapter {

        private int Count;

        private Fragment[] fragments;

        public ToeicdatePagerAdapter(FragmentManager fm, int Count) {
            super(fm);

            this.Count = Count;

            fragments = new Fragment[Count];

            for(int i=0;i<Count;i++){
                fragments[i] = ToeicdateFragment.create(toeicdate_handler, i);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return this.Count;
        }
    }
}
