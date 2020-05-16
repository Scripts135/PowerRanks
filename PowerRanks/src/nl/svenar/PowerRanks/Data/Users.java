package nl.svenar.PowerRanks.Data;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import nl.svenar.PowerRanks.Main;
import org.bukkit.event.Listener;

public class Users implements Listener {
	Main m;

	public Users(Main m) {
		this.m = m;
	}

	public void setGroup(Player player, String t, String rank) {
		if (player != null) {
			if (player.hasPermission("powerranks.cmd.set")) {
				Player target = Bukkit.getServer().getPlayer(t);

				if (target != null) {
					File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
					File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
					YamlConfiguration rankYaml = new YamlConfiguration();
					YamlConfiguration playerYaml = new YamlConfiguration();
					try {
						rankYaml.load(rankFile);
						if (rankYaml.get("Groups." + rank) != null) {
							playerYaml.load(playerFile);
							playerYaml.set("players." + target.getUniqueId() + ".rank", (Object) rank);
							playerYaml.save(playerFile);
							player.sendMessage(String.valueOf(this.m.plp) + " Set '" + t + "' to rank: " + rank);
							target.sendMessage(String.valueOf(this.m.plp) + " You have been promoted to: " + rank
									+ " by " + player.getName());
							this.m.setupPermissions(target);
						} else {
							player.sendMessage(String.valueOf(this.m.plp) + " Group '" + rank + "' not found! "
									+ ChatColor.RED + "Group names must be Case sensitive");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					File rankFile2 = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
					File playerFile2 = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
					YamlConfiguration rankYaml2 = new YamlConfiguration();
					YamlConfiguration playerYaml2 = new YamlConfiguration();
					try {
						rankYaml2.load(rankFile2);
						if (rankYaml2.get("Groups." + rank) != null) {
							playerYaml2.load(playerFile2);

							boolean offline_player_found = false;

							for (String key : playerYaml2.getConfigurationSection("players").getKeys(false)) {
								if (playerYaml2.getString("players." + key + ".name").equalsIgnoreCase(t)) {
									playerYaml2.set("players." + key + ".rank", (Object) rank);
									playerYaml2.save(playerFile2);
									player.sendMessage(
											String.valueOf(this.m.plp) + " Set '" + t + "' to rank: " + rank);

									offline_player_found = true;
								}
							}

							if (!offline_player_found) {
								player.sendMessage(String.valueOf(this.m.plp) + " Player '" + t + "' not found!");
							}
						} else {
							player.sendMessage(String.valueOf(this.m.plp) + " Group '" + rank + "' not found! "
									+ ChatColor.RED + "Group names must be Case sensitive");
						}
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
					}
				}
			} else {
				this.m.errorArgsSet(player);
			}
		} else {
			ConsoleCommandSender console = Bukkit.getConsoleSender();
			Player target2 = Bukkit.getServer().getPlayer(t);

			if (target2 != null) {
				File rankFile2 = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
				File playerFile2 = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
				YamlConfiguration rankYaml2 = new YamlConfiguration();
				YamlConfiguration playerYaml2 = new YamlConfiguration();
				try {
					rankYaml2.load(rankFile2);
					if (rankYaml2.get("Groups." + rank) != null) {
						playerYaml2.load(playerFile2);
						playerYaml2.set("players." + target2.getUniqueId() + ".rank", (Object) rank);
						playerYaml2.save(playerFile2);
						console.sendMessage(String.valueOf(this.m.plp) + " Set '" + t + "' to rank: " + rank);
						target2.sendMessage(String.valueOf(this.m.plp) + " You have been promoted to: " + rank + " by "
								+ console.getName());
						this.m.setupPermissions(target2);
					} else {
						console.sendMessage(String.valueOf(this.m.plp) + " Group '" + rank + "' not found! "
								+ ChatColor.RED + "Group names must be Case sensitive");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else {
				File rankFile2 = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
				File playerFile2 = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
				YamlConfiguration rankYaml2 = new YamlConfiguration();
				YamlConfiguration playerYaml2 = new YamlConfiguration();
				try {
					rankYaml2.load(rankFile2);
					if (rankYaml2.get("Groups." + rank) != null) {
						playerYaml2.load(playerFile2);

						boolean offline_player_found = false;

						for (String key : playerYaml2.getConfigurationSection("players").getKeys(false)) {
							if (playerYaml2.getString("players." + key + ".name").equalsIgnoreCase(t)) {
								playerYaml2.set("players." + key + ".rank", (Object) rank);
								playerYaml2.save(playerFile2);
								console.sendMessage(String.valueOf(this.m.plp) + " Set '" + t + "' to rank: " + rank);

								offline_player_found = true;
							}
						}

						if (!offline_player_found) {
							console.sendMessage(String.valueOf(this.m.plp) + " Player '" + t + "' not found!");
						}
					} else {
						console.sendMessage(String.valueOf(this.m.plp) + " Group '" + rank + "' not found! "
								+ ChatColor.RED + "Group names must be Case sensitive");
					}
				} catch (IOException | InvalidConfigurationException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean setGroup(Player player, String rank) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		YamlConfiguration playerYaml = new YamlConfiguration();
		boolean success = false;
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				playerYaml.load(playerFile);
				playerYaml.set("players." + player.getUniqueId() + ".rank", (Object) rank);
				playerYaml.save(playerFile);
				this.m.setupPermissions(player);
				success = true;
			} else {
				success = false;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return success;
	}

	public String getPrefix(Player player) {
		String prefix = "";
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		YamlConfiguration playerYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			playerYaml.load(playerFile);
			String rank = playerYaml.getString("players." + player.getUniqueId() + ".rank");
			prefix = rankYaml.getString("Groups." + rank + ".chat.prefix").replaceAll("&", "§");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prefix;
	}

	public String getGroup(String plr, String t) {
		Player sender = (plr == null || plr == "API") ? null : Bukkit.getServer().getPlayer(plr);
		Player target = Bukkit.getServer().getPlayer(t);
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration playerYaml = new YamlConfiguration();
		String group = "";
		if (target != null) {
			try {
				playerYaml.load(playerFile);
				group = playerYaml.getString("players." + target.getUniqueId() + ".rank");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				playerYaml.load(playerFile);
				for (String key : playerYaml.getConfigurationSection("players").getKeys(false)) {
					if (playerYaml.getString("players." + key + ".name").equalsIgnoreCase(t)) {
						group = playerYaml.getString("players." + key + ".rank");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (group.length() > 0) {
			if (sender != null) {
				sender.sendMessage(
						String.valueOf(this.m.plp) + (target == null ? t : target.getName()) + "'s rank is: " + group);
			} else {
				Bukkit.getConsoleSender().sendMessage(
						String.valueOf(this.m.plp) + (target == null ? t : target.getName()) + "'s rank is: " + group);
			}
		} else {
			if (sender != null) {
				sender.sendMessage(String.valueOf(this.m.plp) + "Player " + t + " not found!");
			} else {
				Bukkit.getConsoleSender().sendMessage(String.valueOf(this.m.plp) + "Player " + t + " not found!");
			}
		}
		return (group.length() == 0) ? "error" : group;
	}

	public String getGroup(Player player) {
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration playerYaml = new YamlConfiguration();
		String group = null;
		try {
			playerYaml.load(playerFile);
			group = playerYaml.getString("players." + player.getUniqueId() + ".rank");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	public ArrayList<String> getGroups() {
		ArrayList<String> ranks = new ArrayList<String>();
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			rankYaml.getList("Groups");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ranks;
	}

	public boolean addPermission(String rank, String permission) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				List<String> list = (List<String>) rankYaml.getStringList("Groups." + rank + ".permissions");
				list.add(permission);
				rankYaml.set("Groups." + rank + ".permissions", (Object) list);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removePermission(String rank, String permission) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				List<String> list = (List<String>) rankYaml.getStringList("Groups." + rank + ".permissions");
				list.remove(permission);
				rankYaml.set("Groups." + rank + ".permissions", (Object) list);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addInheritance(String rank, String inheritance) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				List<String> list = (List<String>) rankYaml.getStringList("Groups." + rank + ".inheritance");
				if (!list.contains(inheritance)) {
					list.add(inheritance);
				}
				rankYaml.set("Groups." + rank + ".inheritance", (Object) list);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setPrefix(String rank, String prefix) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank + ".chat.prefix", (Object) prefix);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setSuffix(String rank, String suffix) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank + ".chat.suffix", (Object) suffix);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setChatColor(String rank, String color) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank + ".chat.chatColor", (Object) color);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setNameColor(String rank, String color) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank + ".chat.nameColor", (Object) color);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeInheritance(String rank, String inheritance) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				List<String> list = (List<String>) rankYaml.getStringList("Groups." + rank + ".inheritance");
				if (list.contains(inheritance)) {
					list.remove(inheritance);
				}
				rankYaml.set("Groups." + rank + ".inheritance", (Object) list);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean createRank(String rank) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) == null) {
				rankYaml.set("Groups." + rank + ".permissions", (Object) "[]");
				rankYaml.set("Groups." + rank + ".inheritance", (Object) "[]");
				rankYaml.set("Groups." + rank + ".build", (Object) true);
				rankYaml.set("Groups." + rank + ".chat.prefix", (Object) ("[&7" + rank + "&r]"));
				rankYaml.set("Groups." + rank + ".chat.suffix", (Object) "");
				rankYaml.set("Groups." + rank + ".chat.chatColor", (Object) "&f");
				rankYaml.set("Groups." + rank + ".chat.nameColor", (Object) "&f");
				rankYaml.set("Groups." + rank + ".level.promote", (Object) "");
				rankYaml.set("Groups." + rank + ".level.demote", (Object) "");
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRank(String rank) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank, (Object) null);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean setBuild(String rank, boolean enabled) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		try {
			rankYaml.load(rankFile);
			if (rankYaml.get("Groups." + rank) != null) {
				rankYaml.set("Groups." + rank + ".build", (Object) enabled);
				rankYaml.save(rankFile);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean promote(String playername) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		YamlConfiguration playerYaml = new YamlConfiguration();
		Player player = Bukkit.getServer().getPlayer(playername);
		if (player != null) {
			try {
				rankYaml.load(rankFile);
				playerYaml.load(playerFile);
				String rank = playerYaml.getString("players." + player.getUniqueId() + ".rank");
				if (rankYaml.get("Groups." + rank) != null) {
					String rankname = rankYaml.getString("Groups." + rank + ".level.promote");
					if (rankYaml.get("Groups." + rankname) != null && rankname.length() > 0) {
						playerYaml.set("players." + player.getUniqueId() + ".rank", (Object) rankname);
						playerYaml.save(playerFile);
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				rankYaml.load(rankFile);
				playerYaml.load(playerFile);
				
				boolean offline_player_found = false;

				for (String key : playerYaml.getConfigurationSection("players").getKeys(false)) {
					if (playerYaml.getString("players." + key + ".name").equalsIgnoreCase(playername)) {
						String rankname = rankYaml.getString("Groups." + playerYaml.getString("players." + key + ".rank") + ".level.promote");
						if (rankname.length() == 0) return false;
						
						playerYaml.set("players." + key + ".rank", (Object) rankname);
						playerYaml.save(playerFile);

						offline_player_found = true;
						return true;
					}
				}

				if (!offline_player_found) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean demote(String playername) {
		File rankFile = new File(String.valueOf(this.m.fileLoc) + "Ranks" + ".yml");
		File playerFile = new File(String.valueOf(this.m.fileLoc) + "Players" + ".yml");
		YamlConfiguration rankYaml = new YamlConfiguration();
		YamlConfiguration playerYaml = new YamlConfiguration();
		Player player = Bukkit.getServer().getPlayer(playername);
		if (player != null) {
			try {
				rankYaml.load(rankFile);
				playerYaml.load(playerFile);
				String rank = playerYaml.getString("players." + player.getUniqueId() + ".rank");
				if (rankYaml.get("Groups." + rank) != null) {
					String rankname = rankYaml.getString("Groups." + rank + ".level.demote");
					if (rankYaml.get("Groups." + rankname) != null && rankname.length() > 0) {
						playerYaml.set("players." + player.getUniqueId() + ".rank", (Object) rankname);
						playerYaml.save(playerFile);
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				rankYaml.load(rankFile);
				playerYaml.load(playerFile);
				
				boolean offline_player_found = false;

				for (String key : playerYaml.getConfigurationSection("players").getKeys(false)) {
					if (playerYaml.getString("players." + key + ".name").equalsIgnoreCase(playername)) {
						String rankname = rankYaml.getString("Groups." + playerYaml.getString("players." + key + ".rank") + ".level.demote");
						if (rankname.length() == 0) return false;
						
						playerYaml.set("players." + key + ".rank", (Object) rankname);
						playerYaml.save(playerFile);

						offline_player_found = true;
						return true;
					}
				}

				if (!offline_player_found) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}