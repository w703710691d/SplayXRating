package com;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

public class Contest extends Controller 
{

	public void index()
	{
		int contestid = getParaToInt(0);
		List<ContestModel> list = ContestModel.dao.find("select * from contest where contestid=?", contestid);
		Collections.sort(list, new Comparator<ContestModel>()
		{
			public int compare(ContestModel arg0, ContestModel arg1)
			{
				return arg1.getInt("rank").compareTo(arg0.getInt("rank"));
			}
		});
		setAttr("contestname",Db.findById("contestlist", contestid).getStr("contest"));
		for(int i = 0;i < list.size();i++)
		{
			String username = UserModel.dao.findById(list.get(i).getInt("userid")).getStr("user");
			list.get(i).put("user", username);
		}
		Collections.sort(list, new Comparator<ContestModel>()
		{
			public int compare(ContestModel arg0, ContestModel arg1)
			{
				return arg0.getInt("rank").compareTo(arg1.getInt("rank"));
			}
		});
		setAttr("ContestUserList",list);
		render("/contest.html");
	}
}
