package com.appjam.assist.assist.team;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appjam.assist.assist.BaseActivity;
import com.appjam.assist.assist.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by gominju on 2017. 6. 27..
 */

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    private ImageView iv_icon, iv_select_icon;
    private String selectedFormatDate;
    private boolean isSelected = false;

    private ArrayList<String> items;
    public static List<String> day_string;
    private View previousView;
    public ArrayList<Schedule> scheduleArrayList;

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<Schedule> scheduleArrayList) {
        this.context = context;
        month = monthCalendar;
        this.scheduleArrayList = scheduleArrayList;
        CalendarAdapter.day_string = new ArrayList<>();
        Locale.setDefault(Locale.US);
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();
    }


    @Override
    public int getCount() {
        return day_string.size();
    }

    @Override
    public Object getItem(int position) {
        return day_string.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cal_item, null);
            BaseActivity.setGlobalFont(context, v);

        }

        dayView = (TextView) v.findViewById(R.id.date);
        iv_icon = (ImageView) v.findViewById(R.id.date_today);
        iv_select_icon = (ImageView) v.findViewById(R.id.date_select);
        String[] separatedTime = day_string.get(position).split("-");

        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.WHITE);
        }

        if (day_string.get(position).equals(curentDateString)) {    // 현재 날짜
            iv_icon.setVisibility(View.VISIBLE);
        } else {
            iv_icon.setVisibility(View.GONE);
        }

        dayView.setText(gridvalue);

        // create date string for comparison
        String date = day_string.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
        /*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.GONE);
        }
        */

        if (isSelected && selectedFormatDate.equals(day_string.get(position))) {
            iv_select_icon.setVisibility(View.VISIBLE);
        } else {
            iv_select_icon.setVisibility(View.GONE);
        }

        setEventView(v, iv_icon, position, dayView);

        return v;
    }

    public View setSelected(View view, int pos) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.color.colorPrimaryDark);
//            previousView.setBackgroundColor(Color.parseColor("#343434"));
        }

        view.setBackgroundResource(R.color.colorPrimaryDark);
        // view.setBackgroundColor(Color.CYAN);

        int len = day_string.size();
        if (len > pos) {
            if (day_string.get(pos).equals(curentDateString)) {
            } else {
                previousView = view;
            }
        }
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

    public void setEventView(View v, ImageView icon, int pos, TextView txt) {
        for (int i = 0; i < scheduleArrayList.size(); i++) {
            Schedule cal_obj = scheduleArrayList.get(i);
            String date = cal_obj.getGame_dt().substring(0, 10);
            int len1 = day_string.size();
            if (len1 > pos) {
                if (day_string.get(pos).equals(date)) {
                    v.setBackgroundResource(R.color.colorPrimaryDark);
                    icon.setBackgroundResource(R.drawable.oval_pink);
                    icon.setVisibility(View.VISIBLE);
                    txt.setTextColor(Color.WHITE);
                }
            }
        }
    }


    public void setSelectedPosition(String selectedGridDate) {
        selectedFormatDate = selectedGridDate;
        isSelected = true;
    }


    public boolean isScheduled(String date) {
        boolean result = false;

        for (int i = 0; i < scheduleArrayList.size(); i++) {
            Schedule cal_collection = scheduleArrayList.get(i);
            String event_date = cal_collection.getGame_dt().substring(0, 10);

            if (date.equals(event_date)) {
                result = true;
                break;
            } else {
                result = false;
            }
        }
        return result;
    }
}