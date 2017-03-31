package scripto;
import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class LogFileTailer extends Runner 
{
	private long sampleInterval = 500;
	private long filePointer = 0;
	RandomAccessFile file;
	private File logfile;
	private boolean startAtBeginning = false;

	protected boolean tailing = false;

	private Set listeners = new HashSet();
	public LogFileTailer( File file )
	{
		this.logfile = file;
	}
	public LogFileTailer( File file, long sampleInterval, boolean startAtBeginning )
	{
		this.logfile = file;
		this.sampleInterval = sampleInterval;
	}
	public void addLogFileTailerListener( LogFileTailerListener l )
	{
			this.listeners.add( l );
	}
	public void removeLogFileTailerListener( LogFileTailerListener l )
	{
		this.listeners.remove( l );
	}
	protected void fireNewLogFileLine( String line )
	{
		for( Iterator i=this.listeners.iterator(); i.hasNext(); )
		{
			LogFileTailerListener l = ( LogFileTailerListener )i.next();
			l.newLogFileLine( line );
		}
	}
	public void stopTailing()
	{
		this.tailing = false;
	}
	public class msg extends JPanel
	{
		JLabel a;
		public msg (String item)
		{
			a = new JLabel(item);
			add(a);
		}
	}
	private void eMsg(String reason)
	{
		System.out.println(reason);
		JOptionPane.showMessageDialog(null, new msg(reason));
		System.exit(1);
	}
	public void run()
	{
		if( this.startAtBeginning )
		{
			filePointer = 0;
		}
		else
		{
			filePointer = this.logfile.length();
		}
		try
		{
			this.tailing = true;
			try
			{
				file = new RandomAccessFile( logfile, "r" );
			}
			catch (FileNotFoundException ex)
			{
				eMsg("File not found: " + logfile);
				System.exit(1);
			}
			while( this.tailing )
			{
				try
				{
					long fileLength = this.logfile.length();
					if( fileLength < filePointer ) 
					{
						try
						{
							file = new RandomAccessFile( logfile, "r" );
						}
						catch (FileNotFoundException ex)
						{
							System.out.println("File not found: " + logfile);
							System.exit(1);
						}
						filePointer = 0;
					}

					
					  if( fileLength > filePointer ) 
					  {
					    // There is data to read
					    file.seek( filePointer );
					    String line = file.readLine();
					    while( line != null )
					    {
					      this.fireNewLogFileLine( line );
					      line = file.readLine();
					    }
					    filePointer = file.getFilePointer();
					  }
					sleep( this.sampleInterval );
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				}
			}
			file.close();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
