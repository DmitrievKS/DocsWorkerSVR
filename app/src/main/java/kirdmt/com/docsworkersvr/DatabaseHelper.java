package kirdmt.com.docsworkersvr;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.*;

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
