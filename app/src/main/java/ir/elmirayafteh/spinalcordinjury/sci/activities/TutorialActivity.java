package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ir.elmirayafteh.spinalcordinjury.sci.MyAdapter;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TutorialActivity extends AppCompatActivity {

    Button tut_btn;
    String mCaller;
    private static int currentPage = 0;
    private ArrayList<Integer> imagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        imagesArray.add(R.drawable.tut_0);
        imagesArray.add(R.drawable.tut_1);
        imagesArray.add(R.drawable.tut_2);
        imagesArray.add(R.drawable.tut_3);
        imagesArray.add(R.drawable.tut_4);
        imagesArray.add(R.drawable.tut_5);
        imagesArray.add(R.drawable.tut_6);
        imagesArray.add(R.drawable.tut_7);
        imagesArray.add(R.drawable.tut_8);
        imagesArray.add(R.drawable.tut_9);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        tut_btn = (Button) findViewById(R.id.tut_button);

        Intent intent = getIntent();
        mCaller = intent.getStringExtra("caller");

        if (mCaller.equals("main")) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setVisibility(View.VISIBLE);

        } else {
            toolbar.setVisibility(View.GONE);
        }


        mPager.setAdapter(new MyAdapter(TutorialActivity.this, imagesArray));
        indicator.setViewPager(mPager);

        if (currentPage == 0) {
            currentPage = imagesArray.size();
        }

        mPager.setCurrentItem(currentPage--, true);

        ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCaller.equals("sign_up")) {
                    if (position == 0) {
                        tut_btn.setVisibility(View.VISIBLE);
                    } else {
                        tut_btn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        mPager.setOnPageChangeListener(mListener);

        tut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
