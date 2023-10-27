package nl.svenar.powerranks.nukkit.commands.usertags;

import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;

import nl.svenar.powerranks.nukkit.PowerRanks;
import nl.svenar.powerranks.nukkit.commands.PowerCommand;
import nl.svenar.powerranks.common.utils.PRUtil;


import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;

public class cmd_setusertag extends PowerCommand {

	public cmd_setusertag(PowerRanks plugin, String command_name, COMMAND_EXECUTOR ce) {
		super(plugin, command_name, ce);
		this.setCommandPermission("powerranks.cmd." + command_name.toLowerCase());
	}

	@Override
	public boolean onCommand(CommandSender sender, String commandLabel, String commandName, String[] args) {
		if (args.length == 1) {
			final String playername = sender.getName();
			final String tag = args[0];
			final boolean result = plugin.getUsertagManager().setUsertag(playername, tag);
			if (result) {
				Player targetPlayer = plugin.getServer().getPlayer(playername);

				sender.sendMessage(PRUtil.powerFormatter(
						plugin.getLanguageManager().getFormattedMessage(
								"commands." + commandName.toLowerCase() + ".success"),
						ImmutableMap.<String, String>builder()
								.put("player", sender.getName())
								.put("target", targetPlayer.getName())
								.put("usertag", tag)
								.build(),
						'[', ']'));
			} else {
				sender.sendMessage(PRUtil.powerFormatter(
						plugin.getLanguageManager().getFormattedMessage(
								"commands." + commandName.toLowerCase() + ".failed"),
						ImmutableMap.<String, String>builder()
								.put("player", sender.getName())
								.put("target", playername)
								.put("usertag", tag)
								.build(),
						'[', ']'));
			}
		} else if (args.length == 2) {
			if (sender.hasPermission("powerranks.cmd." + commandName.toLowerCase() + ".other")) {
				final String playername = args[0];
				final String tag = args[1];
				final boolean result = plugin.getUsertagManager().setUsertag(playername, tag);
				if (result) {
					Player targetPlayer = plugin.getServer().getPlayer(playername);
					sender.sendMessage(PRUtil.powerFormatter(
							plugin.getLanguageManager().getFormattedMessage(
									"commands." + commandName.toLowerCase() + ".success"),
							ImmutableMap.<String, String>builder()
									.put("player", sender.getName())
									.put("target", targetPlayer.getName())
									.put("usertag", tag)
									.build(),
							'[', ']'));
				} else {
					sender.sendMessage(PRUtil.powerFormatter(
							plugin.getLanguageManager().getFormattedMessage(
									"commands." + commandName.toLowerCase() + ".failed"),
							ImmutableMap.<String, String>builder()
									.put("player", sender.getName())
									.put("target", playername)
									.put("usertag", tag)
									.build(),
							'[', ']'));
				}
			} else {
				sender.sendMessage(plugin.getLanguageManager().getFormattedMessage("general.no-permission"));
			}
		} else {
			sender.sendMessage(
					plugin.getLanguageManager().getFormattedUsageMessage(commandLabel, commandName,
							"commands." + commandName.toLowerCase() + ".arguments", sender instanceof Player));
		}

		return false;
	}

	public ArrayList<String> tabCompleteEvent(CommandSender sender, String[] args) {
		ArrayList<String> tabcomplete = new ArrayList<String>();

		if (args.length == 1) {
			for (Player player : plugin.getServer().getOnlinePlayers().values()) {
				tabcomplete.add(player.getName());
			}
		}

		if (args.length == 2) {
			for (String tag : plugin.getUsertagManager().getUsertags()) {
				tabcomplete.add(tag);
			}
		}

		return tabcomplete;
	}
}
