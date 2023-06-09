package net.BKTeam.illagerrevolutionmod.entity.client.armor;

import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.item.custom.ArmorVindicatorJacketItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VindicatorPlayerArmorModel extends GeoModel<ArmorVindicatorJacketItem> {
    @Override
    public ResourceLocation getModelResource(ArmorVindicatorJacketItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "geo/vindicator_player_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArmorVindicatorJacketItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "textures/models/armor/vindicator_armor/vindicator_jacket_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArmorVindicatorJacketItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "animations/illager_player_armor.animation.json");
    }
}
