package com.viettel.bss.viettelpos.v4.object;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.helper.DatabaseHelper;
import com.viettel.bss.viettelpos.v4.service.DatabaseService;
import com.viettel.bss.viettelpos.v4.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author thientv7
 *
 */
public class MenuActionMain {

    private Context mContext;
    private MenuAction menuAction;

    /**
     *
     * @param mContext
     * @param userId
     */
    public MenuActionMain(Context mContext, String userId){
        this.mContext = mContext;

        menuAction = new MenuAction();

        menuAction.setUserId(userId);


    }

    /**
     * add item to database when click action menu
     * @param nameActionMenu
     * @param urlActionMenu
     * @param idActionMenu
     */
    public void addMenuActionToDatabase(String nameActionMenu, String urlActionMenu, String idActionMenu, int idIcon){

        Date date = new Date();

        menuAction.setNameMenu(nameActionMenu);
        menuAction.setUrlMenu(urlActionMenu);
        menuAction.setIdMenu(idActionMenu);
        menuAction.setTime(Utils.convertDate(date));
        menuAction.setIdIcon(idIcon);


        if(mContext != null) {
            Intent service = new Intent(mContext, DatabaseService.class);
            service.putExtra("menuAction", menuAction);
            mContext.startService(service);
            Log.d("MenuActionMain", "addMenuActionToDatabase");
        }

    }


    public ArrayList<MenuAction> getAllValueMenus(){
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        return dbHelper.getAllMenuActions();
    }

    public ArrayList<MenuAction> getFiveValueMenus(int count){
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        return dbHelper.getValuesFrequencyUsed(count);
    }

    public ArrayList<MenuAction> getFiveValueMenusWithUserId(String userId, int count){
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        return dbHelper.getValuesFrequencyUsedWithUserId(userId, count);
    }

}