package commands;

import mainpackage.Main;
import mainpackage.ModulBasics;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SqlCMD extends ModulBasics 
{

	public SqlCMD(MessageReceivedEvent event) {
		super(event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		channel.sendMessage(Main.connectMySQl()).queue();
		
	}

}
