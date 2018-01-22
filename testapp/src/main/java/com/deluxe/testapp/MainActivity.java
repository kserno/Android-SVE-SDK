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

        goTo(getInitialFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

        }
    }

    private BaseFragment getInitialFragment() {
        return new ConnectFragment();
    }
}
