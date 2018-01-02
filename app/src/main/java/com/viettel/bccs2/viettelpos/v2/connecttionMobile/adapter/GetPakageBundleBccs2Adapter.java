package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DialogDetailAtribute;
import com.viettel.bss.viettelpos.v4.commons.GetInfoPackageAsyn;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;

public class GetPakageBundleBccs2Adapter extends BaseAdapter{
   private  Context mContext;
   private ArrayList<ProductOfferingDTO> arrPakageChargeBeans  = new ArrayList<>();;
   private ArrayList<ProductOfferingDTO> arraylist = new ArrayList<>();

   public GetPakageBundleBccs2Adapter(ArrayList<ProductOfferingDTO> arr, Context context) {

      if(arr != null){
         this.arraylist.addAll(arr);
         this.arrPakageChargeBeans.addAll(arr);
      }
      mContext = context;
   }
   
   @Override
   public int getCount() {
      return arrPakageChargeBeans.size();
   }

   @Override
   public Object getItem(int arg0) {
      return arrPakageChargeBeans.get(arg0);
   }

   @Override
   public long getItemId(int arg0) {
      return arg0;
   }

   class ViewHolder {
      TextView txtItem;
      Button btnInfo;
   }
   
   @Override
   public View getView(int arg0, View arg1, ViewGroup arg2) {
      ViewHolder holder;
      View mView = arg1;
      
      if (mView == null) {
         LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
         mView = inflater.inflate(R.layout.simple_list_info_layout, arg2,
               false);
         holder = new ViewHolder();
         holder.txtItem = (TextView) mView.findViewById(R.id.txtItem);
         holder.btnInfo = (Button) mView.findViewById(R.id.btnInfo);
         mView.setTag(holder);
      } else {
         holder = (ViewHolder) mView.getTag();
      }
      
      final ProductOfferingDTO pakageChargeBeans = arrPakageChargeBeans.get(arg0);

         
      
      if(pakageChargeBeans.getName() != null && !pakageChargeBeans.getName().isEmpty()){
         holder.txtItem.setText(pakageChargeBeans.getCode() +"-" + pakageChargeBeans.getName());
      }
      if(!CommonActivity.isNullOrEmpty(pakageChargeBeans.getProductOfferingId())){
         holder.btnInfo.setVisibility(View.VISIBLE);
      }

      holder.btnInfo.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View arg0) {
            final GetInfoPackageAsyn getInfoPackageAsyn = new GetInfoPackageAsyn((Activity) mContext, new OnPostExecuteListener<ProductOfferCharacterClone>() {

               @Override
               public void onPostExecute(ProductOfferCharacterClone result, String errorCode, String description) {
                  DialogDetailAtribute.showDialogPackageDetail((Activity) mContext, result);
               }
            }, null );

            getInfoPackageAsyn.execute(pakageChargeBeans.getProductOfferingId()+"");
         }
      });
   
      return mView;
   }
   
   public ArrayList<ProductOfferingDTO> SearchInput(String chartext){
      chartext = chartext.toLowerCase();
      arrPakageChargeBeans.clear();
      if(chartext.isEmpty()){
         Log.d("size arraylist", ""+arraylist.size());
         arrPakageChargeBeans.addAll(arraylist);
      }else{
         for (ProductOfferingDTO pakageChargeBeans : arraylist) {
            if( CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getCode()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext)) || CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
               arrPakageChargeBeans.add(pakageChargeBeans);
            }
         }
      }
      
      notifyDataSetChanged();
      return arrPakageChargeBeans;
   }
   
}
