package project.as224qc.dv606.slcommuter.model.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.Deviation;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLiteDeviation extends SQLiteOpenHelper {

    private String CREATE_TABLE_QUERY = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY, %s TEXT,%s TEXT,%s INTEGER)", DeviationEntry.TABLE_NAME, DeviationEntry.COLUMN_ID, DeviationEntry.COLUMN_DETAILS, DeviationEntry.COLUMN_SCOPE, DeviationEntry.COLUMN_TO_DATE);

    public SQLiteDeviation(Context context) {
        super(context, Constants.Database.DB_NAME, null, Constants.Database.DB_VERSION);
    }


    public SQLiteDeviation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Insert deviation
     *
     * @param deviation
     */
    public void insertDeviation(Deviation deviation) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DeviationEntry.COLUMN_ID, deviation.getId());
            values.put(DeviationEntry.COLUMN_DETAILS, deviation.getDetails());
            values.put(DeviationEntry.COLUMN_SCOPE, deviation.getScope());
            values.put(DeviationEntry.COLUMN_TO_DATE, deviation.getToDate());

            db.insertOrThrow(DeviationEntry.TABLE_NAME, null, values);

        } catch (SQLiteException e) {
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Delete a deviation
     *
     * @param deviation
     */
    public void deleteDeviation(Deviation deviation) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DeviationEntry.TABLE_NAME, DeviationEntry.COLUMN_ID + "=?", new String[]{String.valueOf(deviation.getId())});
    }

    /**
     * Get all deviations
     *
     * @return -a list of deviations
     */
    public ArrayList<Deviation> getDeviations() {
        ArrayList<Deviation> deviations = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", DeviationEntry.TABLE_NAME);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            deviations.add(parseDeviation(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return deviations;
    }

    /**
     * Clear table
     */
    public void clearAll() {
        getWritableDatabase().delete(DeviationEntry.TABLE_NAME, null, null);
    }

    /**
     * Helper method to parse a row from table
     *
     * @param cursor
     * @return
     */
    private Deviation parseDeviation(Cursor cursor) {
        Deviation deviation = new Deviation();

        deviation.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DeviationEntry.COLUMN_ID)));
        deviation.setDetails(cursor.getString(cursor.getColumnIndexOrThrow(DeviationEntry.COLUMN_DETAILS)));
        deviation.setScope(cursor.getString(cursor.getColumnIndexOrThrow(DeviationEntry.COLUMN_SCOPE)));
        deviation.setToDate(cursor.getLong(cursor.getColumnIndexOrThrow(DeviationEntry.COLUMN_TO_DATE)));

        return deviation;
    }

    private static class DeviationEntry {
        private static final String TABLE_NAME = "deviations";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_DETAILS = "details";
        private static final String COLUMN_SCOPE = "scope";
        private static final String COLUMN_TO_DATE = "toDate";
    }
}
