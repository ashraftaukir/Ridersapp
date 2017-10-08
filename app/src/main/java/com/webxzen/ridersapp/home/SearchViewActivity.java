package com.webxzen.ridersapp.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.util.Appinfo;


public class SearchViewActivity extends BaseActivity {

    FrameLayout fragmentcontainer;

//    Context context;
//    public SearchViewActivity(Context context) {
//
//        this.context=context;
//       // this.fragmentcontainer = fragmentcontainer;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchviewactivity);
        Bundle bundle = getIntent().getExtras();
        String fragmentname = bundle.getString(Appinfo.FRAGMENT_NAME);

        fragmentcontainer = (FrameLayout) findViewById(R.id.fragment_container);
        initFragmentWithValue(new SearchViewFragment(), Appinfo.SEARCHFRAGMENT, fragmentcontainer.getId(), fragmentname);
        // initFragment(new SearchViewFragment(), Appinfo.SEARCHFRAGMENT,fragmentcontainer.getId());


    }
}
