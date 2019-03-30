package project.as224qc.dv606.slcommuter.contract;

import java.util.List;

import project.as224qc.dv606.slcommuter.event.DeviationEvent;
import project.as224qc.dv606.slcommuter.model.Deviation;

public interface DeviationContract {

    interface View{

        void displayProgressBar(boolean show);

        void updateAdapter(List<Deviation> deviations);

        void displayNetworkError(boolean show);

    }

    interface Presenter{

        void fetchDeviations();

        void updateData(DeviationEvent event);

    }

}
