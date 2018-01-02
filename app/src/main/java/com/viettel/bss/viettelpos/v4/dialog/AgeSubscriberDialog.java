package com.viettel.bss.viettelpos.v4.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.ChargeAgeSubscriberAdapter;
import com.viettel.bss.viettelpos.v4.bo.AgeSubscriberDTO;
import com.viettel.bss.viettelpos.v4.bo.SubPreChargeDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.utils.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thuandq on 10/22/2017.
 */

public class AgeSubscriberDialog extends FragmentCommon {
    View mView;
    Activity activity;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.noMonth)
    TextView noMonth;
    @BindView(R.id.noMonthNoData)
    TextView noMonthNoData;
    @BindView(R.id.totalChargeTitle)
    TextView totalChargeTitle;
    @BindView(R.id.totalCharge)
    TextView totalCharge;
    @BindView(R.id.totalChargeNotData)
    TextView totalChargeNotData;
    @BindView(R.id.avgCharge)
    TextView avgCharge;
    @BindView(R.id.avgChargeNoData)
    TextView avgChargeNoData;
    @BindView(R.id.tvListNotData)
    TextView tvListNotData;

    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.icTableChart)
    ImageView icTableChart;
    @BindView(R.id.tableChart)
    LinearLayout tableChart;
    @BindView(R.id.rvCharge)
    RecyclerView rvCharge;
    Boolean show = true;
    SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.layout_ages_subscriber;
        ButterKnife.bind(getActivity());
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.txt_age_subscriber));
    }

    @Override
    protected void unit(View v) {
        Bundle bundle = getArguments();
        rvCharge.setHasFixedSize(true);
        rvCharge.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCharge.setNestedScrollingEnabled(false);
        if (bundle != null) {
            AgeSubscriberDTO ageSubscriberDTO = (AgeSubscriberDTO) bundle.getSerializable("ageSubscriberDTO");
            if (!CommonActivity.isNullOrEmpty(ageSubscriberDTO)) {
                if (!CommonActivity.isNullOrEmpty(ageSubscriberDTO.getAge())) {
                    if (ageSubscriberDTO.getAge() <= 0) {
                        noMonth.setText("0 tháng");
                    } else {
                        Long year = ageSubscriberDTO.getAge() / 12;
                        Long month = ageSubscriberDTO.getAge() % 12;
                        String age = (year > 0 ? year + " năm " : "") + (month > 0 ? month + " tháng" : "");
                        noMonth.setText(age);
                    }
                    noMonthNoData.setVisibility(View.GONE);
                } else {
                    noMonth.setText("");
                    noMonthNoData.setVisibility(View.VISIBLE);
                }
                if (!CommonActivity.isNullOrEmpty(ageSubscriberDTO.getTotalCharge())) {
                    totalCharge.setText(StringUtils.formatMoney(ageSubscriberDTO.getTotalCharge().longValue() + "") + " VND");
                    totalChargeNotData.setVisibility(View.GONE);
                } else {
                    totalCharge.setText("");
                    totalChargeNotData.setVisibility(View.VISIBLE);
                }
                if (!CommonActivity.isNullOrEmpty(ageSubscriberDTO.getAvgCharge())) {
                    avgCharge.setText(StringUtils.formatMoney(ageSubscriberDTO.getAvgCharge().longValue() + "") + " VND/tháng");
                    avgChargeNoData.setVisibility(View.GONE);
                } else {
                    avgCharge.setText("");
                    avgChargeNoData.setVisibility(View.VISIBLE);
                }
                if (!CommonActivity.isNullOrEmpty(ageSubscriberDTO.getSubPreChargeDTOList())) {
                    List<SubPreChargeDTO> subPreChargeDTOs = ageSubscriberDTO.getSubPreChargeDTOList();
                    Comparator<SubPreChargeDTO> asc = new Comparator<SubPreChargeDTO>() {
                        @Override
                        public int compare(SubPreChargeDTO o1, SubPreChargeDTO o2) {
                            return o1.getMonthId().compareTo(o2.getMonthId());
                        }
                    };
                    //sắp xếp tăng dần
                    Collections.sort(subPreChargeDTOs, asc);
                    // show ra bảng
                    initTableLayout(subPreChargeDTOs);
                    // show ra bieu do đường
                    initLineChart(subPreChargeDTOs);
                    // show ra bieu do tron
//                    initPieChart(subPreChargeDTOs);

                    show = true;
                    tvListNotData.setVisibility(View.GONE);
                    tableChart.setVisibility(View.VISIBLE);
                } else {
                    tvListNotData.setVisibility(View.VISIBLE);
                    tableChart.setVisibility(View.GONE);
                    pieChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.GONE);
                    show = false;
                }
            }
        }
    }

    @Override
    protected void setPermission() {

    }

    private void initLineChart(List<SubPreChargeDTO> subPreChargeDTOs) {
        List<Entry> entries = new ArrayList<>();
        int i = 0;
        Date today = Calendar.getInstance().getTime();

        for (SubPreChargeDTO preChargeDTO : subPreChargeDTOs) {
            if (!CommonActivity.isNullOrEmpty(preChargeDTO) && preChargeDTO.getMonthId().length() >= 6) {
                try {
                    String strMonth = preChargeDTO.getMonthId().substring(4, 6) + "/" + preChargeDTO.getMonthId().substring(0, 4);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(df.parse(strMonth));
                    int subMonth = DateTimeUtils.monthsBetween(today, calendar.getTime());
                    entries.add(new Entry(subMonth, preChargeDTO.getTotalCharge().longValue(), strMonth));
                } catch (Exception e) {
                    Log.e(e.getMessage());
                }
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, null);

        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(ContextCompat.getColor(activity, R.color.blue));
        dataSet.setCircleColor(ContextCompat.getColor(activity, R.color.orange));
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setFillAlpha(65);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });
        dataSet.setFillColor(ContextCompat.getColor(activity, R.color.gray_omi));
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setValueTextColor(Color.GREEN);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawValues(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (Math.abs(value - (int) value) > 0.01) return "";
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, (int) value);
                return df.format(calendar.getTime());
            }
        });

        dataSet.setColor(R.color.yellow);
        dataSet.setFormSize(15f);
        dataSet.setValueTextColor(R.color.blue);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.getLegend().setEnabled(false);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    private void initPieChart(List<SubPreChargeDTO> subPreChargeDTOs) {
        pieChart.setUsePercentValues(false);
        pieChart.setHighlightPerTapEnabled(false);
        Description description = new Description();
        description.setText("");
//        description.setText(getString(R.string.chart_charge_12_month));
        description.setTextSize(15f);
        pieChart.setDescription(description);


        // enable hole and configure
        this.pieChart.setDrawHoleEnabled(true);
        this.pieChart.setHoleRadius(2);
        this.pieChart.setTransparentCircleRadius(3);

        // enable rotation of the chart by touch
        this.pieChart.setRotationAngle(0);
        this.pieChart.setRotationEnabled(true);
        this.pieChart.setDrawEntryLabels(false);
        this.pieChart.animateX(500);

        // set a chart value selected listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (SubPreChargeDTO preChargeDTO : subPreChargeDTOs) {
            if (!CommonActivity.isNullOrEmpty(preChargeDTO.getMonthId()) && preChargeDTO.getMonthId().length() >= 6) {
                String month = preChargeDTO.getMonthId().substring(4, 6) + "/" + preChargeDTO.getMonthId().substring(0, 4);
                entries.add(new PieEntry(preChargeDTO.getTotalCharge().floatValue(), month));
            }
        }
        // create pie data set
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return new Float(value).longValue() + "";
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);
        pieChart.setData(data);
        pieChart.setExtraOffsets(5, 5, 50, 5);
        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();


        // customize legends
        Legend legendPie = pieChart.getLegend();
        legendPie.setTextColor(Color.BLUE);

        legendPie.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legendPie.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legendPie.setOrientation(Legend.LegendOrientation.VERTICAL);
        legendPie.setDrawInside(false);
        legendPie.setWordWrapEnabled(true);
        legendPie.setXOffset(0f);
        legendPie.setYOffset(10f);
        legendPie.setYEntrySpace(5f);
        legendPie.setXEntrySpace(6f);

        // undo all highlights
        this.pieChart.highlightValues(null);
        this.pieChart.invalidate();
    }

    private void initTableLayout(List<SubPreChargeDTO> subPreChargeDTOs) {
        ChargeAgeSubscriberAdapter adapter = new ChargeAgeSubscriberAdapter(subPreChargeDTOs, getActivity());
        rvCharge.setAdapter(adapter);
    }

    @OnClick(R.id.icTableChart)
    public void tableChartOnClick() {
        if (show) {
            tableChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.icPieChart)
    public void pieChartOnClick() {
        if (show) {
            tableChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.icChartLine)
    public void lineChartOnClick() {
        if (show) {
            tableChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
        }
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 200);
    }


}
