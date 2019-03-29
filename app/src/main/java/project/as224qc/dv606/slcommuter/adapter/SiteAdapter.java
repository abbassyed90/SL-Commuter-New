package project.as224qc.dv606.slcommuter.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.interfaces.OnClickSiteListener;
import project.as224qc.dv606.slcommuter.model.Site;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {

    private final List<Site> sites = new ArrayList<>();

    private OnClickSiteListener onClickSiteListener;

    @Override
    public SiteViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new SiteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false));
    }

    @Override
    public void onBindViewHolder(SiteViewHolder holder, int position) {
        holder.bind(this.sites.get(position));
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    public void addSites(List<Site> sites){
        this.sites.clear();
        this.sites.addAll(sites);
        notifyDataSetChanged();
    }

    public Site getSite(int index){
        return new Site(sites.get(index));
    }

    public void setOnClickSiteListener(OnClickSiteListener onClickSiteListener) {
        this.onClickSiteListener = onClickSiteListener;
    }

    class SiteViewHolder extends RecyclerView.ViewHolder {

        private TextView stationTextView;
        private TextView stationNameTextView;

        SiteViewHolder(View itemView) {
            super(itemView);
            stationTextView = itemView.findViewById(R.id.siteTextView);
            stationNameTextView = itemView.findViewById(R.id.siteNameTextView);

            itemView.setOnClickListener(new OnItemClickListener<SiteViewHolder>(this) {
                @Override
                public void onClickItem(int position, SiteViewHolder viewHolder) {
                    if(onClickSiteListener != null){
                        onClickSiteListener.onClickSite(getSite(position));
                    }
                }
            });
        }

        public void bind(Site site){
            String stationName = site.getName();
            if (stationName.contains("(")) {
                String secondaryInformation = null;
                secondaryInformation = stationName.substring(stationName.indexOf("(")).trim();
                stationName = stationName.substring(0, stationName.indexOf("(")).trim();

                stationTextView.setText(createSpannableString(itemView.getContext(), stationName, secondaryInformation));
            } else {
                stationTextView.setText(site.getName().trim());
            }

            stationNameTextView.setText(String.valueOf(site.getName().charAt(0)));
        }

        private CharSequence createSpannableString(Context context, String primaryInformation, String secondaryInformation) {
            SpannableString primarySpannable = new SpannableString(primaryInformation);
            SpannableString secondarySpannable = new SpannableString(secondaryInformation);

            secondarySpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey)), 0, secondarySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return TextUtils.concat(primarySpannable, "\n", secondarySpannable);
        }
    }

}