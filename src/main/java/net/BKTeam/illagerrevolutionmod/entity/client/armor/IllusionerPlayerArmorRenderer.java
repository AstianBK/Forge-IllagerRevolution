package net.BKTeam.illagerrevolutionmod.entity.client.armor;

import net.BKTeam.illagerrevolutionmod.item.custom.ArmorIllusionerRobeItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class IllusionerPlayerArmorRenderer extends GeoArmorRenderer<ArmorIllusionerRobeItem> {

    public IllusionerPlayerArmorRenderer() {
        super(new IllusionerPlayerArmorModel());
    }
}
