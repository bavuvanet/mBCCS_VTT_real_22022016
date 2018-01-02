package com.viettel.gem.screen.collectinfo.custom.picture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.BuildConfig;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.object.ProductSpecCharValueDTO;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.base.view.CustomView;
import com.viettel.gem.screen.collectinfo.TakePictureDialog;
import com.viettel.gem.screen.collectinfo.custom.radio.RadioItemView;
import com.viettel.gem.screen.picturedetail.PictureDetailActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 23/11/2017.
 */

public class PictureBoxView extends CustomView implements PictureItemView.Callback {

    private static final int REQUEST_PERMISSIONS_CAMERA = 1;
    public static final int CAPTURE_IMAGE = 234;
    public static final int PICK_FROM_FILE = 345;

    Activity mActivity;

    Uri capturedImageUri;

    int mode = -1;//1 - Chup 2 - Gallery

    String pictureAvatarTempPath;

    String isdn;

    @BindView(R.id.hsRoot)
    HorizontalScrollView hsRoot;

    @BindView(R.id.picture_box)
    LinearLayout mPictureBox;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.layoutTakePhoto)
    RelativeLayout layoutTakePhoto;

    ProductSpecCharDTO productSpecCharDTO;

    private List<String> filePathList = new ArrayList<>();

    private HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();

    public PictureBoxView(Context context) {
        super(context);
    }

    public void build(ProductSpecCharDTO productSpecCharDTO, Activity activity) {
        mActivity = activity;
        this.productSpecCharDTO = productSpecCharDTO;
        if (null == this.productSpecCharDTO) return;

        tvName.setText(productSpecCharDTO.getName());
        mPictureBox.removeAllViews();

        File f = new File(Environment.getExternalStorageDirectory(), "/mBCCS");
        if (!f.exists()) {
            f.mkdir();
        } else {
            FileUtils.deleteDir(f);
        }

        File f1 = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn());
        if (!f1.exists()) {
            f1.mkdir();
        } else {
            FileUtils.deleteDir(f1);
        }

        File f2 = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn() + "/zip");
        if (!f2.exists()) {
            f2.mkdir();
        } else {
            FileUtils.deleteDir(f2);
        }

        String base64 = productSpecCharDTO.getValueData();
        String base64_2 = "UEsDBBQACAgIAIUQfUsAAAAAAAAAAAAAAAAbAAAAaW1hZ2VfemlwXzE1MTE4OTM5NjMxODguanBn\n" +
                "lY5nOBsM24aTGlE0Zu0ZscqTWrWJmKE122qIvR7EpkGpVXtLtWbV1tYqNWPvR2tUVMTelIfWXqVe\n" +
                "_fN9x_vzve7713Ue93ncV6SrRQC9nrauNgAIBACA1wO4mgFoAKhBICoQJTUVFRUNNTUNmI0efOsW\n" +
                "mPs2CwObAK8gFMIL4RcSk7srJCojyg-RQEjKKCipqqoKwjR1NZSRciqqSn8kQGoaGvAtMBc9PZeS\n" +
                "CERE6X_OVSeAgQoQCCgkA0IANxiAZAzAq14A5_WfFH--_b-QAYA3yCkoQddQlR4AJAeQk5GDQEAQ\n" +
                "xR8IBNwgYyCnYORnkkRAjG3TKKVMvMMLum4LSKvjasaYBR_Z-XTv7snc09B8eH3PDgSQAf9Lfu0m\n" +
                "I6e4RnwMwD-u6wX-P2Ig-yMWkEaEX00DaK_JdcMAgANIhq781cftus7eF6uBojKVUh7sWyfMTTYH\n" +
                "sAM1RYcm_b0DUa-s7chw1K5-5YsDOpkLIbC-J84c5HODEjWwwYNRkh0yi-rhwP4gAYdd6ZsWkHsG\n" +
                "WIVt4douja1ydvzjJFW73wJ1HBS0TkysVrXlKvi39VNzFl1rODu1e7WEKWp7T8KxdW7sPHnJRvOk\n" +
                "H96N0qX3KYZRWysCK3CGYnimKlvbsGumosoUFYWfDtKaefHzfrtcT2ugIRoxopytPD5z-frsnasf\n" +
                "KkbrrmPv9gz-9Ei7hMVZUynk5ublyqDeyzFEjOHxAM6Le-IKIKio16Cm_mYnPZ2gppr-nlXP3ARI\n" +
                "MDf2KykB_CXqokl4nR726BXExPUBpfYXnJOJb-svBuYq8bgUbBl40fk5q436lx5ticBoWfMC20Xe\n" +
                "ISTc6GGGuj5R9BB-7nI_79KtLUXQly7MiY3yYzwZ7n2WvtAde9hdCUmmwUpsKigCBTAHJNuLGUIs\n" +
                "McpVc2z6MkdaZE_1FgE_Tb0U5k0mo70ToN_XE0Dztd6JMz-jOy3vpK7YE-2drAzyGuTuMw2Or4Ms\n" +
                "mgKzFxetKEt_gLdIHp-jb9TIW_FEdA7cW-T-ECv2QKZScNY2fUJDz9xYJr0vVo-ZWY_fxYgfSJ6-\n" +
                "Ce1eoDoRXnw1QZtCK1Z3BcBlDnoVzRxQ1zOuNgk3w27-sPQkkcwviCXH2kwegwf3jZ6tDOk0q9Vm\n" +
                "OPkEMzzNHPZ3FZOT-geSQLuZj0N7rDJSkQ1ycPo6CJWI2gXd5PjtDAYfp2SnlFCpf7iQlsQN9Teh\n" +
                "MBqtd0YnkAt1wRMmKzG9ekuhAcRUzxb7Q72JJRWH5ec8Yc3Ts1YWbjlzqG7HVrmwL8LZdY6goLlH\n" +
                "vcvekr3rtpvVgUezBOdgZvcxyGqTKT4xXFnBz2aGg8UmgweatIwmIzq7b-3ObtfbZo2FO4dXR3Qx\n" +
                "ysT--BXRETNYox-VJNH6UO3INMQsh81b_COyHYGFe9h4_t10uoleEWr12fAgFQn2vr0w2H_go2xG\n" +
                "UjddmMk03DujW5Mc9bIvuojZL7_QeIvVT6qlZF-PqpiijNDIRvDQvOvb2FMFSQk1sVLXPoOFBAbE\n" +
                "FOaARb1B3rosMRF6z9H6zcniodtfzj9nDu2FzkMvtYzriHlbMl9_PbxNDIQbI9cD3v4evdT6e0tv\n" +
                "bDoJ1iLxZobdIkTEsE9ldlav4hZ1Xd8_cxz6BHN2gWZJ0fiXgtas9Dimz50qDHE0ucOXkl2O-2w2\n" +
                "KeeNdKiaw7GloCnthqAx5G92Weyj4qGWZ2ZMeQbDXDG9-YjMT5IYa_ZKi5nglu46yKvKr0TSd_XR\n" +
                "LXwJ6W_WZO4iZhuwXf8V4J4TrUMtb1ti7RnZGEWTaE-98Wkbi3ZbBSw13EqU22URCmKeqIWq_BuR\n" +
                "QDB1JO3Os6Wz5Dm1pnT5NXkifTbzk3UlxcNHWhyqCOY6knji_TIY1nxqOe-w_-H8O52sxRJfNA-M\n" +
                "tXn-HaZLkDdWOfjltJYUY76lKwdhYjHsVXr6vQBophW_ZqQLBCfH0paMiZHQgUrReSP4rX-Mh7a7\n" +
                "zCJZ7-uJ2Pu5f-t_admlbeYSsNJgxhf6GUPhwhIuirHz6HkDKHSwR4LtyfAJm6tleLz04cJaR0Rl\n" +
                "3GskF6BLX4Qv1d7x52xjFpQ_P3OmA9a4r8FW-tuoW4RBLeeUoPRWhyEbDh9YGqJf87El7bwx4MhV\n" +
                "-WVk8S0vdiNlrjmoAx36avuv4BFngei787FWiyMT1kZMZ268XIGUZt_bfaODS06faG9RxKANVkr7\n" +
                "MFoxI6R5riYBG8uYussnBXK3RfjYuOb6fjoI9ywLpXfE0dECayawhWv1Kxtpa5mJP1AOkkiuybox\n" +
                "cmPq3LM8MbY5AIv0e7RVqi3Ew1vpMNypjyJBnuFjdHzdckfigwcZSR1O790QPX223LR4v_bcZYPF\n" +
                "CdSUra6P3ZtiV-hXU7YNza_TjTwaZbh_BzYHMuqkprwOXIRilehDbJ_JFTUNSrKBuWrZofHh3rKG\n" +
                "A8OBMzXyae_zVfK3lzSB-C0p2kc1H6VpBzC-IfsaW-cDZc8FRGszDAiER4kvY1_01Ae9m2bOrFBj\n" +
                "_dDWaZ6c_ksbd1_ciTwsIM8G-lYv6Kkm7-0bt0u1OPvwWOnOF4c3R3jofqaFZIRS-6GNfbF3fvPu\n" +
                "RhV00AFcKhx0PB2vAN1Q8K2eKSUaMR7kTCJRvv88UqgfuYMkuHFW4yIzM6vtgyb7ZHy6ZeaJ2Cl7\n" +
                "jFtwXPuNQANOQh2d9xnhRWEqfRfc9Xg76ezw9L13USZLPzq4kYpLB07_fShSnqk48xgUctg-7V7E\n" +
                "JFpQ9bRlULeTwnjcq9j5rvtJWjNVSlZnWoUrRlcAMVvG_m0VysFJvgNCVpW_7j23FheOto1b9WXG\n" +
                "npVPWRdXDTk5WAjdq_qInTWqb-zJnbNVqJn1VU2THBnK0_H_VuXPTJiBKapxmw6UBBZnck1V2LNd\n" +
                "2pxPjnxePUh0G-lTFVb-zuwcEGY6a-Ff_aziDNr22FqMTxjRyNTyzYvUXJiTwlnXrjquy9rRB6zw\n" +
                "q6rFatMCiwP-sjj6uuRL26D7Ju61sOJ0EBRooOJ6YiDNGQoWpYGs7yi5oh7wPTQXn1SrRNB-avyR\n" +
                "eYuniuxE9GVBgnxWjXytOrYKW_H8w1Q-KXW6fM53uGsZzdNMg5pZc7joi7ItesB4L4UvfMhnv_wA\n" +
                "Qb3tDBM23pjHffanl4-g7IO6zW_Z8fxz568XZjLrEpMm6tWxOVKxaL54NZUcOAwja3HCyZlgSa4v\n" +
                "UOACOkGNPg52sSty4s4uzlbkmzA7DR5bnAHfvDcasjbyJctOsiTCqL9OsRPj4ifvL6M299hKDGZW\n" +
                "KbPy-4NuD9F8KheplLpYyK5hePvQcqRv36Rfm_ZFTI_7tnv-NCe7xETJHuWCA-U-c0j9AcqUlKdO\n" +
                "w4WGhtXtYzovv61l9UuQey74aEzTNYORNJDU-o8JVwCqtQ44jibypUJ_QZBGdCri55xhTTHalpJv\n" +
                "0-p05GSaF1-P2K4Re6OWJCvW_HdH4j9HQyR9BrUb2H_3z8ubbIg84xxc2msF9OUZPQ5NwQ95ZfYU\n" +
                "iCsGdJAofkcSyLH5i_0OZdMMQm6ke6pnaT0CzPm-97gF-twIHdmvm6WlVCgYvuLdBnjipOAp3nYw\n" +
                "CfNF2W-0y4_qz5j13oX80qTZxPe8KwvqrWC8hT5JTig-XIfvoEaOPKoz9ptNueUHiHNFVpiilMNe\n" +
                "n5DPyll-sB2dj7si9rTEN4rArUrQE3PDT8N0KA9qxlyYUxIY-4GTUbwe9Vp-o4eyZvipjzupY87p\n" +
                "c_dAnHjya9kS3i1J-hbTUvx5GL1GLbwVPt52GjiwOlFzwixnJ4iJTd8Ck5o4i0v7dJTdJ3gy6Ebo\n" +
                "oMLEHMjxPD5fDHmi-IR-J05QEEJcgODI5u6PKhnh1NdZUrnoJLRKldMtB73xXSGctEjffPfkH6Pb\n" +
                "lbLZXZr-hEq5D6WjqKC053m51P7_hpT2b4TPfjUqxHVxsQbVRTmf98wzS8KP3K8AdxKUMfHgHsUn\n" +
                "DdpYQKGaJzxkk4Utjk9hxf1dtjY8wmhqK_iR21G1jhssnR5dUj_5wcQ270RmL_creKk1Ppc30jhr\n" +
                "6-2z9IXHOVGZj-2sx3MQpl4fVeUYnXz7uGOrQvcORKqOmBq_6SsGxpQJHSDVQMTQ8yciwzBv7Ev_\n" +
                "IFjZeOivXOuN6-ou6_KF3mHAz6VkK90zkeFUJfvGNulBstoQ2QJ49a66Jzy-mBbaMJ6UNl-EmAyK\n" +
                "lB5GHqgpxONRd_hLGgiV0rsRN4_i-ag-heiK5rnc-Rp6NfUfUEsHCGnU389jDAAAhwwAAFBLAwQU\n" +
                "AAgICACFEH1LAAAAAAAAAAAAAAAAGwAAAGltYWdlX3ppcF8xNTExODkzOTcyMzg1LmpwZ5WTZVAU\n" +
                "DvSuF3bp7paUjl1KWQRZQrpDWlpgly4FJCRcSukUCWHplEaQBulcYJcuCWkpEbi__9y5d-Z-vM_5\n" +
                "-LzzzjkfzsPCwxqAUuOF-gsADg4AgPPfAB6wACUAMQEBIQE-MSEhIQkxMQk5IyU5GRk5Gx09FSM3\n" +
                "-2MeLnYuTl6hJ2K8ApICnFxgGERSRlZeXv6xqLK60jO1J3Lysv9TgkNMQkJORs5KSckqy8_FL_v_\n" +
                "zUMXgIoQ0A74C8ThAuBS4QCpcB76ACwAABDnfwD8H3CBIBwAHj7Bf1KJEoCLgwsC4uHj4eCD8IH_\n" +
                "3YRLBQCCqDkhNDB9Lluv8CQ8WvGCuhNuRYPuqXV8Ogm75FMeSUNvAqX3hfU99F83_ith-H_r_wcc\n" +
                "IO5_4hEVzv9WwP-rcahwqTlpIDB92wcMgBSI89-eQCrAc8B1O_vCM0OA-6abxQOgTjOUTvUBEEZz\n" +
                "GqT7AJh6AFy8smnXJ_cjGCq5Z18cw17G1f6iD7vbL7rvChLlPGWsD8r6L27-V_9P9QbfgZXsbo3E\n" +
                "4o_FUKcg0V8M5BkkeaPHUgck0oPbN5Onv3eflpUmYZZM4tBhbLWO9buvTTPL4lAkas_5C-lninWu\n" +
                "f0vlk6wAwHYUdyiKAkn1uPpbyOd-O9IhfNWhuaEc3fWSPbpCjG_Zof3JfLj0Ui0xY08uMums-v6s\n" +
                "OxL-2TYNlowzDeDbl3NJxJRElyRdVEnrXRRVi3ebICZpNhJ9qVVnmbcvoxmXBPpkItWG3yZ_3eFC\n" +
                "KblURStK2qsjWEJMDDcExuJqkds1FkxEZMIgxjxUJ13TkUh_qk2PHawks7P8Sa-1PpjwSDKt-e1W\n" +
                "VZOMn9zcXmepy5dR-kqonRgNW7EQ0KrtYoxubIemPNUWcZ9yYHWEfr1GO9PBsykM2Fp6AFBszLge\n" +
                "c7nBzWpjzZIUmJNd4D6EWmKkid3kOYyZhQjjD61dLnabdpHv5VPbRRBl3IS7EvyHvEVdEduojuXZ\n" +
                "IZfXGPkJcoMXtgWs4hSDKSX7VMqd0z2wYRYlqmTy_ee3xI_7IDRHH2UV2Lj7bowJz8IzHXhcKQSJ\n" +
                "5jH0FuuNngl-P2f26m65EupKm-nwSh6nSkYlgcJ_TAxnu1voP559kw1-m1D6-OkJlamtR5biJI0-\n" +
                "gPEU_eyTxlSc9-4HEl-GXHtYAfQvhTmqdvLFNbZwBGpz9A3ASR6ZbBNztFqi_rUR2eq9blI7BLr8\n" +
                "5Bsqea6okz0F9JoDNUVUCTRXMSTvD7P2QA3mvNUIvg35lj_T7BVrHNpOvHMtlSMTYqw7LH4ASJWV\n" +
                "c_hVhlZZh2Fa3Cyl6DrpKjg66nyEx6NhtI1dtqll6_soWAq3wnPbXYENpJ5K6HLtVdVO7dHOIiCG\n" +
                "B6B6RpFUxVAZpdXyZOiY162PUpn6ech-JaZpxxxTd0xj3lRV0-umGhZLFIVlAFuftMBO0o0cALnB\n" +
                "V2FwubNprF2LoZCPAKXUR_vSFTJLQPUo_ha5sfKTOpND296FXqxIxtpj1W2szgke3F6kY1spysRA\n" +
                "x-mxDdEDwP7lDXry7coDwFEFbcnIqck_kv7KVP4Fb3Vtunt-R5fx6_cORJNDeXIuKZJ24QG1TWJC\n" +
                "r5uIuIiY2Tt0q_AZyF4TOJjVt-oheLK_phGWSsYYwqI9y8TwgCG2YN2ANElhrJv_zWxTFhWLdCKG\n" +
                "MfNmOvGK8rfk5Bl229pHarvDq3-kn3a3cJLnCzplxrXxSHuSRq-r4Zt6gmzjSrLHK1sffqJe9lxS\n" +
                "6c-N5UUrT0STCTosOH00wnDAkgsXNwZfVngS35hmSM-W_rMpiD8KzX639EJ8hcPCQtPPTlOApLoN\n" +
                "5r6PIoHg7lHiTBJz-fZUcw09jXOPD7AWgLC4UuTS3D4n8YuTS-xDiuu0h-Jmdb_Ne9O6-6OfUvGJ\n" +
                "v3wa7jgJ6RDV7wpBJE4j7bZeLJ0CgBDgXeb1svbaeaPxp41GlBP6-UqYdU_jnErQ6CgP6UR-MvSK\n" +
                "WXC_V1GkdD_YNJv-m8sHV0weXT6Xs3rRUs_C0CLCPBOyQNuY2EfKHF5tUAT_cSLnHG3pBzQBjid7\n" +
                "LLIswzZ66o428BckqJ4zrviX1cmk0WURgJ12THOjaD-7vxw0_R7P6ODIw5SQAJVN0JqXiLNKpiUj\n" +
                "QmDVVkeNKV97pDnOHxZlPEJimmr1iarMoE21ZBf3qYrvg9B1L8X8RfnrjXAjYbx-873HVrTXlkG4\n" +
                "X6nDtcMit__9w3C777cQ555eur1ae-p5kwYTtElpYG_5k98d3VcvHwwz8ZjWyR76hFMSCjUPgp74\n" +
                "OfW3OxIo8Xl-rfG6OM3ghc6NhNM7jKrFfGKnaAFXPm_A3PIzOikozH0y6C25Uf0bpQcP-94S0ADJ\n" +
                "YIoTEvqezIFDYAXj0pnhG0g4CWBzr_nO2Or5NO1zGwzYrptVHO_PF824xi6prjfDlrUiNa_NVwUM\n" +
                "0qXpZqBZbL54KWOqRAhKAB1TKWDlJ4ICRGT6vnku1SWdDkHZiWbygYF_8AgL6XCIF9iE8JzLCVnL\n" +
                "u3woXfGZwccVrUba7bP5iq_hBjyeS6rZDwDaeLnPzTHSONLbOhbFSD7T2T9SSl4t9NdMwGRVdzup\n" +
                "vXVFNETE8_N-rOo2rxN_0UYlSQbeCj2bUhr68HtfU77Pyc2ci9uhgrEpYQX1ixmNOrvdhh_xqg4K\n" +
                "K3JwUaxjkveODUxc5CfzR2tfFQmf7NIHwNydkDqHOx4MQeK6HFc1c3Kjxh7iq3ZErdK-RUkqSXjB\n" +
                "9gAQIxaMbmW_QX9A3jcI-GVlsqTnM4J8qDZLoH29XSmBu6McAu8--dk2Rom6a4y4cBY6f3Ca2fxD\n" +
                "i2rB-SjVFQi6T30mzNVfhW8LcJILvPm3p-0B6Ktj-pij_izsb6xA4X76SJ93IN0nTCNsvZQzBlcS\n" +
                "ovDloC9qyDJr3yk7G5_mG_sOEmYyL24J3SNjDi0vHCx_nFzZjB9AramFK7X0hwDdIS2Zs5bIZKJx\n" +
                "2bCqYx05Nf9ceD7djUHzSRVGxXaxhcFZKBvGclaOlDj5t-v10t229ptb-i11ZR-Z2gGwhSL3ae0X\n" +
                "4wxt15aqo4K20aYR_nQgjEZTc35BciGZEGiaJXer4tqtbBKbExc_StCOHCkblYReXQfOqDKdYPor\n" +
                "igPVDG8noW2JTTVfeIPqMv017q0I-A8kxV7TboXwG4dz0HAMiVOUfiImLmxDXVkUxfQ9ZQXH40Av\n" +
                "5GDH5XpgPdZoGykOBd5SzJSV6VH6bOOgkX6rPcAIxB8F8BNJBSfy8h0XFJoYfgO2TP5ZCYuiLK4a\n" +
                "QWzj9QujiCNKF-43Z_Un3V6sbjytEFSdPnhjEAYaYH6-z_QA0DQ2lkviL_nz4_DOZfalAFWJiJCo\n" +
                "GlOqJgygpbuktwjizJtdNbTy9rHg0ILkKSm_yJBgWF9ffQragocRAT1MaUHc5kOIMkMMaHX1l5El\n" +
                "qZgXPoOEEzG5F4_HpWzpoynLEQ-gW_hkYreDng19-h8qR9wsrYnSZzdXJg4GzN30hA7XRikgn9u7\n" +
                "0rF03nWEKOuEi-rO-fGX0GPlN8OHC40IasWGfd_EkNtdygL0sJVTY06pWaLUrzWXqiAKx7NUY3tq\n" +
                "xdBHdCNe-eO8K7UwAyLmBOn50Nb8Dr8Ipv6oYSncpdFYAotW9_0dNe6eNu9vXSk0m1e0qf0WTYD2\n" +
                "t5YNiQrTMsFI1OPZltVoB0o_tsG7Xfr5owwOUztuJYgUemUT1urxgewv6q0Ba-E2xwZjdbar74z-\n" +
                "9u08V3FFJElAPEVM04x5OasT_TGdeY_Tiy9ce7tE25WDAYIsTyuCSYJfOramOlH6Qqpeed6zMI7h\n" +
                "KrBkx-6w0M4zVu0aqAPzy97Oh9w7BjJInEyQS3iP8hiNhdLl8PdksmR9eTX-pVF73RMbp4w_Yzjt\n" +
                "dx6rZQ_Tq0KEDULebQS1TuVYrD5d42juARoE8OkeKKNP3T4Yb3At5Zq6PUqV8aqOJsa6ONNAvbeC\n" +
                "Dtxs6zirGkf1OY3ec2-Ug2298H3s1NKA9TrRXKGhgjMRbZM6ghtZyTgzNjJXMedVWo8vYvqpcezr\n" +
                "fkEmUu-EXuJX2G1XZGRdh1VIsGntKbwJkj9GVFiJVe64_dtLCByrJVlWAXU9Dktp5L2Il63sWCr4\n" +
                "pdfbOD8IM82UJdzYOH38Uqy_SE32rRGtSJ-3IjRzsjuzssPWY2vUzIxFvD4X9l4-8u-0515kr7mI\n" +
                "W315Gc83mqJkP9kEbTkxi3dxWlp0HH0DA-HW6zanJLhv8j98sUF-Il7__VsaHu_2s6175W1So3eQ\n" +
                "KuFlwtQ7RX-HXG7JFzkxSTpPDr86-o80lcDKwo3IlsiZ76b8t1u9xkN-3e2le16erPfXMWEhp7e0\n" +
                "JAm9HVj_6TWntuJkmXD2Mcf4EHZKWYoah-7VBhz2V1V4UlKrp7pRhobWDwCG4WIiseRu7vtyChPc\n" +
                "8rIk4NeIWs93pPezHjGNiMuoCSGtkPexdrUyYVt29ZtRgKvwfbuk11QOjb1ZqRJ7WRpnbKp-dAcV\n" +
                "aQoGZz3iSWXM53KzijWPv3m5O3SIVBb27U0U4_W6sWUsQyh4ffiTyqAxknzUYz4CT9xyXxRz54WK\n" +
                "lK3fBnMHur-YIRjUAH2Wqxilq81gXjD-IIJvJ71dixJpIsv1DKMDckuoL3iXVW88wqhgS43aSQc2\n" +
                "yXflUiZnKhlqzdfhGTbBrJZT8hy-5cTb7cVBjHCZTso-RD7y7p9AeeWoz4IKyPVFPTzhyl_IjdFF\n" +
                "HD36xl0w9E3_jo2JYswMeU_w43JiJE3zcFi1iWU5KAX8zEUd2baradpCnu0Yg63C0dLu8bHqcMYl\n" +
                "5OYkeEWM0wcxE4ldD-tz_uKlUP3Zk4V2a29xBLBn7Glv0jIeOspwSZfgyAjPfCPDrlHOk4JwWpGq\n" +
                "BgqG83TFSOa8sXBVI_NNHAwaD_p7NfMUPvdjNjQb-W7Ov8mYnZsN6FumcasUg1IxTlAKNvfdk_fa\n" +
                "NJEQjLEDZCbDo7AIHPJK8r5He3-NDdxN-6Tm_Pooy1Wd8L8eOWP6Bxyf6S9SOvpBI-bbYjwseABr\n" +
                "QyA4us4r0-nag1uni4mvJ9-0S3hHjOefuUdB3r3qtuW7PHvx77X86tPcVnu9DDaYJ_Rn9d3iKP-T\n" +
                "yqscd41qJwlmgdXjA2WcHqy0CTZdUHrenK57UY04Eg_b4Dv5aWk-6CKN0xQsbKvsUUw5wOlZKJ7o\n" +
                "GIeD7x3OciyfRhXzvrbqTRRf3Cm-CxUVlXbjh4ZqUTzeNaqszBRmF74uo5hDoPcGxkIO5WTg0BXO\n" +
                "MYoInFoalGcDskr18llWcL4w4_midF2uwk0QTWgYDhdIIEZOKvyhfQm-9CizQCdvZCbuE0IaOesY\n" +
                "FtKbnUtVBZUsXN7xqsKgWszHgf1Rip7mjT2VeD-HZaQx41_a5oyJonsrKqriXIfEK0DUBArwl2Qf\n" +
                "9NMQ5kBtXHI9gslAg6PlH71PhDFigMTIKJ6E6A4XVaqPnN62UmBmtd-Hzy6WDv86i5YdhyLJ9LfV\n" +
                "OtpmqVtJovqdmEE8fw2rbIVNqdzEY3LH_3SyU3lg75xVS8t3i0ikW7IUuMXgG9CfsheYtn283k7j\n" +
                "RS0fFF5c5bIRRc25sjIy_1zsB_HNU1RPnFkOn81x5_eSvzMiGyIZI4PJfVREQ_v4EQV0zSt_E9HH\n" +
                "Yc1qI_zk1qODwX1_r_Xwg7WLWl8zKiM9emMglUQzgaV-GRO-X5df_95HLZEFh3zozMzMH65scg8L\n" +
                "LKBqfsWepv1lx2LIfdXOFxxGx-NC1bQKMZ96Ez_IhMCG51-XVJ83T3TmB9mpOUuL5moprEhWb1gP\n" +
                "Tzmkgwbygd9DVhq-uWT1br1lemH3N5Hx6u28RcNl9ef0riZ6BmtzVfnzvVUVH6t-AKNtd-48GOQx\n" +
                "MYNuNb8YcW9GnPxkVgIqutv1DNUSHdtwa6DCHYZAPrTyfvEskYVYvIh03cy_s6053DpS3BcAyW2Y\n" +
                "2vQm4RKu7shlVdF6ZuJzhlipdOTenStS8oU9m9v6o80KcSzTkGkm8_lJmckO_lz-aDVupxLHkICc\n" +
                "jIfxBiPq0aZIJyexmu4carb9DYp3a91djNxvhGnD_89ffYNvNifzM1EOWYTDKr3MH7h1kjuZJwRR\n" +
                "Q73gDbO6rKT3lDMcumKdxk0Q05JWF3ZCI3HNZ0lkLMqGTzMz6qfg3Dv6oAhUyi-aHcEC_zMvNGad\n" +
                "uFpnBhQ1n6HLbPWhT-SsJqIpIC4GUaRENf4W_kubLCHoid4lm9EWmCcLMosAXZ9V_4BjyCPNviPV\n" +
                "wZZwyi4y2bO8V08hFoz6Q71VTQy0_KOkRTHyKWYfOg4pui0W_HBSvRipFiE14_IDVcFSB0jjGrqU\n" +
                "U6FEhYjm0uLFnk19EYJ-zTImzGswgX6zTtCkCY7sjSGtQmz0Du75MxR_6QTYqClnoKRmmEDQtlti\n" +
                "qDXo6YaTHuw3MuzJQRVeSNpXjewBcvSj22X10X3amBnFMXnHBD1-wjdoNDGGvPgVPp50RtB6vkoS\n" +
                "Q_Ub4mdy46LMcxPo8A4V3NDRpFlD8OW1aZDJA0AR779XpOc6q-Eq-qt6rmqpMM4sBQi_2rbahsl-\n" +
                "Lc6Bu-9qtcEpMW4HtPXNY0U_Y5TDQWljsqGvzKXJEUk949pD4lMoD2ygpdU355ynlAMhlN2-6Oe2\n" +
                "xiRkXnOTt4qLSMQQvi2BGZs0itNPqX8Qn0oiUYjs7QOgkWDTelw0SEZ_mLV4AZr7sTNUew_ZTJLj\n" +
                "FtkQl_7YlE1GLaEUd61e32el1f8RkZXWMdV6Jdmefx5Z4H5942gXTvaLukyA2i2mVPPNdFjjiMYW\n" +
                "azd-SpE7VBtuMeAVV3Xw1p_Y6OK5XOfV--Cmamp6NKdt1zjIZ7Un9IuPcuZ3pELvKwE3BQLOlANj\n" +
                "cwbvEIjBzI1z4XtP9abJ7AiZ1-Mo6SUJUDeWpHS7RvdHHVzUyU8itYabDT7RtoDY_X3V69wV3pyx\n" +
                "OhzrltbSttSUHmajYO1Tsrqkr4e98m3rF7EWxjlMErS3tmZvFuzEw5ki1uHAr-3KJCjNS1ONrDc4\n" +
                "7IKE_frcdJlAILDNHOCT_2eL3G9of8M4gjx_1tQdr2wE-folohfCo8TG9ZZGYyx608K4_Q2tLaWy\n" +
                "mozNzae8d_p7JE1DSV5qMZNnBcHaOQ7R72DvAXymmTNtie9yPzipcsYoqs1X6s6kPVlGkKCOSV-L\n" +
                "fW6-qd_O00BOxdcPVTPF1u9QJjRbNVQhrCIVdzbSDHyoKhVS5CQrr-AwOsVEOm8AYwL0UeNIVG-c\n" +
                "I17qsDoPlbhaxFn-4z4MvBDtPkVNYP751qCnRdUi1myR-qWqjVpYsOrTcrw4xTXPMEnlTz864Mn9\n" +
                "Oa7l6UnGTgA9LtdI68vieDlUu2-SWygY7YsfLAUcaDp6iqVfRKS_8Dc5hIEEoOSBZazl5f3GKsTl\n" +
                "ABYAP50H7Tx-eq6asb0Qt9mkKoRdQ_jxb5Vt3ruI1wo2iX7QgQ_pKxwzKogxBV-GEOIpK581SRos\n" +
                "-6nEvTnJm1u97v6fLx15zO0k7Z7g8nfHHaw89zLzIHDRlBCuE4PmZv4sYvOhcs5BPALYS6ASA69j\n" +
                "sZmVYUkuS6Jb4nv7cBaCKE21z5xJWoI_w_luUKLMPQUqDQXk-_hfvvgpv_TaRa8iMxACqWNZqqIF\n" +
                "nWlNsVgy3022eTMGVoCU3PKAYBzaLU70uywicsfRLwnge1aakdA1lBnPzBEhneL7XnrWgxb0z0n6\n" +
                "iFvtNYgK6QypXvQPcxZGsdF9Y4kdlVWQgGx1SC0Row5nXJg3Vyzgpb9j5pMwOYMN5VnIAPtU6jpo\n" +
                "zOpAYeq8O5ZruSkMJpO96H6vxR3awW9yLyH4TTB5anEt8e3Vl9SdAayhJ8hfekMxhs8ULNNvv6kA\n" +
                "bnKHZE56VDLj3Y6NhJGz_eFJOsHJT_A7LXoADIDx2KnJOzLWE378ufu3_-MNYhLRwux9e1wAXwiT\n" +
                "fWo5oDY1MPNj1TwfETdOV0PQ5EdpSu2_Z3mtiXBoXBUafx5oI7wff5AUPuf0VvVY2RVPJBUGqGPf\n" +
                "8V41VEBG0XY0jjHvjX9x5eu1hserLICS8fAclUQN64qqCUibs6_IrSKp1aG-Jdp0VHGuC2FUNghu\n" +
                "V07XBUQPJgIt--vAUpJWSeMrHC7kCpIWn15dyxqDNMuRBoLlGyNYnJfgnouJib5__bb3BZYkRXK4\n" +
                "xs-ZxyiBxbivGOxWEjzVIKjvJXtVdhv9I2qX85uLltvbkr8rShxcmnJe5t2tI6W7LfEml6h_5zOR\n" +
                "EXq_V6bByLsGbXxYHugoNrnSHC8OMjPRyAQR7kjJ-lYhHMc5mz3It6LI-GCg8D8pbHMSm3IFT_q_\n" +
                "82xpRi_aBCEIR9bFg21-C5kcNzLvidjSAbcpA9R10Lfd72QUzolb5rvZ31tCoEgmVYGUGqhVvI4Q\n" +
                "jePC-ljnJOH-egTRkq5YHTrjyDReR8R9o_ojcSUJh7OuzI3P0UHOZL52Wr_uxhJIYU82a3vU8ZGx\n" +
                "pmuRCdBiOD_nAQBdzRWfG70SE9sN_nELu9t7rYkMmpjOchO3bFutOg89BF4W4Xn5lLIxsiuS0eHt\n" +
                "_HRcCjHm_RjOkLz3hKwyq560dSClStXlevxyGYyurs0cweSS5x_fzwU1bwg6jnHkKT7PhmlG70O6\n" +
                "MXyQ0QEegC_HNfyXseIH6tKewxH-J0ygrlUrqItGe1yT-0rz3ZqIq4n4PwJh56CP37aVP8UZfToa\n" +
                "Uaq0xA3-WjmzusIm2YzAlVKlgbp1UHKtWey_P5fBF9-Sw-bpdszmd7JFw8euXU20IOCQwHcCxXXh\n" +
                "XQKOIvGRLW5Bf26n68j62hZP_Rd9cBJzlYdW3-nULdPqEXRE1lOat76NCXOycYZS_Ehx2zDfjHoa\n" +
                "VCabzuJx9Uu9jdEU0XJSgMQZGsOXZj6fvLk2WbSLrYt7qcyaEpI0r-DM384qrjOC-YPOCIhXpz_D\n" +
                "YYbMm-1Nt9bRs3qO_HzHnZX_HR2LbVm77CZW5VC-78nX_GzKVoHWUz8M8J48vz1GWbR8lxrzY8pX\n" +
                "H5Uqe2_rtvBMWGTewF6Y6WDiyIVVWGD-tjXy4uU9v5yebyF-eomGTCGhPjHAuTt_ysLMnL8qv93P\n" +
                "TTiLJxPg7lwufHOq7ZUITnGDJQHtl8umi_0moCMBYnbhG9bSvApGCRR3s7UtcZrPLGb4zp34ioDa\n" +
                "Fs3C6ohn_JAvAWy9oWJVwp_dOdKeVJmlxec7cVyd5f5Bf7W6fQDQLvZlGbezfSXeXOcrNuQKKIWY\n" +
                "Wmi1iu742vNARJbBgS7Nhy8yqDxNjlgE--oLADjteHmf5_I2RUsm9B39tHubs8Hr-hGko--loR_v\n" +
                "c-e0X5LhxzNb7llk-uUbd5M-c0ZvbViv7ql4GITGWg1kPOI-UC5jwPzaV3kU-QCQJHUkjdLpU0Gg\n" +
                "9Ux51g9TOmHyaUZaODYz-IQExz2ooPTr4o-ck0IOXBzAH88v0bme1JebKfBdV0uVnea0O_RICqnj\n" +
                "LKUd-d0kLxKbOcrcTtBfnwY2oilizF9fj6vqRX4fH_oKrWMK-2Ld2TD1sUEhuoEpMtFdYDiQDd6S\n" +
                "IY73rUeZxZG1YevZvRFg5Qjd0Ox_qZw4VTaLZPtrsuQQIOJD4JnoAOZLwSnR6TRckz41zQl4vwLA\n" +
                "0Ii49_48nS69GuE7By_4p1bfesD4fxBK3EKP7GDIJEjJKcQkx2T9A5ltoy3L9fQqXboiJoA7uNj3\n" +
                "QvLbRxWIN9Yi4Rzq8kNpyVXgn5A3BZmDqyQmQ6yHlbUjHekmiU8mcHrQv24undv9_rtawl_gdCsZ\n" +
                "tuBwqdFNwWPEM9dXDg93qA8sdIysSpfGJ1waLL2d1FjOhQwTaBXGHn87t9ywWOdWl0hC6dyCqxk1\n" +
                "8xXawshhphT3RhS0iulk79Dct0xhzb-bibzu1imb6bJxOHxYcM6PC-X54coCD4CTa9-mDGGvrFmJ\n" +
                "Jm8ONgXTXyzJ5N8bZudE1uVKL6t3gvzdxeuGiDjUMqG6H5f_0Iy5GWWAD1w3pJmuw_1knMhNzw5h\n" +
                "eTvv5_rtfZea1B3BBgvhBQmSH1-oaghIkeWs06UtODapu381LKuxcjcTnWcgyGL87RC1lBkx5Jf7\n" +
                "pDglAmG11yO70I50UrZQ2DYgjOaYkJq2e11aepxzNpjnPbxkLYd1rFZ11HyhP1OiXkxqYrG-gVbc\n" +
                "1lEwi-wjk2S-yG-P_OaXuiECvddj8gN5n86bomqbXq77D4OGuN19zc5sqje-0EUVAyJOa1eUySRv\n" +
                "pvWNr51ZaZ9YDSdk-VHtq9j-EEVggTJMviwB10Rfce-a6KeXq6mRpJYTRarjKZe5WpkqFR6Bvx71\n" +
                "VjXYiNNNAtT4dPEUgvcvW0wW0pMlec6LHNp6mO8NoZ6zZM92MoJIXNcyRLvORO1nXPeXnVz6LVha\n" +
                "LlAW31EcY4rgFq3nLgis4Sx_wdM-Q9ziifWL_TpQDTK_Dz8o_fArHS-W0oeqdGRmoNjSVLBuKORC\n" +
                "abVR8AGQa1R6AnkOU8uXPn2mmpM-TCtjtK_0ZmDuy-9m2rF2moZIZmcCCNrZ_JQv8CBAfFc6uyC9\n" +
                "tvMofaSxiaOXCiHqwkHWFGu4pMlfunCWZd9gaPhjNc30IyvADwWAEiNjPCpWWjYO16jqAd5hOvbN\n" +
                "mQlwEos-O5Htp3Faq1kKLDERE2onaBmXd76T_LYotGYz81aBiazTccFTVoi4230Ij3r87E11toBY\n" +
                "ZO1atGJX346dP6bWFrzf319M2pc8Vd3xZ6-SF6nbtn7-aw0rIpFZyoeOJ-QFAc3hghXajn6-kxqo\n" +
                "jhf3k0EGOyRLeYw7Py2LFJKhTjE388nShRo7JL83T-mrygUUadbFXExrwjmSRq8PzF5TeY8dgcW3\n" +
                "mEQqhzgmrPb1bw0JBEvL6hDXcc3LdNlHLllh5k5Sx6iaBivjJGGMkctjwPMS0XNZXXmngq1gIW06\n" +
                "7c-2dSDzhi0BPUuaAmjCP1UrXT_qWwvqLCBda29LM0MML_HvJ5ivxZCV2N9-2JYKIkeD4cjWPspi\n" +
                "rQNoQjMzmSwXt9n573UIciz7b1jfT2oL0Vqe67nG7_uis9InDJVf40ybSI8Y2-PHKHnevXMeZ6dy\n" +
                "N91krGGUGeMWDMmufBlzl9_upcjVSqychC5uUW-Y6tKy-d68BAsZouwopJjVzbWcNxmnpW_NFPw4\n" +
                "Bc389YrPdtmLvwBpwhzbTOc38c3ICWxNEaVrIeUh4_n5VcPxhnuDjPFWx6xAvHS6VcfbWQZVeT-p\n" +
                "KzdZVddN863BaYvBQuwElKpIb_kyF9smnC_fuGbDlj3hEpNQPmsvO9dh__Htp9dk8ZEkXgt1Ozp_\n" +
                "HgBvEpy-wyNO9hE6gqcNDEjb5um23Mfbhw-AkMjgpkbg52NrFAlm5RQ1CkqEbwp717wk7PfADNs3\n" +
                "Wv5t2z-FXEofEZh9rWFevi07aV3JPX5Ssimftlo6MTKdvVcDXvJf_pQU-4g7QaK5Stfz7IIEZ9TE\n" +
                "icBOaCR50EA3ppKdVeETcV5ScAdvS4Q_7ocV4QfAnK55WOmuECHQNS_xrHbTs-A6OOjt0OeCvsHI\n" +
                "ydt7lvv6B0Dpy18PAEzFVe39fMOle0fT2lE87Brzferw2HXw393xhUkyVmps6GHxfwFQSwcI6sdi\n" +
                "FtYhAAD5IQAAUEsDBBQACAgIAIUQfUsAAAAAAAAAAAAAAAAbAAAAaW1hZ2VfemlwXzE1MTE4OTU4\n" +
                "MjE0NDUuanBnlY5nOBsM24aTGlE0Zu0ZscqTWrWJmKE122qIvR7EpkGpVXtLtWbV1tYqNWPvR2tU\n" +
                "VMTelIfWXqVe_fN9x_vzve7713Ue93ncV6SrRQC9nrauNgAIBACA1wO4mgFoAKhBICoQJTUVFRUN\n" +
                "NTUNmI0efOsWmPs2CwObAK8gFMIL4RcSk7srJCojyg-RQEjKKCipqqoKwjR1NZSRciqqSn8kQGoa\n" +
                "GvAtMBc9PZeSCERE6X_OVSeAgQoQCCgkA0IANxiAZAzAq14A5_WfFH--_b-QAYA3yCkoQddQlR4A\n" +
                "JAeQk5GDQEAQxR8IBNwgYyCnYORnkkRAjG3TKKVMvMMLum4LSKvjasaYBR_Z-XTv7snc09B8eH3P\n" +
                "DgSQAf9Lfu0mI6e4RnwMwD-u6wX-P2Ig-yMWkEaEX00DaK_JdcMAgANIhq781cftus7eF6uBojKV\n" +
                "Uh7sWyfMTTYHsAM1RYcm_b0DUa-s7chw1K5-5YsDOpkLIbC-J84c5HODEjWwwYNRkh0yi-rhwP4g\n" +
                "AYdd6ZsWkHsGWIVt4douja1ydvzjJFW73wJ1HBS0TkysVrXlKvi39VNzFl1rODu1e7WEKWp7T8Kx\n" +
                "dW7sPHnJRvOkH96N0qX3KYZRWysCK3CGYnimKlvbsGumosoUFYWfDtKaefHzfrtcT2ugIRoxopyt\n" +
                "PD5z-frsnasfKkbrrmPv9gz-9Ei7hMVZUynk5ublyqDeyzFEjOHxAM6Le-IKIKio16Cm_mYnPZ2g\n" +
                "ppr-nlXP3ARIMDf2KykB_CXqokl4nR726BXExPUBpfYXnJOJb-svBuYq8bgUbBl40fk5q436lx5t\n" +
                "icBoWfMC20XeISTc6GGGuj5R9BB-7nI_79KtLUXQly7MiY3yYzwZ7n2WvtAde9hdCUmmwUpsKigC\n" +
                "BTAHJNuLGUIsMcpVc2z6MkdaZE_1FgE_Tb0U5k0mo70ToN_XE0Dztd6JMz-jOy3vpK7YE-2drAzy\n" +
                "GuTuMw2Or4MsmgKzFxetKEt_gLdIHp-jb9TIW_FEdA7cW-T-ECv2QKZScNY2fUJDz9xYJr0vVo-Z\n" +
                "WY_fxYgfSJ6-Ce1eoDoRXnw1QZtCK1Z3BcBlDnoVzRxQ1zOuNgk3w27-sPQkkcwviCXH2kwegwf3\n" +
                "jZ6tDOk0q9VmOPkEMzzNHPZ3FZOT-geSQLuZj0N7rDJSkQ1ycPo6CJWI2gXd5PjtDAYfp2SnlFCp\n" +
                "f7iQlsQN9TehMBqtd0YnkAt1wRMmKzG9ekuhAcRUzxb7Q72JJRWH5ec8Yc3Ts1YWbjlzqG7HVrmw\n" +
                "L8LZdY6goLlHvcvekr3rtpvVgUezBOdgZvcxyGqTKT4xXFnBz2aGg8UmgweatIwmIzq7b-3Obtfb\n" +
                "Zo2FO4dXR3QxysT--BXRETNYox-VJNH6UO3INMQsh81b_COyHYGFe9h4_t10uoleEWr12fAgFQn2\n" +
                "vr0w2H_go2xGUjddmMk03DujW5Mc9bIvuojZL7_QeIvVT6qlZF-PqpiijNDIRvDQvOvb2FMFSQk1\n" +
                "sVLXPoOFBAbEFOaARb1B3rosMRF6z9H6zcniodtfzj9nDu2FzkMvtYzriHlbMl9_PbxNDIQbI9cD\n" +
                "3v4evdT6e0tvbDoJ1iLxZobdIkTEsE9ldlav4hZ1Xd8_cxz6BHN2gWZJ0fiXgtas9Dimz50qDHE0\n" +
                "ucOXkl2O-2w2KeeNdKiaw7GloCnthqAx5G92Weyj4qGWZ2ZMeQbDXDG9-YjMT5IYa_ZKi5nglu46\n" +
                "yKvKr0TSd_XRLXwJ6W_WZO4iZhuwXf8V4J4TrUMtb1ti7RnZGEWTaE-98Wkbi3ZbBSw13EqU22UR\n" +
                "CmKeqIWq_BuRQDB1JO3Os6Wz5Dm1pnT5NXkifTbzk3UlxcNHWhyqCOY6knji_TIY1nxqOe-w_-H8\n" +
                "O52sxRJfNA-MtXn-HaZLkDdWOfjltJYUY76lKwdhYjHsVXr6vQBophW_ZqQLBCfH0paMiZHQgUrR\n" +
                "eSP4rX-Mh7a7zCJZ7-uJ2Pu5f-t_admlbeYSsNJgxhf6GUPhwhIuirHz6HkDKHSwR4LtyfAJm6tl\n" +
                "eLz04cJaR0Rl3GskF6BLX4Qv1d7x52xjFpQ_P3OmA9a4r8FW-tuoW4RBLeeUoPRWhyEbDh9YGqJf\n" +
                "87El7bwx4MhV-WVk8S0vdiNlrjmoAx36avuv4BFngei787FWiyMT1kZMZ268XIGUZt_bfaODS06f\n" +
                "aG9RxKANVkr7MFoxI6R5riYBG8uYussnBXK3RfjYuOb6fjoI9ywLpXfE0dECayawhWv1Kxtpa5mJ\n" +
                "P1AOkkiuyboxcmPq3LM8MbY5AIv0e7RVqi3Ew1vpMNypjyJBnuFjdHzdckfigwcZSR1O790QPX22\n" +
                "3LR4v_bcZYPFCdSUra6P3ZtiV-hXU7YNza_TjTwaZbh_BzYHMuqkprwOXIRilehDbJ_JFTUNSrKB\n" +
                "uWrZofHh3rKGA8OBMzXyae_zVfK3lzSB-C0p2kc1H6VpBzC-IfsaW-cDZc8FRGszDAiER4kvY1_0\n" +
                "1Ae9m2bOrFBj_dDWaZ6c_ksbd1_ciTwsIM8G-lYv6Kkm7-0bt0u1OPvwWOnOF4c3R3jofqaFZIRS\n" +
                "-6GNfbF3fvPuRhV00AFcKhx0PB2vAN1Q8K2eKSUaMR7kTCJRvv88UqgfuYMkuHFW4yIzM6vtgyb7\n" +
                "ZHy6ZeaJ2Cl7jFtwXPuNQANOQh2d9xnhRWEqfRfc9Xg76ezw9L13USZLPzq4kYpLB07_fShSnqk4\n" +
                "8xgUctg-7V7EJFpQ9bRlULeTwnjcq9j5rvtJWjNVSlZnWoUrRlcAMVvG_m0VysFJvgNCVpW_7j23\n" +
                "FheOto1b9WXGnpVPWRdXDTk5WAjdq_qInTWqb-zJnbNVqJn1VU2THBnK0_H_VuXPTJiBKapxmw6U\n" +
                "BBZnck1V2LNd2pxPjnxePUh0G-lTFVb-zuwcEGY6a-Ff_aziDNr22FqMTxjRyNTyzYvUXJiTwlnX\n" +
                "rjquy9rRB6zwq6rFatMCiwP-sjj6uuRL26D7Ju61sOJ0EBRooOJ6YiDNGQoWpYGs7yi5oh7wPTQX\n" +
                "n1SrRNB-avyReYuniuxE9GVBgnxWjXytOrYKW_H8w1Q-KXW6fM53uGsZzdNMg5pZc7joi7ItesB4\n" +
                "L4UvfMhnv_wAQb3tDBM23pjHffanl4-g7IO6zW_Z8fxz568XZjLrEpMm6tWxOVKxaL54NZUcOAwj\n" +
                "a3HCyZlgSa4vUOACOkGNPg52sSty4s4uzlbkmzA7DR5bnAHfvDcasjbyJctOsiTCqL9OsRPj4ifv\n" +
                "L6M299hKDGZWKbPy-4NuD9F8KheplLpYyK5hePvQcqRv36Rfm_ZFTI_7tnv-NCe7xETJHuWCA-U-\n" +
                "c0j9AcqUlKdOw4WGhtXtYzovv61l9UuQey74aEzTNYORNJDU-o8JVwCqtQ44jibypUJ_QZBGdCri\n" +
                "55xhTTHalpJv0-p05GSaF1-P2K4Re6OWJCvW_HdH4j9HQyR9BrUb2H_3z8ubbIg84xxc2msF9OUZ\n" +
                "PQ5NwQ95ZfYUiCsGdJAofkcSyLH5i_0OZdMMQm6ke6pnaT0CzPm-97gF-twIHdmvm6WlVCgYvuLd\n" +
                "BnjipOAp3nYwCfNF2W-0y4_qz5j13oX80qTZxPe8KwvqrWC8hT5JTig-XIfvoEaOPKoz9ptNueUH\n" +
                "iHNFVpiilMNen5DPyll-sB2dj7si9rTEN4rArUrQE3PDT8N0KA9qxlyYUxIY-4GTUbwe9Vp-o4ey\n" +
                "ZvipjzupY87pc_dAnHjya9kS3i1J-hbTUvx5GL1GLbwVPt52GjiwOlFzwixnJ4iJTd8Ck5o4i0v7\n" +
                "dJTdJ3gy6EbooMLEHMjxPD5fDHmi-IR-J05QEEJcgODI5u6PKhnh1NdZUrnoJLRKldMtB73xXSGc\n" +
                "tEjffPfkH6PblbLZXZr-hEq5D6WjqKC053m51P7_hpT2b4TPfjUqxHVxsQbVRTmf98wzS8KP3K8A\n" +
                "dxKUMfHgHsUnDdpYQKGaJzxkk4Utjk9hxf1dtjY8wmhqK_iR21G1jhssnR5dUj_5wcQ270RmL_cr\n" +
                "eKk1Ppc30jhr6-2z9IXHOVGZj-2sx3MQpl4fVeUYnXz7uGOrQvcORKqOmBq_6SsGxpQJHSDVQMTQ\n" +
                "8yciwzBv7Ev_IFjZeOivXOuN6-ou6_KF3mHAz6VkK90zkeFUJfvGNulBstoQ2QJ49a66Jzy-mBba\n" +
                "MJ6UNl-EmAyKlB5GHqgpxONRd_hLGgiV0rsRN4_i-ag-heiK5rnc-Rp6NfUfUEsHCGnU389jDAAA\n" +
                "hwwAAFBLAwQUAAgICACFEH1LAAAAAAAAAAAAAAAAGwAAAGltYWdlX3ppcF8xNTExODk1ODMwMTQ0\n" +
                "LmpwZ5WOZVTTDf-Hf9uISU8USZHRzagRMkqQhsGA0d1wI3BLiUrnpJQGaQTG6NGiwDAopWEgSqnc\n" +
                "otIdj_eb__88L5_P99X3XOdc57qcu_wCMOlp62oDIBAAgP4ccLkAaAI01NRQaioaKBRKS0NDy8DK\n" +
                "xEBPz8B1nQXGysvNzwfnhvMIiCIlBYRlhXngUuoIWUVlFArFL3FHV_O2DlIFpfyvBERDS8tAz8DJ\n" +
                "xMSpLAQXUv6fd_kagEGBMKAMAoIDYBgIAgNdkgCOP52U_9b-3yAACExBSUX9B6oyAeA_DwWEkgpC\n" +
                "eeVfCAIDEBjFVR4EJbM6Gh6QQXXN1ClKWqO0qW9Lxmz5Ogtv4DifnKZzdGZZ88QfARsIgID-y_7H\n" +
                "AKGg_IPgMAD87yD_j0Fg2FUEhAcdAJdWZ3a6JAN0ENCfUggMUAUWO36zuHHdsa08ultxL1kezXcn\n" +
                "LGkb2tKgf6JLFmsEwt5q-eu0cEf2W6uUAC2qfUeDq0eWMyzjBFDh51Y_zMzABMDU13Zyt2bT-TmX\n" +
                "Hn6K6MGWy5CT09HGkYfrU6mHZJ0U2OzkRzhwm9HG6slOqgSzI06b69NXuhW7bo5NlokN70vvi7FP\n" +
                "jFvvStRueyimCgmhmmcYiG6U3m0uH0ccWspQT_juGGKTZEmO5q8JGInumNYPpG6MQWTGoxbZ5SM_\n" +
                "wUvAsw4SLE6pmVVUpJfkQGNR5EYwIksXDIVKczPUqfU2J1yR9cLQa7S5wkkQOSH0Ol7ybPNuhsDE\n" +
                "pFSmoBNZ-HaTGy4a5iXoOROZaC0sWWHCJtbkFveeRf-12k8WrG1G82vQeooYD3YmEtJq8bTVIvJ6\n" +
                "0zUtdeOuaQ2VO8Or1CwijVWduZtG_V4rtS9F5hyBd9Nq_hPYN1PlJrhk-dkxgycl1cv4ZcSoaUGQ\n" +
                "NzfO45bDuhc-8oXJ9eta79jzar1KkXC9TFPotEN_5NzNr1rztRizweWUuUkWZsUjBB5NO-c6F_Wq\n" +
                "OKxnupqD0vFKan4mdOoOgR4_f5eyfyJO7X2poFW7jxzhI8fFWeV9tuAc4Rg0qLUjL9CT0-TsKVmr\n" +
                "BjbhpQ3Q-WhIk7XHQbSsFYRrLpKnnT9vdLd7zdBRsXIvM1rxxA5Uqy43zJjzBAfab37CuSQ4fRzr\n" +
                "fhoPnhdOTF6WDBpi2YnjCQ4gGC1sUASmlgWzJkfI1yJlTTpZRoCe9W0FDMO8hNaJH7Nmc43bb7gd\n" +
                "ioerut1TRqvb4OczajaP0oub9bHSx0nWlab1_gE70of8vaap0nlV_V-_dWaOOl4CUDn9FD0FsjGW\n" +
                "xnd02Cwgu9RhyKgyQMd275lNkrK8HiZlSuVNtG5jyGuCue2qgqmsuxJbkxqF1thf4Xlxv28bza5H\n" +
                "-b0frpPnTrQMVHyWTJ_4PaL8I9nzh5RXfN1rReJFUG1pX2OSri_rov3h8xB9Jkz0FeaqF4NNzvmp\n" +
                "D47UIFatrtXra3ZFggf2zMePcXFG9NHOrKUmSFCrDOTU5vrZ4SXQ8Xrq7UzNQa1MWJ7lRR5agleT\n" +
                "8YOLxvciyt_SS0Vp5GzrJuW-nJ4cA09C-mEytyzaZFQ1rGa7GHuxa7aCaqZVz1OnCLNHlA13C-2n\n" +
                "IL70c-qfaSNIiB8cnz7s3vhIkop8egH_3BA7gPjyLp-0H-MdO4xaU5o6o_MPdEg73GDxlHLTcNXN\n" +
                "SWCktv2qsFz7eAW5zKJTxxY12NHGHbt8TOwxIPNL3Xe5KFYcfUHEHFU-MnM6_xS5dbTrfkaxud37\n" +
                "bmvx4aNggYKpQPHcKb9uLJvNadTrFqo1jph7WncvgX_YtOPLJlvymwgPfGZ4axcETFFm9Q7uUq3C\n" +
                "iKrRKjKXim7hDC-zMaNVTQGVmYsM0qAyk7AfwkuMba7MA6_Lc60ir5XJg3eqBFPNMCLZHsogUJu8\n" +
                "YIogA96g14J_8FmBvffHg1fxXUtNNQlNTWMS1JW_UEFjR0UhJJcx987GJav-ac34tXQcILB5d_Zw\n" +
                "fdZqEiqicMy3uVcbERRvYfKeJPX1FGIC-e42MvsjbHUumT6-wZEjm1yqnKGU5hraJrpExUDU2GNI\n" +
                "etwVsXqPcDVv0V40UdUBu0MDuyNwcGaArlOGlkP86SJg21eeF5NF1F-SbnL-8PR-5R6kkv8TJOpG\n" +
                "x_a-bTaK0RKhaOuzXbwXV43eoBux-KS9nV10-zimc_KvfC33ZOFSOb1IKVCuzQry_NxFFENRbeaD\n" +
                "ZQmqM4wfaBHuZkxXutkskMV_Xz893Gfy0EtCqxo-zWdZ1dliG-zMM20OArBcjlt7GxOm7afaJwNM\n" +
                "4qGb8ens0XhsufXbv7bocssN4qtlmMshPvraaxxQUMRnBSuxIsrwUA2iyA1H-rmNsK-oj8v6AeFj\n" +
                "OK3Nv1WF_2GAVq2QsR8UVwdySFd-UVSYKl4CQcH-zIq7iFG-sooFLz_83-x6rLU5W09GiH5Zle43\n" +
                "5ZIGm6gD-OMDcfQJhJJt74cb8aY6saVqjSOHgN9ukZflXeZgX_FwBRySpwaS-1lNl_pVYUt_zXLj\n" +
                "mT7dVF40mL1ckd3G_rnNsUGUVRSmYj6XFntL_tzg5UCHua3zg0_hUV6ZaiP3vWxu_b53pv1cElEa\n" +
                "WOuXm-IwpPoqfT7QNu2No-CN8ljauNLchWCmvY58xIdue_JgMq37lzcEYtW4gQvJ4htJxTntGsvY\n" +
                "P6lpgtYEJZ9VR_qGG-KmjbRoW1jX5_0niVXWRTFubSdG1AdKLvuictYe-j96hPCGIU4PQrJ2FAWm\n" +
                "qexn0rKSTvUAbvr36eNlDym8C9dF1afkuzLC7ALUm-gK9_gUTcAcMdWXgLGuADgzCEIBecywbU7Q\n" +
                "FwhCYyD-V5e-aR4_CkRXZvujhaLQ4wWGWK9Qp0QXrVxM5fz9xaHE0cQqPaXG3rz4YkX3OOT6GjwJ\n" +
                "gl5e0Lnv2tzAtPI2oQuc_KXKeZ4gQ92GfeSfQvN4tPhE99RYCx-I4WautpPtfTXjNHsQXFLmbULC\n" +
                "F5BHKj1cVfOTW_KtOpU87GJXdUmQiWtgcp7vfMPp0r3ececas5WI2ipS5cDfi0nEzRbM9VrRFJhB\n" +
                "GslIciH71U27wZo5H_-ecT3YWHrAGNZm3vUjHcji5Xr4nJPBLueTrhH-FWyPeKe5XoNLwzcxeVSb\n" +
                "aOws3jxFupF9oePYKJWBu-hoU1GxV26NkIzxNZsWHHiICiTaqisx_gBP5y0Y8m1Mtp0-35JxEnIW\n" +
                "dcLzextmvR3yNl05IpYPfw01_tRWPOEuEuMD1lHWFOn0ofF9AOW5IbwCdQ4R7GkyvuEKX-N6063u\n" +
                "tMVosXiCT-CSkHCyTCgRU1w82Bh-IHUJVDx5FG-SRPdQnxE8yB9sDK3wlZ2e3ceW06ok4oiRxNan\n" +
                "NQYYQ90T1rEzcml2Sgl7aCJyD0eaO0p7trveUqUks7PuY90Q-5BO_OzWWk0IEOZz7mqV_23wSBeP\n" +
                "QFd3_OJ7Ny4R2UJRMWLopwb_Ymsv56uH-05MDnzzQpxU5P1L0qB62xO6AY-awIPf2p-GH78LOBdG\n" +
                "fcf44MdtiS0eHXQ3qmf34jWmUYMhdlNzuvPRc8wuU1xrMgQR7UptAPJy7cmB5apfZi-svVP5IRyP\n" +
                "Rt7vsrGTzWxI6Z8dIoIzJWqxXFpxP39wTUJ_ZhRRHpiuObTO8k1oe5n5vy_o2wzN0ijrG2BCtRci\n" +
                "20VePKF9LHYJbE5ONRbKXNHDO0v9mjW5jfy9aDMUltA2_EiEsMN7CfAqSeM6xTm3cOVvpnoKa0yX\n" +
                "qIFzP8nlaDrmshKgsWPpJFEq-iSqLSdNh5zlW7UkmoZW7pA_l2EwvPV8NSHYnUaTq7_oVioMXU95\n" +
                "dqJRabCweHPHraF3F943WZXNln3-F5JZnxGzTjN25bRu4RKwUrd-K5XhztOK5MIBM96Rk3yf_Dqc\n" +
                "Ah7X1e9vmHN2TWU8kBZ6NkFMtPAYQm3MbtDRK5U0yf_0rPOUinDrUUhoN-Y07bX8fU2sVqU-w0-v\n" +
                "RlVhmvzDpfQXfw7ZjNCz6qFHdc97uABhc7Z-N_xvhxzzOa5DGa1X8YLIn9zb9y4MmZ90cU20uAXk\n" +
                "OrZJ-PsXuiQOdMFGgT0BspF7wtzV16dU-nFg931IPEUQApRmp4UjjpQY4QTFlHSKcIXUWUWBgXPf\n" +
                "LXXlrK3qNTIjXognnLY4MB7AA0rmG_GGsOTaeKcayKPJdy9bb61GvdI8zvtH9IBup8H38RLkrLgm\n" +
                "_97DkePrd96Zj0STvJY0eazNMf5Op2d8Zm4n7ycvuIx5Tz7YxPiPD6hA0H48krf4Uich6n1IJBkV\n" +
                "52o0PnOL0xeU2a4f9obLNadOk-PZObS90ORFaInM5fx_AFBLBwgsRMSvlgwAALkMAABQSwMEFAAI\n" +
                "CAgAhRB9SwAAAAAAAAAAAAAAABsAAABpbWFnZV96aXBfMTUxMTg5NTgzNzI0MC5qcGeVV2VUFFC3\n" +
                "naGG7pB2SEkB6RKku7sZwpEcShRpaakhlIaha-iBQUDpEpEa6RakQxqE5_fej_f-vn1_nbXv2mef\n" +
                "c9c9a52H2YdVALmmqoYqAAgEAID_DuBhAaAEIASB8EF4hPj4-ESEhESkj8hJSUhImWloKR6xs3Jy\n" +
                "sLGygbn4JJ5y8YjygNmEFIVFpWTk5eU5BZU1lGTVJeTkZf4jAiQkIiIlIWUiJ2eSecL2ROb_jYev\n" +
                "AAp8wGfADTaQDYBFAcSmAD70ARj--cT95_N_gQMAYmHj4v3jDMgBQGxsLGwcXBAeiBQE-E9J_yIK\n" +
                "MKUwrqIBFZsDjFokPLWBht3Qp6h74tkLxwj4JB7ED9HYc8zBKSqmZGTsFJmWXrx2QqusYuLb1PtP\n" +
                "kh74P_g_-YDYWDi4_yg2in_qQBzs_0MDKbCwqcAGlMKKDtQ4Ig_zAGLsf3cosCkAzwEBRK3P1e-M\n" +
                "5ntXTkxFJGhRY-JSc4IDhqCx9ovf9QGlG-1r3zGGL-vlAwzfiaSEQBeS1KpSyj-tqh6-g5eayNpE\n" +
                "zp2YSs7aFZoy8MbYKx9MBov2bKhqGsfr7E-sXVZS-gExDwAaV8ctdKI47lfabX923eOJCiumyZJ2\n" +
                "4-XzjgmSIflXAhGbNvvYoRbW7Pb0mXI3pZpv1ByGjiJXQzbD0KBC009zKS4u7x4AOPPsOPMuC0P3\n" +
                "hMb5gRcVnqAI5YTUNX_nYtZ8UoEwY92EtPyPjS086ORXf1xW_Ho2WTT31BwcN9acBptQ3xuNy7aY\n" +
                "C32M1evrmUaajPQ4-lDLdfPpnmvztIfJaD4ee5KYFra1H0HwxH0K1AjOa50DlEl7Nl63QloKWePI\n" +
                "pNe6Lv0FF0vpQXuwJyjoFivujeQYPPFO-2SJfZibuEC05c3t1J6cpqGXmrIiMaiPs7wL1mR0kTR_\n" +
                "eFnRtsQj8QCg_KHRiTLZaYqT_GPAE0ug0XnlswViuQ3eBTQnrF6V-SpuVu3PrdbyvxrXSdaueABI\n" +
                "eTlCTAxDRwp8agk4Id3eSXCokk6cg-vyr5nRUSp2HeUHwHbBkbZrzt3eAwDq2hpL6b7LW_amnjWc\n" +
                "93DuiFWC4q-31SU331Aaf8zrtlsjp8IlbnLQZw3smNOI5xVprsi7G4B8zdd7_IieIF6lKiahblYM\n" +
                "4I4wMquPrFJOYu_F0bI-jdOqBf5KegwSmverIoT3x3wM6W0v2W9J1gR9O6f6qGABxfPA9wd_zYfu\n" +
                "42NLa73uC23Oi7yWnPCsenaAUQoRjgIc3csCpF7LhYesVANOFaCzV06sGAWTNOpHa0VoDxvzB4C7\n" +
                "3sBut7IAlOtd5Fmv3mJ81w4O-5Uz0d9XkkyhmlwFaKE1avaZMjf5IoGrx7wa7PaflzHXOeCCvpf0\n" +
                "e5ip1u-myZ3mI-oI5u9TC-zJy0hn2WSP-R61eKBV35OSc97V1-lh1TKBB95aMb8jjiIaFp_Z2mkx\n" +
                "dvRquhxnii41N4heiVVNXo0r2vWBXfYL5vTAV2Jmpy2PegvYQzqeevUJKKo8pmVQ0IfDTUXbu3vz\n" +
                "4B8H3uJIJvIN-rjInk0Dbw3Fku5GLJxukK5HbCLwWPyfIxJrLfahTc9BJZJh8i2cRu_WuVcTddwe\n" +
                "E898vEkZDp2CDJ4bTX13JRN8CYWMemtchcU_YWC1rL5W6trEdRlB-PjQtBEWoxcWU_ZoM1KspUoa\n" +
                "X7P4UwYntS5-kOGt_MTuNi-fk3cE422MYlKYFqs5cx1h0ThUHcizdZ6lVRn0pdIIKHaAC4Q6MsY9\n" +
                "WtCCsvIp5dmD_XM3hkhDL3b8lEkm9BhFo3fsGRRaGlDb64g73eH9LGkwIH84rdBUF65OA9fgP1fh\n" +
                "QHNkIMPNuRqjlE-MlJ2zFZnsw6wD6MRvZyhOGOhL3A1MFTmSei2HseO2h4G0uGwFztSU9niqEULW\n" +
                "tPCx5NOam971ZrNA1KQgFAl3XnDUzhMhbgUwqc5pdnOz3HZM2Tie0qRRX2prRnACFWRmQkeYj_HX\n" +
                "N0mARyahT14vVsVzExa316djCHs7GMSNew5MjJp-7yhD4AwZ5VNu1qpHHl_s2R-Jua08immKlrLe\n" +
                "4ceJ_vpaT4aRD8F53NInSiny6EDhDfVzmlIxfbFaqG-L5fyoIigohzUCFTaPN6AuSELKWYfB51jo\n" +
                "dAhuZ7uzZsjMcDQ1oIkub8yDKwKoZistJPHBjqmEAXruhXUamiMBmiPE95z8bBQ49XHfyj8zdpqR\n" +
                "qyDfWjf7rrjZuE5MGE8YE-xWtEHY8eYrHcH73Fiz43qvZtcZcImXSgSzstX93M9zSfFZ-LBj5J-n\n" +
                "M6zp9f_yZS1hGLSpp5S2K2I8t1FqO_vN4F8viQWko8NhhalHORDEWrepgv98-08TNbGejj7bI4uf\n" +
                "AEMTgp-qc43AHT-7x7XHGfLyEg86by0ZUecGDY6JV3vS6oEpEqc1fY5wsYzx0_-1EkN2HnmMP8jy\n" +
                "koHqT0txqeJ9golByZr622Jsz3hESgiWko542kohpZZaViMyozNnE8rxkdniy2OQWEha8Di8FHl_\n" +
                "3S7_gjx48x6_nxelfGNGUPRMyGmAsdosKsNP70AjtQE_G-KmBASp484OUJ2IU73YFu2gTXEf7CzZ\n" +
                "wnwGvpbXaLOY0Z7cQiMfL8p2pObXARevi44dNS1fWBpQaGhpKhbhvPAX4IEqDKef_WKbJtfQHK4p\n" +
                "LENI6OhxaKf0bYuGXRxOTfMPSTKu5WoH5hm_H-IIsl4r3bTyBinQZmi90kiYWJxYlj4nSoD6f8kr\n" +
                "d06BuukYG8Kks2IpRVvSD5otW4iw0ez9mEZkUTOi3KnvyeZEiEWBuQBl0war9HiNPJ9CrbBX6Q4l\n" +
                "wjhpl9Nvuu9JSiPP3i7XRqPhWzcnd10YnNag7SX9D6bTPeuj8BwBn6rGgj1WMewoSCdkq1DBvCOW\n" +
                "EqGZluGM9YSBHboXczP4AMiheH9V-G9c97h9uf4XWlGoUEY9y1Mbo9RJeeqxrL5lNOXxFFk2UlZP\n" +
                "-FPj2wDmNo73kkiqt5-M_QzzdbyEKXjlUi3PWSeKP-mzamrVYUtUU05q8iuyKPXtRm50aWe17S25\n" +
                "whIaAHf_8C8HKHiWZYnbSEsLKXCmeq-ZLTh25LaR0ASQDFHuDzWT1n0DPvaHaznTO7kLy5vliCUK\n" +
                "yitSZPVfTQUGI2IJuL07k8ZYni_ct9HAYG9jrYyMmJXWff1joNKAvQ-4r4nLcuJgBjYGtO0B35if\n" +
                "LSrYHx95jTJT9Rr0b1ZVIeNf0xeFVCLcTlXuk8UTzG_pMFNTg5q8vPdt1RfGLXditYmNE8NEWQ4L\n" +
                "KuCZdZ3aL3Mb47oxcdMqoeod0yhSqvV9B2mrqtkv0Rt8Qie2TNffuN8r8BCNxbalk7bXXXgMqwzY\n" +
                "0g1wIZKOYjUVEjfJD3U6B188XQv-aLEz8E5b4DNAkQ6Ewy1uscjcnzqsuqPdlaT6ER1V0nzg7SN1\n" +
                "y9-seS21sZHeW_KGozIfKe14-EO6uxWHRGVQEuCUgxVwxQgCka-TVhJXrpGVh-UQKI-9Ga7_rKq2\n" +
                "9Ctj5Ef7z9gK19T-VREJyU9_3Ult-WiQ6xl65fCWyckpcdNMsRrc09fR-ZPLtXe4rZ31rbxO70Qn\n" +
                "R5lczBqixuyX-kgyrZlcqooOiNeM8C4SIYu2y7-GOgeu8C7UzV9J1QY2ZePjXe-Ka5cgitMGr12C\n" +
                "7ZsJhyi_TLK_hERGoibVua5Gfv7391bkc60ojzER46l4I47qBBdncXMvEhwbFMERmqryFQMuHLv7\n" +
                "kZGqung1RVdLwpVO2mdDldkE3M2-A3VzQXq2Ad9Hlunwy9cmlOeKP0nouyFwRH6KAyjdAf11wwEz\n" +
                "CYxFD4D-fYhzOQy4KYrXcAAD6QtgCLi-7k56_pD9qeYcpwEbyPs7Zff0e2ehht6TOMQRx6teXXVA\n" +
                "zlohabXPUM3Ht4NMM17eEK1cJUvcYS4M4eDbfmBf6WQ30D7I1e2TJcEM5AOR_2On8DzMUzBDJYl5\n" +
                "XL3ts3vD7dIbyzmmm7MxpIUrqhh8ojvMKjAvNvgx_4QEYPX4vP6Iub4ZkpqmyvFhFzq7EMrKYRHB\n" +
                "CdKKj--9835-OvsBTKAtH1Bxnh6qWPH0CUgyhlWsMMDixtp3RsOr-NDmXfE285b-pMj52eENf-i5\n" +
                "gcjbwz_G7T2qMt-C9qvH5ccNf9xYWCT9YeJXufbber8G68XW9h7WeyzYubNB4jF0zC15xZ7_lwLd\n" +
                "F-6AVjDUMJhXHYlueD_sjemjh5X-lPTtkjhWjs5mjMUK9FDHlkvowqVMbRhkSxUTT9JESsx4S3Mb\n" +
                "tHyPYNbJSqQTSCkwX0hl9JuzuKPfL67LgE-OXPcGJZNOzM5fCsrGJDcO7MYQtIEYoWSpDWJrS4NW\n" +
                "Vk-DAg-ctDv4YtVdg8WUmHt38zn8lUpJ3KLGWuwk0W17-3Yu67DqdUO52Y8wFhxOKDLMVLuHIBtf\n" +
                "RrztT9u0mGsvr10PwZVCflnahFN8wzjUr8-aKEVjBTGWdQeaLEmtqeaHSjt6RNpAkk6GcAKtbaxo\n" +
                "k3j6uNdbBHzZ3BNhLvWwH7kQ7DVYez4FFb1Pl5GwtdjVm4rXlKmn6O8dxJ3za-4pgGmNTd_xUKrK\n" +
                "YZfjtxBiEd2tR0syLXhSfZlCDrXo37RxfFtu2rq-Cm8nyBDq17O2Q_PbFl2ogQvZRKRcNe2nb4f8\n" +
                "8xq7e1UWAMgSbTSQQr4idUwCNrsxnmp1KgwXajgghdI-_kDNISuYlCupxhj-LsJMphnmmyuO5toe\n" +
                "WnMmomyvCvlO8MEzGeXuGWR5CRDvlqczT582CUkDkSOrkor6ohtOpjNVf6jPiqG_xDV8tcw1zGih\n" +
                "hjNbWmPSQtTaVZI7CmT4A_l4SXxtDzm7l-alEn3Z33CEhYra-YbWs_UiDEJSBumsdJpC4q3TdY19\n" +
                "ivLSxXSOUoAgDVg984s5PgaZgG9u0Fn6av0u0tv50Ze1Ql5lIxuOuVNA0-tbrIOn8XhSA1b7UyW2\n" +
                "g3PBSUJehZxnBmm60P5Yd2XRlx63iDEOD4_4jKDdYggJExeUqLbcynJFgiqHjLhyUvl3JTtkPZ57\n" +
                "_shJvhA2jAKJaB5Uc2WKSDaVJMV3ZpcG2ckekjq8nImOuG0pmcfQij_aQtGKPUntePNNyNhcbrvZ\n" +
                "X0fDI_EBIFqv9h3TJWUdhXpB_JoI3hg4SpxmKNwoFWPhv_DR3PKCRXnT6Y2Nu6TTdVHVmt_0ZM8g\n" +
                "1O-Dr1JtA7t_8s9M7kwVK6Vb6UPM1bTlRgUUVU2c2uT5NoVflVA-n1t1EJSDHvdN0qCXrWuFM-Dj\n" +
                "0CC4X3B-Tlxea6gPKEtgokq8fDnMLW06Ui1S8Z5TXHuwuvseYlAC5gHBtitZJ15FeOJzofZBFWpo\n" +
                "wu5FLzh8QW2v7OPpe3BVQWH3jWFYQg5lgWs3ruWSoaFV-mrBnE-NyCUbxoy4xF804lgDXwTbtZFr\n" +
                "cSdgqaDJv-xiD5JTgVr_-QgMVBIVMVb__SvTahY9zhHmqRLtt-H5uzXuScq0hoj1tML5NDkMP0hq\n" +
                "AWW59lkvYqsqNNL_RHoCWmF_ru4NxR5CtvDXnrX8mVqX4-hHV9kyXCjRXZYeZRVeRJ0CfjIrtOLs\n" +
                "AxpoxzB5TwkaoFXefrCcfmwfTK5TDlGa2z7u8IBptqmN633uqaW0qbahrf1wWpkiQU4VHerrUok5\n" +
                "0kfasszk0zjhGkU7baOZVG0DIC3F44sfMn2THI06_rLaVnsWeCs-3TwLlEEyBzd6BJ2aMishDHgO\n" +
                "P1X8MrLjSbFAAefaVmQ7E1z2nnjSlostC5x1Ray7BPSwUkECSenIKvwAXnk6eLfhbnyVOD0ZEaW1\n" +
                "7ohLkpQO4o0OBW7azezGJOnZHOq6rBpSNIXm79F6lTgo3twt9c7OWml4ilHfEMK-ybzbXi5BtWbK\n" +
                "yuT0G_VRknrWY7THMB3wrs_JfPXX6qq9-4FKATj5V5tW5RHPh2bZocPfX7gWdOoFPcmqV5tr4ge9\n" +
                "h8fFGD1rvIcNGvs3TS2LSq0VeDkreAReGIifjckphSIo-h7_BogKs9Sy4hgvfgMj7PctCsbwB4nB\n" +
                "WeVKsCEegeaQdobkUZmhd-R_LMfe3A3a7bMfKZs4egmFrQv6vrS26ipLDgozibY7Z8cCGKvvZrF7\n" +
                "h9fE7UOUPaf327zOYCPLk0l_LPyQNEyqL4MC8-baUjBDg1KoQA5ygCy64O0LcUrtPPK0Ux2_dNI8\n" +
                "sbRCx4xJDWzqn-plCv32UhkvqNQPifRwPNrOwvmU6G_sPQW-2LG4eZrK_crmFI5uP6wH6e9kxPRJ\n" +
                "av_pnDQZk_OT4K6H9AU21JPQ4kS6p-3_e6zACjjc0djHzLIDYzhts7qgteMJ18hMzDo5DoSs47Wa\n" +
                "lsdQft1KUPPRap7HcOTmDQEEqId_OMtwz2fJtMpDW807ysDKUfy-I9YoRTsOOuJxstgriovioUZQ\n" +
                "1AMgYWH1Mnmbmk7Ss8pJTclbfDc28DAjCBpUDPJMyHorGpJQdVccUres39TUHKO3I3qbS7aI-h3o\n" +
                "XVfxOWoDEoTpvD4zG-15suEeJtcoVbgUjuEuI2iTl3Fvd858ttmLr85bwE3-DvdA4KUELTMMEnQw\n" +
                "sogWzBSk1-KzvEB9ewwsvWSRRF_afdpt4ihClFVU5CFTTzsTwcXewt8SaF1Tu2WYSqVOMgI1VOaz\n" +
                "lmT-DLX-NfbPYOLXpbEcCXZFEAnmmdVccc6zhOO-L9uqFmGJC4hBEGTQsEx1S0BXI7TgXiDkvtJS\n" +
                "T7HfV7dpNxsYeGnFPEVutOrpttOA9n5tjQ6ziOfkXRpdUjybln5NYgiRmV3EEd6NnaF2cV23AARx\n" +
                "UKXMWG6AkeasYWsi_SUSl_JKpsSW9imYeZKnBbtlJAcMN2sWKzua77k0Vihsc3kytCNafA_JHF6D\n" +
                "_56Zf-a2oHXU8Fytm0ylu8yJ1D6TMsgsRVBROle9nl3mAeDONl1hGakOeA1bNabvftUr4xqGaWkf\n" +
                "Ai8EoU9VLhQgStMfi9ViM2OwkR58JW5LLp1o-rHkmOzp6ui8iNfCSZ6oTHSPyN_Hrp5ByTeU6LcS\n" +
                "BLw4XgHRswnLP4e6bWpy8VKbSRO0rI9XbMZeylvZh3D6M5X0LTZyIt81SHP1L4m_dKkWJVepDWL5\n" +
                "1D3eIVVIPU3jAUc8soiRpU3YlUAVOwyxyiT44MR-xkc42bxXMvYICgewnHDgnYLoV7J2I6rf8a-H\n" +
                "LlE0fI-3J44QslnsguP1_OTOS0bTj2Jp7mqLtRikLDpv3Hr5A810A_LZb9ZoR-TpmAyFjgcIsEWU\n" +
                "dbqn3M-OzFd6FkfeC01RCQfzeW4x0r5YIGa0t1xMHHxRLB3FhmCDsZPt4YLZ30gGTZA4g1MnxWOM\n" +
                "PWWYTT035wdT9t551ri9LMm2EW1s59a1HRDrqtYZUJfoFvgqflmkT6e_Szld4U_EGOyRgWYXkXXy\n" +
                "lNCXlc_NvfhytHJVkUs4TWWDF2jDs4J6AMSmnejLssTeu385oQw5ZSJt0_EjG5qW-OLyALgcvzNk\n" +
                "YnnvQfC3NYSn--ukEVU697DfuyKW04LNeYNmRUJKOOuPQmyS1hSCt0_u5mxlI0NxJG1NTmY6E0m8\n" +
                "SbEdw0STHgCan4IYb6vwkpQeAPkVLAMhu115yj_L6spD6S7Kzo7W72fSfouEHtLYffs7Q9W72Xlv\n" +
                "UH3ZdGk0YjYuuMCSd2c3j7j5KYVyE037fmnBytWRfJLheiBxaXgtvHgV5fAXdsWdXpd7IZOt_jQt\n" +
                "-cbD7HaG68fsVlXh9ZCZIE-x9pjesK9VmDrdYnir3HfaAuIS-It1mzkh5Z-UG3sr9gJdfbf45vyl\n" +
                "0nrj34Xzr4dOxq0EXmmG9Celpo0te4xmXxLT5ZCTkqrOtuYdTEtHnxTGB4Z4fICPPUYfPsZePwpR\n" +
                "kPFqzLWWQjoXz4DfcMx3f5PiztT1-PG3Kv5txARxUuJB1CHeUQCAeYEoBbDi46N7KyBt1lDjkER5\n" +
                "1Yy_wdUXjsYhDh-FtKpMqRfmGiYJsvY47-c-AAomvAPk1hq2f_mOV43vb2yX71u9C3y2OYYbjjx4\n" +
                "5M4czK3fJls3vtwYiZe6zPJrwHbBftsZTwFfXKJzwjUZhn7ylxhDRdKcS5Q6edGOTwU7uE1T1k07\n" +
                "uEJ6uD7KV1Xrl8w1-O224Q1imP1J0dX3wTN4twxj0ug5q_pvGZMA6Td0Xhq8Pofv7V-Vjnis5-xu\n" +
                "PxuXTWn4nmYQoEtU5_NsvYZJN0Dsmwe_TuJmHtbdQnnQitufLROyc9uPegfDgkZu1IXrlEY78h8G\n" +
                "8zgLO-xwNr225Mq7bVgfVTt8oA3Iy01Mze4WF5gl3o8O-Xbxzqj1NH86Ln2-32lmPBvIuOqmEFCr\n" +
                "45x4_Xd2oV3ASTjFl_SSJqhTz0GfpUJ2v7bsu0hdMyits19wokt-vYalqeML9RJ1y2w_6USx7wXX\n" +
                "AeOMznL96XMfZRLsYf7ZwZbg4hC45cJZRHp-5SlPO59LX0AS6i_4SMsoJOWyInkkiPHE1PzVQnZm\n" +
                "DsU8Q85ZTKeAUsVlJUBQeIxtotwT6vpn8OUu6BOPtb-2pcws62zJDyKr8L8RmaHPdw6ly-Sh2Zid\n" +
                "Wd-rStWBS5li-9qC8ZvEO0POwtUNeEdDSWv7s7XNMoz4RrvcyFzDTWmSAyRyaTfpN8g3uGsB0eaX\n" +
                "GhfplFrLaN0RnmQ-jlYQLmwf8_y6j9yfK2FmX1qnHCuYSMUo8SN9tzrM_Efzf1wSsXR6Sv_5Y3kM\n" +
                "e6teABYTcsK6oGd0XPMRSDtZ6vdFiAfj_dlEOglLtn365FFgzLepmjuIafEIljMRgvBZX6JM2561\n" +
                "uH3SOVii6-qruvfKJ5P7Qs1EfRxnuUYFnAqKTqgejYU9WkF89BCOD32Y-y9QSwcI2vfgXM4ZAADx\n" +
                "GQAAUEsDBBQACAgIAIUQfUsAAAAAAAAAAAAAAAAbAAAAaW1hZ2VfemlwXzE1MTE4OTU4NDE2NTQu\n" +
                "anBnlZNnONsN1Mb_ERGVSm2KmolNqzSxa--9qiR9jNKqGRFtH6OoPSNE1B61S4k92iqKmlW7NjWL\n" +
                "ojq0xtvn0_vl_fLe59v53ee-znWu65xPny8BTIa6BroACAQAoL8FnM8CWgADFEoPpWOgp6eHMTDA\n" +
                "4FxMcEZG-BV2DmYuYX4kQohfSFBEEn1VRFxeXFDomoasvKKympoaUkbbQEtFH62qpvxfCIgBBoMz\n" +
                "wnmZmHiVxYTElP_fOu8AmOmBfSAaDBICaJhBYGbQeTfACwAA9L9t_1cgGjAtBEpH_5eqMgEgMA0t\n" +
                "GAKloYVA_nOBgL-UGcIiKEvHagEV0nAKY7uuaYlLKRCuZed4OyqnRd0_EEE5-4Uv_x2_DALAoP8j\n" +
                "_C9CMP_t09DQgMCQ_0XM4L-5rLQaBUKaFriU80_ARfBfEzOYGbgJvCpast-O_Rx3LwYMbXcIzGuN\n" +
                "tXqbEWHTSa4aypih2k0fV1mwR369Wx_okvGT0XZJ5l49hyqJhfvXN_vTG-DT8LLciYYDFJtsEENh\n" +
                "Nuqw_l68Cf7S16aKHgSHqHancrhOavUFWoRutlSyBJpWhUUWiDJPpOWiRoTPvvFKpWiPFqha0vSn\n" +
                "cSVyqfPY5XhYyEtyXxRpY5VxZZfbkOYMsKDqvuy744mil6Rb_q5pal5KbOdxZUTM045VzA4meNWp\n" +
                "Fv7BFDXzkLV0ovBizxDFe8g3rHElK3rj5qPT1u89S4ZkZggn12eRR982Ju-kSia_CsF6i2MGlRlz\n" +
                "KzRvmkSEmm50SKndcgvMGS9v4Kpy1eZ99uAuSXlRMsc3X9lmBLs_m1ROtcwmWl7H6GnLG-B08HfD\n" +
                "3Prqt4mGkvkDAZN84rYmUOyidv7nMEdkqJXTe6-AMthFSXhZ95fC5yVu7gjUFahlUPpQ-NLRLxG-\n" +
                "fkMcvm3R685eZebOVwgBCTswjIpqzXeN-xNF-H6Tusuub_ssKSlXlELlgJJMPTzwr8tEPxhnrypE\n" +
                "cV4LKVLf8qLIdX9fqc697eOoaxs5-C6-HT23GGz54MuibU_NJjR8OzvbD6tyDjxNlard3RbcjqTg\n" +
                "uuYYzeKcmCetRxDeabwFjWNYhlRRt8TPykjcerjffCvz8pEkeVOOQpMl6B90qytvQPq5P5nmH3f4\n" +
                "x0lpoanTKfntjNby6xg293t1MVBXwQlm5taE26wNov7gkA8-rQ5uauIqGT6tNKh1gLlcg9_1cG6P\n" +
                "YNjKwQcRQ08gASN3qr77jNi1CJZOKefX1z5IrNUFf8D6slO-pStsFkVM3UQEAsCfWYImt2cCTbVl\n" +
                "ddHxHx623QgTHq0QgbGMUwMxGUx4uDJfqD7Nv5ftZrg30NdoWWY_rRZW-ecsgcxqe8XQVheanIKM\n" +
                "wsz0wALVUTJLNOJXyEOHU-9uX9SNabpW-fTqHUe7vEtWr-vYY3Mfgis-4DLtxH2b7KeEurZbr63u\n" +
                "oj5__p30DEPYlhueak5pTmbR0DO45i9vkb7uPfxY9Utw6BEGVvGAvjmttDllj6stvl3nHEDeolq5\n" +
                "Zj1vmpyQ_7N1851E1gGtma-f0xKyoD4lwRoea9JTAm_hJwMRtka6Dd7KAvPl6zjGJg-yNdtwdhYj\n" +
                "zaPlkW9GWh52v2KfLiDYJWQEI7Ios8VTKvfvKBWwXySN8ErRtvQsat_3mkBfCd0afEAcvAO99LhC\n" +
                "mh9i5CDu6d3_4eH8VaalzZof_d_sjJzpTudHQs30HrK8iekM0viugb_TP93zsulDPsVi-ko8SJBz\n" +
                "esb46WWXGEjHpYqyiiIy-VIbY3xivbFSdnJExb59wLQei5aeQ7aBlxQ9tj4jbTpRWGZFK6u0otvR\n" +
                "JdI6ALrI3zpXU-HgVMNyo489iHK5bECG12-AkIoL9HMZcfxl96FcRi-uffXmn8LyNzIWvxhiyhOh\n" +
                "GYVIFkQH2y1tQwYzI-OrJ6J8xSY4HA7g29tGsZOJUAUMhuxlBsdIFFY4uBG6glJTJA6QPdvoxkzD\n" +
                "UcnbGWENo9RzwGdevTx4XU2_MJDAhFsLRiomZYroejmqn7VVZA4LduN4ArEagfbe3JDIs7gidKbQ\n" +
                "SiR2_NmDhXOAjc2aubaEbGUGfi9QP_te2-3yBSdkFWKnUTxQcXnrglr_5OIah7nITh3sMS7O3YVu\n" +
                "ErRh1n-DMdNqKW1u-iurv7BN_ugQVED2OTA5LSjq6ul5LyXWdGrW9Pu7U6urO6uckS3utdHTJx38\n" +
                "m3kGIVuHvc1135tzW3UfLDgCyQI0z_G882_t581cMipzN1DlyYg50vw5IJm4wsdRtjxoq8TQe2OE\n" +
                "JOP2dBmRvFn5MtxPh1lczz-8sIxJgzbRR2--2JkibZRjKPAq64OY7xYMSjTEv8idiSHSZUmUm7rz\n" +
                "Bk1EJO2lfq40LUY6X5jeWhRoNhE_MhUSCjInXB6nPvXA5L8AoQd1Rqw53nDoNFURNSCkMFomdUj0\n" +
                "jw6fXv_VYvCT8bVmKXTDt3PgTu9adN2pmU3yXKXY41p9o5A1eqEN076XnsM0W-aaYIJHnNPBx06-\n" +
                "-bkvQKEasbNvYbVy3pH4yREF-BvgUxDNz5PdNgiFfhHPTffW2sf9p0AmRaiEMkMTOWpNqWpcJiPR\n" +
                "4ngZMrvI9uBVUDi9DEdoOL0ATVegrVNWvfoISLMjZM-B1KVsFuA9WuoTU3Cf3-V0wtkmS0_9HjFq\n" +
                "l-Cq1_Wx0VYf0oe-8JvB1EaOD7uYMix0Dlz9A5HA6Pz0ZDIg73G3rV2xEmZ_ZGNj9TnQe9MxwPzf\n" +
                "tne2VPEKpxuX3O_6MgHCfrecIq3H-oW6Zue9pxZOb6mf8XVtlHpKMV6Eyo75kWueVamqkC0PVzlR\n" +
                "LYcmI0ZS0vgFeXfgd5VSauSW2GBSvBj-p6DNAfSiW9ddPD9FfFj_0GrpWXcVfmcyPs3lUgUej-Jk\n" +
                "-lcSI3h0oPrR1OEDDRqbw1M2r99Ldy3rH_Xa9nSskmzXo91sZXil974iU-I-6T23_V7kfVIrp9EZ\n" +
                "zOTdj32MrsIK7nFbS0DNJkeG66y0sBQBFR3IxgR6jYRsjpQu7uV9URtxyg-HhTn-d723bcYjou5k\n" +
                "C93A4WIXPfJ0Ezk0INF2O2Qch66ZGSxxhJZu3bBFYgLsl1VqUQ6HPUaz--yeryeMhMMQrC22Kc5u\n" +
                "UP649Y3BwqDBalzKYA3ffWoyePm2aypvjGp3PY1tjskeJNPxklOvXV_UVIu9jtyLjro-0ldXm26C\n" +
                "SgJN7c9zQCzsu5Iky4HXlGwvgU6Nf41xnVx1xH0OaJfWDOchF-BLMr3ht9WEpQp-ynYPAHTxlOIo\n" +
                "Tnm82URA9tWED8u4mMwz7LyfRdrcHUX3MRulaPrOPETCdSP-hxrV58AZO9mF4FzHke57Pdr-rSed\n" +
                "MZq5IKXKF_eoZSp_1OXR0E3qip9W4NrHU-uDobsvqldE_Fm8w3d2Bix26jmE9j7VBuWjYY5Nt-Fv\n" +
                "IsZz6l1epip0Cfmo83r9Ecv2-1PUcMaTIuBtPriYETyVcwAfw_X_brJ-r9-Tmg9kgT4X5N7sedtc\n" +
                "ksC6WvEJh_MYfhL1UOXnKv_ARzddxoXR7faZTzfwEn90etYj3w5e9ADxoRLzzzS0966tQEMfM8O-\n" +
                "VJ0DrSXFXR4HEsdrZVD5DorydpHKo_28UunFzYMWh3V3E968MrkrV8s3S96bb5fTsuGIcMqBRI8M\n" +
                "c64eRm339TLWfL_vOegHZMFtIHd9qvz1lQ6sJqNxZt_yG52xXhHbMIKVvaT7pqs_anjyp87XitWZ\n" +
                "7um0q4KfWeD58WJZhT25vRSJ9n9jbmUU-TY7io5XKIVcJV1EKRaXBmZvMBnphika9cjaTlLR3evr\n" +
                "ymntscB-g0p4t8OodDa76XJR33csTFIcjFfW7lhREqknTtnxBBZLj41rlCzHlnGvmpkvhzwnvzu4\n" +
                "IZX2azCBMKelM9ROeSzK5TSEUFB5sv-EWkaJufa7_5jWf_nnlFy1TK7ydDW8-5FAvb5rflrzgOao\n" +
                "VwP_DKRkrIGJkiFE7LzMUWpVbeGR1y8oS6GIIl_EW7kEpicanBRVE68_iBN711W8Bs2jk9_9ecFm\n" +
                "UP4o2V96iGcXzKf7g5PQXl2PwCmvvZeNrUq4M3QgACMR-5_xTZo6O3rlkNPHQ9mLZdiRxtdRGdiU\n" +
                "Dq5joyADNHiPfDvsdTxMJvJ4oQ-bsTb1D5DemeRpWYfwI_VE53DTDcHq34a-7JyVdxtuSKSwRzmx\n" +
                "K-TYhd4YvO-OHOag7jarPWsOnNc9xseqjvqSYVKovBn3Y7OvPCSDb1ZU3WUqbrReJSn2rSl9M-xi\n" +
                "SaWx93Kya-SOQOKEid4Fp7r2V6PxechRHcO7j-7zFLc-G8R5NRr1VYgusZXk1BzOpLQBRVpdaePf\n" +
                "vjnAvFHNrZvz3O6jR0YuhmOGPVkZdmajcIyLqZb0ka1K5V60gMAEy5ilXct4lVpcPE9c8swgcezw\n" +
                "c7KFU-suo5xZZ-WXmqj-j-s1_xSn1JayRtmicAOUZe_qbxr63ft6jVrSoltvnFUYGP6IVrb5MDpV\n" +
                "p3Hq-K2WC0hy7u_xOt91k2xUDcd4n9oGW6_YfjJs-yVN65uRxZ5h0vd6Td63n2t6vRF0z_sFQkI-\n" +
                "2v--ALeEwksSXXw8f2GSUEnY8WrsC9_p0GKTYGtx5kLpsfiFbVfeIaLrqz7rr6_G0qitl59GLUj4\n" +
                "9fquTTjoz3w6GW2dRJu-fq47qZC1gXpICkA37MDn2MwS0xHyPHscPGNGKFE59eUypYhRH_O0Xg4d\n" +
                "Z-_BDtq722S6w2PhJhL2JAVDy0VXTAePi49pzlUBpVA6txRDp-PtmJr7q0c9iuHMaEpwDI9ExieH\n" +
                "lPwpp5Txh0U1D-NgiOSYgxlXlbj-aP1dU6zBkcYtQ2wWxljIlqQOlLu5XYqR4VpRaI8aLHkyr8zL\n" +
                "qyHbkviC_1jxlt_2lzw5NcciAzk115E4jOWPYGP1I-7SrCib5ofb85sfk4kEu74fPD_Dk06iLZdU\n" +
                "rW52IpVHZI9ArDWaDzcUnuy63kSqFflG4wVMWvbl7590_4K4MPLKak4GMu6Ok9K5J3kaw0K_U4PH\n" +
                "xu38iw_SieoMXuMBI4qQqtKLVzzJv595b9hJfdJQCBdO8PHbQC5aR-kdBJOoeYhQrKw1RrgZRX0i\n" +
                "rr1O-kg_D-o8SA2uo8dnzrZyuh0dYBpht6Po-Y1iOgfu7BLPBnSr8-HDH2cbOrsJt88Uu65buOWm\n" +
                "cXbfwAfZ1_eMGNQcsbHtPKp4KyvLqOUttfHoSScss99TuhWm3VTH9lW1h8pxDEtbFa_62rfBfwOR\n" +
                "yHrv-sYDH9JobVPhZV548glSePYtEO4G1qNNlV9dhig_QWgnxYJDJsQW5FULNk6of19-WEkgZkR8\n" +
                "JjTYkJdKeDy0tpSuP4_IjQHXaKtrXY1TZXLm2UuE0V7fagXFrrAwom3I8prqOpETkw5POrWJ0acW\n" +
                "NT2_ntKVd0ne9Y055PtSH9ZEgtlU5N8dGpBy6hoUqM-O3XyDtzL1IaVtEECojWQmjge60gZIFcxi\n" +
                "msjc-kkZLSaNpQ69phMQ1Kh_AYicDbQrVPy3ItRttdBATQNW_ystK7GapNcq67QUd3Z_TymC95I2\n" +
                "oPdDVyqS4b3JgwteGpfS6SdoC6j0Ducz_wNQSwcIk6NHdlgQAAB9EAAAUEsBAhQAFAAICAgAhRB9\n" +
                "S2nU389jDAAAhwwAABsAAAAAAAAAAAAAAAAAAAAAAGltYWdlX3ppcF8xNTExODkzOTYzMTg4Lmpw\n" +
                "Z1BLAQIUABQACAgIAIUQfUvqx2IW1iEAAPkhAAAbAAAAAAAAAAAAAAAAAKwMAABpbWFnZV96aXBf\n" +
                "MTUxMTg5Mzk3MjM4NS5qcGdQSwECFAAUAAgICACFEH1LadTfz2MMAACHDAAAGwAAAAAAAAAAAAAA\n" +
                "AADLLgAAaW1hZ2VfemlwXzE1MTE4OTU4MjE0NDUuanBnUEsBAhQAFAAICAgAhRB9SyxExK-WDAAA\n" +
                "uQwAABsAAAAAAAAAAAAAAAAAdzsAAGltYWdlX3ppcF8xNTExODk1ODMwMTQ0LmpwZ1BLAQIUABQA\n" +
                "CAgIAIUQfUva9-BczhkAAPEZAAAbAAAAAAAAAAAAAAAAAFZIAABpbWFnZV96aXBfMTUxMTg5NTgz\n" +
                "NzI0MC5qcGdQSwECFAAUAAgICACFEH1Lk6NHdlgQAAB9EAAAGwAAAAAAAAAAAAAAAABtYgAAaW1h\n" +
                "Z2VfemlwXzE1MTE4OTU4NDE2NTQuanBnUEsFBgAAAAAGAAYAtgEAAA5zAAAAAA==\n";
        if (null != base64) {
            byte[] decodedString = Base64.decode(base64, Activity.TRIM_MEMORY_BACKGROUND);
            File zipFile = writeBytesToFile(decodedString);
            try {

                File targetDirectory = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn() + "/images");
                if (!targetDirectory.exists()) {
                    targetDirectory.mkdir();
                } else {
                    FileUtils.deleteDir(targetDirectory);
                }
                FileUtils.unzip(zipFile, targetDirectory);

                List<File> fileList = FileUtils.getListFiles(targetDirectory);
                if (!CommonActivity.isNullOrEmpty(fileList)) {
                    for (File file : fileList) {
                        filePathList.add(file.getAbsolutePath());
                        PictureItemView pictureItemView = new PictureItemView(getContext());
                        pictureItemView.setPath(file.getAbsolutePath());

                        pictureItemView.build(file.getAbsolutePath(), this);

                        mPictureBox.addView(pictureItemView);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File writeBytesToFile(byte[] bFile) {

        File folder = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn() + "/unzip");
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            FileUtils.deleteDir(folder);
        }

        String fileZipPath = Environment.getExternalStorageDirectory() + "/mBCCS/" + getIsdn() + "/unzip" + File.separator
                + "image_collect_unzip.zip";

        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(fileZipPath);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new File(fileZipPath);
    }

    @OnClick(R.id.layoutTakePhoto)
    void takePhoto() {
        requestPermission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_picture_box_view;
    }

    @Override
    public boolean validateView() {
        boolean valid = null != this.filePathList && !this.filePathList.isEmpty();
        if (valid) {
            List<File> listZip = new ArrayList<>();
            for (String path : filePathList) {
                listZip.add(new File(path));
            }

            String base64 = "";
            File folder = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn() + "/zip");
            if (!folder.exists()) {
                folder.mkdir();
            }

            String fileZipPath = Environment.getExternalStorageDirectory() + "/mBCCS/" + getIsdn() + "/zip" + File.separator
                    + "image_collect.zip";

            File isdnZip = FileUtils.zip(listZip, fileZipPath);
            try {
                byte[] bytes = FileUtils.fileToBytes(isdnZip);
                base64 = Base64.encodeToString(bytes, Activity.TRIM_MEMORY_BACKGROUND);
                productSpecCharDTO.setValueData(base64);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return valid;
    }

    @Override
    public void onPictureClick(String url) {
        Intent intent = new Intent(getContext(), PictureDetailActivity.class);
        Bundle b = new Bundle();
        b.putString("url", url);
        intent.putExtras(b);
        getContext().startActivity(intent);
    }

    @Override
    public void onRemoveClick(View view) {
        mPictureBox.removeView(view);
        mPictureBox.invalidate();

        deletePictureView((PictureItemView) view);
    }

    public void takePicture() {
        TakePictureDialog dialog = new TakePictureDialog(mActivity)
                .setListener(new TakePictureDialog.GetAvatarListener() {
                    @Override
                    public void fromCam() {
                        mode = 1;
                        File dirToSaveFile = new File(Environment.getExternalStorageDirectory(), "/mBCCS/" + getIsdn() + "/temps");
                        if (!dirToSaveFile.exists()) {
                            dirToSaveFile.mkdir();
                        } else {
                            FileUtils.deleteDir(dirToSaveFile);
                        }
                        pictureAvatarTempPath = Environment.getExternalStorageDirectory() + "/mBCCS/" + getIsdn() + "/temps/picture_temp_" + System.currentTimeMillis() + ".jpg";
                        File destination = new File(pictureAvatarTempPath);
                        if (!destination.exists()) {
                            try {
                                destination.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            destination.delete();
                            try {
                                destination.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            capturedImageUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", destination);
                        } else {
                            capturedImageUri = Uri.fromFile(destination);
                        }

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                        mActivity.startActivityForResult(intent, CAPTURE_IMAGE);

                    }

                    public void fromGal() {
                        mode = 2;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        mActivity.startActivityForResult(Intent.createChooser(intent, "Choose Picture"), PICK_FROM_FILE);
                    }
                });
        dialog.show();
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (!mActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_CAMERA);
                } else {
                    Snackbar.make(mActivity.findViewById(android.R.id.content), getResources().getString(R.string.permisson_explain_camera), Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                            intent.setData(uri);
                            mActivity.startActivity(intent);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        }
                    }).show();
                }
            } else {
                takePicture();

            }
        } else {
            takePicture();
        }
    }

    public void onResult(String filePath) {
        PictureItemView pictureItemView = new PictureItemView(getContext());
        switch (mode) {
            case 1:
                addPictureView(pictureAvatarTempPath, pictureItemView);
                break;
            case 2:
                addPictureView(filePath, pictureItemView);
                break;
            default:
                break;
        }
    }

    void addPictureView(String filePath, PictureItemView pictureItemView) {
        File oldFile = new File(filePath);
        String fileZipPath = Environment.getExternalStorageDirectory() + "/mBCCS/" + getIsdn() + "/zip" + File.separator
                + "image_zip_" + System.currentTimeMillis() + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileZipPath);
            long length = oldFile.length();
            int percent = length < Constant.MAX_SIZE_IMG ? 100
                    : (int) (Constant.MAX_SIZE_IMG * 100 / length);
            Bitmap bitmap;
            if (oldFile.length() < Constant.MAX_SIZE_IMG) {
                bitmap = BitmapFactory.decodeFile(oldFile.getPath());
            } else {
                bitmap = FileUtils.decodeBitmapFromFile2(oldFile
                        .getPath(), 500, 500);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] buffer = baos.toByteArray();
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        filePathList.add(fileZipPath);

        pictureItemView.build(fileZipPath, this);
        mPictureBox.addView(pictureItemView);

        hsRoot.postDelayed(new Runnable() {
            public void run() {
                hsRoot.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    void deletePictureView(PictureItemView pictureItemView) {
        if (null == this.filePathList || this.filePathList.isEmpty())
            return;

        if (this.filePathList.contains(pictureItemView.getPath())) {
            this.filePathList.remove(pictureItemView.getPath());
        }
    }

    public String getIsdn() {
        return CommonActivity.isNullOrEmpty(isdn) ? "test" : isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
}
