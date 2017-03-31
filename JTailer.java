package scripto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class JTailer extends JFrame implements ActionListener
{
	private JDesktopPane desktop = new JDesktopPane();
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem menuOpenScript = new JMenuItem( "Open script file" );
	private JButton StartorStop;
	private JPanel buttonPanel;
	private DrawStuff drawPanel = new DrawStuff();
	ArrayList<String> names;
	boolean ready;
	Robot robot;
	String fn;
	ArrayList<TailerFrame> frames;
	ArrayList<Location> locs;
	ArrayList<Location> colors;
	ArrayList<Func> funcs;
	ArrayList<Counter> counters;
	ArrayList<Command> comparisons;
	ArrayList<RunnerFrame> threads; 
	Func m;
	RunnerFrame runthread;
	public class msg extends JPanel
	{
		JLabel a;
		public msg (String item)
		{
			a = new JLabel(item);
			add(a);
		}
	}
	public JTailer()
	{
		init(null);
	}
	public JTailer(String[] filename)
	{
		init(filename);
	}
	private void popUp(String m)
	{
		msg mesg = new msg(m);
		JOptionPane.showMessageDialog(this, mesg);
	}
	private void eMsg(int ln, String reason, String fn)
	{
		System.out.println("File " + fn + ": bad command on line " + ln + ". " + reason + ".");
		popUp("File " + fn + ": bad command on line " + ln + ". " + reason + ".");
		System.exit(1);
	}
	private Location getPt(String name)
	{
		Point p = new Point();
		popUp("Press enter with your cursor over point " + name + ".");
		p = MouseInfo.getPointerInfo().getLocation();		
		return new Location(name, p.x, p.y);
	}
	private Location getPt2(String name)
	{
		System.out.printf("Press Enter with cursor over point " + name + ".");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		Point p = MouseInfo.getPointerInfo().getLocation();
		return new Location(name, p.x, p.y);
	}
	private Location getPix(String name)
	{
		Point p = new Point();
		popUp("Press enter with your cursor over pixel " + name + ".");
		p = MouseInfo.getPointerInfo().getLocation();
		Color k = robot.getPixelColor(p.x, p.y);
		System.out.println(k);
		return new Location(name, p.x, p.y, k);
	}
	int parseKey(String token)
	{
		switch (token.toLowerCase())
		{
			case "a": return KeyEvent.VK_A;
			case "b": return KeyEvent.VK_B;
			case "c": return KeyEvent.VK_C;
			case "d": return KeyEvent.VK_D;
			case "e": return KeyEvent.VK_E;
			case "f": return KeyEvent.VK_F;
			case "g": return KeyEvent.VK_G;
			case "h": return KeyEvent.VK_H;
			case "i": return KeyEvent.VK_I;
			case "j": return KeyEvent.VK_J;
			case "k": return KeyEvent.VK_K;
			case "l": return KeyEvent.VK_L;
			case "m": return KeyEvent.VK_M;
			case "n": return KeyEvent.VK_N;
			case "o": return KeyEvent.VK_O;
			case "p": return KeyEvent.VK_P;
			case "q": return KeyEvent.VK_Q;
			case "r": return KeyEvent.VK_R;
			case "s": return KeyEvent.VK_S;
			case "t": return KeyEvent.VK_T;
			case "u": return KeyEvent.VK_U;
			case "v": return KeyEvent.VK_V;
			case "w": return KeyEvent.VK_W;
			case "x": return KeyEvent.VK_X;
			case "y": return KeyEvent.VK_Y;
			case "z": return KeyEvent.VK_Z;
			case "0": return KeyEvent.VK_0;
			case "1": return KeyEvent.VK_1;
			case "2": return KeyEvent.VK_2;
			case "3": return KeyEvent.VK_3;
			case "4": return KeyEvent.VK_4;
			case "5": return KeyEvent.VK_5;
			case "6": return KeyEvent.VK_6;
			case "7": return KeyEvent.VK_7;
			case "8": return KeyEvent.VK_8;
			case "9": return KeyEvent.VK_9;
			case "space": return KeyEvent.VK_SPACE;
			case "period": return KeyEvent.VK_PERIOD;
			case "num0": return KeyEvent.VK_NUMPAD0;
			case "num1": return KeyEvent.VK_NUMPAD1;
			case "num2": return KeyEvent.VK_NUMPAD2;
			case "num3": return KeyEvent.VK_NUMPAD3;
			case "num4": return KeyEvent.VK_NUMPAD4;
			case "num5": return KeyEvent.VK_NUMPAD5;
			case "num6": return KeyEvent.VK_NUMPAD6;
			case "num7": return KeyEvent.VK_NUMPAD7;
			case "num8": return KeyEvent.VK_NUMPAD8;
			case "num9": return KeyEvent.VK_NUMPAD9;
			case "decimal": return KeyEvent.VK_DECIMAL;
			case "shift": return KeyEvent.VK_SHIFT;
			case "ctrl": case "control": return KeyEvent.VK_CONTROL;
			case "backspace": return KeyEvent.VK_BACK_SPACE;
			case "left": return KeyEvent.VK_LEFT;
			case "right": return KeyEvent.VK_RIGHT;
			case "up": return KeyEvent.VK_UP;
			case "down": return KeyEvent.VK_DOWN;
			case "escape": case "esc": return KeyEvent.VK_ESCAPE;
			case "enter": return KeyEvent.VK_ENTER;
			case "pgup": return KeyEvent.VK_PAGE_UP;
			case "pgdn": return KeyEvent.VK_PAGE_DOWN;
			case "home": return KeyEvent.VK_HOME;
			case "end": return KeyEvent.VK_END;
			case "delete": return KeyEvent.VK_DELETE;
			case "insert": return KeyEvent.VK_INSERT;
			case "equals": return KeyEvent.VK_EQUALS;
			case "plus": return KeyEvent.VK_PLUS;
			case "underscore": return KeyEvent.VK_UNDERSCORE;
			case "minus": return KeyEvent.VK_MINUS;
			case "*": case "asterisk": return KeyEvent.VK_ASTERISK;
			case "/": return KeyEvent.VK_SLASH;
			case "\\": return KeyEvent.VK_BACK_SLASH;
			case "tab": return KeyEvent.VK_TAB;
			case "add": return KeyEvent.VK_ADD;
			case "subtract": return KeyEvent.VK_SUBTRACT;
			case "divide": return KeyEvent.VK_DIVIDE;
			case "multiply": return KeyEvent.VK_MULTIPLY;
			case ",": return KeyEvent.VK_COMMA;
			case ";": return KeyEvent.VK_SEMICOLON;
			case "f1": return KeyEvent.VK_F1;
			case "f10": return KeyEvent.VK_F10;
			case "f11": return KeyEvent.VK_F11;
			case "f12": return KeyEvent.VK_F12;
			case "f13": return KeyEvent.VK_F13;
			case "f14": return KeyEvent.VK_F14;
			case "f15": return KeyEvent.VK_F15;
			case "f16": return KeyEvent.VK_F16;
			case "f17": return KeyEvent.VK_F17;
			case "f18": return KeyEvent.VK_F18;
			case "f19": return KeyEvent.VK_F19;
			case "f2": return KeyEvent.VK_F2;
			case "f3": return KeyEvent.VK_F3;
			case "f4": return KeyEvent.VK_F4;
			case "f5": return KeyEvent.VK_F5;
			case "f6": return KeyEvent.VK_F6;
			case "f7": return KeyEvent.VK_F7;
			case "f8": return KeyEvent.VK_F8;
			case "f9": return KeyEvent.VK_F9;
		}
		return -1;
	}
	private void parseFunc(Scanner line, int ln, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String token;
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad function name: " + token, fn);
			}
			token = parse(line, ln, fn);
			if(isFound(token, ln, fn) != -1)
			{
				eMsg(ln, "Function already found with name " + token, fn);
			}
			funcs.add(new Func(token));
		}
	}
	String parse(Scanner line, int ln, String fn)
	{
		if (!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String token = line.next();
		return token;
	}
	Command check(String token, Scanner line, int ln, String fn, boolean ne)
	{
		token = parse(line, ln, fn);
		int rv = isColor(token);
		if(rv == -1) eMsg(ln, "PixelGrabber " + token + " not found", fn);
		token = parse(line, ln, fn);
		int rv2 = isFound(token, ln, fn);
		if(rv2 == -1) eMsg(ln, "Function " + token + " not found", fn);
		return new Command("ch", rv, rv2, ne);
	}
	Command cmp(String token, Scanner line, int ln, String fn)
	{
		//syntax:
		//(compare function) (counter1) (some integer) (func to run)
		//(compare function) (counter1) (counter2) (func to run)
		//if counter1 (compare function) counter2/integer run func
		String cmd = token;
		token = parse(line, ln, fn);
		int rv = findCounter(token); //rv holds the index of counter1
		if(rv == -1) eMsg(ln, "Counter " + token + " not found", fn);
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		int comparison;
		String param;
		boolean comparetoint;
		if(line.hasNextInt())
		{
			comparison = line.nextInt(); //comparison = number
			param = null;
			comparetoint = true;
		}
		else
		{
			param = line.next();
			comparison = findCounter(param); //comparison = index2
			if(comparison == -1) eMsg(ln, "Counter " + param + " not found", fn);
			comparetoint = false;
		}
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		/*int r2 = isFound(line.next(), ln, fn);
		if(r2 == -1) eMsg(ln, "Function not found", fn);*/
		int r2 = createComparison(new Scanner(getString(line)), ln, fn);
		return new Command(cmd, param, rv, comparison, r2, comparetoint);
	}
	private int createComparison(Scanner line, int ln, String fn)
	{
		//private void parseLine(Scanner line, ArrayList<Command> thread, int ln, String fn)
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		parseLine(line, comparisons, ln, fn);
		return comparisons.size() - 1;		
	}
	private void incrementer(Scanner line, int ln, ArrayList<Command> thread, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String token;
		int rv;
		while(line.hasNext())
		{
			if(line.hasNextInt())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad counter name: " + token, fn);
			}
			token = parse(line, ln, fn);
			rv = findCounter(token);
			if(rv == -1) eMsg(ln, "Counter not found", fn);
			thread.add(new Command("in", rv));	
		}
	}
	private void decrementer(Scanner line, int ln, ArrayList<Command> thread, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String token;
		int rv;
		while(line.hasNext())
		{
			if(line.hasNextInt())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad counter name: " + token, fn);
			}
			token = parse(line, ln, fn);
			rv = findCounter(token);
			if(rv == -1) eMsg(ln, "Counter not found", fn);
			thread.add(new Command("dc", rv));	
		}
	}
	private void pointCreator(Scanner line, int ln, String fn, boolean pop)
	{
		String token;
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad point name: " + token, fn);
			}
			token = parse(line, ln, fn);
			if(isPoint(token) != -1)
			{
				eMsg(ln, "Point already found with name " + token, fn);
			}
			if(pop) locs.add(getPt(token));
			if(!pop) locs.add(getPt2(token));
		}	
	}
	private void pixelGrabber(Scanner line, int ln, String fn)
	{
		String token;
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad point name: " + token, fn);
			}
			token = parse(line, ln, fn);
			if(isColor(token) != -1)
			{
				eMsg(ln, "PixelGrabber already found with name " + token, fn);
			}
			colors.add(getPix(token));			
		}
	}
	private Command move(Scanner line, int ln, String fn)
	{
		String token = parse(line, ln, fn);
		int rv = isPoint(token);
		if (rv == -1) eMsg(ln, "Point " + token + " not found", fn);
		String k = "nm";
		if (token.equals("move")) k = "mv";
		return new Command(k, rv);
	}
	Command click(Scanner line, int ln, String token, String fn)
	{
		int rv = 0;
		if (line.hasNext()) eMsg(ln, "Too many parameters", fn);
		String k;
		if (token.contains("middle"))
		{
			rv = InputEvent.BUTTON2_MASK;
		}
		else if (token.contains("right"))
		{
			rv = InputEvent.BUTTON3_MASK;
		}
		else
		{
			rv = InputEvent.BUTTON1_MASK;
		}
		if (token.contains("click"))
		{
			k = "ck";
		}
		else if (token.contains("down"))
		{
			k = "md";
		}
		else
		{
			k = "mr";
		}
		return new Command(k, rv);
	}
	int pInt(String token, int ln, String fn)
	{
		int retval = 0;
		try
		{
			retval = Integer.parseInt(token);
		}
		catch (NumberFormatException ex)
		{
			eMsg(ln, "Parameter must be a number", fn);
		}
		return retval;
	}
	Command wait(Scanner line, int ln, String token, String fn)
	{ //wait (time to wait)
		//add wt as cmd, token as param if integer
		int n = pInt(parse(line, ln, fn), ln, fn);
		if (line.hasNext()) eMsg(ln, "Too many parameters", fn);
		String k = "rw";
		switch (token)
		{
			case "wait": k = "wt";
		}
		return new Command(k, n);
	}
	Command KP(Scanner line, int ln, String token, String fn)
	{
		String k;
		switch (token)
		{
			case "keypress": k = "kp"; break;
			case "keydown": k = "kd"; break;
			default: k = "ku"; break;
		}
		token = parse(line, ln, fn);
		int rv = parseKey(token);
		if (rv == -1) eMsg(ln, "Bad key", fn);
		return new Command(k, rv);
	}
	Command shift(String token, Scanner line, int ln, String fn)
	{	//dummy function for now. This will need to parse the line and then extract information from it, like what is being shifted.
		switch(token)
		{
			case "lshift": return new Command(token); 
			case "rshift": return new Command(token); 
			case "ushift": return new Command(token); 
			default: return new Command(token); 
			
		}
	}
	void logF(Scanner line, int ln, String fn)
	{ 	//structure: lf (log file name) (path/to/log/file)
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		if(line.hasNextInt() || line.hasNextDouble()) eMsg(ln, "Log name cannot be a number", fn);
		String token = parse(line, ln, fn); //log file name
		String filename = parse(line, ln, fn);
		if (findTailer(token) != -1) eMsg(ln, "Log Reader already found with that name", fn);
		TailerFrame searcher = new TailerFrame(filename, token);
		this.desktop.add(searcher);
		frames.add(searcher);
		System.out.println("Adding log file " + token + " for " + filename);
	}
	void makeLFT(Scanner line, int ln, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String token;
		String filename;
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad log name: " + token, fn);
			}
			token = parse(line, ln, fn);
			//create a TailerFrame with name token and add it to the list of frames
			//LogFileTailer must start executing immediately after script file is parsed
			popUp("Open log file for Log Reader " + token + ".");
			JFileChooser fc = new JFileChooser();
			int retval = fc.showOpenDialog( this );
			if (findTailer(token) != -1) eMsg(ln, "Log Reader already found with that name", fn);
			if( retval == JFileChooser.APPROVE_OPTION )
			{
				filename = fc.getSelectedFile().getAbsolutePath();
				TailerFrame searcher = new TailerFrame(filename, token);
				this.desktop.add( searcher );
				frames.add(searcher);
				System.out.println("Adding log reader " + token);
			}
			else
			{
				eMsg(ln, "Must provide logfile file for " + token, fn);
			}
		}
	}
	void logRun(Scanner line, int ln, String fn)
	{
		//structure: 
		//"logrun" "threadname" "funcname" "rest of the string that is the condition upon which funcname is executed"
		int rv = findTailer(parse(line, ln, fn)); //gets index of the TailerFrame in frames
		if (rv == -1) eMsg(ln, "Log Reader not found", fn);
		int r2 = isFound(parse(line, ln, fn), ln, fn);
		if (r2 == -1) eMsg(ln, "Function not found", fn);
		if (!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		frames.get(rv).addCond(getString(line), r2);
	}
	void makeCounters(Scanner line, int ln, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn); //if there are 0 parameters
		String token;
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad counter name: " + token, fn);
			}
			token = parse(line, ln, fn);
			if (findCounter(token) != -1) eMsg(ln, "Counter already found with that name", fn);
			System.out.println("Adding counter " + token);
			Counter k = new Counter(0, token);
			counters.add(k);
		}
	}
	Command set(Scanner line, int ln, String token, String fn, boolean random)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		token = line.next();
		int rv = findCounter(token);
		if(rv == -1) eMsg(ln, "Counter not found", fn);
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		if(!line.hasNext()) eMsg(ln, "Parameter must be a number", fn);
		token = line.next();
		if(line.hasNext()) eMsg(ln, "Too many parameters", fn);
		if(random) return new Command("rs", token, rv);
		return new Command("st", token, rv);
	}
	void runFunc(Scanner line, int ln, ArrayList<Command> thread, String fn)
	{
		String token;
		int rv;
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn); //if there are 0 parameters
		while(line.hasNext())
		{
			if(line.hasNextInt())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad function name: " + token, fn);
			}
			token = parse(line, ln, fn);
			rv = isFound(token, ln, fn);
			if(rv == -1) eMsg(ln, "Function " + token + " not found", fn);
			thread.add(new Command("rn", rv));
		}
	}
	void repeat(Scanner line, int ln, String token, String fn)
	{ // rpt|brpt (number of times) [func]
		String cmd = token;
		int times = pInt(parse(line, ln, fn), ln, fn);
		int rv = -1;
		if (token.equals("brpt"))
		{
			token = parse(line, ln, fn);
			rv = isFound(token, ln, fn);
			if (rv == -1) eMsg(ln, "Function " + token + " not found", fn);
		}
		if (times < 0) eMsg(ln, "Number of repetitions cannot be negative", fn);
		if (times == 0) times = -1;
		if (rv == -1)
		{
			m.times = times;
			return;
		}
		funcs.get(rv).times = times;
	}
	void readScripts(ArrayList<Command> thread, Scanner line, int ln, String fn)
	{
		String token = parse(line, ln, fn);
		parseScript(token, thread);
		while(line.hasNext())
		{
			token = parse(line, ln, fn);
			parseScript(token, thread);
		}
	}
	private void parseLine(Scanner line, ArrayList<Command> thread, int ln, String fn)
	{
		if(!line.hasNext()) return;
		String token = line.next();
		int rv;
		switch(token)
		{
			case "point": pointCreator(line, ln, fn, true); break;
			case "spnp": pointCreator(line, ln, fn, false); break;
			case "move": case "naturalmove": thread.add(move(line, ln, fn)); break;
			case "click": case "middleclick": case "rightclick": 
			case "mousedown": case "middledown": case "rightdown":
			case "mouseup": case "middleup": case "rightup": thread.add(click(line, ln, token, fn)); break;
			case "rwait": case "wait": thread.add(wait(line, ln, token, fn)); break;
			case "keyup": case "keydown": case "keypress": thread.add(KP(line, ln, token, fn)); break;
			case "function": parseFunc(line, ln, fn); break;
			case "log": makeLFT(line, ln, fn); break;
			case "logf": logF(line, ln, fn); break;
			case "logrun": logRun(line, ln, fn); break;
			case "counter": makeCounters(line, ln, fn); break;
			case "set": thread.add(set(line, ln, token, fn, false)); break;
			case "rpt": case "brpt": repeat(line, ln, token, fn); break;
			case "gt": case "ge": case "eq": case "le": case "lt": case "ne": thread.add(cmp(token, line, ln, fn)); break;
			case "increment": incrementer(line, ln, thread, fn); break;
			case "decrement": decrementer(line, ln, thread, fn); break;
			case "run": runFunc(line, ln, thread, fn); break;
			case "print": thread.add(new Command("pt", getString(line))); break;
			case "read": readScripts(thread, line, ln, fn); break;
			case "lprint": thread.add(new Command("lp", getString(line))); break;
			case "cprint": cPrint(line, ln, fn, thread, false); break;
			case "lcprint": cPrint(line, ln, fn, thread, true); break;
			case "comment": break;
			case "thread": makeNewThread(line, ln, fn); break;
			case "rset": thread.add(set(line, ln, token, fn, true)); break;
			case "pixel": pixelGrabber(line, ln, fn); break;
			case "check": thread.add(check(token, line, ln, fn, true)); break;
			case "check2": thread.add(check(token, line, ln, fn, false)); break;
			case "lshift": case "rshift": case "ushift": case "dshift": thread.add(shift(token, line, ln, fn)); break;
			default: lastCase(line, token, ln, fn); break;
		}
		line.close();
	}
	
	void killThreads(Scanner line, int ln, String fn, ArrayList<Command> thread)
	{
		//this is wrong. the kill command should kill threads but not all functions are threads
		//This should search the list of threads, not the list of functions. if the thread is found, add a command to kill it.
		String token;
		if(!line.hasNext())
		{
			thread.add(new Command("kl", -1)); //kill main thread, this part is correct
		}
		while(line.hasNext())
		{
			token = parse(line, ln, fn);
			int rv = isFound(token, ln, fn);
			if(rv == -1) eMsg(ln, "Thread " + token + " not found", fn);
		}
	}
	void makeNewThread(Scanner line, int ln, String fn)
	{
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		String name;
		int rv;
		while(line.hasNext())
		{
			name = parse(line, ln, fn);
			if((rv = isFound(name, ln, fn)) == -1) eMsg(ln, "Function " + name + " not found", fn);
			funcs.get(rv).separate = true;
		}
	}
	void lastCase(Scanner line, String token, int ln, String fn)
	{
		int rv;
		if((rv = isFound(token, ln, fn)) != -1)
		{
			if(!line.hasNext()) eMsg(ln, "Function found but not enough parameters", fn);
			parseLine(new Scanner(getString(line)), funcs.get(rv).cmds, ln, fn);
		}
		else
		{
			eMsg(ln, "Line is unreadable, bad token: " + token, fn);
		}
	}
	void cPrint(Scanner line, int ln, String fn, ArrayList<Command> thread, boolean l)
	{
		String token;
		int rv;
		if(!line.hasNext()) eMsg(ln, "Not enough parameters", fn);
		while(line.hasNext())
		{
			if(line.hasNextInt() || line.hasNextDouble())
			{
				token = parse(line, ln, fn);
				eMsg(ln, "Bad point name: " + token, fn);
			}
			token = parse(line, ln, fn);
			rv = findCounter(token);
			if(rv == -1) eMsg(ln, "Counter " + token + " not found", fn);
			System.out.println("L IS " + l);
			if(!l) thread.add(new Command("pc", rv));
			if(l) thread.add(new Command("lc", rv));
		}
	}
	int isFound(String name, int ln, String fn)
	{
		int i = 0;
		for(Func b: funcs)
		{
			if (name.equals(b.name))
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	private int isPoint(String token)
	{
		int i = 0;
		for(Location l:locs)
		{
			if(l.getName().equals(token)) return i;
			i ++;
		}
		return -1;
	}
	private int isColor(String token)
	{
		int i = 0;
		for(Location l:colors)
		{
			if(l.getName().equals(token)) return i;
			i++;
		}
		return -1;
	}
	private int findCounter(String name)
	{
		int i = 0; 
		for(Counter k: counters)
		{
			if(k.name.equals(name)) return i;
			i++;
		}
		return -1;
	}
	private int findTailer(String name)
	{
		int i = 0;
		for(TailerFrame f: frames)
		{
			if(f.name.equals(name)) return i;
			i ++;
		}
		return -1;
	}
	private String getString(Scanner line)
	{
		if(!line.hasNext()) return null;
		String retval = line.next();
		while(line.hasNext())
		{
			retval += " " + line.next();
		}
		return retval;
	}
	private void parseScript(String filename, ArrayList<Command> thread)
	{
		boolean found = false;
		for(String s: names)
		{
			if(s.equals(filename))
			{
				found = true;
				break;
			}
		}
		if(found == true) return;
		System.out.println("Parsing " + filename);
		names.add(filename);
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String nextLine;
			int ln = 1;
			while((nextLine = in.readLine()) != null)
			{
				parseLine(new Scanner(nextLine), thread, ln, filename);
				ln ++;
			}
			in.close();
		}
		catch (FileNotFoundException ex)
		{
			popUp("Error: script file \"" + filename + "\" not found.");
			System.exit(1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private void init(String[] filename)
	{
		// Setup the menu
		ready = false;
		names = new ArrayList<String>();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		StartorStop = new JButton("Start");
		StartorStop.addActionListener(this);
		buttonPanel.add(StartorStop);
		Container contentPane = this.getContentPane();
		contentPane.add(buttonPanel, BorderLayout.NORTH);
		add(drawPanel);
		try
		{
			robot = new Robot();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		JMenu tailMenu = new JMenu( "Script File" );
		tailMenu.add( menuOpenScript );
		this.menuOpenScript.addActionListener( this );
		this.menuBar.add( tailMenu );
		if(filename == null) this.setJMenuBar( this.menuBar );
		this.setTitle( "Scripto v2.0" );
		this.getContentPane().add( desktop, BorderLayout.CENTER );
		this.setSize( 150, 50 );
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( d.width / 2 - 75, d.height / 2 - 25 );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frames = new ArrayList<TailerFrame>();
		runthread = new RunnerFrame();
		locs = new ArrayList<Location>();
		colors = new ArrayList<Location>();
		funcs = new ArrayList<Func>();
		counters = new ArrayList<Counter>();
		comparisons = new ArrayList<Command>();
		m = new Func("main");
		if(!ready) this.StartorStop.setText("Exit");
		if(filename != null)
		{
			for(String a: filename)	parseScript(a, m.cmds);
			setup();
		}
		this.setVisible( true );
	}
	void setup()
	{
		threads = new ArrayList<RunnerFrame>();
		m.locs = locs;
		m.colors = colors;
		m.funcs = funcs;
		m.counters = counters;
		m.comparisons = comparisons;
		for(Func b: funcs)
		{
			b.locs = locs;
			b.colors = colors;
			b.counters = counters;
			b.funcs = funcs;
			b.comparisons = comparisons;
			if(b.separate) 
			{
				threads.add(new RunnerFrame(b, funcs));
			}
		}
		runthread.tailer.mainfunc = m;
		runthread.tailer.funcs = funcs;
		for(TailerFrame f:frames)f.funcs = funcs;
		ready = true;
		StartorStop.setText("Start");
		this.setSize(150,50);
		repaint();
		
	}
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == this.menuOpenScript )
		{
			if(!ready)
			{
				JFileChooser fc = new JFileChooser();
				int retval = fc.showOpenDialog( this );
				if( retval == JFileChooser.APPROVE_OPTION )
				{
					fn = fc.getSelectedFile().getAbsolutePath();
					parseScript(fn, m.cmds);
					this.setJMenuBar(null);
					setup();
				}
			}
		}
		else if (e.getSource() == StartorStop) 
		{
			if(ready)
			{
				StartorStop.setText("Exit");
				runthread.begin();
				for(TailerFrame f: frames) f.begin();
				for(RunnerFrame r: threads) r.begin();
				repaint();
				ready = false;
				return;
			}
			for(TailerFrame f: frames) f.stopTailing(); 
			System.exit(0);
		}
	}
	public static void main( String[] args )
	{
		JTailer tailer = null;
		if(args.length == 0) 
		{
			tailer = new JTailer();
		}
		else
		{
			tailer = new JTailer(args);
		}
	}
	class DrawStuff extends JPanel 
	{
		@Override
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g.drawString("Hello", getHeight() / 2, getWidth() / 2);
		}
	}
}
