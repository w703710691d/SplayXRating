package com;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class UserMain extends Controller 
{
	public void index()
	{
		int userid = getParaToInt(0);
		List<ContestModel> list = ContestModel.dao.find("select * from contest where userid=?", userid);
		Collections.sort(list, new Comparator<ContestModel>()
		{
			public int compare(ContestModel arg0, ContestModel arg1)
			{
				return arg0.getInt("contestid").compareTo(arg1.getInt("contestid"));
			}
		});
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).put("contestname", Db.findById("contestlist",list.get(i).getInt("contestid")).getStr("contest"));
		}
		String name = UserModel.dao.findById(userid).getStr("user");
		
		setAttr("name",name);
		setAttr("ContestList",list);
		render("/contestList.html");
	}
}
