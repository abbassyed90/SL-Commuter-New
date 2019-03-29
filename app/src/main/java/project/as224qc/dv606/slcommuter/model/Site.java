package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class Site implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Site> CREATOR = new Parcelable.Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }

        @Override
        public Site[] newArray(int size) {
            return new Site[size];
        }
    };
    @SerializedName("Name")
    private String name;
    @SerializedName("SiteId")
    private int siteId;

    public Site() {}

    public Site(Site site) {
        this.setName(site.getName());
        this.setSiteId(site.getSiteId());
    }

    public Site(String name, int siteId) {
        this.setName(name);
        this.setSiteId(siteId);
    }

    protected Site(Parcel in) {
        name = in.readString();
        siteId = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(siteId);
    }

    public Site copy() {
        Site site = new Site();

        site.setName(name);
        site.setSiteId(siteId);

        return site;
    }
}
