package net.BKTeam.illagerrevolutionmod.entity.client.armor;

import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.item.custom.ArmorIllusionerRobeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IllusionerPlayerArmorModel extends GeoModel<ArmorIllusionerRobeItem> {

    @Override
    public ResourceLocation getModelResource(ArmorIllusionerRobeItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "geo/illusioner_player_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArmorIllusionerRobeItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "textures/models/armor/illusioner_armor/illusioner_robe_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArmorIllusionerRobeItem animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "animations/illager_player_armor.animation.json");
    }
}
