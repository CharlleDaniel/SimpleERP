package com.simpleerp;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import com.simpleerp.Control.SimpleControl;
import com.simpleerp.adapters.TabAdapters;
import com.simpleerp.extras.SlidingTabLayout;


public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener, SearchView.OnCloseListener {
    private FloatingActionButton fab;
    private Toolbar bar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int tabposition;
    private RelativeLayout rl;
    public static SimpleControl sistema;
    private MenuItem searchItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_principal);
        sistema = new SimpleControl(this);
        acessviews();

        bar.setTitle("SimpleERP");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitle);
        bar.setSubtitle(sistema.getUsuarioLogado().getNome());
        bar.setSubtitleTextAppearance(this, R.style.AppThemeBarSubTitle);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        setSupportActionBar(bar);

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabAdapters(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tv_tab);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabposition = position;
                showSearch();
                if(searchView!=null){
                    if(searchView.isActivated()){
                        onClose();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        if (sistema.getInsumoList().size() < 1 && sistema.getProducaoList().size() < 1 && sistema.getProdutoList().size() < 1) {
            Dialog d = new Dialog(this);
            d.setContentView(R.layout.dialog_bemvindos);
            d.setTitle("Boas Vindas");
            d.setCancelable(true);
            d.show();
            rl.setVisibility(View.VISIBLE);


        }
    }

    public void acessviews() {
        bar = (Toolbar) findViewById(R.id.bar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rl = (RelativeLayout) findViewById(R.id.rl_indica);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                if (mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Produção")) {
                    Intent it = new Intent(MenuPrincipal.this, CadProducao.class);
                    startActivity(it);

                }
                if (mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Produtos")) {
                    Intent it = new Intent(MenuPrincipal.this, CadProduto.class);
                    startActivity(it);

                }
                if (mViewPager.getAdapter().getPageTitle(tabposition).toString().equalsIgnoreCase("Insumos")) {
                    Intent it = new Intent(MenuPrincipal.this, CadInsumo.class);
                    startActivity(it);

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        searchItem = menu.findItem(R.id.action_search);

        showSearch();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(filterText());
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint("Pesquisar...");
            searchView.setOnSearchClickListener(this);
            searchView.setOnCloseListener(this);
        }
        return true;
    }

    private SearchView.OnQueryTextListener filterText() {
        SearchView.OnQueryTextListener oq = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        };
        return oq;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent it = new Intent(this, MinhasPlanilhas.class);
            startActivity(it);
        }
        if (id == android.R.id.home) {
            this.onClose();


        }


        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSearch() {
        if (searchItem != null) {
            if (tabposition == 0) {
                searchItem.setVisible(false);

            } else {
                searchItem.setVisible(true);
            }
        }
    }


    private void hideToolbar() {
        mSlidingTabLayout.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void showToolbar() {
        mSlidingTabLayout.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }

    @Override
    public void onClick(View v) {
        searchView.onActionViewExpanded();
        searchView.setActivated(true);
        hideToolbar();
    }

    @Override
    public boolean onClose() {
        searchView.onActionViewCollapsed();
        searchView.setActivated(false);
        showToolbar();

        return true;
    }

    @Override
    public void onBackPressed() {
        if(searchView!=null){
            if(searchView.isActivated()==true){
               onClose();
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }

    }
}