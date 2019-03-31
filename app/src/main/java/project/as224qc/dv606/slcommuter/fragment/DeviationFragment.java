package project.as224qc.dv606.slcommuter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.List;

import project.as224qc.dv606.slcommuter.EventBus;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.adapter.DeviationAdapter;
import project.as224qc.dv606.slcommuter.contract.DeviationContract;
import project.as224qc.dv606.slcommuter.event.DeviationEvent;
import project.as224qc.dv606.slcommuter.interfaces.OnClickDeviationListener;
import project.as224qc.dv606.slcommuter.model.Deviation;
import project.as224qc.dv606.slcommuter.presenter.DeviationPresenter;
import project.as224qc.dv606.slcommuter.util.IntentHelper;

/**
 * A fragment that shows all current deviations
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class DeviationFragment extends Fragment implements DeviationContract.View,OnClickDeviationListener {

    private DeviationPresenter presenter;
    private DeviationAdapter adapter;

    private View progressBar;
    private View networkErrorView;

    public static DeviationFragment getInstance() {
        return new DeviationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DeviationAdapter();
        presenter = new DeviationPresenter(this);
        presenter.fetchDeviations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);
        adapter.setOnClickDeviationListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
        adapter.setOnClickDeviationListener(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deviations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        networkErrorView = view.findViewById(R.id.networkErrorView);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void deviationEvent(DeviationEvent event) {
        presenter.updateData(event);
    }

    @Override
    public void onClickDeviation(Deviation deviation) {
        IntentHelper.startDeviationDetailActivity(getActivity(),deviation);
    }

    @Override
    public void displayProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateAdapter(List<Deviation> deviations) {
        adapter.addDeviations(deviations);
    }

    @Override
    public void displayNetworkError(boolean show) {
        networkErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
