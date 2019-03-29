package project.as224qc.dv606.slcommuter.model;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class PreviousTrip extends Trip {

    private Site origin;
    private Site destination;

    public PreviousTrip(Site origin, Site destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Site getOrigin() {
        return origin;
    }

    public void setOrigin(Site origin) {
        this.origin = origin;
    }

    public Site getDestination() {
        return destination;
    }

    public void setDestination(Site destination) {
        this.destination = destination;
    }
}
