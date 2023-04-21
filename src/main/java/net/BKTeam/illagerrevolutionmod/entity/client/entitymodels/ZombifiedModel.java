package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.ZombifiedEntity;
import software.bernie.geckolib.model.GeoModel;

public class ZombifiedModel<I extends Monster> extends GeoModel<ZombifiedEntity> {

    private static final ResourceLocation MODEL=new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "geo/zombified_illager.geo.json");

    protected static final ResourceLocation ANIMATION_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "animations/zombified_entity.animation.json");

    @Override
    public ResourceLocation getModelResource(ZombifiedEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ZombifiedEntity animatable) {
        if(animatable.isHasSoul()){
            return new ResourceLocation(IllagerRevolutionMod.MOD_ID,
                    "textures/entity/zombified/zombified_"+animatable.getnameSoul()+".png");
        }
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID,
                "textures/entity/zombified/zombified_pillager.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ZombifiedEntity animatable) {
        return ANIMATION_RESLOC;
    }
}
