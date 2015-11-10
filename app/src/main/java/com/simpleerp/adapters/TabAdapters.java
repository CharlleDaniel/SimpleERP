package com.simpleerp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.simpleerp.fragments.FragInsumo;
import com.simpleerp.fragments.FragProducao;
import com.simpleerp.fragments.FragProduto;


/**
 * Created by CharlleNot on 09/10/2015.
 */
public class TabAdapters extends FragmentPagerAdapter {
    private Context mContext;
    private String[]titles= {"PRODUÇÃO","PRODUTOS","INSUMOS"};

    public TabAdapters(FragmentManager fm, Context c) {
        super(fm);
        this.mContext=c;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment frag= null;
        if(i==0){
            frag= new FragProducao();
        }else if (i==1){
            frag= new FragProduto();
        }else if (i==2){
            frag= new FragInsumo();
        }

        Bundle b = new Bundle();
        b.putInt("position",i);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return (titles[position]);
    }
}
