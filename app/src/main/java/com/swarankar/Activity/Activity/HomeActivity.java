package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jackandphantom.blurimage.BlurImage;
import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Fragment.HomeFragment;
import com.swarankar.Activity.Fragment.Profile1Fragment;
import com.swarankar.Activity.Model.ModelProfile.ModelProfile;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String TAG_FRAGMENT = "TAG_FRAGMENT";
    public static TextView mTextToolBar;
    public static CircleImageView navProfile;
    public static TextView navUsername;
    public static TextView navEmail;
    //    String flag;
    CircleImageView profileImg;
    Typeface myfont;
    //    ImageView ly_notification, lv_search;
    String strUserid = "";
    RelativeLayout lvFamily;
    RelativeLayout lv_profile, lv_jobs, lv_Events, lv_society, lv_adverticement;
    TextView text;

    Constants constants;
    String ProfileImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        flag = getIntent().getStringExtra("flag");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Swarnkar Connect");

//        lv_search= (ImageView) findViewById(R.id.home_layout_search);
//        ly_notification= (ImageView) findViewById(R.id.ly_notification);
        profileImg = (CircleImageView) findViewById(R.id.nav_profile_img);
        lvFamily = (RelativeLayout) findViewById(R.id.home_layout_family);
        lv_profile = (RelativeLayout) findViewById(R.id.lv_profile);
        lv_jobs = (RelativeLayout) findViewById(R.id.lv_jobs);
        lv_Events = (RelativeLayout) findViewById(R.id.layout_events);
        lv_society = (RelativeLayout) findViewById(R.id.lv_society);
        /* lv_search = (RelativeLayout) findViewById(R.id.home_layout_search);*/
        /*lv_notification = (RelativeLayout) findViewById(R.id.ly_notification);*/
        lv_adverticement = (RelativeLayout) findViewById(R.id.lv_adverticemnet);
        text = (TextView) findViewById(R.id.text);

        text.setText("SOCIETY & PERIODICALS");

        lv_profile.setOnClickListener(this);
        lvFamily.setOnClickListener(this);
        lv_jobs.setOnClickListener(this);
        lv_Events.setOnClickListener(this);
//        ly_notification.setOnClickListener(this);
//        lv_notification.setOnClickListener(this);
        lv_society.setOnClickListener(this);
//        lv_search.setOnClickListener(this);
        lv_adverticement.setOnClickListener(this);
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.swarankar",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mTextToolBar = (TextView) findViewById(R.id.txt_toolbar);
        mTextToolBar.setText("Swarnkar Connect");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navProfile = (CircleImageView) header.findViewById(R.id.nav_profile_img);
        final ImageView header_background = header.findViewById(R.id.header_background);

        navUsername = (TextView) header.findViewById(R.id.nav_username);
        navEmail = (TextView) header.findViewById(R.id.nav_email);
        TextView naveditProfile = (TextView) header.findViewById(R.id.nav_edit_profile);

        myfont = Typeface.createFromAsset(getAssets(), "Prima Sans Bold BT.ttf");
        navUsername.setTypeface(myfont);

        Glide.with(getApplicationContext()).load("https://upload.wikimedia.org/wikipedia/commons/f/fc/Nemer_Saade_Profile_Picture.jpg").into(navProfile);

        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... unused) {
                try

                {
                    Bitmap bitmap = Glide.with(getApplicationContext()).asBitmap().load("https://upload.wikimedia.org/wikipedia/commons/f/fc/Nemer_Saade_Profile_Picture.jpg").into(300, 150).get();
                    BlurImage.with(getApplicationContext()).load(bitmap).intensity(20).Async(true).into(header_background);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

//        Toast.makeText(getApplicationContext(), "" + Constants.loginSharedPreferences.getString(Constants.uid, ""), Toast.LENGTH_SHORT).show();
        final DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        naveditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer1.closeDrawer(GravityCompat.START);
                Profile1Fragment m2 = new Profile1Fragment();
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm1.beginTransaction();
                ft2.replace(R.id.content_frame, m2);
                ft2.addToBackStack("Profile");
                ft2.commit();
            }
        });
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... unused) {
                getNavData();
                return null;
            }
        }.execute();

//        username.setText(Constants.loginSharedPreferences.getString(Constants.name,""));
//        email.setText(Constants.loginSharedPreferences.getString(Constants.email,""));

        navigationView.setNavigationItemSelectedListener(this);

//        if (flag.equals("")) {
//
//        } else if (flag.equals("profile")) {
//            displaySelectedScreen(R.id.nav_account);
//
//        }

    }

    private void getNavData() {
        constants = new Constants();
        Constants.loginSharedPreferences = getSharedPreferences(Constants.LoginPREFERENCES, MODE_PRIVATE);
        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
//        Toast.makeText(getApplicationContext(),""+strUserid,Toast.LENGTH_SHORT).show();
/*final ProgressDialog loading=new ProgressDialog(HomeActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();*/

//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        // TODO My Changes
        Call<ModelProfile> call1 = apiService.userInfo(strUserid);
        call1.enqueue(new Callback<ModelProfile>() {
            @Override
            public void onResponse(Call<ModelProfile> call, final retrofit2.Response<ModelProfile> response) {
//        loading.dismiss();

                Log.e("resprofilrff", response.body().getFirstname() + "");

                navUsername.setText(AndroidUtils.wordFirstCap(response.body().getFirstname() + " " + response.body().getLastname()));
                navEmail.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));

                new AsyncTask<Void, Void, Bitmap>() {

                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        Bitmap bitmap = null;
                        try {
//                            bitmap = Glide.with(getApplicationContext()).load(response.body().getPicture()).asBitmap().into(500, 500).get();
                            InputStream input = new java.net.URL(response.body().getPicture()).openStream();
                            // Decode Bitmap
                            bitmap = BitmapFactory.decodeStream(input);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap != null) {

                            ProfileImage = AndroidUtils.BitMapToString(bitmap);
                        }
                    }
                }.execute();
                if (!response.body().getPicture().isEmpty()) {
                    Picasso.with(getApplicationContext()).load(response.body().getPicture())/*.error(R.drawable.placeholder)*/.into(HomeActivity.navProfile);
                }

            }

            @Override
            public void onFailure(Call<ModelProfile> call, Throwable t) {
//        loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });

    }

    @Override
    public void onBackPressed() {

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    /*final Profile1Fragment fragment = (Profile1Fragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
                    {
                        if (fragment.allowBackPressed()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
                            super.onBackPressed();
                        } else {

                        }
                    }*/
                    mTextToolBar.setText("Swarnkar Connect");
                    super.onBackPressed();
                    return;

                } else {

                    mTextToolBar.setText(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName() + "");
                    super.onBackPressed();
                    return;
                }

            } else {

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(false);
                dialog.setTitle("Swarankar");
                dialog.setMessage("Are you sure you want to close App?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finishAffinity();
                    }
                }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
            }
        }
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.nav_home:
                Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(i);

            case R.id.nav_account:
                Profile1Fragment m2 = new Profile1Fragment();
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm1.beginTransaction();
                ft2.replace(R.id.content_frame, m2, TAG_FRAGMENT);
                ft2.addToBackStack("Profile");
                ft2.commit();
                break;

            case R.id.nav_jobs:

                if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                    DialogProfileNotCompleted();
                } else {
                    Intent iq = new Intent(HomeActivity.this, JobsActivity.class);
                    startActivity(iq);
                }

                break;

            case R.id.nav_family:
                if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                    DialogProfileNotCompleted();
                } else {
                    Intent i1 = new Intent(HomeActivity.this, FamilyActivity.class);
                    startActivity(i1);
                }
                break;

            case R.id.nav_news:
                if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                    DialogProfileNotCompleted();
                } else {
                    Intent ie = new Intent(HomeActivity.this, NewsActivity.class);
                    startActivity(ie);
                }

                break;

            case R.id.nav_periodicals:

                if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                    DialogProfileNotCompleted();
                } else {
                    Intent ie1 = new Intent(HomeActivity.this, Periodicals.class);
                    startActivity(ie1);
                }

                break;
            case R.id.nav_society:

                if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                    DialogProfileNotCompleted();
                } else {
                    Intent i2 = new Intent(HomeActivity.this, SocietyActivity.class);
                    startActivity(i2);
                }
                break;

            case R.id.nav_donation:

                Intent i2 = new Intent(HomeActivity.this, DonationActivity.class);
                startActivity(i2);

                break;

            case R.id.nav_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(false);
                dialog.setTitle("Swarankar");
                dialog.setMessage("Are you sure you want to sign out?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finishAffinity();
                    }
                }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();

                break;

        }

        //replacing the fragment
        if (fragment != null) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void DialogProfileNotCompleted() {

        final Dialog alertDialog = new Dialog(HomeActivity.this);
        View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_profile_not_completed, null);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(rootView);
//        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.drawable_back_dialog));
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

     /*   WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        */

        Button button_ok = alertDialog.findViewById(R.id.dialog_profile_button_ok);
//        TextView textview = rootView.findViewById(R.id.textview);
        ImageView btnClose = alertDialog.findViewById(R.id.img_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Profile1Fragment m2 = new Profile1Fragment();
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm1.beginTransaction();
                ft2.replace(R.id.content_frame, m2, TAG_FRAGMENT);
                ft2.addToBackStack("Profile");
                ft2.commit();

            }
        });
        alertDialog.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == lv_profile) {
            Profile1Fragment m2 = new Profile1Fragment();
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.replace(R.id.content_frame, m2, TAG_FRAGMENT);
            ft2.addToBackStack("Profile");
            ft2.commit();
        }
        if (view == lvFamily) {

        /*    if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                DialogProfileNotCompleted();
            } else {*/
            Intent i = new Intent(HomeActivity.this, FamilyActivity.class);
            startActivity(i);
            //}

        }
        if (view == lv_jobs) {

            if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                DialogProfileNotCompleted();
            } else {
                Intent i = new Intent(HomeActivity.this, JobsActivity.class);
                startActivity(i);
            }

        }
        if (view == lv_Events) {

            if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {

                DialogProfileNotCompleted();
            } else {

                Intent i = new Intent(HomeActivity.this, EventsActivity.class);
                startActivity(i);
            }

        }
     /*   if (view == ly_notification) {

            if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                DialogProfileNotCompleted();
            } else {
                Intent i = new Intent(HomeActivity.this, Notification1.class);
                startActivity(i);
            }


        }*/
        if (view == lv_society) {

            if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {

                DialogProfileNotCompleted();
            } else {

                Intent i = new Intent(HomeActivity.this, SocietyActivity.class);
                startActivity(i);
            }

        }
        /* if (view == lv_search) {

         *//*if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {

                DialogProfileNotCompleted();
            } else {*//*

            Intent i = new Intent(HomeActivity.this, AdvanceSearchActivity.class);
            startActivity(i);
            //}
        }*/
        if (view == lv_adverticement) {

            final Dialog alertDialog = new Dialog(HomeActivity.this);
            View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_profile_not_completed, null);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(rootView);
          /*  alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.drawable_back_dialog));
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
*/
 /*           WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;

            alertDialog.getWindow().setAttributes(lp);*/

            Button button_ok = alertDialog.findViewById(R.id.dialog_profile_button_ok);
            ImageView btnClose = alertDialog.findViewById(R.id.img_close);
            TextView textview = alertDialog.findViewById(R.id.textview);
            textview.setText("            Coming Soon          ");

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equals("false")) {
                DialogProfileNotCompleted();
            } else {
                Intent i = new Intent(HomeActivity.this, Notification1.class);
                startActivity(i);
            }
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_search) {
            Intent i = new Intent(HomeActivity.this, AdvanceSearchActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
