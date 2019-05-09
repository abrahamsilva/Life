package mx.itesm.edu.life;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menuNav = navigationView.getMenu();

        MenuItem item  =  menuNav.findItem(R.id.nav_notifications);
        RelativeLayout layout = (RelativeLayout) item.getActionView();
        mSwitch = layout.findViewById(R.id.notif_switch);


        if(mSwitch != null){
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        FirebaseMessaging.getInstance().subscribeToTopic("pushNotification");
                    } else {
                        Log.d("Suscripcion", "false");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotification");
                    }
                }
            });
        }

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, EventosFragment.newInstance());
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_eventos:
                    selectedFragment = EventosFragment.newInstance();
                    break;
                case R.id.navigation_directorio:
                    selectedFragment = DirectorioFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            setTitle(R.string.title_eventos);
            transaction.commit();

            return true;
        }
    };


    //open and close menu
    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
    //To navigate to any section (view) specified in the menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_tips:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new TipsFragment())
                            .commit();
                break;
            case R.id.nav_faqs:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new FaqsFragment())
                        .commit();
                break;
            case R.id.nav_surveys:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new SurveysFragment())
                        .commit();
                break;
            case R.id.nav_opinion:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSe1vugFyANAqogeoS5BBT2tu6btPSqxoHChx1KhdRA_am7B5g/viewform"));
                startActivity(browserIntent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void callEmergency(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "5554831581"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
