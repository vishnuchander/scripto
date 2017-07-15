package scripto;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class Runner extends Thread {
	Func mainfunc;
	protected ArrayList<Func> funcs;
	Robot r;

	Runner() {
		mainfunc = new Func("a");
	}

	Runner(Func a, ArrayList<Func> b) {
		mainfunc = a;
		funcs = b;
	}

	public void run() {
		try {
			r = new Robot();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mainfunc.doStuff();
	}
}
