package com.example.bang.toeichelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.transition.ActionBarTransition;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.view.View;
import android.widget.Button;

import at.markushi.ui.RevealColorView;

/**
 * Created by BANG on 2015-01-28.
 */
public class RevealActivity extends ActionBarActivity implements View.OnClickListener{

    private RevealColorView revealColorView;
    private View vSel;
    private int backColor;

    private Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        setActionBar();

        Init();
    }

    public void setActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background_black));
        actionBar.setTitle("토익 시험 일정");
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();

        return super.getSupportParentActivityIntent();
    }

    public void Init(){
        backColor = Color.parseColor("#98ceaa");

        revealColorView = (RevealColorView) findViewById(R.id.revealTest);
        btn1 = (Button) findViewById(R.id.btnReveal1);
        btn2 = (Button) findViewById(R.id.btnReveal2);

        btn1.setOnClickListener(RevealActivity.this);
        btn2.setOnClickListener(RevealActivity.this);
    }

    @Override
    public void onClick(View v) {
        final int color = getColor(v);
        final Point p = getLocationInView(revealColorView, v);

        if (vSel == v) {
            revealColorView.hide(p.x, p.y, backColor, 0, 300, null);
            vSel = null;
        } else {
            revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
            vSel = v;
        }
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private int getColor(View view) {
        return Color.parseColor((String) view.getTag());
    }
}
