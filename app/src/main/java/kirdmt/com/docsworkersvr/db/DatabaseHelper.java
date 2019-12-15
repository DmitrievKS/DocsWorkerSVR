package kirdmt.com.docsworkersvr.db;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.*;

import kirdmt.com.docsworkersvr.Data.ExcelData;

@Database(entities = { ExcelData.class }, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract NeedDao getDataDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
