package com.viettel.bss.viettelpos.v4.fragment;


import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.CheckBoxSelectAdapter;
import com.viettel.bss.viettelpos.v4.adapter.ReportRevenueAdapter;
import com.viettel.bss.viettelpos.v4.bo.Attr;
import com.viettel.bss.viettelpos.v4.bo.Criteria;
import com.viettel.bss.viettelpos.v4.bo.CriteriaGroup;
import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.SelectBTSDialogFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.Station;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetCriteriaBTS;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListBTS;
import com.viettel.bss.viettelpos.v4.task.AsynTaskLookupRevenueBTS;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.maps.MapView;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.layers.MapLayer;
import com.viettel.maps.objects.InfoWindowOptions;
import com.viettel.maps.objects.MapObject;
import com.viettel.maps.objects.Marker;
import com.viettel.maps.objects.MarkerOptions;
import com.viettel.maps.util.MapConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LookupRevenueStationFragment extends FragmentCommon {

    @BindView(R.id.btnViewRevenue)
    Button btnViewRevenue;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtLookupDate)
    EditText edtLookupDate;
    @BindView(R.id.spnCriteria)
    Spinner spnCriteria;
    @BindView(R.id.edtSearchBTS)
    EditText edtSearchBTS;
    @BindView(R.id.lnInfo)
    LinearLayout lnInfo;
    @BindView(R.id.idMapViewStaff)
    MapView mapView;
    @BindView(R.id.lnTotal)
    LinearLayout lnTotal;
    @BindView(R.id.tvCriteria)
    TextView tvCriteria;
    @BindView(R.id.tvSln)
    TextView tvSln;
    @BindView(R.id.tvDeltaSln)
    TextView tvDeltaSln;
    @BindView(R.id.tvSlt)
    TextView tvSlt;
    @BindView(R.id.tvDeltaSlt)
    TextView tvDeltaSlt;
    @BindView(R.id.vfListAndMap)
    ViewFlipper mViewFlipper;
    @BindView(R.id.recyclerViewCriterial)
    RecyclerView recyclerViewCriterial;
    @BindView(R.id.autoCompleteTextViewBTS)
    AutoCompleteTextView autoCompleteTextViewBTS;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtBTS)
    EditText edtBTS;
    CheckBoxSelectAdapter checkBoxSelectAdapter;
    List<StationBO> lstData;
    List<StationBO> lstBTS;
    List<StationBO> lstBTSExt;
    ReportRevenueAdapter adapter;
    Integer page = 1;
    Integer size = 20;
    CriteriaGroup criteriaGroup;
    SelectBTSDialogFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_lookup_revenue_station_manager;
        setHasOptionsMenu(true);
        setTitleActionBar(R.string.txtReportRevenueBTS);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void unit(View v) {

        MapConfig.changeSRS(MapConfig.SRSType.SRS_900913);
        mapView.setZoom(8);

        new DateTimeDialogWrapper(edtLookupDate, getActivity());
        edtSearchBTS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String data = s.toString().trim();
                if (!CommonActivity.isNullOrEmpty(lstData)) {
                    List<StationBO> lstDataTmp = new ArrayList<StationBO>();
                    if (CommonActivity.isNullOrEmpty(data)) {
                        lstDataTmp = lstData;
                    } else {
                        for (StationBO serviceBO : lstData) {
                            if (serviceBO.getStation() != null && serviceBO.getStation().toUpperCase().contains(data.toUpperCase())) {
                                lstDataTmp.add(serviceBO);
                            }
                        }
                    }
                    formatReportRevenue(lstDataTmp);
                    adapter = new ReportRevenueAdapter(getActivity(), lstReportData);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        spnCriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lnInfo.setVisibility(View.GONE);
                criteriaGroup = (CriteriaGroup) spnCriteria.getSelectedItem();
                checkBoxSelectAdapter = new CheckBoxSelectAdapter(getActivity(), createCriterial(criteriaGroup.getLstCriterias()), new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        lnInfo.setVisibility(View.GONE);
                    }
                });
                recyclerViewCriterial.setAdapter(checkBoxSelectAdapter);
                recyclerViewCriterial.setHasFixedSize(true);
                recyclerViewCriterial.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new AsynTaskGetCriteriaBTS(getActivity(), new OnPostExecuteListener<List<CriteriaGroup>>() {
            @Override
            public void onPostExecute(List<CriteriaGroup> result, String errorCode, String description) {
                Utils.setDataSpinner(getActivity(), result, spnCriteria);
                checkBoxSelectAdapter = new CheckBoxSelectAdapter(getActivity(), createCriterial(result.get(0).getLstCriterias()), new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        lnInfo.setVisibility(View.GONE);
                    }
                });
                recyclerViewCriterial.setAdapter(checkBoxSelectAdapter);
                recyclerViewCriterial.setHasFixedSize(true);
                recyclerViewCriterial.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }, moveLogInAct).execute();

        new AsynTaskGetListBTS(getActivity(), new OnPostExecuteListener<List<StationBO>>() {
            @Override
            public void onPostExecute(List<StationBO> result, String errorCode, String description) {
                lstBTS = result;
                if (CommonActivity.isNullOrEmpty(lstBTSExt)) {
                    lstBTSExt = convertToStationBO(initLstBTS(lstBTS));
                }

                if (!CommonActivity.isNullOrEmpty(result)) {
                    Log.d(Constant.TAG, "lstBTS size = " + lstBTS.size());
                    for (StationBO stationBO : result) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .icon(BitmapFactory.decodeResource(
                                        getResources(),
                                        R.drawable.location_pin))
                                .position(
                                        new LatLng(stationBO.getLatitude(),
                                                stationBO.getLongitude()));
                        Marker marker = mapView.addMarker(markerOptions);
                        marker.setUserData(stationBO);
                    }

                    final StationBO stationBO = result.get(0);
                    mapView.setCenter(new LatLng(stationBO.getLatitude(), stationBO.getLongitude()));

                    mapView.setMarkerClickListener(new MapLayer.OnClickListener() {
                        @Override
                        public boolean onClick(Point point, LatLng latLng, MapObject mapObject) {
                            Marker marker = (Marker) mapObject;
                            stationInfoWindow = (StationBO) marker.getUserData();
                            showInfoWindow(stationInfoWindow);
                            return true;
                        }
                    });

                    mapView.setInfoWindowClickListener(new MapLayer.OnClickListener() {
                        @Override
                        public boolean onClick(Point point, LatLng latLng, MapObject mapObject) {
                            initBTSExt(stationInfoWindow);
                            showTapMap();
                            return false;
                        }
                    });

                    initAutoCompleteBTS(lstBTS);
                }
            }
        }, moveLogInAct).execute();

        edtBTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = SelectBTSDialogFragment.newInstance(lstBTSExt);
                fragment.setOnItemClickListener(onItemClickListener);
                fragment.show(getFragmentManager(), "Select BTS");
            }
        });


        autoCompleteTextViewBTS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchBTS();
            }
        });

    }

    private void initBTSExt(StationBO stationBO) {
        String[] arrBTS = stationBO.getChildren().split(";");
        for (StationBO station : lstBTSExt) {
            station.setIsSelected(false);
            for (String bts : arrBTS) {
                if (bts.equalsIgnoreCase(station.getName())) {
                    station.setIsSelected(true);
                    break;
                }
            }
        }
        initEdtBTS();
    }

    StationBO stationInfoWindow;
    private void showInfoWindow(StationBO stationBOInfo) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions(stationBOInfo.getCode(), new LatLng(stationBOInfo.getLatitude(), stationBOInfo.getLongitude()));
        infoWindowOptions.snippet(stationBOInfo.getName() + "\n" + "DS tram con: " + stationBOInfo.getChildren());
        infoWindowOptions.anchor(new Point(0, -BitmapFactory.decodeResource(
                getResources(),
                R.drawable.location_pin).getHeight()));

        mapView.showInfoWindow(infoWindowOptions);
    }

    private List<String> initLstBTS(List<StationBO> lstData) {
        List<String> lstBTS = new ArrayList<>();
        for (StationBO stationBO : lstData) {
            String[] arrBTS = stationBO.getChildren().split(";");
            for (String bts : arrBTS) {
                lstBTS.add(bts);
            }
        }
        return lstBTS;
    }

    private void initAutoCompleteBTS(List<StationBO> lstData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, initLstBTS(lstData));
        autoCompleteTextViewBTS.setAdapter(adapter);
    }

    private List<StationBO> convertToStationBO(List<String> lstBTS) {
        List<StationBO> lstStationBOs = new ArrayList<>();
        for (String bts : lstBTS) {
            StationBO stationBO = new StationBO();
            stationBO.setName(bts);
            lstStationBOs.add(stationBO);
        }
        return lstStationBOs;
    }

    @Override
    protected void setPermission() {

    }

    private List<List<Criteria>> createCriterial(List<Criteria> lstCriterias) {
        List<List<Criteria>> lstData = new ArrayList<>();
        List<Criteria> lstCriteriasTmp = new ArrayList<>();
        for (int i = 0; i < lstCriterias.size(); i++) {
            lstCriteriasTmp.add(lstCriterias.get(i));
            if ((i + 1) % 4 == 0) {
                lstData.add(lstCriteriasTmp);
                lstCriteriasTmp = new ArrayList<>();
            }
        }
        lstData.add(lstCriteriasTmp);
        return lstData;
    }

    @OnClick(R.id.btnViewRevenue)
    public void lookupRevenueStation() {
        String criteria = "";
        if (criteriaGroup != null) {
            for (Criteria crit : criteriaGroup.getLstCriterias()) {
                if (crit.getChecked()) {
                    criteria += crit.getId() + ",";
                }
            }
        }
        if (CommonActivity.isNullOrEmpty(criteria)) {
            CommonActivity.toast(getActivity(), R.string.mbccs_criteria_required);
            return;
        }
        criteria = criteria.substring(0, criteria.lastIndexOf(","));

        if(CommonActivity.isNullOrEmpty(strlstBTS)){
            CommonActivity.toast(getActivity(), R.string.mbccs_bts_not_select);
            return;
        }

        String date = String.valueOf(DateTimeUtils.convertStringToTime(edtLookupDate.getText().toString(), "dd/MM/yyyy").getTime());
        lnInfo.setVisibility(View.GONE);
        page = 1;

        new AsynTaskLookupRevenueBTS(getActivity(),
                new OnPostExecuteListener<List<StationBO>>() {
                    @Override
                    public void onPostExecute(List<StationBO> result, String errorCode, String description) {
                        if (CommonActivity.isNullOrEmpty(result)) {
                            CommonActivity.toast(getActivity(), getString(R.string.txtViewRevenueBTSNoData));
                        } else {
                            lnInfo.setVisibility(View.VISIBLE);
                            lstData = result;

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            formatReportRevenue(result);
                            adapter = new ReportRevenueAdapter(getActivity(), lstReportData);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                }, moveLogInAct, date, criteria, strlstBTS, page, size, false).execute();
    }

    List<String> lstReportTotal = new ArrayList<>();
    List<List<String>> lstReportData = new ArrayList<>();

    private void initHeaderTotal() {
        if (!CommonActivity.isNullOrEmpty(lstReportTotal)) {
            lnTotal.setVisibility(View.VISIBLE);
            tvCriteria.setText(lstReportTotal.get(0));
            tvSln.setText(StringUtils.formatMoney(lstReportTotal.get(1)));

            Long delta = DataUtils.safeToLong(lstReportTotal.get(2));
            if(delta > 0) {
                tvDeltaSln.setTextColor(getResources().getColor(R.color.green));
                tvDeltaSln.setText( "+" + StringUtils.formatMoney(lstReportTotal.get(2)));
            } else {
                tvDeltaSln.setTextColor(getResources().getColor(R.color.red));
                tvDeltaSln.setText(StringUtils.formatMoney(lstReportTotal.get(2)));
            }
            tvSlt.setText(StringUtils.formatMoney(lstReportTotal.get(3)));

            delta = DataUtils.safeToLong(lstReportTotal.get(4));
            if(delta > 0) {
                tvDeltaSlt.setTextColor(getResources().getColor(R.color.green));
                tvDeltaSlt.setText("+" + StringUtils.formatMoney(lstReportTotal.get(4)));
            } else {
                tvDeltaSlt.setTextColor(getResources().getColor(R.color.red));
                tvDeltaSlt.setText(StringUtils.formatMoney(lstReportTotal.get(4)));
            }
        } else {
            lnTotal.setVisibility(View.GONE);
        }
    }

    private void formatReportRevenue(List<StationBO> lstData) {
        if (CommonActivity.isNullOrEmpty(lstData)) {
            return;
        }

        lstReportData.clear();
        lstReportTotal.clear();

        Double sumSln = new Double("0");
        Double deltaSln = new Double("0");
        Double sumSlt = new Double("0");
        Double deltaSlt = new Double("0");

        StationBO station = lstData.get(0);
        int attrSize = station.getAttrs().size();
        for (StationBO stationBO : lstData) {
            if (attrSize == 1) {
                Attr attrBO = stationBO.getAttrs().get(0);
                List<String> lstStation = new ArrayList<>();
                lstStation.add(stationBO.getStation());
                lstStation.add(attrBO.getName());
                lstStation.add(attrBO.getAttrExts().get(0).getValue());
                lstStation.add(String.valueOf(attrBO.getAttrExts().get(0).getValueInc()));
                lstStation.add(attrBO.getAttrExts().get(1).getValue());
                lstStation.add(String.valueOf(attrBO.getAttrExts().get(1).getValueInc()));

                lstReportData.add(lstStation);

                sumSln += new Double(attrBO.getAttrExts().get(0).getValue());
                deltaSln += new Double(attrBO.getAttrExts().get(0).getValueInc());
                sumSlt += new Double(attrBO.getAttrExts().get(1).getValue());
                deltaSlt += new Double(attrBO.getAttrExts().get(1).getValueInc());
            } else {
                //add header theo tram
                lstReportData.add(initHeaderRevenue(stationBO.getStation()));

                for (Attr attrBO : stationBO.getAttrs()) {
                    List<String> lstStation = new ArrayList<String>();
                    lstStation.add("");
                    lstStation.add(attrBO.getName());
                    lstStation.add(attrBO.getAttrExts().get(0).getValue());
                    lstStation.add(String.valueOf(attrBO.getAttrExts().get(0).getValueInc()));
                    lstStation.add(attrBO.getAttrExts().get(1).getValue());
                    lstStation.add(String.valueOf(attrBO.getAttrExts().get(1).getValueInc()));

                    lstReportData.add(lstStation);
                }
            }
        }


        if (attrSize == 1) {
            lstReportTotal.add(station.getAttrs().get(0).getName());
            lstReportTotal.add("" + sumSln);
            lstReportTotal.add("" + deltaSln);
            lstReportTotal.add("" + sumSlt);
            lstReportTotal.add("" + deltaSlt);
        }

        initHeaderTotal();
    }

    private List<String> initHeaderRevenue(String station) {
        List<String> lstHeader = new ArrayList<>();
        lstHeader.add(station);
        lstHeader.add("");
        lstHeader.add("");
        lstHeader.add("");
        lstHeader.add("");
        lstHeader.add("");
        return lstHeader;
    }

    MenuItem menuItemMap;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menuItemMap = menu.findItem(R.id.btnMap);
        menuItemMap.setVisible(true);

        MenuItem menuItem = menu.findItem(R.id.btnHome);
        menuItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnMap:
                showTapMap();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean ktMap = false;

    private void showTapMap() {
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.right_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
                getActivity(), R.anim.left_out));

        if (!ktMap) {
            menuItemMap.setIcon(R.drawable.button_list_background);
            ktMap = true;
            MapConfig.changeSRS(MapConfig.SRSType.SRS_4326);
        } else {
            menuItemMap.setIcon(R.drawable.background_button_map);
            ktMap = false;
        }
        mViewFlipper.showNext();
    }

    @OnClick(R.id.imgSearch)
    public void imgSearchOnClick() {
        searchBTS();
    }

    private void searchBTS(){
        String btsCode = autoCompleteTextViewBTS.getText().toString();
        if (CommonActivity.isNullOrEmpty(btsCode)) {
            CommonActivity.toast(getActivity(), getString(R.string.mbccs_bts_required));
            return;
        }

        for (StationBO station : lstBTS) {
            String[] arrStation = station.getChildren().split(";");
            for (String btsChildren : arrStation) {
                if (btsCode.equalsIgnoreCase(btsChildren)) {
                    if(station.getLatitude() == null
                            || station.getLongitude() == null
                            || station.getLatitude().equals(0L)
                            || station.getLongitude().equals(0L)){
                        CommonActivity.toast(getActivity(), getString(R.string.mbccs_bts_location));
                        return;
                    }
                    stationInfoWindow = station;
                    try {
                        mapView.setCenter(new LatLng(station.getLatitude(), station.getLongitude()));
                        showInfoWindow(station);
                    }catch (Exception ex){
                        Log.d(Constant.TAG, "Error mapView setCenter", ex);
                    }
                    return;
                }
            }
        }

        CommonActivity.toast(getActivity(), getString(R.string.mbccs_bts_invalid));
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object item) {
            lstBTSExt = (List<StationBO>) item;
            initEdtBTS();
        }
    };

    String strlstBTS = "";
    private void initEdtBTS() {
        int index = 0;
        strlstBTS = "";
        if(!CommonActivity.isNullOrEmptyArray(lstBTSExt)){
            for (StationBO station : lstBTSExt) {
                if (station.getIsSelected()) {
                    strlstBTS += station.getName() + ",";
                    index++;
                }
            }
        }


        if(CommonActivity.isNullOrEmpty(strlstBTS)){
            edtBTS.setText(getString(R.string.mbccs_select_bts));
            return;
        }

        strlstBTS = strlstBTS.substring(0, strlstBTS.lastIndexOf(","));


        if (index == lstBTSExt.size()) {
            edtBTS.setText(getString(R.string.all));
        } else {
            if(index < 6) {
                edtBTS.setText(strlstBTS);
            } else {
                edtBTS.setText(getString(R.string.mbccs_bts_number_selected, index));
            }
        }

        lnInfo.setVisibility(View.GONE);
    }
}
