package ServerConnect;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app_test.R;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class QueryTask  {
   
    public  void resultJson( Context context,String result,TextView tv_result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int resultCode = jsonObject.getInt("resultcode");
                if (resultCode == 200) {
                    JSONArray resultJsonArray = jsonObject.getJSONArray("result");
                    JSONObject resultJsonObject = resultJsonArray.getJSONObject(0);
                    String output = context.getString(R.string.city) + ": " + resultJsonObject.getString("city") + "\n"
                            + context.getString(R.string.PM25) + ": " + resultJsonObject.getString("PM2.5") + "\n"
                            + context.getString(R.string.AQI) + ": " + resultJsonObject.getString("AQI") + "\n"
                            + context.getString(R.string.quality) + ": " + resultJsonObject.getString("quality") + "\n"
                            + context.getString(R.string.PM10) + ": " + resultJsonObject.getString("PM10") + "\n"
                            + context.getString(R.string.CO) + ": " + resultJsonObject.getString("CO") + "\n"
                            + context.getString(R.string.NO2) + ": " + resultJsonObject.getString("NO2") + "\n"
                            + context.getString(R.string.O3) + ": " + resultJsonObject.getString("O3") + "\n"
                            + context.getString(R.string.SO2) + ": " + resultJsonObject.getString("SO2") + "\n"
                            + context.getString(R.string.time) + ": " + resultJsonObject.getString("time") + "\n";
                    tv_result.setText(output);
                } else if (resultCode == 202) {
                    String reason = jsonObject.getString("reason");
                    tv_result.setText(reason);
                } else {
                    Toast.makeText(context, "≤È—Ø ß∞‹",
                            Toast.LENGTH_LONG).show();
                    tv_result.setText("");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "≤È—Ø ß∞‹",
                                    Toast.LENGTH_LONG).show();
            tv_result.setText("");
        }
    }  
  
}
