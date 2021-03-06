package project.as224qc.dv606.slcommuter.model.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.Site;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLiteStation extends SQLiteOpenHelper {

    String CREATE_TABLE_QUERY = String.format("CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY,%s INTEGER)", StationEntry.TABLE_NAME, StationEntry.COLUMN_NAME, StationEntry.COLUMN_SITE_ID);

    public SQLiteStation(Context context) {
        super(context, Constants.Database.DB_NAME, null, Constants.Database.DB_VERSION);
    }

    public SQLiteStation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
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
     * Insert a site into table
     *
     * @param site
     */
    public void insertStation(Site site) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(StationEntry.COLUMN_NAME, site.getName());
            values.put(StationEntry.COLUMN_SITE_ID, site.getSiteId());

            db.insertOrThrow(StationEntry.TABLE_NAME, null, values);

        } catch (SQLiteException e) {
            // Do nothing
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * get all sites saved sites
     *
     * @return
     */
    public ArrayList<Site> getStations() {
        ArrayList<Site> sites = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", StationEntry.TABLE_NAME);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sites.add(parseStation(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return sites;
    }

    /**
     * Clear table
     */
    public void clearAll() {
        getWritableDatabase().delete(StationEntry.TABLE_NAME, null, null);
    }

    /**
     * Helper method to parse a row from table
     *
     * @param cursor
     * @return
     */
    public Site parseStation(Cursor cursor) {
        Site site = new Site();

        site.setName(cursor.getString(cursor.getColumnIndex(StationEntry.COLUMN_NAME)));
        site.setSiteId(cursor.getInt(cursor.getColumnIndex(StationEntry.COLUMN_SITE_ID)));

        return site;
    }

    private static class StationEntry {
        private static final String TABLE_NAME = "station";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_SITE_ID = "station_id";
    }
}
