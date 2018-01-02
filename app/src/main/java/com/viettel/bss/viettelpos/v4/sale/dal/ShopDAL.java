package com.viettel.bss.viettelpos.v4.sale.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;

public class ShopDAL {
    private SQLiteDatabase database;

    public ShopDAL(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Lay ban ghi staff dua vao staffId
     *
     * @param
     * @return
     */
    public Shop getShopByShopId(Long shopId) throws Exception {
        String sql = "select shop_id , price_policy,province, district,shop_path from shop "
                + " where status  = 1 and " + " shop_id  = ? ";
        Cursor c = null;

        try {
            c = database.rawQuery(sql, new String[]{shopId + ""});
            if (c.moveToFirst()) {
                Shop shop = new Shop();
                shop.setShopId(c.getLong(0));
                shop.setPricePolicy(c.getString(1));
                shop.setProvince(c.getString(2));
                shop.setDistrict(c.getString(3));
                shop.setShopPath((c.getString(4)));
                return shop;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return null;
    }

    /**
     * Tim kiem shop chi nhanh trong bang tbl_shop_tree
     *
     * @param shopId
     * @return
     */
    public Shop getShopBrand(Long shopId) throws Exception {
        StringBuilder sql = new StringBuilder();
        Shop shop = getShopByShopId(shopId);
        if(shop!= null){
            String shopPath = shop.getShopPath();
            if(shopPath == null){
                return null;
            }
            String[] shopPaths = shopPath.split("_");
            String shopBrandId = "0";
            if(shopPath.contains("_7281_")){
                shopBrandId = shopPaths[3];
            }else{
                shopBrandId = shopPaths[2];
            }
            if(CommonActivity.isNullOrEmpty(shopBrandId)){
                return null;
            }

            return getShopByShopId(Long.parseLong(shopBrandId));
        }
        return null;

    }


}
