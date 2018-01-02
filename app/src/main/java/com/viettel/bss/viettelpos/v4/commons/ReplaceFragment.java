package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentCusCareByDay;
import com.viettel.bss.viettelpos.v4.login.activity.FragmentLoginNotData;

//import android.support.v4.app.Fragment;

public class ReplaceFragment {
    private static final String TAG = ReplaceFragment.class.getSimpleName();

    public static void replaceFragment(Activity activity, Fragment fragment, boolean isAnimation) {
        String backStateName = fragment.getClass().getName();

        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { // fragment not in back stack, create it.
//            FragmentTransaction ft = manager.beginTransaction();
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
//            if (isAnimation)
//                ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
//                        R.anim.slide_up, R.anim.slide_down);

            // ft.add(arg0, arg1, arg2)
            ft.replace(R.id.frame_container, fragment, backStateName);

            if (activity instanceof FragmentCusCareByDay) {
                Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
                activity.findViewById(R.id.llViewPager).setVisibility(View.GONE);
            } else {
                Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
            }

            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

//    public static void replaceFragmentNotaddStack(Activity activity, Fragment fragment) {
//        android.support.v4.app.FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = manager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.frame_container, fragment);
//
//        manager.popBackStack();
//
//        fragmentTransaction.addToBackStack(fragment.getClass().getName());
//
//        fragmentTransaction.commit();
//    }

    public static Fragment getActiveFragment(Activity activity) {
        Fragment fragment = null;

        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        if (manager != null) {
            String fragmentTagName = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
            fragment = manager.findFragmentByTag(fragmentTagName);
            if (fragment != null) {
                Log.d(Constant.TAG, "getActiveFragment fragmentTagName: " + fragmentTagName + " isVisible: " + fragment.isVisible());
            } else {
                Log.d(Constant.TAG, "getActiveFragment fragmentTagName: " + fragmentTagName + " Fragment NULL");
            }
        } else {
            Log.d(Constant.TAG, "getFragmentManager NULL");
        }
        return fragment;
    }

    public static Fragment getActiveFragmentActivity(FragmentActivity fragmentActivity) {
        Fragment fragment = null;

        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        if (manager != null) {
            String fragmentTagName = fragmentActivity.getResources().getString(R.string.conecmobile);
            fragment = manager.findFragmentByTag(fragmentTagName);
            if (fragment != null) {
                Log.d(Constant.TAG, "getActiveFragment fragmentTagName: " + fragmentTagName + " isVisible: " + fragment.isVisible());
            } else {
                Log.d(Constant.TAG, "getActiveFragment fragmentTagName: " + fragmentTagName + " Fragment NULL");
            }
        } else {
            Log.d(Constant.TAG, "getFragmentManager NULL");
        }
        return fragment;
    }

    public static void replaceFragmentSlidingMenu(Activity activity,
                                                  Fragment fragment, boolean isAnimation) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        boolean fragmentPopped = manager
                .popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { // fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
//			if (isAnimation)
//				ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
//						R.anim.slide_up, R.anim.slide_down);
            ft.replace(R.id.frame_container, fragment);

            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static void getActionBar(Activity activity, String title,
                                    String content, OnClickListener listener) {
        ActionBar mActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.layout_actionbar_channel);
        Button btnHome = (Button) mActionBar.getCustomView().findViewById(
                R.id.btnHome);
        btnHome.setVisibility(View.VISIBLE);
        btnHome.setOnClickListener(listener);
        LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
                .findViewById(R.id.relaBackHome);
        relaBackHome.setOnClickListener(listener);
        TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtNameActionbar);
        txtNameActionBar.setText(title);
        TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtAddressActionbar);
        if (content == null) {
            txtAddressActionBar.setVisibility(View.GONE);
        } else {
            txtAddressActionBar.setVisibility(View.VISIBLE);
            txtAddressActionBar.setText(content);
        }
    }

    public static void replaceFragmentToHome(Activity activity,
                                             boolean isAnimation) {

        Fragment fragment = new FragmentLoginNotData();
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();

        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        boolean fragmentPopped = manager
                .popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { // fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
//			if (isAnimation){
//				ft.setCustomAnimations(R.anim.fab_slide_in_from_left,R.anim.fab_slide_out_to_right);
//			}
//				ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
//						R.anim.slide_up, R.anim.slide_down);

            // ft.add(arg0, arg1, arg2)
            ft.replace(R.id.frame_container, fragment, backStateName);

            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static void replaceFragmentFromMain(final Activity activity, final Fragment fragment, boolean isAnimation) {

//		Handler handler = new Handler();
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { // fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
//			if (isAnimation){
//				ft.setCustomAnimations(R.anim.fab_slide_in_from_left,R.anim.fab_slide_out_to_right);
//				ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
//						R.anim.slide_up, R.anim.slide_down);
//			}
//             ft.add(activity, fragment, isAnimation);
            ft.replace(R.id.frame_container, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
