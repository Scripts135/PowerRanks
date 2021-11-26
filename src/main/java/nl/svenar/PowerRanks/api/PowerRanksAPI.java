package nl.svenar.PowerRanks.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import nl.svenar.PowerRanks.PowerRanks;
import nl.svenar.PowerRanks.Cache.CacheManager;
import nl.svenar.PowerRanks.Data.Users;
import nl.svenar.common.structure.PRPermission;
import nl.svenar.common.structure.PRRank;

public class PowerRanksAPI {

	private String API_VERSION = "1.0";
	
	public static PowerRanks plugin;
	private Users users;
	
	public PowerRanksAPI(PowerRanks plugin) {
		PowerRanksAPI.plugin = plugin;
		this.users = new Users(plugin);
	}
	
	public PowerRanksAPI() {
		this.users = new Users(plugin);
	}

	public String getName() {
		return plugin.getName();
	}

	public String getVersion() {
		return PowerRanks.getVersion();
	}

	public String getApiVersion() {
		return API_VERSION;
	}
	
	public String getPlayerRank(Player player) {
		String rank = users.getGroup(player);
		return rank;
	}
	
	public boolean setPlayerRank(Player player, String rank) {
		boolean success = users.setGroup(player, users.getRankIgnoreCase(rank), true);
		return success;
	}
	
	public List<String> getPermissions(String rank) {
		List<String> permissions = new ArrayList<String>();
		for (PRPermission permission : CacheManager.getRank(users.getRankIgnoreCase(rank)).getPermissions()) {
			permissions.add(permission.getName());
		}
		return permissions;
	}
	
	public boolean addPermission(String rank, String permission) {
		return users.addPermission(users.getRankIgnoreCase(rank), permission);
	}
	
	public boolean removePermission(String rank, String permission) {
		return users.removePermission(users.getRankIgnoreCase(rank), permission);
	}
	
	public List<String> getInheritances(String rank) {
		return users.getInheritances(users.getRankIgnoreCase(rank));
	}
	
	public boolean addInheritance(String rank, String inheritance) {
		return users.addInheritance(users.getRankIgnoreCase(rank), inheritance);
	}
	
	public boolean removeInheritance(String rank, String inheritance) {
		return users.removeInheritance(users.getRankIgnoreCase(rank), inheritance);
	}
	
	public Set<String> getRanks() {
		Set<String> ranks = new HashSet<String>();
		for (PRRank rank : CacheManager.getRanks()) {
			ranks.add(rank.getName());
		}
		return ranks;
	}
	
	public String getPrefix(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getPrefix();
		}
		return null;
	}
	
	public boolean setPrefix(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setPrefix(new_value);
			return true;
		}
		return false;
	}
	
	public String getSuffix(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getSuffix();
		}
		return null;
	}
	
	public boolean setSuffix(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setSuffix(new_value);
			return true;
		}
		return false;
	}
	
	public String getNameColor(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getNamecolor();
		}
		return null;
	}
	
	public boolean setNameColor(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setNamecolor(new_value);
			return true;
		}
		return false;
	}
	
	public String getChatColor(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getChatcolor();
		}
		return null;
	}
	
	public boolean setChatColor(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setChatcolor(new_value);
			return true;
		}
		return false;
	}
	
	@Deprecated
	public boolean isBuildingEnabled(String rank) {
		return false;
	}

	@Deprecated
	public boolean setBuildingEnabled(String rank, boolean new_value) {
		return false;
	}
	
	public String getPromoteRank(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getPromoteRank();
		}
		return null;
		// String value = users.getRanksConfigFieldString(users.getRankIgnoreCase(rank), "level.promote");
		// return value;
	}
	
	public boolean setPromoteRank(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setPromoteRank(new_value);
			return true;
		}
		return false;
		// boolean value = users.setRanksConfigFieldString(users.getRankIgnoreCase(rank), "level.promote", new_value);
		// return value;
	}
	
	public String getDemoteRank(String rank) {
		if (CacheManager.getRank(rank) != null) {
			return CacheManager.getRank(rank).getDemoteRank();
		}
		return null;
		// String value = users.getRanksConfigFieldString(users.getRankIgnoreCase(rank), "level.demote");
		// return value;
	}
	
	public boolean setDemoteRank(String rank, String new_value) {
		if (CacheManager.getRank(rank) != null) {
			CacheManager.getRank(rank).setDemoteRank(new_value);
			return true;
		}
		return false;
		// boolean value = users.setRanksConfigFieldString(users.getRankIgnoreCase(rank), "level.demote", new_value);
		// return value;
	}
	
	public boolean createRank(String rank) {
		boolean value = users.createRank(rank);
		return value;
	}
	
	public boolean deleteRank(String rank) {
		boolean value = users.deleteRank(rank);
		return value;
	}
	
	public boolean addBuyableRank(String rank, String buyable_rank) {
		boolean value = users.addBuyableRank(rank, buyable_rank);
		return value;
	}
	
	public boolean delBuyableRank(String rank, String buyable_rank) {
		boolean value = users.delBuyableRank(rank, buyable_rank);
		return value;
	}
	
	public boolean setRankBuyCost(String rank, int cost) {
		return users.setBuyCost(rank, String.valueOf(cost));
	}
	
	public List<String> getSubranks(Player player) {
		return users.getSubranks(player.getName());
	}
	
	public boolean addSubrank(Player player, String rank) {
		return users.addSubrank(player.getName(), rank);
	}
	
	public boolean removeSubrank(Player player, String rank) {
		return users.removeSubrank(player.getName(), rank);
	}
	
	public String getPlayerSubrankPrefixes(Player player) {
		return users.getSubrankprefixes(player);
	}
	
	public String getPlayerSubrankSuffixes(Player player) {
		return users.getSubranksuffixes(player);
	}

	public boolean removePermission(Player player, String permission) {
		return users.delPlayerPermission(player.getName(), permission);
	}

	public boolean addPermission(Player player, String permission) {
		return users.addPlayerPermission(player.getName(), permission);
	}
}