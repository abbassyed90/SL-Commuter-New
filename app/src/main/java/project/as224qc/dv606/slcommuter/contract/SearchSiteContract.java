package project.as224qc.dv606.slcommuter.contract;

import java.util.List;

import project.as224qc.dv606.slcommuter.model.Site;

public interface SearchSiteContract {

    interface View {

        void updateAdapter(List<Site> sites);

        void displayProgressBar(boolean show);

        void displayEmptyStateView(boolean show);

        void displayRecyclerView(boolean show);

    }

    interface Presenter{

        void onSearchSite(String query);

        void fetchPreviousSearches();

        void saveSearchedSite(Site site);

    }

}
