package demoapp.com.navtabgrid.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demoapp.com.navtabgrid.R;

public class HomeFragment extends Fragment {

    //Defining Variables
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String CurntStatus;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view initialize and functionality declare

        // initialize tab layout with tab icon
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Latest"));
        tabLayout.addTab(tabLayout.newTab().setText("Dress"));
        tabLayout.addTab(tabLayout.newTab().setText("Footwear"));
        tabLayout.addTab(tabLayout.newTab().setText("Watch"));
        tabLayout.addTab(tabLayout.newTab().setText("Cosmetics"));
        tabLayout.addTab(tabLayout.newTab().setText("Bag"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // initialize view pager
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(6);         // for smooth transition between tabs
        // initialize view pager adapter and setting that adapter
        final PagerAdapter adapter = new PageAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        // view pager listener
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // tab listener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the position which tab is selected
                viewPager.setCurrentItem(tab.getPosition());
                int Status = tab.getPosition();
                CurntStatus = String.valueOf(Status);
                Log.d("Home: ", CurntStatus);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // get the position which tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // get the position which tab is reselected
            }
        });

    }

    // view pager adapter class
    class PageAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PageAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.mNumOfTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    LatestFragment LatestFragment = new LatestFragment();
                    return LatestFragment;
                case 1:
                    DressFragment DressFragment = new DressFragment();
                    return DressFragment;
                case 2:
                    LatestFragment LatestFragment2 = new LatestFragment();
                    return LatestFragment2;
                case 3:
                    LatestFragment LatestFragment3 = new LatestFragment();
                    return LatestFragment3;
                case 4:
                    LatestFragment LatestFragment4 = new LatestFragment();
                    return LatestFragment4;
                case 5:
                    LatestFragment LatestFragment5 = new LatestFragment();
                    return LatestFragment5;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

}
