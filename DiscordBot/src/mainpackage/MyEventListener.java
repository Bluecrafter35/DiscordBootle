package mainpackage;

import java.util.ArrayList;

import commands.AddCMD;
import commands.PrefixCMD;
import commands.RemoveCMD;
import commands.SqlCMD;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyEventListener extends ListenerAdapter
{
	private static ArrayList<String> commands = new ArrayList<>();
	private String command = "";
	
	public static void fillCommands()
	{
		commands.add("add");
		commands.add("remove");
		commands.add("list");
		commands.add("show");
		commands.add("reminder");
		commands.add("changereminder");
		commands.add("language");
		commands.add("lang");
		commands.add("prefix");
		commands.add("ping");
		commands.add("sqlconnection");
	}
	
	public void onMessageReceived(MessageReceivedEvent event)
	{
		
		if(event.getAuthor().isBot())return;
		if(event.getMessage().getChannel().getType()==ChannelType.PRIVATE)
		{
			return;
		}
		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel();
		
		if(content.startsWith(Main.prefix))
		{
			if(content.contains(" "))
			{
				String[] parts = content.split(" ");
				command = parts[0].substring(Main.prefix.length());
				for(String cmd:commands)
				{
					if(command.equalsIgnoreCase(cmd))
					{
						findCommand(cmd, event);
					}
				}
			}
			else
			{
				command = content.substring(Main.prefix.length());
				for(String cmd:commands)
				{
					if(command.equalsIgnoreCase(cmd))
					{
						findCommand(cmd, event);
					}
				}
			}
			
			//channel.sendMessage("Hallo"+event.getJDA().getPing()).queue();
		}
		else if(content.startsWith("!prefix"))
		{
			event.getChannel().sendMessage("Der aktuelle Prefix ist: "+Main.prefix).queue();
		}
	}
	
	private void findCommand(String command, MessageReceivedEvent event)
	{
		switch(command.toLowerCase())
		{
		case "add": new AddCMD(event);
		break;
		case "remove": new RemoveCMD(event);
		break;
		case "prefix": new PrefixCMD(event);
		break;
		case "sqlconnection": new SqlCMD(event);
		break;
		}
	}
	
}
