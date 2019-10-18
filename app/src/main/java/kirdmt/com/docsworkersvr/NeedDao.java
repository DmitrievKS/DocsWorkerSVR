package kirdmt.com.docsworkersvr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
