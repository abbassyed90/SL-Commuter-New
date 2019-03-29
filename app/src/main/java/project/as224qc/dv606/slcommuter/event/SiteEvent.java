package project.as224qc.dv606.slcommuter.event;

import java.util.List;

import project.as224qc.dv606.slcommuter.model.Site;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class SiteEvent extends BaseEvent {

    private List<Site> sites;

    public SiteEvent() {
        setSuccess(false);
    }

    public SiteEvent(List<Site> sites) {
        setSuccess(true);
        this.sites = sites;
    }

    public List<Site> getSites() {
        return sites;
    }
}
