package de.felix_kurz.premiumcustomspawneggs.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import de.felix_kurz.premiumcustomspawneggs.items.remote.MobRemote;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class CustomMob {

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public PathfinderMob entity;

    public UUID owner;

    public String id;
    public String name;
    public String type;
    public int health;
    public float speed;
    public boolean multiRemote;
    public boolean dropOnDeath;
    public boolean dropOnExplosion;
    public int explosionRadius;
    public int explosionDamage;
    public String explosionPotion;
    public int explosionPotionDuration;
    public int explosionPotionAmplifier;
    public double exlosionPower;
    public int lavaRadius;
    public double explosionBreakBlockChance;
    public double explosionDropBlockChance;
    public int explosionTimer;
    public boolean randomStroll;
    public float strollSpeed;
    public List<String> attackEntities;
    public int attackDamage;
    public int attackSpeed;
    public float walkToTargetSpeed;
    public int attackTriggerRange;
    public boolean multiAttack;
    public List<String> breakBlocks;
    public int breakDamage;
    public int breakSpeed;
    public float walkToBlockSpeed;
    public int breakTriggerRange;
    public boolean multiBreak;
    public boolean prioritizeBlocks;

    public BukkitTask r;

    public static HashMap<Integer, CustomMob> mobs = new HashMap<>();

    private final static HashMap<Block, Integer> blocks = new HashMap<>();

    public CustomMob(UUID owner, String id, String name, String type, int health, float speed, boolean multiRemote, boolean dropOnDeath,
                     boolean dropOnExplosion, int explosionRadius, int explosionDamage, String explosionPotion, int explosionPotionDuration, int explosionPotionAmplifier, double explosionPower, int lavaRadius,
                     double explosionBreakBlocksChance, double explosionDropBlockChance, int explosionTimer, boolean randomStroll, float strollSpeed, String attackEntities, int attackDamage, int attackSpeed,
                     float walkToTargetSpeed, int attackTriggerRange, boolean multiAttack, String breakBlocks, int breakDamage, int breakSpeed, float walkToBlockSpeed, int breakTriggerRange,
                     boolean multiBreak, boolean prioritizeBlocks) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.multiRemote = multiRemote;
        this.dropOnDeath = dropOnDeath;
        this.dropOnExplosion = dropOnExplosion;
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
        this.explosionPotion = explosionPotion;
        this.explosionPotionDuration = explosionPotionDuration;
        this.explosionPotionAmplifier = explosionPotionAmplifier;
        this.exlosionPower = explosionPower;
        this.lavaRadius = lavaRadius;
        this.explosionBreakBlockChance = explosionBreakBlocksChance;
        this.explosionDropBlockChance = explosionDropBlockChance;
        this.explosionTimer = explosionTimer;
        this.randomStroll = randomStroll;
        this.strollSpeed = strollSpeed;
        this.attackEntities = new ArrayList<>(Arrays.asList(attackEntities.toUpperCase().split(",")));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.walkToTargetSpeed = walkToTargetSpeed;
        this.attackTriggerRange = attackTriggerRange;
        this.multiAttack = multiAttack;
        this.breakBlocks = new ArrayList<>(Arrays.asList(breakBlocks.toUpperCase().split(",")));
        this.breakDamage = breakDamage;
        this.breakSpeed = breakSpeed;
        this.walkToBlockSpeed = walkToBlockSpeed;
        this.breakTriggerRange = breakTriggerRange;
        this.multiBreak = multiBreak;
        this.prioritizeBlocks = prioritizeBlocks;
    }

    public void spawnEntity(Location l) {
        Player p = Bukkit.getPlayer(owner);
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        entity = new PathfinderMob(EntTypes.valueOf(type.toUpperCase()).type, level) {
            @Override
            public float getWalkTargetValue(BlockPos blockposition) {
                return super.getWalkTargetValue(blockposition);
            }
        };
        entity.setPos(l.getX(), l.getY(), l.getZ());
        level.addFreshEntity(entity);

        entity.getBukkitEntity().setCustomName(name);
        ((org.bukkit.entity.LivingEntity) entity.getBukkitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        entity.setHealth(health);

        Tuple<Integer, Boolean> slotAndSpace = MobRemote.getRemoteSlotAndHasSpace(p.getInventory(), id);

        if (slotAndSpace.getA() == -1 || !multiRemote) {
            if (slotAndSpace.getB()) p.getInventory().addItem(new MobRemote(new int[]{entity.getId()}, id).item);
            else {
                p.getWorld().dropItem(p.getLocation(), new MobRemote(new int[]{entity.getId()}, id).item);
            }
        } else {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(p.getInventory().getItem(slotAndSpace.getA()));
            CompoundTag itemTag = nmsItem.getTag();
            int[] mobIDs = itemTag.getIntArray("pcse_con_mobs");
            int[] newMobIDs = Arrays.copyOf(mobIDs, mobIDs.length + 1);
            newMobIDs[mobIDs.length] = entity.getId();
            itemTag.putIntArray("pcse_con_mobs", newMobIDs);
            nmsItem.setTag(itemTag);
            p.getInventory().setItem(slotAndSpace.getA(), CraftItemStack.asBukkitCopy(nmsItem));
        }
        mobs.put(entity.getId(), this);

        if (!entity.getBukkitEntity().getNearbyEntities(0.01, 0.01, 0.01).isEmpty()) entity.push(Math.random() / 10 - 0.05, 0.01, Math.random() / 10 - 0.05);

        if (explosionTimer > 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    explode();
                }
            }.runTaskLater(Main.getPlugin(), explosionTimer);
        }

        startBehavior();

    }

    public void startBehavior() {
        entity.goalSelector.removeAllGoals();
        if (randomStroll) entity.goalSelector.addGoal(0, new RandomStrollGoal(entity, strollSpeed));
        entity.goalSelector.addGoal(1, new FloatGoal(entity));
        if (!attackEntities.contains("NONE") || !breakBlocks.contains("NONE")) {
            r = new BukkitRunnable() {
                final int[] attackRotation = {0};
                final int[] breakRotation = {0};
                final boolean[] movesToEntity = {false};
                final int[] aID = {0};
                @Override
                public void run() {
                    if (!attackEntities.contains("NONE")) {
                        final double[] maxDistance = {attackTriggerRange + 1};
                        final double[] prio = {attackEntities.size()};
                        final boolean[] notDamaged = {true};
                        final boolean[] dmgSmth = {false};

                        entity.getBukkitEntity().getNearbyEntities(attackTriggerRange, attackTriggerRange, attackTriggerRange).forEach(e -> {
                            if (!(e instanceof LivingEntity le)) return;
                            CustomMob leMob = mobs.get(le.getEntityId());
                            if (leMob != null) {
                                if (leMob.owner.equals(owner)) return;
                            }

                            if ((attackEntities.contains(le.getType().toString()) || attackEntities.contains("ALL")) && !le.getUniqueId().equals(owner)) {
                                double distance = e.getLocation().distance(entity.getBukkitEntity().getLocation());
                                Path path = entity.getNavigation().createPath(((CraftEntity)le).getHandle(), 0);

                                if (path != null && path.canReach()) {
                                    if (distance < maxDistance[0] || attackEntities.indexOf(le.getType().toString()) < prio[0]) {
                                        entity.getNavigation().moveTo(path, walkToTargetSpeed);
                                        movesToEntity[0] = true;
                                        prio[0] = attackEntities.indexOf(le.getType().toString());
                                        maxDistance[0] = distance;
                                    }

                                    if (distance <= 1.9 && notDamaged[0] && attackRotation[0] <= 0) {
                                        le.damage(attackDamage, entity.getBukkitEntity());
                                        dmgSmth[0] = true;
                                        notDamaged[0] = multiAttack;
                                        Location l = entity.getBukkitEntity().getLocation();
                                        l.setY(l.getY() + entity.getEyeHeight());
                                        l.add(l.getDirection().multiply(.4));
                                        l.getWorld().spawnParticle(Particle.CRIT, l, 5);
                                        entity.swing(InteractionHand.MAIN_HAND);
                                    }
                                }
                            }
                        });
                        if (dmgSmth[0]) attackRotation[0] = attackSpeed;
                        attackRotation[0]--;
                    }
                    if (!prioritizeBlocks && movesToEntity[0]) return;
                    if (!breakBlocks.contains("NONE")) {
                        double maxDistance = breakTriggerRange + 1;
                        double prio = breakBlocks.size();
                        boolean notDamaged = true;
                        boolean dmgSmth = false;

                        Location l = entity.getBukkitEntity().getLocation();
                        int bx = l.getBlockX();
                        int by = l.getBlockY();
                        int bz = l.getBlockZ();

                        for (int x = bx - breakTriggerRange; x <= bx + breakTriggerRange; x++) {
                            for (int y = by + breakTriggerRange; y >= by - breakTriggerRange; y--) {
                                for (int z = bz - breakTriggerRange; z <= bz + breakTriggerRange; z++) {
                                    double distance = l.distance(new Location(l.getWorld(), x, y, z));

                                    if (distance < breakTriggerRange) {
                                        Block block = l.getWorld().getBlockAt(x,y,z);

                                        if (!breakBlocks.contains(block.getType().toString())) continue;

                                        Path path = entity.getNavigation().createPath(x + ((x - bx) > 0 ? 1 : -1), y, z + ((z - bz) > 0 ? 1 : -1), 1);

                                        if (path != null && path.canReach()) {
                                            if (distance < maxDistance || breakBlocks.indexOf(block.getType().toString()) < prio) {
                                                entity.getNavigation().moveTo(path, walkToBlockSpeed);
                                                prio = breakBlocks.indexOf(block.getType().toString());
                                                maxDistance = distance;
                                            }
                                            if (distance <= 2 && notDamaged && breakRotation[0] <= 0) {
                                                notDamaged = multiBreak;
                                                dmgSmth = true;

                                                int damage = breakDamage;
                                                if (blocks.containsKey(block)) damage = blocks.get(block) + breakDamage;
                                                blocks.put(block, damage);

                                               if (damage > 9) {
                                                   block.breakNaturally();
                                                   blocks.remove(block);
                                               } else {
                                                   PacketContainer packet2 = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                                                   packet2.getIntegers().write(0, aID[0]);
                                                   packet2.getIntegers().write(1, damage);
                                                   packet2.getBlockPositionModifier().write(0, new BlockPosition(x,y,z));
                                                   manager.broadcastServerPacket(packet2);
                                                   aID[0]++;
                                               }

                                                l.setY(l.getY() + entity.getEyeHeight());
                                                l.add(l.getDirection().multiply(.5));
                                                l.getWorld().spawnParticle(Particle.CRIT_MAGIC, l, 5);
                                                entity.swing(InteractionHand.MAIN_HAND);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (dmgSmth) breakRotation[0] = breakSpeed;
                        breakRotation[0]--;
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 5, 5);
        }
    }

    private void explode() {
        if (entity.isAlive()) {
            Location l = entity.getBukkitEntity().getLocation();
            PacketContainer packet = manager.createPacket(PacketType.Play.Server.EXPLOSION);
            packet.getDoubles().write(0, l.getX());
            packet.getDoubles().write(1, l.getY());
            packet.getDoubles().write(2, l.getZ());
            packet.getFloat().write(0, (float) explosionRadius);
            manager.broadcastServerPacket(packet);
            entity.getBukkitEntity().getNearbyEntities(explosionRadius, explosionRadius, explosionRadius).forEach(ent -> {
                if (ent instanceof org.bukkit.entity.LivingEntity ent1) {
                    if (ent1.getUniqueId().equals(owner)) return;
                    try {
                        ent1.addPotionEffect(PotionEffectType.getByName(explosionPotion).createEffect(explosionPotionDuration, explosionPotionAmplifier));
                    } catch (Exception ignore) {
                        Main.c.sendMessage(Main.PRE + "§cInvalid potion effect §b" + explosionPotion);
                    }
                    ent1.damage(explosionDamage, Bukkit.getPlayer(owner));
                    ent1.setVelocity(new Vector(ent1.getLocation().getX() - l.getX(), ent1.getLocation().getY() - l.getY() + 0.001, ent1.getLocation().getZ() - l.getZ()).normalize().multiply(exlosionPower).add(new Vector(0,0.2 * exlosionPower,0)));
                }
            });
            int bx = l.getBlockX();
            int by = l.getBlockY();
            int bz = l.getBlockZ();
            //From Synapz (https://www.spigotmc.org/members/synapz.93056/)
            for(int x = bx - explosionRadius; x <= bx + explosionRadius; x++) {
                for(int y = by + explosionRadius; y >= by - explosionRadius; y--) {
                    for(int z = bz - explosionRadius; z <= bz + explosionRadius; z++) {
                        double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                        if(distance < explosionRadius * explosionRadius) {
                            if (explosionBreakBlockChance <= Math.random()) continue;
                            if (explosionDropBlockChance > Math.random()) {
                                l.getWorld().getBlockAt(x, y, z).breakNaturally();
                            } else {
                                l.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                            }
                        }
                    }
                }
            }
            for (int x = bx - lavaRadius; x <= bx + lavaRadius; x++) {
                for (int z = bz - lavaRadius; z <= bz + lavaRadius; z++) {
                    double distance = ((bx - x) * (bx - x)) + ((bz - z) * (bz - z));
                    if (distance < lavaRadius * lavaRadius - 1) {
                        l.getWorld().getBlockAt(x, by, z).setType(Material.LAVA);
                    }
                }
            }
            entity.kill();
        }
    }

}
