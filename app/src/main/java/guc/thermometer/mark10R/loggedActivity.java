package guc.thermometer.mark10R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class loggedActivity extends AppCompatActivity {
    private Drawer result;
    HomeFragment homeFragment;
    AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        Bundle extras = getIntent().getExtras();
        String mail = extras.getString(EXTRA_MESSAGE);

        homeFragment = HomeFragment.newInstance(mail);
        aboutFragment = new AboutFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down).replace(R.id.fragment_container, homeFragment).commit();


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.home).withIcon(GoogleMaterial.Icon.gmd_home).withDescription(R.string.home_description).withIconTintingEnabled(true).withIdentifier(1);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName(R.string.about).withIcon(GoogleMaterial.Icon.gmd_info).withDescription(R.string.about_description).withIconTintingEnabled(true).withIdentifier(2);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName(R.string.placeholder).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withDescription(R.string.placeholder).withIconTintingEnabled(true).withIdentifier(3);

        SecondaryDrawerItem s1 = new SecondaryDrawerItem().withName(R.string.share).withIcon(GoogleMaterial.Icon.gmd_share).withIconTintingEnabled(true).withIdentifier(4);
        SecondaryDrawerItem s2 = new SecondaryDrawerItem().withName(R.string.contact).withIcon(GoogleMaterial.Icon.gmd_local_phone).withIconTintingEnabled(true).withIdentifier(5);
        SecondaryDrawerItem s3 = new SecondaryDrawerItem().withName(R.string.github).withIcon(FontAwesome.Icon.faw_github).withIconTintingEnabled(true).withIdentifier(6);


        //Account Header Builder
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bkgrnd)
                .addProfiles(
                        new ProfileDrawerItem().withName("Ossama Akram").withEmail(mail).withIcon(getResources().getDrawable(R.drawable.image))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        // final Intent abtintent = new Intent(this, AboutActivity.class);
        final Intent Logoutintent = new Intent(this, MainActivity.class);
        result = new DrawerBuilder()
                .withSelectedItem(0)
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSliderBackgroundColor(Color.DKGRAY)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentNavigationBarProgrammatically(true)
                .addDrawerItems(
                        item1.withTextColor(Color.WHITE).withIconColor(Color.WHITE),
                        item2.withTextColor(Color.WHITE).withIconColor(Color.WHITE),
                        item3.withTextColor(Color.WHITE).withIconColor(Color.WHITE),
                        new DividerDrawerItem(),
                        s1.withTextColor(Color.WHITE).withIconColor(Color.WHITE).withSelectable(false),
                        s2.withTextColor(Color.WHITE).withIconColor(Color.WHITE).withSelectable(false),
                        new DividerDrawerItem(),
                        s3.withTextColor(Color.WHITE).withIconColor(Color.WHITE).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.placeholder)
                )
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                Toast.makeText(loggedActivity.this, R.string.home_description, Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(loggedActivity.this, R.string.about_description, Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                Toast.makeText(loggedActivity.this, R.string.placeholder, Toast.LENGTH_LONG).show();
                                break;
                        }
                        result.closeDrawer();
                        return false;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1)
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                        else if (drawerItem.getIdentifier() == 2)
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    aboutFragment).addToBackStack(null).commit();
                        else if (drawerItem.getIdentifier() == 3)
                            Toast.makeText(loggedActivity.this, R.string.placeholder, Toast.LENGTH_SHORT).show();
                        else if (drawerItem.getIdentifier() == 4) {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.placeholder);
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.placeholdertxt);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (drawerItem.getIdentifier() == 5) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ispamossama@gmail.com"});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Mark10 Support");
                            intent.putExtra(Intent.EXTRA_TEXT, R.string.placeholdertxt);
                            startActivity(Intent.createChooser(intent, ""));
                        } else if (drawerItem.getIdentifier() == 6) {
                            String url = "https://github.com/iSpammer/";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } else if (drawerItem.getIdentifier() == 99) {
                            Intent intent = new Intent(loggedActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        }

                        result.closeDrawer();
                        return false;
                    }

                })
                .

                        withOnDrawerListener(new Drawer.OnDrawerListener() {
                            @Override
                            public void onDrawerOpened(View view) {

                            }

                            @Override
                            public void onDrawerClosed(View view) {
                                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                            }

                            @Override
                            public void onDrawerSlide(View view, float v) {
                                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            }
                        }).build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.logout).withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(99));

    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        } else {
            super.onBackPressed();
        }
    }


}
