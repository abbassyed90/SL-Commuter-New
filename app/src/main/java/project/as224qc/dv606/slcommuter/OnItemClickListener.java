package project.as224qc.dv606.slcommuter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Helper class that is used in conjunction with
 * recyclerview adapters that adds on click events
 * to nested views
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public abstract class OnItemClickListener<T extends RecyclerView.ViewHolder> implements View.OnClickListener {

    private T viewHolder;

    public OnItemClickListener(T viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            onClickItem(adapterPosition, viewHolder);
        }
    }

    public abstract void onClickItem(int position, T viewHolder);

}
