package com.example.tuling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener,HttpCallbackListener{

	private ListView listView;
	private Button send;
	private EditText input;
	private TextAdapter adapter;
	private List<Data> datas;
	private HttpUtils httpUtils;
	
	private String content;
	private double currentTime;
	private double oldTime = 0;
	
	private String[] welcomeArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}
	private void initView()
	{
		listView = (ListView) findViewById(R.id.listview);
		send = (Button) findViewById(R.id.send);
		input = (EditText) findViewById(R.id.input);
		
		datas = new ArrayList<>();
		adapter = new TextAdapter(this, R.layout.msg_item, datas);
		listView.setAdapter(adapter);
		
		send.setOnClickListener(this);
		Data welcomeData = new Data(getRandomWelcomTips(),Data.RECEIVE,getTime());
		datas.add(welcomeData);
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onClick(View v)
	{
//		getTime();
		content = input.getText().toString();
		if(!"".equals(content))
		{
			Data sendData = new Data(content,Data.SEND,getTime());
			datas.add(sendData);
			adapter.notifyDataSetChanged();
			listView.setSelection(datas.size()); 
			input.setText("");
			
			String dropk = content.replace(" ", "");
			String droph = dropk.replace("\n", "");
			httpUtils = (HttpUtils) new HttpUtils("http://www.tuling123.com/openapi/api?key=b54db80ed039c0dfa4e889a2ea2e37db&info="+droph,
					this).execute();
			
		}
	}
	private String getTime()
	{
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = format.format(date);
		if(currentTime - oldTime > 2*60*1000)
		{
			oldTime = currentTime;
			return time;
		}
		return "";
	}
	
	private String getRandomWelcomTips()
	{
		String welcome = null;
		welcomeArray = this.getResources().getStringArray(R.array.welcome);
		int index = (int) (Math.random()*welcomeArray.length);
		welcome = welcomeArray[index];
		return welcome;
	}
	@Override
	public void getDataUrl(String result) 
	{
		parseText(result);
	}
	private void parseText(String result)
	{
		try 
		{
			JSONObject json = new JSONObject(result);
			String receiveData = json.getString("text");
			Data receiverData = new Data(receiveData,Data.RECEIVE,getTime());
			datas.add(receiverData);
			adapter.notifyDataSetChanged();
			listView.setSelection(datas.size());
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	private Dialog ExitDialog(Context context)
	{
		String str = "主人不想和图灵聊了吗？";
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(str);
		dialog.setCancelable(false);
		dialog.setPositiveButton("下次再聊", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				MainActivity.this.finish();
			}
		});
		dialog.setNegativeButton("再聊聊吧", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		return dialog.create();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			ExitDialog(MainActivity.this).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}