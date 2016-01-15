package com.ctfo.upp.accountservice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GroupRadom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> groupList=new ArrayList<Integer>();
		for(int i=0;i<100;i++){
			groupList.add(i);
		}
		GroupRadom.group(groupList, 5);
	}
	public static <T> List<List<T>> group(List<T> idList,int gourpCount){
		List<List<T>> groupList=new ArrayList<List<T>>();
		for(int i=0;i<gourpCount;i++)
			groupList.add(i,new ArrayList<T>());
		
		Random random=new Random();
		for(T id:idList)
			groupList.get(random.nextInt(gourpCount)).add(id);
		
		return groupList;
	}

}
