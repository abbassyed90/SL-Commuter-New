package project.as224qc.dv606.slcommuter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.interfaces.OnClickDeviationListener;
import project.as224qc.dv606.slcommuter.model.Deviation;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class DeviationAdapter extends RecyclerView.Adapter<DeviationAdapter.ViewHolder> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<Deviation> deviations = new ArrayList<>();

    private OnClickDeviationListener onClickDeviationListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deviation, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(deviations.get(position));
    }

    @Override
    public int getItemCount() {
        return deviations.size();
    }

    public void addDeviations(List<Deviation> deviations){
        this.deviations.clear();
        this.deviations.addAll(deviations);
        notifyDataSetChanged();
    }

    public Deviation getDeviation(int index){
        return new Deviation(deviations.get(index));
    }

    public void setOnClickDeviationListener(OnClickDeviationListener onClickDeviationListener) {
        this.onClickDeviationListener = onClickDeviationListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView headerTextView;
        private TextView scopeTextView;
        private TextView detailsTextView;
        private TextView createdTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
            scopeTextView = itemView.findViewById(R.id.scopeTextView);
            detailsTextView = itemView.findViewById(R.id.detailTextView);
            createdTextView = itemView.findViewById(R.id.createdTextView);

            itemView.setOnClickListener(new OnItemClickListener<ViewHolder>(this) {
                @Override
                public void onClickItem(int position, ViewHolder viewHolder) {
                    if(onClickDeviationListener != null){
                        onClickDeviationListener.onClickDeviation(getDeviation(position));
                    }
                }
            });
        }

        private void bind(Deviation deviation) {
            headerTextView.setText(deviation.getHeader());
            scopeTextView.setText(deviation.getScope());
            detailsTextView.setText(deviation.getDetails());
            createdTextView.setText(dateFormat.format(new Date(deviation.getCreated())));
        }
    }
}