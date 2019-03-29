package project.as224qc.dv606.slcommuter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import project.as224qc.dv606.slcommuter.adapter.SiteAdapter;
import project.as224qc.dv606.slcommuter.contract.SearchSiteContract;
import project.as224qc.dv606.slcommuter.event.SiteEvent;
import project.as224qc.dv606.slcommuter.interfaces.OnClickSiteListener;
import project.as224qc.dv606.slcommuter.model.Site;
import project.as224qc.dv606.slcommuter.presenter.SearchStationPresenter;
import project.as224qc.dv606.slcommuter.util.EmptyStateHelper;
import project.as224qc.dv606.slcommuter.util.TextWatcherAdapter;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class StationSearchActivity extends BaseActivity implements OnClickSiteListener, SearchSiteContract.View {

    public static final String SELECTED_STATION = "station.selected";
    public static final String VIEW_ID = "view.id";

    private int viewId;

    private SiteAdapter adapter;

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private View progressBar;
    private View emptyStateView;

    private SearchStationPresenter presenter;

    private TextWatcherAdapter stationSearchTextWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable editable) {
            super.afterTextChanged(editable);
            presenter.onSearchSite(editable.toString());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);

        // init adapter
        adapter = new SiteAdapter();

        // get view id of view that was calling this activity
        viewId = getIntent().getIntExtra(VIEW_ID, 0);

        searchEditText = (EditText) findViewById(R.id.searchSiteEditText);
        progressBar = findViewById(R.id.progressBar);
        emptyStateView = findViewById(R.id.emptyStateView);

        initRecyclerView();

        presenter = new SearchStationPresenter(this);
        presenter.fetchPreviousSearches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        adapter.setOnClickSiteListener(this);
        searchEditText.addTextChangedListener(stationSearchTextWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);

        adapter.setOnClickSiteListener(null);
        searchEditText.removeTextChangedListener(stationSearchTextWatcher);
    }

    @Subscribe
    public void siteEvent(SiteEvent event) {
        if (event.isSuccess()) {
            presenter.updateData(event.getSites());
        }
    }

    private void returnResultOk(Site site) {
        Intent intent = new Intent();
        intent.putExtra(VIEW_ID, viewId);
        intent.putExtra(SELECTED_STATION, site);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickSite(Site site) {
        presenter.saveSearchedSite(site);
        returnResultOk(site);
    }

    @Override
    public void updateAdapter(List<Site> sites) {
        adapter.addSites(sites);
    }

    @Override
    public void displayProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void displayEmptyStateView(boolean show) {
        emptyStateView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void displayRecyclerView(boolean show) {
        recyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
