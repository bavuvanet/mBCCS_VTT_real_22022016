package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.data.FlowRowItem;
import com.viettel.bss.viettelpos.v4.advisory.data.SubPreChargeData;
import com.viettel.bss.viettelpos.v4.advisory.data.VasDTOData;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by thinhhq1 on 7/4/2017.
 */
public class ConsumersInfoDirectFragment extends FragmentCommon {

    @BindView(R.id.tvMonthOne)
    TextView tvMonthOne;
    @BindView(R.id.tvMonthTwo)
    TextView tvMonthTwo;
    @BindView(R.id.tvMonthThree)
    TextView tvMonthThree;

    @BindView(R.id.tvMonthOneFlow)
    TextView tvMonthOneFlow;
    @BindView(R.id.tvMonthTwoFlow)
    TextView tvMonthTwoFlow;
    @BindView(R.id.tvMonthThreeFlow)
    TextView tvMonthThreeFlow;

    @BindView(R.id.tvMonthOnePhoneIn)
    TextView tvMonthOnePhoneIn;
    @BindView(R.id.tvMonthTwoPhoneIn)
    TextView tvMonthTwoPhoneIn;
    @BindView(R.id.tvMonthThreePhoneIn)
    TextView tvMonthThreePhoneIn;
    @BindView(R.id.tvMonthOnePhoneOut)
    TextView tvMonthOnePhoneOut;
    @BindView(R.id.tvMonthTwoPhoneOut)
    TextView tvMonthTwoPhoneOut;
    @BindView(R.id.tvMonthThreePhoneOut)
    TextView tvMonthThreePhoneOut;
    @BindView(R.id.tvMonthOnePhoneGlobal)
    TextView tvMonthOnePhoneGlobal;
    @BindView(R.id.tvMonthTwoPhoneGlobal)
    TextView tvMonthTwoPhoneGlobal;
    @BindView(R.id.tvMonthThreePhoneGlobal)
    TextView tvMonthThreePhoneGlobal;
    @BindView(R.id.tvMonthOneSMSIn)
    TextView tvMonthOneSMSIn;
    @BindView(R.id.tvMonthTwoSMSIn)
    TextView tvMonthTwoSMSIn;
    @BindView(R.id.tvMonthThreeSMSIn)
    TextView tvMonthThreeSMSIn;
    @BindView(R.id.tvMonthOneSMSOut)
    TextView tvMonthOneSMSOut;
    @BindView(R.id.tvMonthTwoSMSOut)
    TextView tvMonthTwoSMSOut;
    @BindView(R.id.tvMonthThreeSMSOut)
    TextView tvMonthThreeSMSOut;
    @BindView(R.id.tvMonthOneSMSGlobal)
    TextView tvMonthOneSMSGlobal;
    @BindView(R.id.tvMonthTwoSMSGlobal)
    TextView tvMonthTwoSMSGlobal;
    @BindView(R.id.tvMonthThreeSMSGlobal)
    TextView tvMonthThreeSMSGlobal;
    @BindView(R.id.tvMonthOneData)
    TextView tvMonthOneData;
    @BindView(R.id.tvMonthTwoData)
    TextView tvMonthTwoData;
    @BindView(R.id.tvMonthThreeData)
    TextView tvMonthThreeData;
    @BindView(R.id.tvMonthOneVas)
    TextView tvMonthOneVas;
    @BindView(R.id.tvMonthTwoVas)
    TextView tvMonthTwoVas;
    @BindView(R.id.tvMonthThreeVas)
    TextView tvMonthThreeVas;
    @BindView(R.id.tvMonthOneTotal)
    TextView tvMonthOneTotal;
    @BindView(R.id.tvMonthTwoTotal)
    TextView tvMonthTwoTotal;
    @BindView(R.id.tvMonthThreeTotal)
    TextView tvMonthThreeTotal;
    @BindView(R.id.tvTitlePieChart)
    TextView tvTitlePieChart;

    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.barChart)
    BarChart barChart;
    @BindView(R.id.pieChart)
    PieChart pieChart;

    @BindView(R.id.llChartOnce)
    LinearLayout llChartOnce;
    @BindView(R.id.llChartTwo)
    LinearLayout llChartTwo;
    @BindView(R.id.tbDataMoney)
    TableLayout tbDataMoney;
    @BindView(R.id.tbDataFlow)
    TableLayout tbDataFlow;
    @BindView(R.id.btChangeShet)
    ImageButton btChangeShet;
    @BindView(R.id.btChangeChart)
    ImageButton btChangeChart;

    @BindView(R.id.cbPhoneIn)
    CheckBox cbPhoneIn;
    @BindView(R.id.cbPhoneOut)
    CheckBox cbPhoneOut;
    @BindView(R.id.cbPhoneGlobal)
    CheckBox cbPhoneGlobal;
    @BindView(R.id.cbSmsIn)
    CheckBox cbSmsIn;
    @BindView(R.id.cbSmsOut)
    CheckBox cbSmsOut;
    @BindView(R.id.cbSmsGlobal)
    CheckBox cbSmsGlobal;
    @BindView(R.id.cbData)
    CheckBox cbData;
    @BindView(R.id.cbVas)
    CheckBox cbVas;
    @BindView(R.id.cbTotal)
    CheckBox cbTotal;

    private boolean isShowLineChart;
    private boolean isShowMoneyShet;

    private int[] numArr = {1, 2, 3};
    private HashMap<Integer, String> numMap;

    private LineDataSet lineSetPhoneIn;
    private LineDataSet lineSetPhoneOut;
    private LineDataSet lineSetPhoneGlobal;
    private LineDataSet lineSetSmsIn;
    private LineDataSet lineSetSmsOut;
    private LineDataSet lineSetSmsGlobal;
    private LineDataSet lineSetData;
    private LineDataSet lineSetVas;
    private LineDataSet lineSetTotal;
    private ArrayList<ILineDataSet> lineDataSets;

    private ArrayList<String> valuesLinePhoneIn;
    private ArrayList<String> valuesLinePhoneOut;
    private ArrayList<String> valuesLinePhoneGlobal;
    private ArrayList<String> valuesLineSmsIn;
    private ArrayList<String> valuesLineSmsOut;
    private ArrayList<String> valuesLineSmsGlobal;
    private ArrayList<String> valuesLineData;
    private ArrayList<String> valuesLineVas;
    private ArrayList<String> valuesLineTotal;

    private ArrayList<String> valuesMonthLast;
    private ArrayList<Integer> colors;
    private ArrayList<SubPreChargeData> subPreChargeDatas;
    private ArrayList<Integer> monthArray;

    public ConsumersInfoDirectFragment(CCOutput ccOutput) {
        super();
        this.subPreChargeDatas = new ArrayList<>(ccOutput.getLstSubPreCharges());
        Collections.sort(subPreChargeDatas, new SubPreChargeData.CompMonth());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.consumers_info_direct_fragment;
    }

    @Override
    protected void unit(View v) {

        this.cbPhoneIn.setChecked(true);
        this.cbPhoneOut.setChecked(false);
        this.cbPhoneGlobal.setChecked(false);
        this.cbSmsIn.setChecked(false);
        this.cbSmsOut.setChecked(false);
        this.cbSmsGlobal.setChecked(false);
        this.cbData.setChecked(false);
        this.cbVas.setChecked(false);
        this.cbTotal.setChecked(true);

        this.isShowMoneyShet = true;
        this.tbDataMoney.setVisibility(View.VISIBLE);
        this.llChartOnce.setVisibility(View.VISIBLE);
        this.llChartTwo.setVisibility(View.VISIBLE);
        this.tbDataFlow.setVisibility(View.GONE);
        this.btChangeShet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowMoneyShet = !isShowMoneyShet;
                if (isShowMoneyShet) {
                    tbDataMoney.setVisibility(View.VISIBLE);
                    llChartOnce.setVisibility(View.VISIBLE);
                    llChartTwo.setVisibility(View.VISIBLE);
                    tbDataFlow.setVisibility(View.GONE);
                    btChangeShet.setImageResource(R.drawable.clock_image);
                } else {
                    tbDataMoney.setVisibility(View.GONE);
                    llChartOnce.setVisibility(View.GONE);
                    llChartTwo.setVisibility(View.GONE);
                    tbDataFlow.setVisibility(View.VISIBLE);
                    btChangeShet.setImageResource(R.drawable.money_button);
                }
            }
        });

        this.cbPhoneIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbPhoneIn.isChecked()) {
                    lineDataSets.add(lineSetPhoneIn);
                } else {
                    lineDataSets.remove(lineSetPhoneIn);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbPhoneOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbPhoneOut.isChecked()) {
                    lineDataSets.add(lineSetPhoneOut);
                } else {
                    lineDataSets.remove(lineSetPhoneOut);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbPhoneGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbPhoneGlobal.isChecked()) {
                    lineDataSets.add(lineSetPhoneGlobal);
                } else {
                    lineDataSets.remove(lineSetPhoneGlobal);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbSmsIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbSmsIn.isChecked()) {
                    lineDataSets.add(lineSetSmsIn);
                } else {
                    lineDataSets.remove(lineSetSmsIn);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbSmsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbSmsOut.isChecked()) {
                    lineDataSets.add(lineSetSmsOut);
                } else {
                    lineDataSets.remove(lineSetSmsOut);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbSmsGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbSmsGlobal.isChecked()) {
                    lineDataSets.add(lineSetSmsGlobal);
                } else {
                    lineDataSets.remove(lineSetSmsGlobal);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbData.isChecked()) {
                    lineDataSets.add(lineSetData);
                } else {
                    lineDataSets.remove(lineSetData);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbVas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbVas.isChecked()) {
                    lineDataSets.add(lineSetVas);
                } else {
                    lineDataSets.remove(lineSetVas);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.cbTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbTotal.isChecked()) {
                    lineDataSets.add(lineSetTotal);
                } else {
                    lineDataSets.remove(lineSetTotal);
                }
                LineData data = new LineData(lineDataSets);
                data.setDrawValues(false);
                lineChart.setData(data);
                lineChart.invalidate();
            }
        });

        this.isShowLineChart = false;
        btChangeChart.setImageResource(R.drawable.line_chart);
        cbPhoneIn.setVisibility(View.INVISIBLE);
        cbPhoneOut.setVisibility(View.INVISIBLE);
        cbPhoneGlobal.setVisibility(View.INVISIBLE);
        cbSmsIn.setVisibility(View.INVISIBLE);
        cbSmsOut.setVisibility(View.INVISIBLE);
        cbSmsGlobal.setVisibility(View.INVISIBLE);
        cbData.setVisibility(View.INVISIBLE);
        cbVas.setVisibility(View.INVISIBLE);
        cbTotal.setVisibility(View.INVISIBLE);
        this.barChart.setVisibility(View.VISIBLE);
        this.lineChart.setVisibility(View.GONE);

        this.btChangeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowLineChart = !isShowLineChart;
                if (isShowLineChart) {
                    barChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                    btChangeChart.setImageResource(R.drawable.bar_chart);

                    cbPhoneIn.setVisibility(View.VISIBLE);
                    cbPhoneOut.setVisibility(View.VISIBLE);
                    cbPhoneGlobal.setVisibility(View.VISIBLE);
                    cbSmsIn.setVisibility(View.VISIBLE);
                    cbSmsOut.setVisibility(View.VISIBLE);
                    cbSmsGlobal.setVisibility(View.VISIBLE);
                    cbData.setVisibility(View.VISIBLE);
                    cbVas.setVisibility(View.VISIBLE);
                    cbTotal.setVisibility(View.VISIBLE);
                } else {
                    barChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                    btChangeChart.setImageResource(R.drawable.line_chart);

                    cbPhoneIn.setVisibility(View.INVISIBLE);
                    cbPhoneOut.setVisibility(View.INVISIBLE);
                    cbPhoneGlobal.setVisibility(View.INVISIBLE);
                    cbSmsIn.setVisibility(View.INVISIBLE);
                    cbSmsOut.setVisibility(View.INVISIBLE);
                    cbSmsGlobal.setVisibility(View.INVISIBLE);
                    cbData.setVisibility(View.INVISIBLE);
                    cbVas.setVisibility(View.INVISIBLE);
                    cbTotal.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.numMap = new HashMap<>();
        this.valuesLinePhoneIn = new ArrayList<>();
        this.valuesLinePhoneOut = new ArrayList<>();
        this.valuesLinePhoneGlobal = new ArrayList<>();
        this.valuesLineSmsIn = new ArrayList<>();
        this.valuesLineSmsOut = new ArrayList<>();
        this.valuesLineSmsGlobal = new ArrayList<>();
        this.valuesLineData = new ArrayList<>();
        this.valuesLineVas = new ArrayList<>();
        this.valuesLineTotal = new ArrayList<>();
        this.valuesMonthLast = new ArrayList<>();

        this.colors = new ArrayList<>();
        this.colors.add(Color.rgb(46, 204, 113));
        this.colors.add(Color.rgb(0, 178, 191));
        this.colors.add(Color.rgb(255, 208, 140));
        this.colors.add(Color.rgb(140, 234, 255));
        this.colors.add(Color.rgb(255, 140, 157));
        this.colors.add(Color.rgb(193, 37, 82));
        this.colors.add(Color.rgb(255, 102, 0));
        this.colors.add(Color.rgb(115, 136, 193));
        this.colors.add(Color.rgb(0, 0, 255));

        this.monthArray = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        this.monthArray.add(month - 1);
        this.monthArray.add(month);
        this.monthArray.add(month + 1);
        this.tvTitlePieChart.setText(
                getString(R.string.advisory_chart_rate_direct_div_toltal_text)
                        + " tháng " + monthArray.get(2));

        SubPreChargeData subPreChargeData = getSubPreData(monthArray.get(0));
        this.tvMonthOne.setText(getResources().getString(R.string.advisory_month_text) + " " + monthArray.get(0));
        this.tvMonthOnePhoneIn.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntTotCharge()));
        this.tvMonthOnePhoneOut.setText(
                StringUtils.formatMoney(subPreChargeData.getvExtTotCharge()));
        this.tvMonthOnePhoneGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntnTotCharge()));
        this.tvMonthOneSMSIn.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntTotCharge()));
        this.tvMonthOneSMSOut.setText(
                StringUtils.formatMoney(subPreChargeData.getsExtTotCharge()));
        this.tvMonthOneSMSGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntnTotCharge()));
        this.tvMonthOneData.setText(
                StringUtils.formatMoney(subPreChargeData.getgTotCharge()));
        this.tvMonthOneVas.setText(
                StringUtils.formatMoney(subPreChargeData.getVasTotCharge()));
        String totalString = "" + getTotalFromData(
                subPreChargeData.getvIntTotCharge(),
                subPreChargeData.getvExtTotCharge(),
                subPreChargeData.getvIntnTotCharge(),
                subPreChargeData.getsIntTotCharge(),
                subPreChargeData.getsExtTotCharge(),
                subPreChargeData.getsIntnTotCharge(),
                subPreChargeData.getgTotCharge(),
                subPreChargeData.getVasTotCharge());
        this.tvMonthOneTotal.setText(
                StringUtils.formatMoney(totalString));
        this.valuesLinePhoneIn.add(subPreChargeData.getvIntTotCharge());
        this.valuesLinePhoneOut.add(subPreChargeData.getvExtTotCharge());
        this.valuesLinePhoneGlobal.add(subPreChargeData.getvIntnTotCharge());
        this.valuesLineSmsIn.add(subPreChargeData.getsIntTotCharge());
        this.valuesLineSmsOut.add(subPreChargeData.getsExtTotCharge());
        this.valuesLineSmsGlobal.add(subPreChargeData.getsIntnTotCharge());
        this.valuesLineData.add(subPreChargeData.getgTotCharge());
        this.valuesLineVas.add(subPreChargeData.getVasTotCharge());
        this.valuesLineTotal.add(totalString);
        this.numMap.put(1, "T" + monthArray.get(0));

        subPreChargeData = getSubPreData(monthArray.get(1));
        this.tvMonthTwo.setText(getString(R.string.advisory_month_text)
                + " " + monthArray.get(1));
        this.tvMonthTwoPhoneIn.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntTotCharge()));
        this.tvMonthTwoPhoneOut.setText(
                StringUtils.formatMoney(subPreChargeData.getvExtTotCharge()));
        this.tvMonthTwoPhoneGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntnTotCharge()));
        this.tvMonthTwoSMSIn.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntTotCharge()));
        this.tvMonthTwoSMSOut.setText(
                StringUtils.formatMoney(subPreChargeData.getsExtTotCharge()));
        this.tvMonthTwoSMSGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntnTotCharge()));
        this.tvMonthTwoData.setText(
                StringUtils.formatMoney(subPreChargeData.getgTotCharge()));
        this.tvMonthTwoVas.setText(
                StringUtils.formatMoney(subPreChargeData.getVasTotCharge()));
        totalString = "" + getTotalFromData(
                subPreChargeData.getvIntTotCharge(),
                subPreChargeData.getvExtTotCharge(),
                subPreChargeData.getvIntnTotCharge(),
                subPreChargeData.getsIntTotCharge(),
                subPreChargeData.getsExtTotCharge(),
                subPreChargeData.getsIntnTotCharge(),
                subPreChargeData.getgTotCharge(),
                subPreChargeData.getVasTotCharge());
        this.tvMonthTwoTotal.setText(
                StringUtils.formatMoney(totalString));
        this.valuesLinePhoneIn.add(subPreChargeData.getvIntTotCharge());
        this.valuesLinePhoneOut.add(subPreChargeData.getvExtTotCharge());
        this.valuesLinePhoneGlobal.add(subPreChargeData.getvIntnTotCharge());
        this.valuesLineSmsIn.add(subPreChargeData.getsIntTotCharge());
        this.valuesLineSmsOut.add(subPreChargeData.getsExtTotCharge());
        this.valuesLineSmsGlobal.add(subPreChargeData.getsIntnTotCharge());
        this.valuesLineData.add(subPreChargeData.getgTotCharge());
        this.valuesLineVas.add(subPreChargeData.getVasTotCharge());
        this.valuesLineTotal.add(totalString);
        this.numMap.put(2, "T" + monthArray.get(1));

        subPreChargeData = getSubPreData(monthArray.get(2));
        this.tvMonthThree.setText(getString(R.string.advisory_month_text)
                + " " + monthArray.get(2));
        this.tvMonthThreePhoneIn.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntTotCharge()));
        this.tvMonthThreePhoneOut.setText(
                StringUtils.formatMoney(subPreChargeData.getvExtTotCharge()));
        this.tvMonthThreePhoneGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getvIntnTotCharge()));
        this.tvMonthThreeSMSIn.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntTotCharge()));
        this.tvMonthThreeSMSOut.setText(
                StringUtils.formatMoney(subPreChargeData.getsExtTotCharge()));
        this.tvMonthThreeSMSGlobal.setText(
                StringUtils.formatMoney(subPreChargeData.getsIntnTotCharge()));
        this.tvMonthThreeData.setText(
                StringUtils.formatMoney(subPreChargeData.getgTotCharge()));
        this.tvMonthThreeVas.setText(
                StringUtils.formatMoney(subPreChargeData.getVasTotCharge()));
        totalString = "" + getTotalFromData(
                subPreChargeData.getvIntTotCharge(),
                subPreChargeData.getvExtTotCharge(),
                subPreChargeData.getvIntnTotCharge(),
                subPreChargeData.getsIntTotCharge(),
                subPreChargeData.getsExtTotCharge(),
                subPreChargeData.getsIntnTotCharge(),
                subPreChargeData.getgTotCharge(),
                subPreChargeData.getVasTotCharge());
        this.tvMonthThreeTotal.setText(
                StringUtils.formatMoney(totalString));
        this.valuesLinePhoneIn.add(subPreChargeData.getvIntTotCharge());
        this.valuesLinePhoneOut.add(subPreChargeData.getvExtTotCharge());
        this.valuesLinePhoneGlobal.add(subPreChargeData.getvIntnTotCharge());
        this.valuesLineSmsIn.add(subPreChargeData.getsIntTotCharge());
        this.valuesLineSmsOut.add(subPreChargeData.getsExtTotCharge());
        this.valuesLineSmsGlobal.add(subPreChargeData.getsIntnTotCharge());
        this.valuesLineData.add(subPreChargeData.getgTotCharge());
        this.valuesLineVas.add(subPreChargeData.getVasTotCharge());
        this.valuesLineTotal.add(totalString);
        this.numMap.put(3, "T" + monthArray.get(2));

        this.valuesMonthLast.add(subPreChargeData.getvIntTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getvExtTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getvIntnTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getsIntTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getsExtTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getsIntnTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getgTotCharge());
        this.valuesMonthLast.add(subPreChargeData.getVasTotCharge());

        this.updateSubChargeFlowData();

        this.lineSetPhoneIn = getLineDataSet(
                getResources().getString(R.string.advisory_phone_in_text),
                this.valuesLinePhoneIn,
                colors.get(0));
        this.lineSetPhoneOut = getLineDataSet(
                getResources().getString(R.string.advisory_phone_out_text),
                this.valuesLinePhoneOut,
                colors.get(1));
        this.lineSetPhoneGlobal = getLineDataSet(
                getResources().getString(R.string.advisory_phone_global_text),
                this.valuesLinePhoneGlobal,
                colors.get(2));
        this.lineSetSmsIn = getLineDataSet(
                getResources().getString(R.string.advisory_sms_in_text),
                this.valuesLineSmsIn,
                colors.get(3));
        this.lineSetSmsOut = getLineDataSet(
                getResources().getString(R.string.advisory_sms_out_text),
                this.valuesLineSmsOut,
                colors.get(4));
        this.lineSetSmsGlobal = getLineDataSet(
                getResources().getString(R.string.advisory_sms_global_text),
                this.valuesLineSmsGlobal,
                colors.get(5));
        this.lineSetData = getLineDataSet(
                getResources().getString(R.string.advisory_data_text),
                this.valuesLineData,
                colors.get(6));
        this.lineSetVas = getLineDataSet(
                getResources().getString(R.string.advisory_vas_text),
                this.valuesLineVas,
                colors.get(7));
        this.lineSetTotal = getLineDataSet(
                getResources().getString(R.string.advisory_total_text),
                this.valuesLineTotal,
                colors.get(8));

        this.lineDataSets = new ArrayList<>();
        this.lineDataSets.add(lineSetPhoneIn);
        this.lineDataSets.add(lineSetTotal);

        LineData data = new LineData(lineDataSets);
        data.setDrawValues(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.rgb(217, 217, 217));
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)value);
            }
        });

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setTextSize(10f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.rgb(217, 217, 217));
        leftAxis.setGranularityEnabled(true);
        leftAxis.setYOffset(0f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(Color.rgb(217, 217, 217));
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(" ");
            }
        });
        //rightAxis.setEnabled(false);

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        //customize legends
        Legend legend = lineChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.BLUE);
        legend.setTextSize(10f);
        legend.setYEntrySpace(4f); // khoang cach dong
        legend.setXEntrySpace(6f);
        legend.setYOffset(0f);     // cach top
        legend.setXOffset(0f);     // cach right
        legend.setForm(Legend.LegendForm.LINE);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        lineChart.setData(data);
        lineChart.invalidate();

        this.drawGroupBar();
        this.drawPieChart();
    }

    @Override
    protected void setPermission() {

    }

    private void updateSubChargeFlowData() {

        this.tvMonthOneFlow.setText(getResources().getString(R.string.advisory_month_text)
                + " " + monthArray.get(0));
        this.tvMonthTwoFlow.setText(getResources().getString(R.string.advisory_month_text)
                + " " + monthArray.get(1));
        this.tvMonthThreeFlow.setText(getResources().getString(R.string.advisory_month_text)
                + " " + monthArray.get(2));

        SubPreChargeData subPreChargeData1 = getSubPreData(monthArray.get(0));
        SubPreChargeData subPreChargeData2 = getSubPreData(monthArray.get(1));
        SubPreChargeData subPreChargeData3 = getSubPreData(monthArray.get(2));

        // table flow
        ArrayList<FlowRowItem> flowRowItems = new ArrayList<>();
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_phone_in_text),
                StringUtils.formatMoney(subPreChargeData1.getvIntDuration()),
                StringUtils.formatMoney(subPreChargeData2.getvExtDuration()),
                StringUtils.formatMoney(subPreChargeData3.getvIntnDuration()),
                "Phút"));
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_phone_out_text),
                StringUtils.formatMoney(subPreChargeData1.getvExtDuration()),
                StringUtils.formatMoney(subPreChargeData2.getvExtDuration()),
                StringUtils.formatMoney(subPreChargeData3.getvExtDuration()),
                "Phút"));
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_phone_global_text),
                StringUtils.formatMoney(subPreChargeData1.getvIntnDuration()),
                StringUtils.formatMoney(subPreChargeData2.getvIntnDuration()),
                StringUtils.formatMoney(subPreChargeData3.getvIntnDuration()),
                "Phút"));
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_sms_in_text),
                StringUtils.formatMoney(subPreChargeData1.getsIntTimes()),
                StringUtils.formatMoney(subPreChargeData2.getsIntTimes()),
                StringUtils.formatMoney(subPreChargeData3.getsIntTimes()),
                "SMS"));
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_sms_out_text),
                StringUtils.formatMoney(subPreChargeData1.getsExtTimes()),
                StringUtils.formatMoney(subPreChargeData2.getsExtTimes()),
                StringUtils.formatMoney(subPreChargeData3.getsExtTimes()),
                "SMS"));
        flowRowItems.add(new FlowRowItem(getString(
                R.string.advisory_sms_global_text),
                StringUtils.formatMoney(subPreChargeData1.getsIntnTimes()),
                StringUtils.formatMoney(subPreChargeData2.getsIntnTimes()),
                StringUtils.formatMoney(subPreChargeData3.getsIntnTimes()),
                "SMS"));

        for (int index = 0; index < flowRowItems.size(); index++) {
            final TableRow tableRow = (TableRow) getActivity().getLayoutInflater()
                    .inflate(R.layout.advisory_tablerow_flow_item_view, null);

            FlowRowItem flowRowItem = flowRowItems.get(index);

            TextView tv1, tv2, tv3, tv4, tv5;
            tv1 = (TextView) tableRow.findViewById(R.id.tv1);
            tv1.setText(flowRowItem.getName());
            tv2 = (TextView) tableRow.findViewById(R.id.tv2);
            tv2.setText(flowRowItem.getValue1());
            tv3 = (TextView) tableRow.findViewById(R.id.tv3);
            tv3.setText(flowRowItem.getValue2());
            tv4 = (TextView) tableRow.findViewById(R.id.tv4);
            tv4.setText(flowRowItem.getValue3());
            tv5 = (TextView) tableRow.findViewById(R.id.tv5);
            tv5.setText(flowRowItem.getUnit());

            if (index % 2 == 0) {
                tv1.setBackgroundResource(R.color.gray_7);
                tv2.setBackgroundResource(R.color.gray_7);
                tv3.setBackgroundResource(R.color.gray_7);
                tv4.setBackgroundResource(R.color.gray_7);
                tv5.setBackgroundResource(R.color.gray_7);
            } else {
                tv1.setBackgroundResource(R.color.gray_8);
                tv2.setBackgroundResource(R.color.gray_8);
                tv3.setBackgroundResource(R.color.gray_8);
                tv4.setBackgroundResource(R.color.gray_8);
                tv5.setBackgroundResource(R.color.gray_8);
            }

            tbDataFlow.addView(tableRow);
        }
    }

    private void drawPieChart() {

        this.pieChart.setUsePercentValues(true);

        // enable hole and configure
        this.pieChart.setDrawHoleEnabled(true);
        this.pieChart.setHoleRadius(2);
        this.pieChart.setTransparentCircleRadius(3);

        // enable rotation of the chart by touch
        this.pieChart.setRotationAngle(0);
        this.pieChart.setRotationEnabled(true);
        this.pieChart.setDrawEntryLabels(false);
        this.pieChart.animateX(500);

        Description descriptionPie = new Description();
        descriptionPie.setText("");
        this.pieChart.setDescription(descriptionPie);

        // add data
        String[] xData = {
                getResources().getString(R.string.advisory_phone_in_text),
                getResources().getString(R.string.advisory_phone_out_text),
                getResources().getString(R.string.advisory_phone_global_text),
                getResources().getString(R.string.advisory_sms_in_text),
                getResources().getString(R.string.advisory_sms_out_text),
                getResources().getString(R.string.advisory_sms_global_text),
                getResources().getString(R.string.advisory_data_text),
                getResources().getString(R.string.advisory_vas_text)
        };
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int index2 = 0; index2 < xData.length; index2++) {
            int yData = (int)Float.parseFloat(valuesMonthLast.get(index2));
            if (yData == 0) {
                continue;
            }
            entries.add(new PieEntry(yData, xData[index2]));
        }

        // create pie data set
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(1f);
        pieDataSet.setIconsOffset(new MPPointF(0, 0));
        pieDataSet.setSelectionShift(3f);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);

        // add a lot of colors
        pieDataSet.setColors(colors);
        pieDataSet.setSelectionShift(0f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        this.pieChart.setData(pieData);
        this.pieChart.setExtraOffsets(5, 5, 50, 5);

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

    private LineDataSet getLineDataSet(String name, ArrayList<String> values, int color) {
        ArrayList<Entry> entries = new ArrayList<>();
        for(int index = 0; index < numArr.length; index++){
            int y = (int)Float.parseFloat(values.get(index));
            entries.add(new Entry(numArr[index], y));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, name);

        lineDataSet.setCircleColor(color);
        lineDataSet.setCircleRadius(2.1f);
        lineDataSet.setCircleColorHole(color);
        lineDataSet.setCircleHoleRadius(2.1f);

        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setDrawValues(false);

        return lineDataSet;
    }

    private int getTotalFromData(
            String phoneIn, String phoneOut, String phoneGlo,
            String smsIn, String smsOut, String smsGlo,
            String data, String vas) {

        int phoneInValue = (int)Float.parseFloat(phoneIn);
        int phoneOutValue = (int)Float.parseFloat(phoneOut);
        int phoneGloValue = (int)Float.parseFloat(phoneGlo);
        int smsInValue = (int)Float.parseFloat(smsIn);
        int smsOutValue = (int)Float.parseFloat(smsOut);
        int smsGloValue = (int)Float.parseFloat(smsGlo);
        int dataValue = (int)Float.parseFloat(data);
        int vasValue = (int)Float.parseFloat(vas);

        int total = phoneInValue + phoneOutValue + phoneGloValue
                + smsInValue + smsOutValue + smsGloValue
                + dataValue + vasValue;
        return total;
    }

    private void drawGroupBar() {

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.setDrawValueAboveBar(false);
        //this.barChart.setExtraOffsets(5, 5, 50, 5);

        Legend l = barChart.getLegend();
        l.setTextColor(Color.BLUE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextColor(Color.BLUE);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.rgb(217, 217, 217));
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(4);
        xAxis.setSpaceMax(1f);
        xAxis.setSpaceMin(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "" + numMap.get((int)value);
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setGridColor(Color.rgb(217, 217, 217));
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setGridColor(Color.rgb(217, 217, 217));
        rightAxis.setTextColor(Color.BLUE);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(" ");
            }
        });

        //data
        float groupSpace = 0.46f;
        float barSpace = 0.01f; // x2 dataset
        float barWidth = 0.26f; // x2 dataset
        // (0.46 + 0.02) * 2 + 0.46 = 1.00 -> interval per "group"

        List<BarEntry> yVals1 = new ArrayList<>();
        List<BarEntry> yVals2 = new ArrayList<>();

        for (int i = 0; i < numArr.length; i++) {
            float val1 = Float.parseFloat(valuesLinePhoneIn.get(i));
            float val2 = Float.parseFloat(valuesLinePhoneOut.get(i));
            float val3 = Float.parseFloat(valuesLinePhoneGlobal.get(i));
            yVals1.add(new BarEntry(numArr[i], new float[]{val1, val2, val3}));
        }

        for (int i = 0; i < numArr.length; i++) {
            float val1 = Float.parseFloat(valuesLineSmsIn.get(i));
            float val2 = Float.parseFloat(valuesLineSmsOut.get(i));
            float val3 = Float.parseFloat(valuesLineSmsGlobal.get(i));
            yVals2.add(new BarEntry(numArr[i], new float[]{val1, val2, val3}));
        }

        BarDataSet set1, set2;

        // create 2 datasets with different types
        set1 = new BarDataSet(yVals1, "Voice");
        set1.setDrawValues(false);
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{
                getResources().getString(R.string.advisory_phone_in_text),
                getResources().getString(R.string.advisory_phone_out_text),
                getResources().getString(R.string.advisory_phone_global_text)
        });

        set2 = new BarDataSet(yVals2, "SMS");
        set2.setDrawValues(false);
        set2.setColors(getColors());
        set2.setStackLabels(new String[]{
                getResources().getString(R.string.advisory_sms_in_text),
                getResources().getString(R.string.advisory_sms_out_text),
                getResources().getString(R.string.advisory_sms_global_text)
        });

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        data.setHighlightEnabled(false);
        barChart.setData(data);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(numArr[0], groupSpace, barSpace);
        barChart.invalidate();
    }

    private int[] getColors() {
        int stacksize = 3;
        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }
        return colors;
    }

    private SubPreChargeData getSubPreData(int month) {
        for (SubPreChargeData subPreChargeData : this.subPreChargeDatas) {
            int monthTemp = DateTimeUtils.getMonthFromString(subPreChargeData.getMonth());
            if (monthTemp == month) {
                return subPreChargeData;
            }
        }
        return new SubPreChargeData();
    }
}