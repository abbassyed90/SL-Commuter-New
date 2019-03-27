package project.as224qc.dv606.slcommuter.util;

/**
 * Constants class
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.util
 */
public class Constants {

    /**
     * Helper constants for recyclerview
     * so that headers can be used
     */
    public static class RecyclerViewHelper {
        public static final int HEADER = 0;
        public static final int ITEM = 1;
    }

    /**
     * Sl traffic types
     */
    public static class TravelTypes {
        public static final String BUS = "Buss";
        public static final String SUBWAY = "Tunnelbanans";
        public static final String TRAIN = "Pendeltåg";
    }

    /**
     * Api urls
     */
    public static class API {
        public static final String BASE_URL = "http://api.sl.se/";

        public static final String URL_DEVIATIONS = "api2/deviations.json?key=%s";
        public static final String URL_STATIONS = "api2/typeahead.json?key=%s&searchstring=%s";
        public static final String URL_REAL_TIME = "api2/realtimedepartures.json?key=%s&siteid=%s&timewindow=%s";
        public static final String URL_TRIP = "api2/TravelplannerV2/trip.json?key=%s&originId=%s&destId=%s";
    }

    /**
     * Sl api keys
     */
    public static class API_KEYS {
        public static final String DEVIATION_API_KEY = "af69da0f89304a53864afc4a3da02bfd";
        public static final String TRAVEL_PLANNER_API_KEY = "82e0bba23b0148968d6ab8fef0136df6";
        public static final String STATION_LOOK_UP_API_KEY = "b84130cf226e479ca8965df47e908423";
        public static final String REAL_TIME_API_KEY = "2403b5e961894eebb4ce37da067f278e";
    }

    /**
     * Database constants
     */
    public static class Database {
        public static final String DB_NAME = "slcommuter.db";
        public static final int DB_VERSION = 1;
    }

    /**
     * Request codes for activities
     */
    public static class RequestCode {
        public static final int STATION_SEARCH = 0;
    }

}
