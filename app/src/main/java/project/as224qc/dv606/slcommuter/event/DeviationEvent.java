package project.as224qc.dv606.slcommuter.event;

import java.util.List;

import project.as224qc.dv606.slcommuter.model.Deviation;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class DeviationEvent extends BaseEvent {

    private List<Deviation> deviations;

    public DeviationEvent() {
        setSuccess(false);
    }

    public DeviationEvent(List<Deviation> deviations) {
        setSuccess(true);
        this.deviations = deviations;
    }

    public List<Deviation> getDeviations() {
        return deviations;
    }
}
