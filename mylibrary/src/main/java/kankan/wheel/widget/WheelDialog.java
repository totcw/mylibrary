package kankan.wheel.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.betterda.mylibrary.R;
import com.betterda.mylibrary.domain.CityModel;
import com.betterda.mylibrary.domain.DistrictModel;
import com.betterda.mylibrary.domain.ProvinceModel;
import com.betterda.mylibrary.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.betterda.mylibrary.wheel.widget.adapters.ArrayWheelAdapter;
import com.betterda.mylibrary.wheel.widget.views.*;
import com.betterda.mylibrary.wheel.widget.views.OnWheelScrollListener;
import com.betterda.mylibrary.wheel.widget.views.WheelView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



/**
 *
 * @author Administrator
 */
public class WheelDialog extends Dialog implements
        OnClickListener, com.betterda.mylibrary.wheel.widget.views.OnWheelChangedListener {
    private Context context;
    private com.betterda.mylibrary.wheel.widget.views.WheelView mViewProvince; //
    private com.betterda.mylibrary.wheel.widget.views.WheelView mViewCity; //
    private com.betterda.mylibrary.wheel.widget.views.WheelView mViewDistrict;//
    private Button mBtnConfirm; //

    private OnAddressCListener onAddressCListener;
    /**
     * all provice
     */
    //protected String[] mProvinceDatas;
    private List<String> mProvinceDatass;
    /**
     * key - provice value - city
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - city values - area
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - area values - youbian
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * current provice
     */
    protected String mCurrentProviceName;
    /**
     * currentend city
     */
    protected String mCurrentCityName;
    /**
     * currentend area
     */
    protected String mCurrentDistrictName = "";

    /**
     * currentend youbian
     */
    protected String mCurrentZipCode = "";

    protected AddressTextAdapter proviceAdapter;
    protected AddressTextAdapter cityAdapter;
    protected AddressTextAdapter areaAdapter;


    public WheelDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    private int maxsize = 24;
    private int minsize = 14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        addViews();
        addListener();
        initProvinceDatas();
        // setting provice data
        proviceAdapter = new AddressTextAdapter(context, mProvinceDatass, 0, maxsize, minsize);
        mViewProvince.setViewAdapter(proviceAdapter);



        // setting vieable item
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);
        mViewDistrict.setVisibleItems(5);
        updateCities();
        updateAreas();
    }

    private void addViews() {
        mViewProvince = (com.betterda.mylibrary.wheel.widget.views.WheelView) findViewById(R.id.id_province);
        mViewCity = (com.betterda.mylibrary.wheel.widget.views.WheelView) findViewById(R.id.id_city);
        mViewDistrict = (com.betterda.mylibrary.wheel.widget.views.WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    /**
     * add listener
     */
    private void addListener() {
        mViewProvince.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
        mViewProvince.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) proviceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, proviceAdapter);
            }
        });

        mViewCity.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, cityAdapter);
            }
        });

        mViewDistrict.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, areaAdapter);
            }
        });

    }

    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected AddressTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.size() > index ? list.get(index) + "" : "";
        }
    }


    /**
     * setting  textsize
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }

    /**
     *
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        setTextviewSize(mCurrentCityName,cityAdapter);
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }

        areaAdapter = new AddressTextAdapter(context, Arrays.asList(areas),0,maxsize,minsize);
        mViewDistrict.setViewAdapter(areaAdapter);
        mViewDistrict.setCurrentItem(0);

        //TODO
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
    }

    /**
     *
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatass.get(pCurrent);
        setTextviewSize(mCurrentProviceName,proviceAdapter);
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        cityAdapter = new AddressTextAdapter(context, Arrays.asList(cities),0,maxsize,minsize);
        mViewCity.setViewAdapter(cityAdapter);
        mViewCity.setCurrentItem(0);

        updateAreas();
    }

    private void showSelectedResult() {

    }

    @Override
    public void onChanged(com.betterda.mylibrary.wheel.widget.views.WheelView wheel, int oldValue, int newValue) {

        if (wheel == mViewProvince) {
            updateCities();

        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            setTextviewSize(mCurrentDistrictName,areaAdapter);
        }

    }

    public interface OnAddressCListener {
        public void onClick(String province, String city, String area);
    }

    /**
     * parse xml
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");

            SAXParserFactory spf = SAXParserFactory.newInstance();

            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();

            provinceList = handler.getDataList();

            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0)
                            .getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }

            mProvinceDatass = Arrays.asList(new String[provinceList.size()]);
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatass.set(i, provinceList.get(i).getName());

                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {

                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j)
                            .getDistrictList();
                    String[] distrinctNameArray = new String[districtList
                            .size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList
                            .size()];
                    for (int k = 0; k < districtList.size(); k++) {

                        DistrictModel districtModel = new DistrictModel(
                                districtList.get(k).getName(), districtList
                                .get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(),
                                districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void setOnAddressCListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        //showSelectedResult();

        if (onAddressCListener != null) {
            onAddressCListener.onClick(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
        }

        dismiss();

    }


}
