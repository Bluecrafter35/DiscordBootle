package commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import mainpackage.Main;
import mainpackage.ModulBasics;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PrefixCMD extends ModulBasics
{

	public PrefixCMD(MessageReceivedEvent event) {
		super(event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		if(event.getMessage().getContentRaw().contains(" "))
		{
			String[] parts = event.getMessage().getContentRaw().split(" ");
			try {
				Main.changePrefix(parts[1]);
				event.getChannel().sendMessage("Prefix erfolgreich auf: ```"+parts[1]+"``` geändert").queue();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			event.getChannel().sendMessage("Der aktuelle Prefix ist: "+Main.prefix).queue();
		}
	}

}
