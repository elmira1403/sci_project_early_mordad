package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ir.elmirayafteh.spinalcordinjury.sci.Case;
import ir.elmirayafteh.spinalcordinjury.sci.Exercise;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.Utils;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager favViewPager = (ViewPager) findViewById(R.id.fav_viewpager);
        setupViewPager(favViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(favViewPager);

        Utils.applyFontedTab(FavoritesActivity.this, favViewPager, tabLayout);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavRecFragment(), "حرکات");
        adapter.addFragment(new FavExerFragment(), "تمرین ها");
        viewPager.setAdapter(adapter);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    public static ContentValues getContentValues(Case mCase) {
        ContentValues values = new ContentValues();
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT, mCase.getSubject());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_VIDEO, mCase.getVideo_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_VOICE, mCase.getVoice_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_TEXT, mCase.getText_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_DESC, mCase.getDesc());

        return values;
    }

    public static ContentValues getContentValues(Exercise mExercise) {
        ContentValues values = new ContentValues();
        values.put(RecCaseDbSchema.FavExerciseTable.Cols.SUBJECT, mExercise.getGoal());
        values.put(RecCaseDbSchema.FavExerciseTable.Cols.IMAGE, mExercise.getImage_file());
        values.put(RecCaseDbSchema.FavExerciseTable.Cols.DESC, mExercise.getDesc());

        return values;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(ir.elmirayafteh.spinalcordinjury.sci.R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(FavoritesActivity.this, HelpActivity.class);
            i.putExtra("caller", "fav_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}
