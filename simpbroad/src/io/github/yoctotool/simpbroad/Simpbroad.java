package io.github.yoctotool.simpbroad;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Simpbroad extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// commande de base qui délivre l'aide de toutes les commandes du plugin : /sb
		if(cmd.getName().equalsIgnoreCase("sb")) {
			
			if(args.length == 0) {
				sender.sendMessage("=== Help for Simpbroad ==");
			}
			
			else {
				
				// commande d'ajout de message
				if(args[0].equalsIgnoreCase("new")) {
					
					//on vérifie, pour éviter les bugs, que l'id ne corresponde pas à l'un des noeuds du config.yml existant
					if(args[2].equalsIgnoreCase("msg") || args[2].equalsIgnoreCase("type") || args[2].equalsIgnoreCase("time") || doesIdExist(args[2])) {
						sender.sendMessage("Message's ID can't be msg, type, time, date or an already existing one.");
						return false;
					}
					
					//traitement de la commande et enregistrement des messages
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
					
					//Une erreur? :O
					else {
						sender.sendMessage("ERROR: help with /sb");
						return false;
					}
				}
			}
		}
		return false;
	}
	
	// Les différents mots du message étants considérés chacun comme des arguments, on concatène le tout à partir du début du message
	public String concatMsg(String[] args) {
		String msg = "";
		for(int i=3; i<args.length; i++) {
			msg += args[i] + " ";
		}
		return msg;
	}
	//Fonction qui regarde tous les IDs entrés puis les comparent au nouvel ID du message 
	public boolean doesIdExist(String arg) {
		Map<String, Object> existingID = this.getConfig().getConfigurationSection("messages").getValues(false);
		Iterator<Entry<String, Object>> it = existingID.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry ids = (Map.Entry)it.next();
			if(((String)ids.getKey()).equalsIgnoreCase(arg)) {
				return true;
			}
			it.remove();
		}
		return false;
	}
}
