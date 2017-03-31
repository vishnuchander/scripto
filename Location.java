package scripto;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class Location extends Point
{
	private String name;
	Color color;
	protected void setName(String n)
	{
		name = n;
	}
	protected String getName()
	{
		return name;
	}
	Location(String n, int a, int b)
	{
		x = a;
		y = b;
		name = n;
	}
	Location(String n)
	{
		name = n;
	}
	Location()
	{
		x = 0;
		y = 0;
		name = "";
	}
	Location(String n, int a, int b, Color c)
	{
		x = a;
		y = b;
		color = c;
		name = n;
	}
}
