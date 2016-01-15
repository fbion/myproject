package com.ctfo.upp.accountservice.payment.external.impl;

import java.util.Random;

public class Test {

	public static void function(){
		int n=0;
		n=(int)(Math.random()*100000);
		while(n<10000 || !handle(n)){
			n=(int)(Math.random()*100000);
		}
		System.out.println("n="+n);
	}
	
	public static boolean handle(int n){
		int[] list=new int[5];
		for(int i=0;i<5;i++){
			list[i]=n%10;
			n=n/10;
		}
		for(int i=0;i<5;i++){
			for(int j=i+1;j<5;j++){
				if(list[i]==list[j]) return false;
			}
		}
		return true;
	}
	public static void main(String args[]) throws Exception{
		function();
		
		System.out.println(String.valueOf(Math.random()).substring(2).substring(0, 5));
	}

}
