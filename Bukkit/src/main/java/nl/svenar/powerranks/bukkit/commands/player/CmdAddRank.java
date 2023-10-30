package nl.svenar.powerranks.bukkit.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import nl.svenar.powerranks.bukkit.PowerRanks;
import nl.svenar.powerranks.bukkit.commands.PowerBaseCommand;
import nl.svenar.powerranks.bukkit.util.Util;
import nl.svenar.powerranks.common.structure.PRPlayer;
import nl.svenar.powerranks.common.structure.PRPlayerRank;
import nl.svenar.powerranks.common.structure.PRRank;

@CommandAlias("%powerrankscommand")
@Description("Add a rank to a player while keeping their other ranks")
public class CmdAddRank extends PowerBaseCommand {

    public CmdAddRank(PowerRanks plugin) {
        super(plugin);
    }

    @Subcommand("addrank")
    @CommandPermission("powerranks.cmd.addrank")
    @Syntax("<player> <rank> [tags]")
    @CommandCompletion("@prplayers @prranks expires:|expires:1h|expires:30d|expires:1y|world:|world:world|world:world_nether|world:world_the_end")
    public void onAddrank(CommandSender sender, String targetName, String rankname, @Optional String... tags) {
        PRPlayer prplayer = getPRPlayer(targetName);
        PRRank prrank = getPRRank(rankname);

        if (prplayer == null) {
            sendMessage(sender, "player-not-found", ImmutableMap.of( //
                    "target", targetName //
            ));
            return;
        }

        if (prrank == null) {
            sendMessage(sender, "rank-not-found", ImmutableMap.of( //
                    "rank", rankname //
            ));
            return;
        }

        if (numTagsStartsWith(tags, "expires") > 1) {
            sendMessage(sender, "player-rank-tag-failed-multiple-expires");
            return;
        }

        if (numTagsEndsWith(tags, ":") > 0) {
            sendMessage(sender, "player-rank-tag-failed-no-value");
            return;
        }

        for (PRPlayerRank prplayerrank : prplayer.getRanks()) {
            if (prplayerrank.getName().equalsIgnoreCase(prrank.getName())) {
                sendMessage(sender, "player-rank-add-failed-already-has-rank", ImmutableMap.of( //
                        "player", targetName,
                        "rank", prrank.getName() //
                ));
                return;
            }

        }

        prplayer.getRanks().add(new PRPlayerRank(prrank, tags));

        sendMessage(sender, "player-rank-add-success-sender", ImmutableMap.of( //
                "player", targetName,
                "rank", prrank.getName() //
        ));

        Player target = Util.getPlayerByName(targetName);
        if (target != null) {
            sendMessage(target, "player-rank-add-success-receiver", ImmutableMap.of( //
                "player", sender.getName(),
                "rank", prrank.getName() //
        ));
        }
    }
}