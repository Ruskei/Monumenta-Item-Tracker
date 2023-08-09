package com.ruskei.mmitemtracker.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin
{
    @Inject(method = "onItemPickupAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift = At.Shift.BY, by = 1))
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo ci)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity entity = client.player.world.getEntityById(packet.getEntityId());
        if (entity != null ) {
            if (entity.getType().equals(EntityType.ITEM)) {
                System.out.println(((ItemEntity) entity).getStack().getName().getString());
            }
        }
    }
}
