package commands;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import mainpackage.Main;
import mainpackage.ModulBasics;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RemoveCMD extends ModulBasics
{

	public RemoveCMD(MessageReceivedEvent event) {
		super(event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		
		if(member.hasPermission(Permission.ADMINISTRATOR))
		{
			if(!event.getMessage().getContentRaw().contains(" "))
			{
				ResultSet rs = Main.mysql.query("SELECT * FROM `termine`");
				
				event.getAuthor().openPrivateChannel().complete().sendMessage("Alle gespeicherten Einträge:").queue();
				try {
					while(rs.next()&&rs.getString("TYP")!=null)
					{
						String id = rs.getString("ID");
						String typ = rs.getString("TYP");
						Date date= rs.getDate("DATUM");
						LocalDate datum = date.toLocalDate();
						String desc = rs.getString("DESCRIPTION");
						
						event.getAuthor().openPrivateChannel().complete().sendMessage("```ID: "+id+", Typ: "+typ+", Datum: "+datum.format(dtf)+", Beschreibung: "+desc+"```").queue();
					}
						
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				String[] parts = event.getMessage().getContentRaw().split(" ");
				Main.mysql.update("DELETE FROM `termine` WHERE ID='"+parts[1]+"'");
			}
		}
		
	}

}
