package com.simpleerp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.adapters.TabAdapters;
import com.simpleerp.extras.SlidingTabLayout;


public class MenuPrincipal extends AppCompatActivity {
    private FloatingActionButton fab;
    private Toolbar bar ;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int tabposition;
    private RelativeLayout rl;
    protected  static  SimpleControl sistema= MainActivity.sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        acessviews();


        bar.setTitle("SimpleERP");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitle);
        bar.setSubtitle(sistema.getUsuarioLogado().getNome());
        bar.setSubtitleTextAppearance(this, R.style.AppThemeBarSubTitle);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));


                setSupportActionBar(bar);
        if(sistema.getInsumoList().size()<1 && sistema.getProducaoList().size()<1 && sistema.getProdutoList().size()<1){
            Dialog d = new Dialog(this);
            d.setContentView(R.layout.dialog_bemvindos);
            d.setTitle("Boas Vindas");
            d.setCancelable(true);
            d.show();
            rl.setVisibility(View.VISIBLE);

        }

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter( new TabAdapters( getSupportFragmentManager(), this ));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.white));
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
        rl=(RelativeLayout)findViewById(R.id.rl_indica);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                if(mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Produção")){
                    Intent it = new Intent(MenuPrincipal.this,CadProducao.class);
                    startActivity(it);

                }
                if(mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Produtos")){
                    Intent it = new Intent(MenuPrincipal.this,CadProduto.class);
                    startActivity(it);

                }
                if(mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Insumos")){
                    Intent it = new Intent(MenuPrincipal.this,CadInsumo.class);
                    startActivity(it);

                }

            }
        });

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

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
