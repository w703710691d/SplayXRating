package com;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.*;
public class admin extends Controller
{
	public void index()
	{
		render("/admin.html");
	}
	public void up()
	{
		String file = getPara("rank");
		Scanner cin = new Scanner(file);
		String ContestName = getPara("contestName");
		List<String> Name = new ArrayList<String>();
		List<Integer> Rank = new ArrayList<Integer>();
		int Num = 0;
		while(cin.hasNext())
		{
			String name;
			int rank;
			if(cin.hasNext())
			{
				name = cin.next();
			}
			else
			{
				renderText("File Error!!");
				return;
			}
			if(cin.hasNextInt())
				rank = cin.nextInt();
			else
			{
				renderText("File Error!!");
				return;
			}
			for(int j = 0; j < Name.size(); j++)
			{
				if(name == Name.get(j))
				{
					renderText("File Error!!");
					return;
				}
			}
			Name.add(name);
			Rank.add(rank);
		}
		cin.close();
		List<Double> Score = new ArrayList<Double>();
		List<Integer> oldrating = new ArrayList<Integer>();
		List<Integer> UserId = new ArrayList<Integer>();
		List<Integer> Join = new ArrayList<Integer>();
		for(int i = 0; i < Name.size(); i++)
		{
			double score = 0;
			for(int j = 0; j < Name.size(); j++)
			{
				if(i != j)
				{
					if(Rank.get(i) < Rank.get(j))
						score += 1;
					else if(Rank.get(i) == Rank.get(j))
						score += 0.5;
				}
			}
			Record rd = Db.findFirst("select * from user where user=?", Name.get(i));
			if(rd == null)
			{
				rd = new Record().set("user", Name.get(i)).set("rating", 1200).set("joined", 0);
				Db.save("user", rd);
			}
			String strid = rd.get("id").toString();
			int x = new Integer(strid);
			UserId.add(x);
			oldrating.add(rd.getInt("rating"));
			Join.add(rd.getInt("joined"));
			Score.add(score);
		}
		List<Double> EScore = new ArrayList<Double>();
		for(int i = 0; i < UserId.size(); i++)
		{
			double escore = 0;
			for(int j = 0; j < UserId.size(); j++)
			{
				if(i != j)
				{
					double Ra = oldrating.get(i);
					double Rb = oldrating.get(j);
					escore += 1.0 / (1 + Math.pow(10, (Rb - Ra) / 400.0));
				}
			}
			EScore.add(escore);
		}
		List<Integer> rating = new ArrayList<Integer>();
		for(int i = 0; i < UserId.size(); i++)
		{
			int k;
			if(Join.get(i) >= 10 && oldrating.get(i) < 2100) k = 24;
			else k = 16;
			rating.add((int)(oldrating.get(i) + k * (Score.get(i) - EScore.get(i))));
		}
		Record rd = new Record().set("contest", ContestName);
		Db.save("contestlist", rd);
		int contestid = Db.findFirst("select * from contestlist where contest=?", ContestName).getInt("id");
		for(int i = 0; i < UserId.size(); i++)
		{
			rd = new Record().set("userid", UserId.get(i)).set("contestid", contestid).set("rank", Rank.get(i)).set("rating", rating.get(i));
			Db.save("contest", rd);
			rd = Db.findById("user", UserId.get(i));
			rd.set("rating", rating.get(i)).set("joined", rd.getInt("joined") + 1);
			System.out.println(rd);
			Db.update("user", rd);
		}
		redirect("/");
	}
}
