package scripto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class Func
{
	String name;
	ArrayList<Command> cmds;
	ArrayList<Counter> counters;
	ArrayList<Location> locs;
	ArrayList<Location> colors;
	ArrayList<Func> funcs;
	ArrayList<Command> comparisons;
	boolean separate;
	Robot r;
	int times;
	public Func(String n)
	{
		name = n;
		cmds = new ArrayList<Command>();
		counters = new ArrayList<Counter>();
		locs = new ArrayList<Location>();
		funcs = new ArrayList<Func>();
		comparisons = new ArrayList<Command>();
		try
		{
			r = new Robot();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		times = 1;
		separate = false;
	}
	public void runFunc(int index)
	{
		funcs.get(index).doStuff();
	}
	public void addCommand(Command k)
	{
		cmds.add(k);
	}
	private void fpause(int time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	protected void naturalmove(Point p)
	{
		Point c = MouseInfo.getPointerInfo().getLocation();
		if(c == p) return;
		int mx = 0, my = 0;
		mx = (c.x - p.x)/20;
		my = (c.y - p.y)/20;
		for(int i = 0; i < 20; i++)
		{	
			c = MouseInfo.getPointerInfo().getLocation();
			r.mouseMove(c.x - mx, c.y - my);
			fpause(10);
		}
		r.mouseMove(p.x, p.y);
		
	}
	private void naturalClick(int a)
	{
		r.mousePress(a);
		fpause(100);
		r.mouseRelease(a);
		fpause(100);
	}
	protected void check(Command k)
	{
		Color p = r.getPixelColor(colors.get(k.index).x, colors.get(k.index).y);
		System.out.println(p.toString());
		System.out.println(colors.get(k.index).color.toString());
		if(k.comparetoint)
		{
			if(p.equals(colors.get(k.index).color)) return;
			funcs.get(k.funcindex).doStuff();
		}
		else
		{
			if(!p.equals(colors.get(k.index).color)) return;
			funcs.get(k.funcindex).doStuff();
		}
	}
	protected void execute(Command k)
	{
		switch (k.cmd)
		{
			case "mv": r.mouseMove(locs.get(k.index).x, locs.get(k.index).y); break;
			case "nm": naturalmove(locs.get(k.index)); break;
			case "ck": r.mousePress(k.index); r.mouseRelease(k.index); break;
			case "md": r.mousePress(k.index); break;
			case "mr": r.mouseRelease(k.index); break;
			case "wt": fpause(k.index); break;
			case "rw": fpause((int) (Math.random()*k.index)); break;
			case "kp": r.keyPress(k.index); r.keyRelease(k.index); break;
			case "kd": r.keyPress(k.index); break;
			case "ku": r.keyRelease(k.index); break;
			case "gt": case "ge": case "eq": case "le": case "lt": case "ne": cmp(k); break;
			case "st": counters.get(k.index).num = Integer.parseInt(k.param); break;
			case "rs": counters.get(k.index).num = (int) (Math.random()*Integer.parseInt(k.param)); break;
			case "in": counters.get(k.index).num ++; break;
			case "dc": counters.get(k.index).num --; break;
			case "pt": System.out.printf(" %s", k.param); break;
			case "lp": System.out.println(" " + k.param); break;
			case "pc": System.out.printf(" %d", counters.get(k.index).num); break;
			case "lc": System.out.println(" " + counters.get(k.index).num); break;
			case "rn": funcs.get(k.index).doStuff(); break;
			case "ch": check(k); break;
		}
	}
	void cmp(Command k)
	{
		int comparevalue;
		if(k.comparetoint)
		{
			comparevalue = k.comparison;
		}
		else
		{
			comparevalue = counters.get(k.comparison).num;
		}
		compare(k.cmd, counters.get(k.index).num, comparevalue, k.funcindex);
	}
	private void compare(String cmd, int a, int b, int index)
	{
		switch (cmd)
		{
			case "gt": if(!(a > b)) {return;} break;
			case "ge": if(!(a >= b)) {return;} break;
			case "eq": if(!(a == b)) {return;} break;
			case "le": if(!(a <= b)) {return;} break;
			case "lt": if(!(a < b)) {return;} break;
			case "ne": if(a == b) {return;} break;
		}
		execute(comparisons.get(index));
	}
	public void doStuff()
	{
		int i = 1; 
		while(true)
		{
			for(Command k: cmds)
			{
				execute(k);
			}
			if((times != -1) && (i == times)) break;
			i++;
		}
	}
}
