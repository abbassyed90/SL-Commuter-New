package project.as224qc.dv606.slcommuter.controller;

import android.content.Context;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.Deviation;
import project.as224qc.dv606.slcommuter.model.PreviousTrip;
import project.as224qc.dv606.slcommuter.model.Site;
import project.as224qc.dv606.slcommuter.model.Subscription;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteDeviation;
import project.as224qc.dv606.slcommuter.model.integration.SQLitePreviousTrip;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteStation;
import project.as224qc.dv606.slcommuter.model.integration.SQLiteSubscription;

/**
 * A controller to handle all sqlite database calls
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model.integration
 */
public class SQLiteController {

    private final SQLitePreviousTrip sqLitePreviousTrip;
    private SQLiteStation sqLiteStation;
    private SQLiteDeviation sqLiteDeviation;
    private SQLiteSubscription sqLiteSubscription;

    public SQLiteController(Context context) {
        sqLiteStation = new SQLiteStation(context);
        sqLitePreviousTrip = new SQLitePreviousTrip(context);
        sqLiteDeviation = new SQLiteDeviation(context);
        sqLiteSubscription = new SQLiteSubscription(context);
    }

    /**
     * Insert a site
     *
     * @param site
     */
    public void insertStation(Site site) {
        sqLiteStation.insertStation(site);
    }

    /**
     * Get all searched stations
     *
     * @return
     */
    public ArrayList<Site> getStations() {
        return sqLiteStation.getStations();
    }

    /**
     * Insert a trip
     *
     * @param origin
     * @param destination
     */
    public void insertTrip(Site origin, Site destination) {
        sqLitePreviousTrip.insertTrip(origin, destination);
    }

    /**
     * get all searched trips
     *
     * @return
     */
    public ArrayList<PreviousTrip> getTrips() {
        return sqLitePreviousTrip.getTrips();
    }

    /**
     * Insert subscription
     *
     * @param subscription
     */
    public void insertSubscription(Subscription subscription) {
        sqLiteSubscription.insertSubscription(subscription);
    }

    /**
     * Delete subscription
     *
     * @param subscription
     */
    public void deleteSubscription(Subscription subscription) {
        sqLiteSubscription.deleteSubscription(subscription);
    }

    /**
     * get all subscriptions
     *
     * @return
     */
    public ArrayList<Subscription> getSubscriptions() {
        return sqLiteSubscription.getSubscription();
    }

    /**
     * Insert a deviation
     *
     * @param deviation
     */
    public void insertDeviation(Deviation deviation) {
        sqLiteDeviation.insertDeviation(deviation);
    }

    /**
     * Delete a deviation
     *
     * @param deviation
     */
    public void deleteDeviation(Deviation deviation) {
        sqLiteDeviation.deleteDeviation(deviation);
    }

    /**
     * Get all deviations
     *
     * @return
     */
    public ArrayList<Deviation> getDeviations() {
        return sqLiteDeviation.getDeviations();
    }

    /**
     * Clear database
     */
    public void clearAll() {
        sqLiteDeviation.clearAll();
        sqLiteStation.clearAll();
        sqLiteSubscription.clearAll();
        sqLitePreviousTrip.clearAll();
    }
}
