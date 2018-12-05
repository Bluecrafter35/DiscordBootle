package mainpackage;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class ModulBasics 
{
	protected String command;
	protected MessageReceivedEvent event;
	protected MessageChannel channel;
	protected User user;
	protected Member member;
	protected Guild guild;
	
	public ModulBasics(MessageReceivedEvent event)
	{
		this.command=event.getMessage().getContentRaw();
		if(command.contains(" "))
		{
			String[] parts = command.split(" ");
			this.command=parts[0].substring(1);
		}
		else
		{
			this.command=this.command.substring(1);
		}
		this.event=event;
		this.channel=event.getChannel();
		this.user=event.getAuthor();
		this.member=event.getMember();
		this.guild=event.getGuild();
		process();
	}
	
	public abstract void process();
	
}
