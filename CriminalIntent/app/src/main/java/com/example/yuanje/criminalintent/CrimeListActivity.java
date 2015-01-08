package com.example.yuanje.criminalintent;

import android.app.Fragment;

/**
 * Created by yuanje on 01/08/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
