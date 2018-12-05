package commands;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.RandomStringUtils;

import mainpackage.Main;
import mainpackage.ModulBasics;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.role.*;
import net.dv8tion.jda.core.requests.Route.Roles;

public class AddCMD extends ModulBasics
{

	public AddCMD(MessageReceivedEvent event) {
		super(event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		List<User> users = event.getJDA().getUsers();
		
		if(event.getMessage().getContentRaw().contains(" "))
		{
			for(User user: users)
			{
				if(user.getId().equals(event.getAuthor().getId()))
				{
					String id = RandomStringUtils.randomAlphanumeric(10);
					String[] parts = event.getMessage().getContentRaw().split(" ");
					LocalDate datum=null;
					LocalDate reminder =null;
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
					if(parts.length>=4&&parts[3].startsWith("R"))
					{
						String typ = parts[1];
						try 
						{
							try 
							{
								datum = LocalDate.parse(parts[2], dtf);
								String part3 = parts[3].substring(1);
								if(part3.equalsIgnoreCase("Null"))
								{
									reminder = null;
								}
								else
								{
									reminder = LocalDate.parse(part3, dtf);
								}
								if(reminder.isAfter(datum))
								{
									channel.sendMessage("Is After").queue();
									throw new NumberFormatException("Reminder darf nicht nach dem eingetragenem Termin, stattfinden!");
								}
							}catch(NumberFormatException nfx)
							{
								channel.sendMessage(nfx.getMessage()).queue();
								reminder=null;
							}catch(Exception ex)
							{
								throw new Exception("Falsches Datum Format! Richtiges Format: yyyy.MM.dd z.B.: 2018.10.11");
							}
						}catch(Exception ex)
						{
							channel.sendMessage(ex.getMessage()).queue();
							
						}
						int count =0;
						String desc="";
						for(String part:parts)
						{
							if(count<4)
							{
								count++;
								continue;
							}
							
							desc+=part+" ";
						}
						if(reminder!=null)
						{
							Main.mysql.update("INSERT INTO `termine`(`ID`,`TYP`,`DATUM`,`REMINDER`, `DESCRIPTION`, `CHANNEL`) VALUES('"+id+"','"+typ+"','"+datum+"','"+reminder+"','"+desc+"','"+channel.getName()+"')");
						}
						else
						{
							Main.mysql.update("INSERT INTO `termine`(`ID`,`TYP`,`DATUM`, `DESCRIPTION`, `CHANNEL`) VALUES('"+id+"','"+typ+"','"+datum+"','"+desc+"','"+channel.getName()+"')");
						}
						
						if(reminder!=null)
						{
							channel.sendMessage("Erfolgreich einen Eintrag hinzugefügt: ```"+typ+" am "+datum.format(dtf)+", Reminder: "+reminder.format(dtf)+", Beschreibung: "+desc+"```").queue();
						}
						else
						{
							channel.sendMessage("Erfolgreich einen Eintrag hinzugefügt: ```"+typ+" am "+datum.format(dtf)+", No Reminder, Beschreibung: "+desc+"```").queue();
						}
					
						
					}
					else if(parts.length>=3)
					{
						String typ = parts[1];
						try 
						{
							try 
							{
								datum = LocalDate.parse(parts[2], dtf);
								reminder = datum.minusWeeks(1);
							}catch(Exception ex)
							{
								throw new Exception("Falsches Datum Format! Richtiges Format: yyyy.MM.dd z.B.: 2018.10.11");
							}
						}catch(Exception ex)
						{
							channel.sendMessage(ex.getMessage()).queue();
						}
						int count =0;
						String desc="";
						for(String part:parts)
						{
							if(count<3)
							{
								count++;
								continue;
							}
							
							desc+=part+" ";
						}
						Main.mysql.update("INSERT INTO `termine`(`ID`,`TYP`,`DATUM`,`REMINDER`, `DESCRIPTION`, `CHANNEL`) VALUES('"+id+"','"+typ+"','"+datum+"','"+reminder+"','"+desc+"','"+channel.getName()+"')");
						
						if(reminder!=null)
						{
							channel.sendMessage("Erfolgreich einen Eintrag hinzugefügt: ```"+typ+" am "+datum.format(dtf)+", Reminder: "+reminder.format(dtf)+", Beschreibung: "+desc+"```").queue();
						}
						else
						{
							channel.sendMessage("Erfolgreich einen Eintrag hinzugefügt: ```"+typ+" am "+datum.format(dtf)+", No Reminder, Beschreibung: "+desc+"```").queue();
						}
					
						
					}
					else
					{
						channel.sendMessage("Richtiger Syntax: ```[Prefix]add [Typ] [yyyy.MM.dd] [Ryyyy.MM.dd] [Beschreibung]``` ```[Prefix]add [Typ] [yyyy.MM.dd] [Beschreibung]``` ").queue();
						channel.sendMessage("```[Prefix]add [Typ] [yyyy.MM.dd] [RNull] [Beschreibung]```").queue();
					}
				}
			}
		}
		else
		{
			channel.sendMessage("Richtiger Syntax: ```[Prefix]add [Typ] [yyyy.MM.dd] [Ryyyy.MM.dd] [Beschreibung]``` ```[Prefix]add [Typ] [yyyy.MM.dd] [Beschreibung]``` ").queue();
			channel.sendMessage("```[Prefix]add [Typ] [yyyy.MM.dd] [RNull] [Beschreibung]```").queue();
		}
	}
}
