package com;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jfinal.core.Controller;
public class MainList extends Controller 
{  

	public void index() 
	{   
		List<UserModel> list = UserModel.dao.find("select * from user");
		Collections.sort(list, new Comparator<UserModel>()
				{
					public int compare(UserModel arg0, UserModel arg1)
					{
						return arg1.getInt("rating").compareTo(arg0.getInt("rating"));
					}
				});
		setAttr("NodeList", list);
		render("/index.html");
	} 
}