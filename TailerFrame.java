package scripto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.*;


/*** An internal frame that is contained by the JTailer. Tails the specified log file*/
public class TailerFrame extends RunnerFrame implements LogFileTailerListener
{
	protected LogFileTailer tailer;
	private JTextArea text = new JTextArea();
	Robot r;
	boolean running = false;
	protected String name;
	ArrayList<Func> funcs;
	protected class Conditional
	{
		String message;
		int functorun;
		protected Conditional(String m, int b)
		{
			message = m;
			functorun = b;
		}
	}
	ArrayList<Conditional> conds;
	protected void addCond(String m, int b)
	{
		conds.add(new Conditional(m, b));
	}
	public void begin()
	{
		running = true;
		this.tailer.start();
	}
	public TailerFrame( String filename , String n)
	{
		this.tailer = new LogFileTailer( new File( filename ), 100, false );
		this.tailer.addLogFileTailerListener( this );
		conds = new ArrayList<Conditional>();
		name = n;
		funcs = new ArrayList<Func>();
		try {r = new Robot();}
		catch (Exception ex) {ex.printStackTrace();}
	}
	public TailerFrame(String filename, String n, boolean a)
	{
		this.tailer = new LogFileTailer(new File(filename), 100, a);
		this.tailer.addLogFileTailerListener(this);
		conds = new ArrayList<Conditional>();
		name = n;
		funcs = new ArrayList<Func>();
		try {r = new Robot();}
		catch (Exception ex) {ex.printStackTrace();}
	}
	public void stopTailing()
	{
		tailer.tailing = false;
		try
		{
			Thread.sleep(1000);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void newLogFileLine(String line)
	{
		if(running)
		{
			for(Conditional c: conds)
			{
				if(line.contains(c.message))
				{
					funcs.get(c.functorun).doStuff();
					break;
				}
			}
		}
	}
}
