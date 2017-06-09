package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.Day;
import fragments.Month;

public class PagerAdapter extends FragmentPagerAdapter{

    private int numOfTabs = 2;
    private String tabTitles[] = new String[] { "Dnevni", "Mesečni"};
    private Context context;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Day day = new Day();
                return day;
            case 1:
                Month month = new Month();
                return month;
            default:
                return  null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
