package com.simpleerp;



import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import com.simpleerp.adapters.TabAdapters;
import com.simpleerp.extras.SlidingTabLayout;





public class MenuPrincipal extends ActionBarActivity{
    private FloatingActionButton fab;
    private Toolbar bar ;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int tabposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        acessviews();
        bar.setTitle("  "+MainActivity.edNomeEmpresa.getText().toString());
        bar.setTitleTextColor(getResources().getColor(R.color.colorFABPressed));
        bar.setLogo(R.drawable.simplelogo);
        setSupportActionBar(bar);
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter( new TabAdapters( getSupportFragmentManager(), this ));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabposition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager( mViewPager );

    }

    public void acessviews(){
        bar=(Toolbar)findViewById(R.id.bar);
        fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage(mViewPager.getAdapter().getPageTitle(tabposition).toString());
            }
        });

    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            showMessage("Settings");
        }
        return super.onOptionsItemSelected(item);
    }




}
