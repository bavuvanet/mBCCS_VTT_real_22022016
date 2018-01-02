package com.viettel.bss.viettelpos.v4.charge.adapter;

import android.app.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import com.viettel.bss.viettelpos.v4.charge.asynctask.AsynCreateNewSerial;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 6/21/2017.
 */

public class SubGoodDTOAdapter extends BaseAdapter {
    private List<SubGoodsDTO> listSubGoods;
    private LayoutInflater layoutInflater;
    private Activity content;
    View.OnClickListener moveLogInAct;

    public SubGoodDTOAdapter(Activity content, List<SubGoodsDTO> productDTOs, View.OnClickListener moveLogInAct) {
        this.content = content;
        this.listSubGoods = productDTOs;
        this.layoutInflater = LayoutInflater.from(content);
        this.moveLogInAct = moveLogInAct;
    }


    public int getCount() {
        return listSubGoods.size();
    }

    @Override
    public SubGoodsDTO getItem(int position) {
        return listSubGoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_product_info, null);
        }
        TextView txtCode = (TextView) convertView.findViewById(R.id.txtCode);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtProductName111);
        TextView txtSerial = (TextView) convertView.findViewById(R.id.txt_serial111);
        TextView txtCreateSerial = (TextView) convertView.findViewById(R.id.txt_create_serial);
        final CheckBox checkSubGood = (CheckBox) convertView.findViewById(R.id.checkSubGood);

        txtCode.setText(getItem(position).getStockModelId());
        txtName.setText(getItem(position).getStockModelName());
        txtSerial.setText(getItem(position).getSerial());


        checkSubGood.setChecked(getItem(position).getChecker());
        checkSubGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubGoodsDTO dto = getItem(position);
                dto.setChecker(checkSubGood.isChecked());
                listSubGoods.set(position, dto);
                notifyDataSetChanged();
            }
        });
        final SubGoodsDTO subGoodsDTO = getItem(position);
        final View.OnClickListener loginClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynCreateNewSerial asynCreateNewSerial = new AsynCreateNewSerial(content, new OnPostSuccessExecute<String>() {
                    @Override
                    public void onPostSuccess(String result) {

                        if (!CommonActivity.isNullOrEmpty(result)) {
                            subGoodsDTO.setSerialToRetrieve(result);
                            subGoodsDTO.setSerial(result);
                            listSubGoods.set(position, subGoodsDTO);
                            notifyDataSetChanged();
                        }
                    }
                }, moveLogInAct);
                asynCreateNewSerial.execute(subGoodsDTO.getStockModelId());
            }
        };
        if (!CommonActivity.isNullOrEmpty(subGoodsDTO.getHiddenCreateSerial()) && subGoodsDTO.getHiddenCreateSerial()) {
            txtCreateSerial.setText("");
        } else {
            txtCreateSerial.setVisibility(View.VISIBLE);
            txtCreateSerial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommonActivity.createDialog(
                            (Activity) content,
                            content.getString(R.string.create_new_serial),
                            content.getString(R.string.app_name),
                            content.getString(R.string.say_ko),
                            content.getString(R.string.say_co),
                            null, loginClick).show();
                }
            });
        }
        return convertView;
    }


    public List<SubGoodsDTO> getSubGoodisCheck() {
        List<SubGoodsDTO> result = new ArrayList<>();
        if (CommonActivity.isNullOrEmpty(listSubGoods)) return result;
        for (SubGoodsDTO dto : listSubGoods) {
            if (dto.getChecker()) result.add(dto);
        }
        return result;
    }

    public void setcheckAll(boolean isChecked) {
        List<SubGoodsDTO> result = new ArrayList<>();
        if (CommonActivity.isNullOrEmpty(listSubGoods)) return;
        for (SubGoodsDTO dto : listSubGoods) {
            dto.setChecker(isChecked);
            result.add(dto);
        }
        listSubGoods = result;
        notifyDataSetChanged();
    }
}
