package scripto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.*;


public class RunnerFrame extends JInternalFrame
{
	Runner tailer;
	public void begin()
	{
		this.tailer.start();
	}
	RunnerFrame()
	{
		tailer = new Runner();
	}
	RunnerFrame(Func a, ArrayList<Func> b)
	{
		tailer = new Runner();
		tailer.mainfunc = a;
		tailer.funcs = b;
	}
}
