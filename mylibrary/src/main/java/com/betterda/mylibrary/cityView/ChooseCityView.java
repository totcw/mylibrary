package com.betterda.mylibrary.cityView;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.betterda.mylibrary.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lyf on 2016/8/13.
 */
public class ChooseCityView extends FrameLayout {
    private Context mContext;
    private ListView sortListView;
    private AnimSideBar sideBar;
    private SortAdapter adapter;
    private EditTextWithDel mEtCityName;
    private List<CitySortModel> SourceDateList;
    private List<String> cityList;
    private CityAdapter adapter1;//
    private GridView mGvCity;
    private TextView tv_loaction_city;
    private onSortItemClickListner onSortItemClickListner;
    private onCityItemClickListner onCityItemClickListner;

    public ChooseCityView(Context context) {
        this(context, null);
    }

    public ChooseCityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.activity_sort_city, this);
        initViews();
    }

    private void initViews() {
        mEtCityName = (EditTextWithDel) findViewById(R.id.et_search);
        sideBar = (AnimSideBar) findViewById(R.id.sidrbar);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        initEvents();
        setAdapter();
    }

    private void setAdapter() {

        SourceDateList = filledData(getResources().getStringArray(R.array.provinces));

        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(mContext, SourceDateList);

        sortListView.addHeaderView(initHeadView());
        sortListView.setAdapter(adapter);
    }

    private void initEvents() {

        sideBar.setOnTouchingLetterChangedListener(new AnimSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {


                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onSortItemClickListner != null) {

                    onSortItemClickListner.click(SourceDateList.get(position-1).getName());
                }
            }
        });


        mEtCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private View initHeadView() {
        View headView = View.inflate(mContext,R.layout.sortcity_headview, null);
        mGvCity = (GridView) headView.findViewById(R.id.gv_hot_city);
        tv_loaction_city = (TextView) headView.findViewById(R.id.tv_loaction_city);
        mGvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onCityItemClickListner != null) {
                    onCityItemClickListner.click(position);
                }

            }
        });
        cityList = new ArrayList<>();
        adapter1 = new CityAdapter(mContext, R.layout.gridview_item, cityList);
        mGvCity.setAdapter(adapter1);
        return headView;
    }

    /**
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CitySortModel> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (CitySortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<CitySortModel> filledData(String[] date) {
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(date[i]);
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }else{
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        return mSortList;
    }

    /**
     * @param cityList
     */
    public void setHotCityList(List<String> cityList) {
        for (String s : cityList) {
            this.cityList.add(s);
        }
        adapter1.notifyDataSetChanged();
    }


    /**
     * @param city
     */
    public void setLoacitonCity(String city) {
        tv_loaction_city.setText(city);
    }

    public void setOnSortItemClickListner(ChooseCityView.onSortItemClickListner onSortItemClickListner) {
        this.onSortItemClickListner = onSortItemClickListner;
    }

    public void setOnCityItemClickListner(ChooseCityView.onCityItemClickListner onCityItemClickListner) {
        this.onCityItemClickListner = onCityItemClickListner;
    }

    public interface onSortItemClickListner{
         void click(String city);
    }
    public interface onCityItemClickListner{
         void click(int position);
    }
}
