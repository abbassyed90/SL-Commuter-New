package project.as224qc.dv606.slcommuter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import project.as224qc.dv606.slcommuter.model.Deviation;

/**
 * Activity that shows a deviation in detail
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class DeviationDetailActivity extends BaseActivity {

    public static final String BUNDLE_DATA_DEVIATION = "bundle_data.deviation";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviation_detail);

        // get deviation of site
        Deviation deviation = getIntent().getParcelableExtra(BUNDLE_DATA_DEVIATION);

        // init views
        TextView headerTextView = (TextView) findViewById(R.id.headerTextView);
        TextView scopeTextView = (TextView) findViewById(R.id.scopeTextView);
        TextView detailsTextView = (TextView) findViewById(R.id.detailTextView);
        TextView createdTextView = (TextView) findViewById(R.id.createdTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // set values
        headerTextView.setText(deviation.getHeader());
        scopeTextView.setText(deviation.getScope());
        detailsTextView.setText(deviation.getDetails());
        createdTextView.setText(dateFormat.format(new Date(deviation.getCreated())));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}