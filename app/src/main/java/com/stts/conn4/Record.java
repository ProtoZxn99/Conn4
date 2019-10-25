package com.stts.conn4;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "record")
public class Record implements Comparable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "record_name")
    public String name;

    @ColumnInfo(name = "record_score")
    public int score;

    @ColumnInfo(name = "record_dif")
    public int dif;

    @ColumnInfo(name = "record_rank")
    public int rank;

    public Record(String name, int score, int dif){
        this.name = name;
        this.score = score;
        this.dif = dif;
        this.rank = 0;
    }

    @Override
    public int compareTo(Object o) {
        return ((Record)o).score - score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getDif() {
        return dif;
    }

}
