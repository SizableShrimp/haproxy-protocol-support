package me.sizableshrimp.proxy_protocol_support.mixin;

import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.net.SocketAddress;

/**
 * Adds Accessor for address
 *
 * @author PanSzelescik
 * @see NetworkManager#socketAddress
 */
@Mixin(NetworkManager.class)
public interface ProxyProtocolAddressSetter {
    @Accessor
    void setSocketAddress(SocketAddress socketAddress);
}
