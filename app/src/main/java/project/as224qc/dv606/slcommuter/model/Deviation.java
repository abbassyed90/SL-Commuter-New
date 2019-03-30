package project.as224qc.dv606.slcommuter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class Deviation implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Deviation> CREATOR = new Parcelable.Creator<Deviation>() {
        @Override
        public Deviation createFromParcel(Parcel in) {
            return new Deviation(in);
        }

        @Override
        public Deviation[] newArray(int size) {
            return new Deviation[size];
        }
    };
    @SerializedName("DevCaseGid")
    private long id;
    private long created;
    @SerializedName("Details")
    private String details;
    @SerializedName("Header")
    private String header;
    @SerializedName("Scope")
    private String scope;
    private long fromDate;
    private long toDate;

    public Deviation() { }

    public Deviation(Deviation deviation){
        this(deviation.getCreated(),deviation.getDetails(),deviation.getHeader(),deviation.getScope(),deviation.getFromDate(),deviation.getToDate());
    }

    public Deviation(long created, String details, String header, String scope, long fromDate, long toDate) {
        this.created = created;
        this.details = details;
        this.header = header;
        this.scope = scope;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    protected Deviation(Parcel in) {
        id = in.readLong();
        created = in.readLong();
        details = in.readString();
        header = in.readString();
        scope = in.readString();
        fromDate = in.readLong();
        toDate = in.readLong();
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(created);
        dest.writeString(details);
        dest.writeString(header);
        dest.writeString(scope);
        dest.writeLong(fromDate);
        dest.writeLong(toDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}