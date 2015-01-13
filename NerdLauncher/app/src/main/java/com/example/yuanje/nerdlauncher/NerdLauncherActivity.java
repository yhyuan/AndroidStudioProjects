package com.example.yuanje.nerdlauncher;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class NerdLauncherActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
