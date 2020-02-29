package com.example.myapplication8;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class Main40Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout mdrawerLayout;
    Toolbar mtoolbar;
    TextView tv,tv8,tv48;
    NavigationView navigationView;
    ActionBarDrawerToggle mtoggle;
    private Button btn_start, btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    String url="https://womenssafety8.000webhostapp.com/locationdetails.php";

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // textView.append("\n" +intent.getExtras().get("coordinates"));
                    TextView textView8 = (TextView) findViewById(R.id.textView);
                    textView8.setText("\n" +intent.getExtras().get("coordinates"));
                    final String locationname = textView8.getText().toString();
                    SharedPreferences sp1=getSharedPreferences("Login",MODE_PRIVATE);
                    final String em=sp1.getString("email",null);
                    RequestQueue queue = Volley.newRequestQueue(Main40Activity.this);
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(Main40Activity.this, response, Toast.LENGTH_SHORT).show();
                            Log.i("My success", "" + response);
                            // startActivity(new Intent(Main40Activity.this, MainActivity.class));
                        }


                    },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Main40Activity.this, "Error : " + error, Toast.LENGTH_LONG).show();
                                    Log.i("My error", "" + error);
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("gmail",em);
                            map.put("locationdetails", locationname);
                            return map;
                        }
                    };
                    queue.add(request);
                }
            };

        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main40);
        Intent intent =getIntent();
        String message8=intent.getStringExtra("extra_message");
        SharedPreferences sp1=this.getSharedPreferences("Login",MODE_PRIVATE);
        String em=sp1.getString("email",null);
      //  TextView textView = (TextView) findViewById(R.id.tv48);
      //  textView.setText(em);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textview8);
        navUsername.setText(em);


        mdrawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        mtoolbar=(Toolbar) findViewById(R.id.nav_action);
        navigationView=findViewById(R.id.navigationView);
        setSupportActionBar(mtoolbar);
      //  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       mtoggle=new ActionBarDrawerToggle(this,mdrawerLayout,R.string.drawerOpen,R.string.drawerClose);
       mdrawerLayout.addDrawerListener(mtoggle);
       mtoggle.syncState();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       navigationView.setNavigationItemSelectedListener(this);
        btn_start = (Button) findViewById(R.id.button);
        btn_stop = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);

        if(!runtime_permissions())
            enable_buttons();


    }
    private void enable_buttons() {

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                startService(i);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                stopService(i);

            }
        });
    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.nav_account: {
                Intent in = new Intent(Main40Activity.this, Main48Activity.class);
                startActivity(in);
                break;
            }
            case R.id.nav_location: {
                Intent in2 = new Intent(Main40Activity.this, locationnotification.class);
                startActivity(in2);
                break;
            }
            case R.id.nav_logout: {
                SessionManagement sessionManagement = new SessionManagement(Main40Activity.this);
                sessionManagement.removeSession();
                moveToLogin();
                break;
            }
        }
        return false;
    }

    private void moveToLogin() {
        Intent intent = new Intent(Main40Activity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
