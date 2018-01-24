package com.deluxe.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.deluxe.testapp.connect.ConnectFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements Router, FragmentManager.OnBackStackChangedListener{

    private Unbinder mUnbinder;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        goTo(getInitialFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void goTo(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void replace(BaseFragment fragment) {
        getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceAllWith(List<BaseFragment> fragments) {
        if (fragments.isEmpty()) {
            return;
        }

        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        for (BaseFragment fragment: fragments) {
            goTo(fragment);
        }
    }

    @Override
    public void reset(BaseFragment fragment) {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(fragment);
        replaceAllWith(fragments);
    }

    @Override
    public void goBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onBackStackChanged() {
        int backStackSize = getSupportFragmentManager().getBackStackEntryCount();
        getSupportActionBar().setDisplayHomeAsUpEnabled(backStackSize > 1);
    }

    private BaseFragment getInitialFragment() {
        return new ConnectFragment();
    }
}
