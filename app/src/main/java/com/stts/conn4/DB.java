package com.stts.conn4;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class DB extends RoomDatabase{
    public abstract DAOrecord DAOrecord();
}
