package kirdmt.com.docsworkersvr;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NeedDao {

    @Query("SELECT count(*) FROM ExcelData")
    int count();

    @Query("SELECT * FROM ExcelData")
        //necessary realize with added == false. //sqlite dont have common boolean type
    List<ExcelData> getAll();

    @Insert
        //necessary realize
    void insert(ExcelData ExcelData);

    @Update
        //necessary realize
    void update(ExcelData ExcelData);

    @Delete
        //necessary realize
    void delete(ExcelData ExcelData);

    @Query("DELETE FROM ExcelData")
    public void nukeTable();

}
