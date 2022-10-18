package com.fallw1nd.autoattack;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AutoAttack.MODID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AutoAttack.MODID)
public class AutoAttack
{
    static final String MODID = "autoattack";

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END &&
            mc.player != null &&
            mc.player.getAttackStrengthScale(0.0F) == 1.0F)
        {
            Vec3 start = mc.player.getEyePosition();
            Vec3 addition = mc.player.getLookAngle().multiply(2.7D, 2.7D, 2.7D);

            EntityHitResult er_mob = ProjectileUtil.getEntityHitResult(
                    mc.player.level,
                    mc.player,
                    start,
                    start.add(addition),
                    mc.player.getBoundingBox().expandTowards(addition).inflate(2.7D),
                    (val) -> true
            );

            if (er_mob != null)
            {
                KeyMapping.click(Minecraft.getInstance().options.keyAttack.getKey());
            }
        }
    }
}
