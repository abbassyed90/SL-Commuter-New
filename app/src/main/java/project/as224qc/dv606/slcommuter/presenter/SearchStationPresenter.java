package project.as224qc.dv606.slcommuter.presenter;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import project.as224qc.dv606.slcommuter.ApiService;
import project.as224qc.dv606.slcommuter.SQLiteContext;
import project.as224qc.dv606.slcommuter.contract.SearchSiteContract;
import project.as224qc.dv606.slcommuter.model.Site;

public class SearchStationPresenter implements SearchSiteContract.Presenter {

    private SearchSiteContract.View view;

    public SearchStationPresenter(SearchSiteContract.View view) {
        this.view = view;
    }

    @Override
    public void onSearchSite(String query) {
        if(query.isEmpty()){
            view.displayEmptyStateView(true);
            view.displayProgressBar(false);
            view.displayRecyclerView(false);
            return;
        }

        view.displayProgressBar(true);
        view.displayEmptyStateView(false);
        view.displayRecyclerView(false);
        ApiService.getInstance().getStationId(query);
    }

    @Override
    public void fetchPreviousSearches() {
        new PreviousSiteFetchAsyncTask(view).execute();
    }

    @Override
    public void saveSearchedSite(Site site) {
        new SaveSiteAsyncTask().execute();
    }

    public void updateData(List<Site> list){
        view.displayRecyclerView(true);
        view.displayProgressBar(false);
        view.displayEmptyStateView(false);
        view.updateAdapter(list);
    }

    /**
     * Async task used to save sites that the user has selected
     */
    private class SaveSiteAsyncTask extends AsyncTask<Site, Void, Void> {

        @Override
        protected Void doInBackground(Site... params) {
            SQLiteContext.getInstance().getController().insertStation(params[0]);
            return null;
        }
    }

    /**
     * Async task used to query sqlite for previous selected sites
     */
    private class PreviousSiteFetchAsyncTask extends AsyncTask<Void, Void, List<Site>> {

        private WeakReference<SearchSiteContract.View> viewWeakReference;

        public PreviousSiteFetchAsyncTask(SearchSiteContract.View view) {
            this.viewWeakReference = new WeakReference<>(view);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(viewWeakReference.get() != null){
                viewWeakReference.get().displayProgressBar(true);
            }
        }

        @Override
        protected List<Site> doInBackground(Void... params) {
            return SQLiteContext.getInstance().getController().getStations();
        }

        @Override
        protected void onPostExecute(List<Site> sites) {
            super.onPostExecute(sites);
            if(viewWeakReference.get() != null){
                viewWeakReference.get().displayProgressBar(false);

                if (!sites.isEmpty()) {
                    viewWeakReference.get().updateAdapter(sites);
                    viewWeakReference.get().displayEmptyStateView(false);
                } else {
                    viewWeakReference.get().displayEmptyStateView(true);
                }
            }
        }
    }

}
