package com.stts.conn4;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface DAOrecord {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecord(Record r);

    @Update
    int updateRecord(Record r);

    @Delete
    int deleteRecord(Record r);

    @Query("SELECT * FROM record")
    Record [] selectAllRecords();

    @Query("SELECT * FROM record where record_dif=:dif")
    Record [] selectRecordDif(int dif);
}
