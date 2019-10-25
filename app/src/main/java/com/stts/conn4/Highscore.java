package com.stts.conn4;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Highscore extends AppCompatActivity {
    static RecyclerView rv;
    static RVadapter ra;
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        rv = findViewById(R.id.rv);
        ra = new RVadapter(new RVclick() {
            @Override
            public void Click(View v, int p) {
                final int position = p;
                PopupMenu pop = new PopupMenu(Highscore.this, v.findViewById(R.id.score));
                pop.getMenuInflater().inflate(R.menu.rvmenu, pop.getMenu());
                //pop.setGravity(Gravity.RIGHT);
                pop.show();
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.del){
                            new AsyncTask<Void,Void,Long>(){

                                @Override
                                protected Long doInBackground(Void... voids) {
                                    long status = db.DAOrecord().deleteRecord(RVadapter.listscore.get(position));
                                    RVadapter.listscore.remove(position);
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Long aLong) {
                                    Highscore.ra.notifyDataSetChanged();
                                }
                            }.execute();
                        }
                        return true;
                    }
                });
            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(Highscore.this);
        rv.setLayoutManager(lm);
        rv.setAdapter(ra);

        db = Room.databaseBuilder(getApplicationContext(), DB.class, "recorddb").build();

        //stub();

        ((TextView) findViewById(R.id.textView3)).setText("Hard");
        getDataDif(3);

        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        hardclick();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.d1){
            easyclick();
        }
        else if(item.getItemId()==R.id.d2){
            medclick();
        }
        else if(item.getItemId()==R.id.d3){
            hardclick();
        }
        return true;
    }


    public void resetMenuColor(){
        findViewById(R.id.d1).setBackgroundColor(Color.parseColor("#000000"));
        ((TextView) findViewById(R.id.d1)).setTextColor(Color.parseColor("#FFFFFF"));
        findViewById(R.id.d2).setBackgroundColor(Color.parseColor("#000000"));
        ((TextView) findViewById(R.id.d2)).setTextColor(Color.parseColor("#FFFFFF"));
        findViewById(R.id.d3).setBackgroundColor(Color.parseColor("#000000"));
        ((TextView) findViewById(R.id.d3)).setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void getDataDif(int level){
        final int dif = level;
        new AsyncTask<Void, Void, ArrayList<Record>>(){

            @Override
            protected ArrayList<Record> doInBackground(Void... voids) {
                return new ArrayList<Record>(Arrays.asList(db.DAOrecord().selectRecordDif(dif)));
            }

            @Override
            protected void onPostExecute(ArrayList<Record> records) {
                RVadapter.listscore = records;
                Collections.sort(RVadapter.listscore);
                int i = 0;
                for (Record r: RVadapter.listscore) {
                    r.rank = i++;
                }
                Highscore.ra.notifyDataSetChanged();
            }
        }.execute();
    }

    public void easyclick(){
        ((TextView) findViewById(R.id.textView3)).setText("Easy");

        View view = findViewById(R.id.d1);
        if (view != null && view instanceof TextView) {
            resetMenuColor();
            ((TextView)view).setBackgroundColor(Color.parseColor("#FFC0CB"));
            ((TextView) view).setTextColor(Color.parseColor("#8B0000"));
        }
        getDataDif(1);
    }

    public void medclick(){
        ((TextView) findViewById(R.id.textView3)).setText("Medium");

        View view = findViewById(R.id.d2);
        if (view != null && view instanceof TextView) {
            resetMenuColor();
            ((TextView)view).setBackgroundColor(Color.parseColor("#FFC0CB"));
            ((TextView) view).setTextColor(Color.parseColor("#8B0000"));
        }
        getDataDif(2);
    }

    public void hardclick(){
        ((TextView) findViewById(R.id.textView3)).setText("Hard");

        View view = findViewById(R.id.d3);
        if (view != null && view instanceof TextView) {
            resetMenuColor();
            ((TextView)view).setBackgroundColor(Color.parseColor("#FFC0CB"));
            ((TextView) view).setTextColor(Color.parseColor("#8B0000"));
        }
        getDataDif(3);
    }

    public void stub(){
//        RVadapter.listscore.clear();
//
//        new AsyncTask<Void, Void, Long>(){
//
//            @Override
//            protected Long doInBackground(Void... voids) {
//                long status = db.DAOrecord().insertRecord(new Record("1Alpha", 7, 1));
//                status = db.DAOrecord().insertRecord(new Record("2Beta", 13, 1));
//                status = db.DAOrecord().insertRecord(new Record("3Charlie", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("4Delta", 4, 1));
//                status = db.DAOrecord().insertRecord(new Record("5Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("6Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("7Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("8Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("9Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("10Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("11Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("12Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("13Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("14Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("15Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("16Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("17Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("18Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("19Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("20Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("21Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("22Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("23Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("24Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("25Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("26Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("27Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("28Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("29Echo", 5, 1));
//                status = db.DAOrecord().insertRecord(new Record("30Echo", 5, 1));
//                return status;
//            }
//        }.execute();
//        RVadapter.listscore.clear();
//        RVadapter.listscore.add(new Record("1", 2, 0));
//        RVadapter.listscore.add(new Record("2", 2, 0));
//        RVadapter.listscore.add(new Record("3", 3, 0));
//        RVadapter.listscore.add(new Record("4", 1, 0));
//        RVadapter.listscore.add(new Record("5", 2, 0));
//        RVadapter.listscore.add(new Record("6", 5, 0));
//        RVadapter.listscore.add(new Record("7", 1, 2));
//        RVadapter.listscore.add(new Record("8", 1, 0));
//        RVadapter.listscore.add(new Record("9", 1, 0));
//        RVadapter.listscore.add(new Record("10", 1, 0));
//        RVadapter.listscore.add(new Record("11", 1, 0));
//        RVadapter.listscore.add(new Record("12", 4, 2));
//        RVadapter.listscore.add(new Record("13", 1, 0));
//        RVadapter.listscore.add(new Record("14", 1, 0));
//        RVadapter.listscore.add(new Record("15", 1, 0));
//        RVadapter.listscore.add(new Record("16", 1, 0));
//        RVadapter.listscore.add(new Record("17", 1, 0));
//        RVadapter.listscore.add(new Record("18", 1, 1));
//        RVadapter.listscore.add(new Record("19", 1, 0));
//        RVadapter.listscore.add(new Record("20", 1, 0));
//        RVadapter.listscore.add(new Record("21", 1, 0));
//        RVadapter.listscore.add(new Record("22", 1, 0));
//        RVadapter.listscore.add(new Record("23", 1, 0));
//        RVadapter.listscore.add(new Record("24", 1, 0));
//        RVadapter.listscore.add(new Record("25", 1, 0));
//        RVadapter.listscore.add(new Record("26", 0, 0));
//        Collections.sort(RVadapter.listscore);
    }
}
