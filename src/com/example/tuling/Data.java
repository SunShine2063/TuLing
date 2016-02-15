package com.example.tuling;

public class Data
{
	public static final int RECEIVE = 1;
	public static final int SEND = 2;
	private String content;
	private int falg;
	private String time;
	public Data(String content, int falg, String time) {
		super();
		this.content = content;
		this.falg = falg;
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFalg() {
		return falg;
	}
	public void setFalg(int falg) {
		this.falg = falg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
