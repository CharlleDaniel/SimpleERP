package com.simpleerp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.simpleerp.adapters.TabAdapters;
import com.simpleerp.extras.SlidingTabLayout;





public class MenuPrincipal extends ActionBarActivity implements PopupMenu.OnMenuItemClickListener {

    private RelativeLayout bar ;
    private ImageButton menu;
    private TextView title;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        acessviews();

        title.setText(MainActivity.edNomeEmpresa.getText().toString());

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter( new TabAdapters( getSupportFragmentManager(), this ));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setViewPager( mViewPager );

    }

    public void acessviews(){
        bar=(RelativeLayout)findViewById(R.id.bar);
        menu=(ImageButton)findViewById(R.id.menu);
        title=(TextView)findViewById(R.id.title);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;

            default:
                return false;
        }
    }



}
