package com;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
public class RatingConfig extends JFinalConfig 
{
	public void configConstant(Constants me) 
	{   
		me.setDevMode(false);  
	}  
	public void configRoute(Routes me) {   
		me.add("/", MainList.class);
		me.add("/user", UserMain.class);
		me.add("/contest", Contest.class);
		me.add("/admin", admin.class);
	}  
	@Override
	public void configPlugin(Plugins me) 
	{
		loadPropertyFile("db_config.txt"); 
    	C3p0Plugin  cp  =  new  C3p0Plugin(getProperty("url"), getProperty("user"), getProperty("password"));
    	me.add(cp);
    	ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
    	me.add(arp);
    	arp.addMapping("user", UserModel.class);
    	arp.addMapping("contest",ContestModel.class);
	}  
	public void configInterceptor(Interceptors me) {}  
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx_path"));
	} 
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
