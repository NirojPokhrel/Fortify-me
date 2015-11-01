package com.pokhrelniroj.ecomhackathon;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Niroj Pokhrel on 8/21/2015.
 */
public class ActivityWithNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        int selectedId = menuItem.getItemId();

        navigate(selectedId);
        return true;
    }

    protected void initializeNavigationView(Toolbar tb) {

        //Navigation View
        NavigationView navView = (NavigationView) findViewById(R.id.navigationView);
        navView.setNavigationItemSelectedListener(this);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // set up the hamburger icon to open and close the drawer
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, tb, R.string.open,
                R.string.close);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


    }

    private void navigate(int menuId) {
        Intent intent;
        Class cls = null;
        switch (menuId) {
            case R.id.starred:
                break;
            case R.id.savedCoupons:
                break;
            case R.id.subscribedAreas:
                break;
            case R.id.settings:
                cls = null;
                break;
            case R.id.helpAndSupport:
                cls = null;
                break;
            default:
                cls = null;
                break;
        }
        if (cls != null) {
            intent = new Intent(getApplicationContext(), cls);
            startActivity(intent);
        }
    }
}
