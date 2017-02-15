package com;
import com.jfinal.plugin.activerecord.Model;
public class UserModel extends Model<UserModel> 
{
	public static final UserModel dao = new UserModel();
}
