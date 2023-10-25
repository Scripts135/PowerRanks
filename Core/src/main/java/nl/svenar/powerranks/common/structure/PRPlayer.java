/**
 * This file is part of PowerRanks, licensed under the MIT License.
 *
 * Copyright (c) svenar (Sven) <powerranks@svenar.nl>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

package nl.svenar.powerranks.common.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nl.svenar.powerranks.common.utils.PRCache;
import nl.svenar.powerranks.common.utils.PRUtil;

import java.util.Map.Entry;

/**
 * Structure to store player data.
 * 
 * @author svenar
 */
@JsonIgnoreProperties({"defaultRanks", "effectivePermissions"})
public class PRPlayer {

    // Storage
    private UUID uuid;
    private String name;
    private String nickname;
    private ArrayList<PRPlayerRank> ranks;
    private ArrayList<PRPermission> permissions;
    private ArrayList<String> usertags;
    private long playtime;

    public PRPlayer() {
        this.name = "";
        this.nickname = "";
        this.ranks = new ArrayList<PRPlayerRank>();
        this.permissions = new ArrayList<PRPermission>();
        this.usertags = new ArrayList<String>();
        this.playtime = 0L;
    }

    /**
     * Get the unique id of this player
     * 
     * @return Java UUID object for this player
     */
    public UUID getUUID() {
        return this.uuid;
    }

    /**
     * Set the unique id of this player
     * 
     * @param uuid
     */
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the name of this player
     * 
     * @return String name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this player
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the nickname of this player
     * 
     * @return String nickname of the player
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Set the nickname of this player
     * 
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Get the rank of this player
     * 
     * @return String rank of the player
     */
    public List<PRPlayerRank> getRanks() {
        return this.ranks;
    }

    /**
     * Get the default ranks of this player
     * @return List of PRRank instances
     */
    public List<PRRank> getDefaultRanks() {
        List<PRRank> defaultRanks = new ArrayList<PRRank>();
        for (PRPlayerRank rank : this.ranks) {
            PRRank prRank = PRCache.getRank(rank.getName());
            if (prRank != null) {
                if (prRank.isDefault()) {
                    defaultRanks.add(prRank);
                }
            }
        }
        return defaultRanks;
    }

    /**
     * Set the ranks of this player
     * 
     * @param ranks
     */
    public void setRanks(ArrayList<PRPlayerRank> ranks) {
        this.ranks = ranks;
    }

    /**
     * Remove all ranks and set a single one on this player
     * 
     * @param rank
     */
    public void setRank(PRPlayerRank rank) {
        this.ranks.clear();
        this.ranks.add(rank);
    }

    /**
     * Add a rank on this player
     * 
     * @param rank
     */
    public void addRank(PRPlayerRank rank) {
        if (!this.ranks.contains(rank)) {
            this.ranks.add(rank);
        }
    }

    /**
     * Remove a rank on this player
     * 
     * @param rank
     */
    public void removeRank(PRPlayerRank rank) {
        if (this.ranks.contains(rank)) {
            this.ranks.remove(rank);
        }
    }

    /**
     * Check if this player has a specific rank
     * 
     * @param rank
     * @return true if this player has that rank, false otherwise
     */
    public boolean hasRank(String rankName) {
        for (PRPlayerRank rank : this.ranks) {
            if (rank.getName().equalsIgnoreCase(rankName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of all stored permissions in this player
     * 
     * @return Java ArrayList with all PRPermission instances
     */
    public ArrayList<PRPermission> getPermissions() {
        return this.permissions;
    }

    /**
     * Overwrite all stored permissions with the provided Java ArrayList
     * 
     * @param permissions
     */
    public void setPermissions(ArrayList<PRPermission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Add a PRPermission instance to this player
     * 
     * @param permission
     */
    public void addPermission(PRPermission permission) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<PRPermission>();
        }

        this.permissions.add(permission);
    }

    /**
     * Remove a PRPermission instance from this player
     * 
     * @param permission
     */
    public void removePermission(PRPermission permission) {
        if (!this.permissions.contains(permission)) {
            return;
        }

        this.permissions.remove(permission);
    }

    /**
     * Get a permission by a String permission node (Eg. permission.node.123)
     * 
     * @param name
     * @return PRPermission instance for the provided node, null if no PRPermission
     *         instance was found
     */
    public PRPermission getPermission(String name) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<PRPermission>();
        }

        for (PRPermission permission : this.permissions) {
            if (permission.getName().equals(name)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * Get all effective permissions for this player
     * @return List of PRPermission instances
     */
    public List<PRPermission> getEffectivePermissions() {
        ArrayList<PRPermission> permissions = new ArrayList<PRPermission>();

        permissions.addAll(this.getPermissions());

        List<PRRank> playerRanks = new ArrayList<>();
        for (PRPlayerRank playerRank : this.getRanks()) {
            if (!playerRank.isDisabled()) {
                PRRank rank = PRCache.getRank(playerRank.getName());
                if (rank != null) {
                    playerRanks.add(rank);
                }
            }
        }

        List<PRRank> effectiveRanks = new ArrayList<PRRank>();

        if (Objects.nonNull(playerRanks)) {
            effectiveRanks.addAll(playerRanks);

            for (PRRank playerRank : playerRanks) {
                for (String inheritance : playerRank.getInheritances()) {
                    PRRank inheritanceRank = PRCache.getRank(inheritance);
                    if (inheritanceRank != null) {
                        effectiveRanks.add(inheritanceRank);
                    }
                }
            }
        }

        effectiveRanks.removeIf(Objects::isNull);
        PRUtil.sortRanksByWeight(effectiveRanks);

        for (PRRank effectiveRank : effectiveRanks) {
            if (Objects.nonNull(effectiveRank)) {
                for (PRPermission permission : effectiveRank.getPermissions()) {

                    PRPermission permissionToRemove = null;
                    for (PRPermission existingPermission : permissions) {
                        if (permission.getName().equals(existingPermission.getName())) {
                            permissionToRemove = existingPermission;
                            break;
                        }
                    }
                    if (Objects.nonNull(permissionToRemove)) {
                        permissions.remove(permissionToRemove);
                    }

                    permissions.add(permission);
                }
            }
        }

        return permissions;
    }

    /**
     * Get the playtime of this player
     * 
     * @return Long playtime of the player
     */
    public long getPlaytime() {
        return this.playtime;
    }

    /**
     * Set the playtime of this player
     * 
     * @param playtime
     */
    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    /**
     * Get a list with all stored usertags
     * 
     * @return Java ArrayList with all stored usertag
     */
    public ArrayList<String> getUsertags() {
        return this.usertags;
    }

    /**
     * Overwrite all stored usertags with the provided Java ArrayList
     * 
     * @param usertags
     */
    public void setUsertags(ArrayList<String> usertags) {
        this.usertags = usertags;
    }

    /**
     * Set the usertag of the player
     * 
     * @param usertag
     */
    public void setUsertag(String usertag) {
        this.usertags = new ArrayList<String>();

        this.usertags.add(usertag);
    }

    /**
     * Add a usertag to the player
     * 
     * @param usertag
     */
    public void addUsertag(String usertag) {
        if (Objects.isNull(this.usertags)) {
            this.usertags = new ArrayList<String>();
        }

        if (!this.usertags.contains(usertag)) {
            this.usertags.add(usertag);
        }
    }

    /**
     * Remove a usertag from this player
     * 
     * @param usertag
     */
    public void removeUsertag(String usertag) {
        if (!this.usertags.contains(usertag)) {
            return;
        }

        this.usertags.remove(usertag);
    }

    /**
     * Check if this player instance has a specific usertag
     * 
     * @param usertag
     * @return true if this player has that usertag, false otherwise
     */
    public boolean hasUsertag(String usertag) {
        if (Objects.isNull(this.usertags)) {
            this.usertags = new ArrayList<String>();
        }

        for (String tag : this.usertags) {
            if (tag.equals(usertag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String output = "uuid:" + uuid.toString()
                + ", name:" + name
                + ", ranks:[<<RANKS>>]"
                + ", permissions:[<<PERMISSIONS>>]"
                + ", usertags:[<<USERTAGS>>]";

        String ranks = "";
        for (PRPlayerRank rank : getRanks()) {
            ranks += rank.getName() + " (";
            for (Entry<String, Object> entry : rank.getTags().entrySet()) {
                ranks += entry.getKey() + "=" + entry.getValue() + ",";
            }
            ranks = rank.getTags().size() > 0 ? ranks.substring(0, ranks.length() - 1) : "";
            ranks += ");";
        }
        ranks = ranks.length() > 0 ? ranks.substring(0, ranks.length() - 1) : "";

        String permissions = "";
        for (PRPermission permission : getPermissions()) {
            permissions += permission.toString() + ";";
        }
        permissions = permissions.length() > 0 ? permissions.substring(0, permissions.length() - 1) : "";

        String usertags = "";
        for (String usertag : getUsertags()) {
            usertags += usertag + ";";
        }
        usertags = usertags.length() > 0 ? usertags.substring(0, usertags.length() - 1) : "";

        output = output.replaceAll("<<RANKS>>", ranks);
        output = output.replaceAll("<<PERMISSIONS>>", permissions);
        output = output.replaceAll("<<USERTAGS>>", usertags);

        return output;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + toString().hashCode();
        return result;
    }

    public void updateTags(String worldName) {
        for (PRPlayerRank prPlayerRank : this.getRanks()) {
            if (prPlayerRank.getTags().containsKey("worlds")) {

                boolean playerInWorld = false;
                for (Object worldObject : (List<?>) prPlayerRank.getTags().get("worlds")) {
                    if (worldObject instanceof String) {
                        String world = (String) worldObject;
                        if (world.equalsIgnoreCase(worldName)) {
                            playerInWorld = true;
                            break;
                        }
                    }
                }
                prPlayerRank.setDisabled(!playerInWorld);
            }
        }
    }
}