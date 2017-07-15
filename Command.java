package scripto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Command {
	String cmd;
	String param;
	int index;
	boolean comparetoint;
	int comparison;
	int funcindex;

	public Command(String a, String b) {
		cmd = a;
		param = b;
	}

	public Command(String a, int b) {
		cmd = a;
		index = b;
	}

	public Command(String a, int b, int c, boolean d) {
		cmd = a;
		index = b;
		funcindex = c;
		comparetoint = d;
	}

	public Command(String a) {
		cmd = a;
	}

	public Command(String a, String b, int c) {
		cmd = a;
		param = b;
		index = c;
	}

	public Command(String a, String b, int c, int d, int e, boolean f) {
		cmd = a;
		param = b;
		index = c;
		comparison = d;
		funcindex = e;
		comparetoint = f;
	}
}
