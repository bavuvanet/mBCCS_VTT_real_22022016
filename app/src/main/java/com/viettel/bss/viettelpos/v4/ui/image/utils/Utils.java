/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.viettel.bss.viettelpos.v4.ui.image.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.work.adapter.CatagoryInforBeans;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBean;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing some static utility methods.
 */
public class Utils {

	public static boolean hasFroyo() {
		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasKitKat() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
	}

	public static <T> void setDataSpinner(Context context, List<T> list, Spinner cbb) {
		if (cbb != null) {
			ArrayAdapter<T> dataAdapterProgress = new ArrayAdapter<>(
					context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			cbb.setSelection(0);
		}
	}

	public static void setDataListView(Context context, List<Spin> list, ListView cbb) {
		if (cbb != null) {
			ArrayAdapter<Spin> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
		}
	}

	public static void setDataSpinnerWithListObject(Context context, String[] list, Spinner cbb) {
		
		if (cbb != null) {
			ArrayAdapter<Object> dataAdapterProgress = new ArrayAdapter<Object>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			cbb.setSelection(0);
		}
	}

	public static <T> void setDataSpinner(Context context, List<T> list, Spinner cbb, int position) {
		if (cbb != null) {
			ArrayAdapter<T> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			if (position < 0 || position >= list.size()) {
				position = 0;
			}
			cbb.setSelection(position);
		}
	}

	public static void setDefaultSpinner(Context context, String value, Spinner cbb) {
		if (cbb != null) {
			List<Spin> list = new ArrayList<>();
			Spin spin = new Spin("", value);
			list.add(spin);

			ArrayAdapter<Spin> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			cbb.setSelection(0);
		}
	}

	public static void setDataComboSpinner(Context context, List<DataComboboxBean> list, Spinner cbb,
			CatagoryInforBeans catagoryInforBeans) {
		if (cbb != null) {
			ArrayAdapter<DataComboboxBean> dataAdapterProgress = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			if (catagoryInforBeans != null) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getValue())) {
					for (DataComboboxBean item : list) {
						if (catagoryInforBeans.getValue().equals(item.getCode())) {
							cbb.setSelection(list.indexOf(item));
							break;
						}
					}
				} else {
					cbb.setSelection(0);
				}
			} else {

			}

		}
	}

	public static void setDataPlege(Context context, List<ReasonPledgeDTO> list, Spinner cbb) {
		if (cbb != null) {
			ArrayAdapter<ReasonPledgeDTO> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			cbb.setSelection(0);
		}
	}
	public static void setDataReasonDTO(Context context, List<ReasonDTO> list, Spinner cbb) {
		if (cbb != null) {
			ArrayAdapter<ReasonDTO> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cbb.setAdapter(dataAdapterProgress);
			cbb.setSelection(0);
		}
	}
	
	
	public static void setDataComboCorSpinner(Context context, List<DataComboboxBean> list, Spinner cbb,
			CorporationCategoryBO catagoryInforBeans) {
		if (cbb != null) {
			ArrayAdapter<DataComboboxBean> dataAdapterProgress = new ArrayAdapter<DataComboboxBean>(context,
					R.layout.spinner_item, list);
			dataAdapterProgress.setDropDownViewResource(R.layout.spinner_dropdown);
			cbb.setAdapter(dataAdapterProgress);
			if (catagoryInforBeans != null) {
				if (!CommonActivity.isNullOrEmpty(catagoryInforBeans.getInforValue())) {
					for (DataComboboxBean item : list) {
						if (catagoryInforBeans.getInforValue().equals(item.getCode())) {
							cbb.setSelection(list.indexOf(item));
							break;
						}
					}
				} else {
					cbb.setSelection(0);
				}
			} else {

			}

		}
	}
	
	public static void setDataSpinnerForm(Context context, List<FormBean> list,
			Spinner cbb) {
		if (cbb != null) {
			 ArrayAdapter<FormBean> dataAdapterProgress = new
                     ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
			 dataAdapterProgress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 cbb.setAdapter(dataAdapterProgress);
			 cbb.setSelection(0);
			
		}
	}

    public static <T> void setDataAutoCompleteTextView(Context context, List<T> list, AutoCompleteTextView cbb) {
        if (cbb != null) {
            ArrayAdapter<T> dataAdapterProgress = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, list);
            cbb.setAdapter(dataAdapterProgress);
        }
    }

    /**
     *
     * @param context
     * @param list
     * @param cbb
     */
    public static void setDataSpinnerOptionSet(Context context,
                                               List<OptionSetValueDTO> list, Spinner cbb) {
        if (cbb != null) {
            if (list == null) {
                list = new ArrayList<OptionSetValueDTO>();
            }
            ArrayAdapter<OptionSetValueDTO> dataAdapterProgress = new ArrayAdapter<OptionSetValueDTO>(
                    context, R.layout.spinner_item, R.id.spinner_value, list);
            // dataAdapterProgress
            // .setDropDownViewResource(R.layout.spinner_dropdown);
            cbb.setAdapter(dataAdapterProgress);
            cbb.setSelection(0);
        }
    }

	public static ArrayList<OptionSetValueDTO> filterTech(Context context, ArrayList<OptionSetValueDTO> list, Spin techSelect){
		ArrayList<OptionSetValueDTO> result = new ArrayList<>();

		for(OptionSetValueDTO optionSetValueDTO : list){
			if(optionSetValueDTO.getValue().equals(techSelect.getValue())){
				result.add(optionSetValueDTO);
			}
		}
		if(result.size() ==0 ){
			Toast.makeText(context,context.getResources().getString(R.string.no_tech), Toast.LENGTH_LONG);
		}
		return result;
	}


}
