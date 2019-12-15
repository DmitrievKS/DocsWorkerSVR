package kirdmt.com.docsworkersvr.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kirdmt.com.docsworkersvr.Data.ExcelData;

@Dao
public interface NeedDao {

    //TODO: изменить логику взаимодействия с БД. Получать запись и удалять ее только при условии получания положительного ответа от сервера на то, что данные были добавлены.

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
