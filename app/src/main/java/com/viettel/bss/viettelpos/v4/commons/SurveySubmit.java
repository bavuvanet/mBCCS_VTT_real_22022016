package com.viettel.bss.viettelpos.v4.commons;

import android.util.Log;

import com.viettel.bss.viettelpos.v4.login.activity.FragmentLoginNotData;
import com.viettel.bss.viettelpos.v4.object.Survey;
import com.viettel.bss.viettelpos.v4.object.SurveyAnswer;
import com.viettel.bss.viettelpos.v4.object.SurveyQuestion;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SurveySubmit {

	public static String POST(String url, Survey survey, String staffCode,
			int connectTimeOut, int responseTimeout) {
		String result = "";
//		HttpClient httpclient = null;
		try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .url(Constant.LINK_WS_UPLOAD_IMAGE)
                    .post(requestBody)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-type", "application/json")
                    .build();

            Response response = OkHttpUtils.getClient().newCall(request).execute();
            result = response.body().string();
            Log.d("FragmentUpdateImageMonth", "response = " + result);

//			HttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams,
//					connectTimeOut);
//			HttpConnectionParams.setSoTimeout(httpParams, responseTimeout);
//			// 1. create HttpClient
//			httpclient = new DefaultHttpClient(httpParams);
//			try {
//				((AbstractHttpClient) httpclient)
//						.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
//								0, false));
//			} catch (Exception e) {
//			}
//			// 2. make POST request to the given URL
//			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject mainObject = new JSONObject();
			mainObject.accumulate("visitorName", staffCode);
			mainObject.accumulate("domainCode", Constant.SURVEY_DOMAIN);
			mainObject.accumulate("domainPath", "/" + Constant.SURVEY_DOMAIN
					+ "/");
			JSONArray lstQuestion = new JSONArray();
			for (SurveyQuestion q : survey.getLstQuestion()) {
				String type = q.getType();
				if (FragmentLoginNotData.TYPE_CHECKBOX.equals(type)
						|| FragmentLoginNotData.TYPE_COMBOBOX.equals(type)
						|| FragmentLoginNotData.TYPE_RADIO_BUTTON.equals(type)) {
					for (SurveyAnswer ans : q.getLstAnswer()) {
						if (ans.getIsChoose()) {
							JSONObject tmp = new JSONObject();
							tmp.accumulate("questionId", ans.getQuestionId());
							tmp.accumulate("surveyId", survey.getId());
							tmp.accumulate("answerId", ans.getId());
							lstQuestion.put(tmp);
						}
					}

				} else {
					if (q.getAnwser() != null
							&& !q.getAnwser().trim()    .isEmpty()) {
						JSONObject tmp = new JSONObject();
						tmp.accumulate("questionId", q.getId());
						tmp.accumulate("surveyId", survey.getId());
						tmp.accumulate("otherAnswer", q.getAnwser());
						lstQuestion.put(tmp);
					}
				}

			}

			mainObject.accumulate("lstResult", lstQuestion);

			// 4. convert JSONObject to JSON to String
			json = mainObject.toString();

			// 5. set json to StringEntity
//			StringEntity se = new StringEntity(json);
//
//			// 6. set httpPost Entity
//			httpPost.setEntity(se);
//
//			// 7. Set some headers to inform server about the type of the
//			// content
//			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-type", "application/json");
//
//			// 8. Execute POST request to the given URL
//			HttpResponse httpResponse = httpclient.execute(httpPost);
//
//			// 9. receive response as inputStream
//			HttpEntity entity = httpResponse.getEntity();
//
//			// 10. convert inputstream to string
//			String tmp = EntityUtils.toString(entity);
//			try {
//				// check request HTTP and shutdown
//				httpclient.getConnectionManager().shutdown();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			return result;
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
//        finally {
//			try {
//				// check request HTTP and shutdown
//				httpclient.getConnectionManager().shutdown();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		// 11. return result
		return "error";
	}
}
