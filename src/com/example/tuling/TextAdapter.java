package com.example.tuling;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextAdapter extends ArrayAdapter<Data> {

	private int resourceId;
	private Context context;
	public TextAdapter(Context context, int resource, List<Data> objects)
	{
		super(context, resource, objects);
		this.context = context;
		resourceId = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Data data = getItem(position);
		ViewHolder viewHolder;
		if(convertView ==  null)
		{
			convertView = LayoutInflater.from(context).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.leftLayout = (RelativeLayout) convertView.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (RelativeLayout) convertView.findViewById(R.id.right_layout);
			viewHolder.robotTime = (TextView) convertView.findViewById(R.id.robot_time);
			viewHolder.robotText = (TextView) convertView.findViewById(R.id.robot_text);
			viewHolder.userTime = (TextView) convertView.findViewById(R.id.user_time);
			viewHolder.userText = (TextView) convertView.findViewById(R.id.user_text);
			convertView.setTag(viewHolder);
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(data.getFalg() == Data.RECEIVE)
		{
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.robotTime.setText(data.getTime());
			viewHolder.robotText.setText(data.getContent());
		}
		else if(data.getFalg() == Data.SEND)
		{
			viewHolder.leftLayout.setVisibility(View.GONE);
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.userTime.setText(data.getTime());
			viewHolder.userText.setText(data.getContent());
		}
		return convertView;
	}
	
	class ViewHolder
	{
		RelativeLayout leftLayout;
		RelativeLayout rightLayout;
		TextView robotTime;
		TextView robotText;
		TextView userTime;
		TextView userText;
	}

}
