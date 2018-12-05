package mainpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import sql.MySQL;

public class Main 
{
	public static String prefix;
	private static File f = new File(".//config.ser");
	public static MySQL mysql;
	
	public static void main(String[] args) throws Exception
	{
		try {
			JDA api = new JDABuilder(AccountType.BOT).setToken("NTE1NDc3NTA3MTc3MzE2MzU1.DuJvJg.Oz0-Zi_fSZFoakAH0eC7SqIe9qo").buildAsync();
			mysql=new MySQL("localhost", "discordbot", "root", "");
			
			/*
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeUTF("!");
			oos.flush();
			*/
			Reminder rem = new Reminder();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			prefix = ois.readUTF();
			ois.close();
			
			MyEventListener.fillCommands();
			api.addEventListener(new MyEventListener());
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static String connectMySQl()
	{
		return mysql.connectcommand();
	}
	
	public static void changePrefix(String arg) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeUTF(arg);
		oos.flush();
		oos.close();
		prefix=arg;
	}
}
