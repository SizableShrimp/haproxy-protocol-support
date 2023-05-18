package me.sizableshrimp.proxy_protocol_support;

import me.sizableshrimp.proxy_protocol_support.config.CIDRMatcher;
import me.sizableshrimp.proxy_protocol_support.config.CommonConfig;
import me.sizableshrimp.proxy_protocol_support.config.TCPShieldIntegration;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod(modid = ProxyProtocolSupportMod.MODID, name = ProxyProtocolSupportMod.NAME, useMetadata = true, serverSideOnly = true, acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber(modid = ProxyProtocolSupportMod.MODID)
public class ProxyProtocolSupportMod {
    public static final String MODID = "proxy_protocol_support";
    public static final String NAME = "Proxy Protocol Support";
    public static final Logger LOGGER = LogManager.getLogger();

    private static boolean enabled = false;
    private static final List<CIDRMatcher> whitelistedIPs = new ArrayList<>();
    public static final List<CIDRMatcher> whitelistedIPsView = Collections.unmodifiableList(whitelistedIPs);

    public ProxyProtocolSupportMod() {
        ConfigManager.sync(MODID, Config.Type.INSTANCE);

        loadConfig();
    }

    @SubscribeEvent
    static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (MODID.equals(event.getModID())) {
            loadConfig();
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static List<CIDRMatcher> getWhitelistedIPs() {
        return whitelistedIPsView;
    }

    private static void loadConfig() {
        if (FMLLaunchHandler.side() != Side.SERVER)
            return;

        if (!CommonConfig.enableProxyProtocol) {
            LOGGER.info("Proxy Protocol disabled!");
            return;
        }

        enabled = true;
        LOGGER.info("Proxy Protocol enabled!");

        for (String ip : CommonConfig.whitelistedIPs) {
            whitelistedIPs.add(new CIDRMatcher(ip));
        }

        if (CommonConfig.whitelistTCPShieldServers) {
            LOGGER.info("TCPShield integration enabled!");

            try {
                whitelistedIPs.addAll(TCPShieldIntegration.getWhitelistedIPs());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        LOGGER.debug("Using {} whitelisted IPs: {}", whitelistedIPs.size(), whitelistedIPs);
    }
}
