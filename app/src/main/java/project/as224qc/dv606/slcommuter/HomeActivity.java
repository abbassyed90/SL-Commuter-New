package project.as224qc.dv606.slcommuter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import project.as224qc.dv606.slcommuter.fragment.DeviationFragment;
import project.as224qc.dv606.slcommuter.fragment.RealTimeFragment;
import project.as224qc.dv606.slcommuter.fragment.TravelFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private List<Fragment> fragments;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initStatusBar();

        fragments = new ArrayList<>();
        fragments.add(TravelFragment.getInstance());
        fragments.add(RealTimeFragment.getInstance());
        fragments.add(DeviationFragment.getInstance());

        bottomNavigationView =
                findViewById(R.id.bottomNavigationView);

        // show travel fragment as first screen
        showFragment(fragments.get(0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.trip) {
            showFragment(fragments.get(0)); return true;
        } else if (item.getItemId() == R.id.realtime) {
            showFragment(fragments.get(1)); return true;
        } else if (item.getItemId() == R.id.deviations) {
            showFragment(fragments.get(2)); return true;
        }
        return false;
    }

    public void initStatusBar(){
        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        window.setNavigationBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
    }

    /**
     * Change fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, fragment)
                .commit();
    }
}
