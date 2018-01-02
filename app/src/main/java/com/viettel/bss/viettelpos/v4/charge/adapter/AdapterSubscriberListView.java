package com.viettel.bss.viettelpos.v4.charge.adapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AccountObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by thuandq on 7/18/2017.
 */
public class AdapterSubscriberListView extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Activity activity;
    private List<SubscriberDTO> listSub;
    private Long subCheck;
    public AdapterSubscriberListView(Activity activity, List<SubscriberDTO> listSub) {
        this.listSub = listSub;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return listSub.size();
    }
    @Override
    public SubscriberDTO getItem(int position) {
        return listSub.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubscriberDTO subscriberDTO = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_subscriber_info, null);
        }
        TextView txtIsdn = (TextView) convertView.findViewById(R.id.txtSub);
        TextView txtService = (TextView) convertView.findViewById(R.id.txtService);
        TextView txtStatusLiquidation = (TextView) convertView.findViewById(R.id.txtLiquidation);
        final CheckBox checkSub = (CheckBox) convertView.findViewById(R.id.cbSubscriber);
        if (!CommonActivity.isNullOrEmpty(subCheck) && subCheck.longValue() == position) {
            checkSub.setChecked(true);
        } else {
            checkSub.setChecked(false);
        }
        convertView.setClickable(false);
        checkSub.setClickable(true);
        checkSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCheck(position, ((CheckBox) v).isChecked());
            }
        });

        txtIsdn.setText(subscriberDTO.getIsdn());
        txtService.setText(subscriberDTO.getTelecomServiceName());
        txtStatusLiquidation.setText(subscriberDTO.getActStatusView());
        return convertView;
    }
    private void subCheck(int position, boolean checkTarget) {
        if (checkTarget) {
            subCheck = Long.valueOf(position);
        } else {
            subCheck = null;
        }
//        for (int i = 0; i < listSub.size(); i++) {
//            SubscriberDTO dto = listSub.get(i);
//            if (i == position) {
//                dto.setChecker(checkTarget);
//                if (checkTarget) subCheck = dto;
//                else subCheck = null;
//            } else {
//                dto.setChecker(false);
//            }
//            listSub.set(i, dto);
//        }
        notifyDataSetChanged();
    }
    public List<SubscriberDTO> getListSubCheck() {
        List<SubscriberDTO> result = new ArrayList<>();
        if (CommonActivity.isNullOrEmpty(subCheck)) return result;
        result.add(listSub.get(subCheck.intValue()));
        return result;
    }
}
