package kirdmt.com.docsworkersvr;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.*;

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
