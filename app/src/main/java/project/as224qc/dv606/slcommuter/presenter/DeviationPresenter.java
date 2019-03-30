package project.as224qc.dv606.slcommuter.presenter;

import project.as224qc.dv606.slcommuter.ApiService;
import project.as224qc.dv606.slcommuter.contract.DeviationContract;
import project.as224qc.dv606.slcommuter.event.DeviationEvent;

public class DeviationPresenter implements DeviationContract.Presenter {

    private DeviationContract.View view;

    public DeviationPresenter(DeviationContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchDeviations() {
        ApiService.getInstance().getDeviations();
        view.displayProgressBar(true);
    }

    @Override
    public void updateData(DeviationEvent event) {
        view.displayProgressBar(false);

        if (event.isSuccess()) {
            view.updateAdapter(event.getDeviations());
        } else {
            view.displayNetworkError(true);
        }
    }

}
