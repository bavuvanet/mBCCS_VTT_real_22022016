package com.viettel.bss.viettelpos.v4.commons.auto;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.auto.dto.SuggestionObj;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.customview.InstantAutoComplete;
import com.viettel.bss.viettelpos.v4.helper.DatabaseHelper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.viettel.bss.viettelpos.v4.R.string.txt_select_reason;

/**
 * Created by diepdc-pc on 7/3/2017.
 */

public class AutoCompleteUtil implements AutoConst {
    private String mFormat = "yyyyMMddHHmmss";
    private static AutoCompleteUtil sInstance = null;
    private Context mContext;

    public AutoCompleteUtil(Context context) {
        mContext = context;
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat);
        return sdf.format(new Date());
    }

    public static synchronized AutoCompleteUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AutoCompleteUtil(context);
        }
        return sInstance;
    }

    public void clearPrefUtil(final String key) {
        PrefUtil.clear(mContext, key);
    }

    private void clearAllSuggestion() {
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        dbHelper.deleteAllSuggestion();
    }

    public void clearSuggestionById(final String suggestionId) {
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        dbHelper.deleteSuggestion(suggestionId);
    }

    public void saveTemplate(String prefKey, Map<String, String> mMapTemplate) {
        if (mMapTemplate == null || mMapTemplate.size() == 0) return;

        mMapTemplate.put(MAP_KEY, prefKey);

        String time = getCurrentTime();
        mMapTemplate.put(TIME_STAMP, time);
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        dbHelper.insertTemplate(mMapTemplate);
    }

    // Calculate the number of days between two dates
    // and check with MAX_SAVE_TIME
    public void checkExpirationDate() {
        try {
            String startDateStr = PrefUtil.getString(mContext, EXPIRATION_DATE);
            if (TextUtils.isEmpty(startDateStr)) {
                setDateToCheckExpiration();
                return;
            }
            //HH converts hour in 24 hours format (0-23), day calculation
            SimpleDateFormat format = new SimpleDateFormat(mFormat);

            Date d1 = format.parse(startDateStr);
            Date d2 = format.parse(getCurrentTime());
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            //long diffSeconds = diff / 1000 % 60;
            // long diffMinutes = diff / (60 * 1000) % 60;
            // long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            // System.out.print(diffHours + " hours, ");
            // System.out.print(diffMinutes + " minutes, ");
            // System.out.print(diffSeconds + " seconds.");

            System.out.print(startDateStr + " startDateStr, ");
            System.out.print(getCurrentTime() + " getCurrentTime(), \n");

            if (diffDays >= MAX_SAVE_TIME) {
                clearAllSuggestion();
                clearPrefUtil(EXPIRATION_DATE);
                setDateToCheckExpiration();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    // set start date
    private void setDateToCheckExpiration() {
        String date = PrefUtil.getString(mContext, EXPIRATION_DATE);
        if (TextUtils.isEmpty(date)) {
            PrefUtil.save(mContext, EXPIRATION_DATE, getCurrentTime());
        }
    }

    // method to get suggestion data
    private synchronized ArrayList<SuggestionObj> getSuggestionList(String prefKey) {
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        return dbHelper.loadAllSuggestionByKey(Session.userName, prefKey);
    }

    public synchronized void addToSuggestionList(String prefKey, String input) {
        if (TextUtils.isEmpty(input) || input.trim().length() == 0) {
            return;
        }
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        ArrayList<SuggestionObj> list = dbHelper.loadAllSuggestionByKey(Session.userName, prefKey);
        for (int i = 0, n = list.size(); i < n; i++) {
            SuggestionObj item = list.get(i);
            // case of update
            if (prefKey.equals(item.sgg_key) && input.equals(item.sgg_value)) {
                item.sgg_selected_count++;
                dbHelper.updateSuggestion(item.sgg_id, item.sgg_selected_count, getCurrentTime());
                return;
            }
        }
        // add new
        SuggestionObj suggestionObj = new SuggestionObj();
        suggestionObj.sgg_login_user = Session.userName;
        suggestionObj.sgg_key = prefKey;
        suggestionObj.sgg_value = input;
        suggestionObj.sgg_datetime = getCurrentTime();
        suggestionObj.sgg_selected_count = 1;

        dbHelper.insertSuggestion(suggestionObj);
    }

    // show suggestions
    public void autoComplete(String prefKey, InstantAutoComplete autoCompleteTextView) {
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        if (suggestionList == null || suggestionList.size() == 0) {
            return;
        }
        // check suggestion max
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        for (int i = 0; i < suggestionList.size(); i++) {
            SuggestionObj item = suggestionList.get(i);
            if (suggestionList.size() > AutoConst.MAX_TEXT_SUGGESTION) {
                dbHelper.deleteSuggestion(item.sgg_id);
                suggestionList.remove(0);
            }
        }
        sortByTime(suggestionList);
        sortByCount(suggestionList);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0, n = suggestionList.size(); i < n; i++) {
            list.add(suggestionList.get(i).sgg_value);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item_suggest, list);
        autoCompleteTextView.setAdapter(adapter);
    }

    public static void sortByCount(ArrayList<SuggestionObj> suggestionList) {
        Collections.sort(suggestionList, new Comparator<SuggestionObj>() {
            @Override
            public int compare(SuggestionObj obj1, SuggestionObj obj2) {
                int count1 = obj1.sgg_selected_count;
                int count2 = obj2.sgg_selected_count;

                if (count1 == count2)
                    return 0;
                else if (count1 < count2)
                    return 1;
                else
                    return -1;
            }
        });
    }

    public static void sortByTime(ArrayList<SuggestionObj> suggestionList) {
        Collections.sort(suggestionList, new Comparator<SuggestionObj>() {
            @Override
            public int compare(SuggestionObj obj1, SuggestionObj obj2) {

                long count1 = Long.parseLong(obj1.sgg_datetime);
                long count2 = Long.parseLong(obj2.sgg_datetime);

                if (count1 == count2)
                    return 0;
                else if (count1 < count2)
                    return 1;
                else
                    return -1;
            }
        });
    }

    public ArrayList<AreaObj> sortStockBySelectedCount(String prefKey, ArrayList<AreaObj> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<AreaObj>() {
                @Override
                public int compare(AreaObj obj1, AreaObj obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        return arrInput;
    }

    public ArrayList<ProductOfferingDTO> sortPakageBySelectedCount(String prefKey, ArrayList<ProductOfferingDTO> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<ProductOfferingDTO>() {
                @Override
                public int compare(ProductOfferingDTO obj1, ProductOfferingDTO obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        return arrInput;
    }

    public ArrayList<HTHMBeans> sortCodeHTHMBySelectedCount(String prefKey, ArrayList<HTHMBeans> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getCodeName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<HTHMBeans>() {
                @Override
                public int compare(HTHMBeans obj1, HTHMBeans obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        return arrInput;
    }

    public ArrayList<SubTypeBeans> sortCategoryTBBySelectedCount2(String prefKey, ArrayList<SubTypeBeans> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<SubTypeBeans>() {
                @Override
                public int compare(SubTypeBeans obj1, SubTypeBeans obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        return arrInput;
    }

    public ArrayList<String> sortCategoryTBBySelectedCount(String prefKey, ArrayList<String> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        String loaithuebao = mContext.getString(R.string.chonloaithuebao);
        for (int i = 0, n = arrInput.size(); i < n; i++) {
            if (loaithuebao.equals(arrInput.get(i).toString())) {
                arrInput.remove(i);
                break;
            }
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).equals(data.sgg_value)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(data.sgg_selected_count).append(AUTO_SPLIT_KEY).append(data.sgg_value);
                        arrInput.set(i, sb.toString());
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    if (s1.indexOf(AUTO_SPLIT_KEY) < 0) {
                        s1 = "0" + AUTO_SPLIT_KEY + s1;
                    }
                    if (s2.indexOf(AUTO_SPLIT_KEY) < 0) {
                        s2 = "0" + AUTO_SPLIT_KEY + s2;
                    }
                    int t1 = Integer.parseInt(s1.substring(0, s1.indexOf(AUTO_SPLIT_KEY)));
                    int t2 = Integer.parseInt(s2.substring(0, s2.indexOf(AUTO_SPLIT_KEY)));
                    return t2 - t1;
                }
            });
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                String data = arrInput.get(i);
                if (data.indexOf(AUTO_SPLIT_KEY) >= 0) {
                    data = data.substring(data.indexOf(AUTO_SPLIT_KEY) + 1, data.length());
                    arrInput.set(i, data);
                }
            }
        }
        arrInput.add(0, loaithuebao);
        return arrInput;
    }

    public ArrayList<PromotionTypeBeans> sortCodePromotionBySelectedCount(String prefKey, ArrayList<PromotionTypeBeans> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        boolean isSelectMKM = false;
        String chonctkm1 = mContext.getResources().getString(R.string.chonctkm1);
        String selectMKM = mContext.getResources().getString(R.string.selectMKM);
        for (int i = 0; i < arrInput.size(); i++) {
            if (arrInput.get(i).getName().equals(chonctkm1)) {
                arrInput.remove(i);
            }
            if (arrInput.get(i).getName().equals(selectMKM)) {
                arrInput.remove(i);
                isSelectMKM = true;
            }
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<PromotionTypeBeans>() {
                @Override
                public int compare(PromotionTypeBeans obj1, PromotionTypeBeans obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        // if(isChonctkm1){
        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
        promotionTypeBeans.setCodeName(chonctkm1);
        promotionTypeBeans.setPromProgramCode("");
        arrInput.add(0, promotionTypeBeans);
        //    }
        if (isSelectMKM) {
            PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
            promotionTypeBeans1.setCodeName(selectMKM);
            promotionTypeBeans1.setName(selectMKM);
            promotionTypeBeans1.setPromProgramCode("-2");
            arrInput.add(1, promotionTypeBeans1);
        }
        return arrInput;
    }

    public ArrayList<String> sortCDTBySelectedCount(String prefKey, ArrayList<String> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        String notcdc = mContext.getResources().getString(R.string.notcdc);
        boolean isNotCDC = false;
        for (int i = 0, n = arrInput.size(); i < n; i++) {
            if (arrInput.get(i).equals(notcdc)) {
                arrInput.remove(i);
                isNotCDC = true;
                break;
            }
        }

        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).equals(data.sgg_value)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(data.sgg_selected_count).append(AUTO_SPLIT_KEY).append(data.sgg_value);
                        arrInput.set(i, sb.toString());
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    if (s1.indexOf(AUTO_SPLIT_KEY) < 0) {
                        s1 = "0" + AUTO_SPLIT_KEY + s1;
                    }
                    if (s2.indexOf(AUTO_SPLIT_KEY) < 0) {
                        s2 = "0" + AUTO_SPLIT_KEY + s2;
                    }
                    int t1 = Integer.parseInt(s1.substring(0, s1.indexOf(AUTO_SPLIT_KEY)));
                    int t2 = Integer.parseInt(s2.substring(0, s2.indexOf(AUTO_SPLIT_KEY)));
                    return t2 - t1;
                }
            });
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                String data = arrInput.get(i);
                if (data.indexOf(AUTO_SPLIT_KEY) >= 0) {
                    data = data.substring(data.indexOf(AUTO_SPLIT_KEY) + 1, data.length());
                    arrInput.set(i, data);
                }
            }
        }
        if (isNotCDC) {
            arrInput.add(0, notcdc);
        }
        return arrInput;
    }

    public List<ReasonDTO> sortReasonChangeSimBySelectedCount(String prefKey, List<ReasonDTO> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        String txt_select = mContext.getString(R.string.txt_select);
        String txt_reg_new_select_reason = mContext.getString(txt_select_reason);

        for (int i = 0; i < arrInput.size(); i++) {
            if (arrInput.get(i).getName().equals(txt_select) || arrInput.get(i).getName().equals(txt_reg_new_select_reason)) {
                arrInput.remove(i);
                break;
            }
        }

        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);

                    String text = arrInput.get(i).getReasonCode() + arrInput.get(i).getName();
                    if (text.equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<ReasonDTO>() {
                @Override
                public int compare(ReasonDTO obj1, ReasonDTO obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        ReasonDTO item = new ReasonDTO();
        item.setReasonId("");
        if (prefKey.equals(AUTO_REG_NEW_REASON)) {
            // item.setName(txt_reg_new_select_reason);
        } else {
            item.setName(txt_select); // change sim
            arrInput.add(0, item);
        }
        return arrInput;
    }

    public ArrayList<ReasonDTO> sortChooseReasonBySelectedCount(String prefKey, ArrayList<ReasonDTO> arrInput) {
        if (arrInput == null || prefKey == null) {
            return null;
        }
        ArrayList<SuggestionObj> suggestionList = getSuggestionList(prefKey);
        boolean isSort = false;
        if (suggestionList != null) {
            for (int i = 0, n = arrInput.size(); i < n; i++) {
                for (int k = 0, m = suggestionList.size(); k < m; k++) {
                    SuggestionObj data = suggestionList.get(k);
                    if (arrInput.get(i).getName().equals(data.sgg_value)) {
                        arrInput.get(i).setSelectedCount(data.sgg_selected_count);
                        isSort = true;
                    }
                }
            }
        }
        if (isSort) {
            // desc order
            Collections.sort(arrInput, new Comparator<ReasonDTO>() {
                @Override
                public int compare(ReasonDTO obj1, ReasonDTO obj2) {
                    int count1 = obj1.getSelectedCount();
                    int count2 = obj2.getSelectedCount();

                    if (count1 == count2)
                        return 0;
                    else if (count1 < count2)
                        return 1;
                    else
                        return -1;
                }
            });
        }
        return arrInput;
    }

    // set selected item of Spinner by value
    public int setSpinnerSelection(final Spinner spinner, final String currentKey, final boolean isFirst) {
        try {
            if (!isFirst || spinner == null || currentKey == null) {
                return -1;
            }
            spinner.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                        String tb = (String) spinner.getAdapter().getItem(i);
                        if (currentKey.equals(tb)) {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return -1;
    }
    // setSelection doesn't work correctly.
    // Clear the selected value from Spinner
    public void clearSpinnerSelectionState(Spinner spinner) {
        try {
            ArrayAdapter<String> adapterTB = (ArrayAdapter<String>) spinner.getAdapter();
            if (adapterTB != null) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < adapterTB.getCount(); i++) {
                    list.add(adapterTB.getItem(i).toString());
                }
                adapterTB.clear();
                adapterTB.addAll(list);
                spinner.setAdapter(adapterTB);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }
}
