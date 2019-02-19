package com.test;

import java.util.Timer;
import java.util.TimerTask;

import com.axp.util.CalcUtil;


public class timeTest {

	public static void main(String[] args) {
		System.out.println(CalcUtil.mul(10000, 0.01, 2));

	}


}


class TimeTask1 extends TimerTask{
	
	private int num = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("执行TimeTask1");
		new Timer().schedule(new TimeTask2(), 3000);
	}
}

class TimeTask2 extends TimerTask{

	@Override
	public void run() {
		System.out.println("执行TimeTask2");
		new Timer().schedule(new TimeTask1(), 4000);
	}
	
}