package kirdmt.com.docsworkersvr.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.sql.Timestamp;
import java.util.List;

import kirdmt.com.docsworkersvr.Data.ExcelData;

public class DbWorker {

    private static final String DBWORKER_TAG = "DbWorkerTAG";

    Context context;

    public DbWorker(Context context) {

        this.context = context;
    }

    private DatabaseHelper db;

    public void DbInit() {

        db = Room.databaseBuilder(context, DatabaseHelper.class, "DocsWorkerSvrDB")
                .allowMainThreadQueries()
                // .fallbackToDestructiveMigration() //new code
                .build();
    }

    public List<ExcelData> getSqlRows() {

        Log.d(DBWORKER_TAG, "Rows in database : " + db.getDataDao().count());

        if (db.getDataDao().count() > 0) {

            List<ExcelData> excelDataList = db.getDataDao().getAll();

            db.getDataDao().nukeTable();

            return excelDataList;
        } else {
            return null;
        }
    }

    public void addSqlData(ExcelData excelData) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        excelData.setId(timestamp.getTime());

        //why?
        if (excelData.getNotes() == null) {
            excelData.setNotes("нет данных");
        }

        db.getDataDao().insert(excelData);

    }

    public int getCountSqlRows() {
        return db.getDataDao().count();
    }




}
