package net.BKTeam.illagerrevolutionmod.deathentitysystem.data;

import net.minecraft.world.ticks.WorldGenTickAccess;
import net.minecraftforge.event.TickEvent;

public class DeathEntityEvent {
    public static void onLivintDeathEvent(TickEvent.LevelTickEvent event){
        DeathManager.tick(event.level);
    }



}
