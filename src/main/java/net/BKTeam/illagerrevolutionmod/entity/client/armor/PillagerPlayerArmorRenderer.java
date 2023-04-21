package net.BKTeam.illagerrevolutionmod.entity.client.armor;

import net.BKTeam.illagerrevolutionmod.item.custom.ArmorPillagerVestItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PillagerPlayerArmorRenderer extends GeoArmorRenderer<ArmorPillagerVestItem> {

    public PillagerPlayerArmorRenderer() {
        super(new PillagerPlayerArmorModel());
    }
}
