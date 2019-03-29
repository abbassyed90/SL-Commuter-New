package project.as224qc.dv606.slcommuter.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.ApiService;
import project.as224qc.dv606.slcommuter.EventBus;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.SQLiteContext;
import project.as224qc.dv606.slcommuter.StationSearchActivity;
import project.as224qc.dv606.slcommuter.adapter.TripAdapter;
import project.as224qc.dv606.slcommuter.event.OnItemClickEvent;
import project.as224qc.dv606.slcommuter.event.TripEvent;
import project.as224qc.dv606.slcommuter.model.PreviousTrip;
import project.as224qc.dv606.slcommuter.model.Site;
import project.as224qc.dv606.slcommuter.model.Trip;
import project.as224qc.dv606.slcommuter.model.TripDTO;
import project.as224qc.dv606.slcommuter.util.Constants;
import project.as224qc.dv606.slcommuter.util.EmptyStateHelper;
import project.as224qc.dv606.slcommuter.util.IntentHelper;
import project.as224qc.dv606.slcommuter.widget.DividerItemDecoration;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;


/**
 * Travelfragment let's the user
 * search for a trip
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class TravelFragment extends Fragment implements View.OnClickListener {

    private Site originSite = null;
    private Site destinationSite = null;

    private TripAdapter adapter;

    private ExtendedRecyclerView recyclerview;
    private EditText searchBarFrom;
    private EditText searchBarToo;
    private TextView searchButton;
    private EmptyStateHelper emptyStateHelper;
    private ImageView changeOrderButton;

    public static TravelFragment getInstance() {
        return new TravelFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TripAdapter();
        new FetchTripTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View emptyState = ((ViewStub) view.findViewById(R.id.viewStub)).inflate();

        initEmptyState(view);
        initRecyclerView(view, emptyState);

        // init views
        searchBarFrom = (EditText) view.findViewById(R.id.searchBarFrom);
        searchBarToo = (EditText) view.findViewById(R.id.searchBarToo);
        searchButton = (TextView) view.findViewById(R.id.searchButton);
        changeOrderButton = (ImageView) view.findViewById(R.id.changeOrderButton);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        emptyStateHelper = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);

        searchBarToo.setOnClickListener(null);
        searchBarFrom.setOnClickListener(null);
        searchButton.setOnClickListener(null);
        changeOrderButton.setOnClickListener(null);
        searchBarToo.setOnClickListener(null);
        searchBarFrom.setOnClickListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        changeOrderButton.setOnClickListener(this);
        searchBarFrom.setOnClickListener(this);
        searchBarToo.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        searchBarToo.setOnClickListener(this);
        searchBarFrom.setOnClickListener(this);
    }

    @Subscribe
    public void tripEvent(TripEvent tripEvent) {
        if (tripEvent.isSuccess()) {
            // add all trips received from server
            adapter.getTrips().addAll(tripEvent.getTrips());
            adapter.notifyDataSetChanged();
        } else {
            // show network error empty state
            emptyStateHelper.showNetworkErrorState(getActivity());
        }
    }

    @Subscribe
    public void onItemClickEvent(OnItemClickEvent event) {
        Trip trip = adapter.getTrips().get(event.getPosition());
        if (trip instanceof TripDTO) {
            IntentHelper.startTripDetailActivity(getActivity(), (TripDTO) trip, originSite, destinationSite);
        } else {
            PreviousTrip previousTrip = (PreviousTrip) trip;
            originSite = previousTrip.getOrigin();
            destinationSite = previousTrip.getDestination();
            searchBarFrom.setText(originSite.getName());
            searchBarToo.setText(destinationSite.getName());
            findTrips();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                findTrips();
                break;
            case R.id.changeOrderButton:
                changeOrder();
                break;
            case R.id.searchBarFrom:
                startSiteSearchActivity(v.getId());
                break;
            case R.id.searchBarToo:
                startSiteSearchActivity(v.getId());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.STATION_SEARCH && data != null) {
            int viewId = data.getIntExtra(StationSearchActivity.VIEW_ID, 0);
            Site selectedSite = data.getParcelableExtra(StationSearchActivity.SELECTED_STATION);
            switch (viewId) {
                case R.id.searchBarFrom:
                    originSite = selectedSite;
                    searchBarFrom.setText(selectedSite.getName());
                    break;
                case R.id.searchBarToo:
                    destinationSite = selectedSite;
                    searchBarToo.setText(selectedSite.getName());
                    break;
            }
        }
    }

    /**
     * Start activity for searching for a station
     *
     * @param viewId
     */
    private void startSiteSearchActivity(int viewId) {
        IntentHelper.startStationSearchActivity(this, viewId);
    }

    /**
     * init empty state
     *
     * @param view
     */
    private void initEmptyState(View view) {
        emptyStateHelper = new EmptyStateHelper(view);
        emptyStateHelper.setEmptyStateTitle(getString(R.string.empty_state_title_plantrip));
        emptyStateHelper.setEmptyStatePicture(R.drawable.ic_trip);
        emptyStateHelper.showEmptyStateScreen(getActivity(), false);
    }

    /**
     * Init recyclerview
     *
     * @param view
     * @param emptyState
     */
    private void initRecyclerView(View view, View emptyState) {
        recyclerview = (ExtendedRecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerview.setEmptyView(emptyState);
    }

    /**
     * Call server to find all trips
     */
    private void findTrips() {
        // show toast if no stations have been selected
        if (originSite == null || destinationSite == null) {
            Toast.makeText(getActivity(), getString(R.string.error_no_station_selected), Toast.LENGTH_SHORT).show();
            return;
        }

        if (originSite.getSiteId() == destinationSite.getSiteId()) {
            Toast.makeText(getActivity(), getString(R.string.error_same_station), Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService.getInstance().findTripNow(getActivity(), originSite, destinationSite);

        // show loading empty state
        adapter.getTrips().clear();
        adapter.notifyDataSetChanged();
        emptyStateHelper.showLoadingScreen(getActivity(), false);

        new SaveTripTask().execute(new PreviousTrip(originSite, destinationSite));
    }

    /**
     * Change the order of the trips
     * make from -> too
     * and too -> from
     */
    private void changeOrder() {
        // show toast if no stations have been selected
        if (originSite == null || destinationSite == null) {
            Toast.makeText(getActivity(), getString(R.string.error_no_station_selected), Toast.LENGTH_SHORT).show();
            return;
        }

        Site newOrigin = destinationSite.copy();
        Site newDestination = originSite.copy();

        originSite = newOrigin;
        destinationSite = newDestination;

        searchBarFrom.setText(originSite.getName());
        searchBarToo.setText(destinationSite.getName());
    }

    /**
     * Asynctask that will save the searched trip in sqlite
     */
    private class SaveTripTask extends AsyncTask<PreviousTrip, Void, Void> {

        @Override
        protected Void doInBackground(PreviousTrip... params) {
            PreviousTrip trip = params[0];
            SQLiteContext.getInstance().getController().insertTrip(trip.getOrigin(), trip.getDestination());
            return null;
        }
    }

    /**
     * Asynctask that will fetch all previous searched trips and
     * update adapter
     */
    private class FetchTripTask extends AsyncTask<Void, Void, ArrayList<PreviousTrip>> {

        @Override
        protected ArrayList<PreviousTrip> doInBackground(Void... params) {
            return SQLiteContext.getInstance().getController().getTrips();
        }

        @Override
        protected void onPostExecute(ArrayList<PreviousTrip> previousTrips) {
            super.onPostExecute(previousTrips);
            adapter.getTrips().addAll(previousTrips);
            adapter.notifyDataSetChanged();
        }
    }
}