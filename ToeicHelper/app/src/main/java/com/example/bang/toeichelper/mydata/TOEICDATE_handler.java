package com.example.bang.toeichelper.mydata;

import com.example.bang.toeichelper.mydata.TOEICDATE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by BANG on 2015-01-29.
 */
public class TOEICDATE_handler implements Serializable{
    private ArrayList<TOEICDATE> arrTOEICDATE;
    private TOEICDATE nearestToeicDate;

    public TOEICDATE_handler(){
        arrTOEICDATE = new ArrayList<TOEICDATE>();
        nearestToeicDate = null;
    }

    public TOEICDATE_handler(ArrayList<TOEICDATE> arrTOEICDATE) {
        this.arrTOEICDATE = arrTOEICDATE;
    }

    public void addToeicDate(TOEICDATE data){
        arrTOEICDATE.add(data);
    }

    public TOEICDATE getToeicDateByIndex(int index){
        return arrTOEICDATE.get(index);
    }

    public TOEICDATE getToeicDateByKey(int key){
        TOEICDATE ret = null;
        TOEICDATE t;
        Iterator<TOEICDATE> iterator = arrTOEICDATE.iterator();
        while(iterator.hasNext()){
            t = iterator.next();

            if(t.getKey() == key){
                ret = t;
            }
        }

        return ret;
    }

    public void setNearestToeicDate(TOEICDATE nearestToeicDate){
        this.nearestToeicDate = nearestToeicDate;
    }

    public TOEICDATE getNearestToeicDate() {
        return nearestToeicDate;
    }

    public int getSize(){return arrTOEICDATE.size();}
}
