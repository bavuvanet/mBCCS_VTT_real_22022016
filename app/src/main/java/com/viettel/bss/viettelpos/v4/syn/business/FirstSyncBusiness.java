package com.viettel.bss.viettelpos.v4.syn.business;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.SdCardHelper;
import com.viettel.bss.viettelpos.v4.commons.ZipUtils;

class FirstSyncBusiness {
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 11;

	
	public FirstSyncBusiness(Activity act) {
		super();
		
	}

	public String sync(String token, String userName) throws Exception {
		Log.e("downloadAndSyn", "Start Download >>>>>>>>>>>>>>>> ");
		String url = Constant.PATH_DOWNLOAD+token;
		

		URL url1 = new URL(url);
		
		Log.i("Bo nho con trong",Double.toString(SdCardHelper.getAvailableInternalMemorySize()));
		
		double availAbleMemory = SdCardHelper.getAvailableInternalMemorySize();
		
		
		if(availAbleMemory > 50 ){

	
			InputStream input = new BufferedInputStream(url1.openStream());
			
			OutputStream output;
	
			File file = new File(Define.PATH_DATABASE);
			if (!file.exists()) {
				file.mkdirs();
			}
			output = new FileOutputStream(Define.PATH_DATABASE + userName);
			byte data[] = new byte[1024];
			int count = 0;
	
			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);
			}
			
			output.flush();
			output.close();
			input.close();
			ZipUtils.unzip(Define.PATH_DATABASE + userName, Define.PATH_DATABASE);
			File file1 = new File(Define.PATH_DATABASE + userName);
			String pathData = Define.PATH_DATABASE + userName.toUpperCase();
			File file2 = new File(pathData);
			File newFileName = new File(Define.PATH_DATABASE + "smartphone");
			file2.renameTo(newFileName);
			file1.delete();
			
			Log.e("FilePath", Define.PATH_DATABASE);
			
			Log.e("downloadAndSyn", "End Download >>>>>>>>>>>>>>>> ");
			
			return Constant.SUCCESS_CODE;
			
		} else{
			
			return Constant.ERROR_CODE;
		}
	}

	/**
	 * end classs
	 */

}
