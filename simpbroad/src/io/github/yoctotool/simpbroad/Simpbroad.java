package io.github.yoctotool.simpbroad;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Simpbroad extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// commande de base qui d�livre l'aide de toutes les commandes du plugin : /sb
		if(cmd.getName().equalsIgnoreCase("sb")) {
			
			if(args.length == 0) {
				sender.sendMessage("=== Help for Simpbroad ==");
			}
			
			else {
				
				// commande d'ajout de message
				if(args[0].equalsIgnoreCase("new")) {
					
					//on v�rifie, pour �viter les bugs, que l'id ne corresponde pas � l'un des noeuds du config.yml existant
					if(args[2].equalsIgnoreCase("msg") || args[2].equalsIgnoreCase("type") || args[2].equalsIgnoreCase("time") || args[2].equalsIgnoreCase("hour")) {
						sender.sendMessage("Message's ID can't be msg, type, time or hour.");
						return false;
					}
					
					else if(args[1].equalsIgnoreCase("normal")) {
						this.getConfig().set("messages."+args[2]+".type", "normal");
						this.getConfig().set("messages."+args[2]+".msg", concatMsg(args));
					    this.saveConfig();
						sender.sendMessage("The message is saved");
						return true;
					}
					
					else if(args[1].contains("date:")) {
						this.getConfig().set("messages."+args[2]+".type", "date");
						this.getConfig().set("messages."+args[2]+".time", args[1].substring(5));
						this.getConfig().set("messages."+args[2]+".msg", concatMsg(args));
					    this.saveConfig();
						sender.sendMessage("The message is saved");
						return true;
					}
					else {
						sender.sendMessage("ERREUR");
						return false;
					}
				}
			}
		}
		return false;
	}
	
	// Les diff�rents mots du message �tants consid�r�s chacun comme des arguments, on concat�ne le tout � partir du d�but du message
	public String concatMsg(String[] args) {
		String msg = "";
		for(int i=3; i<args.length; i++) {
			msg += args[i] + " ";
		}
		return msg;
	}
}
