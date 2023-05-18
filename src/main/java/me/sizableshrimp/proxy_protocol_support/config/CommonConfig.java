package me.sizableshrimp.proxy_protocol_support.config;

import me.sizableshrimp.proxy_protocol_support.ProxyProtocolSupportMod;
import net.minecraftforge.common.config.Config;

@Config(modid = ProxyProtocolSupportMod.MODID, name = ProxyProtocolSupportMod.MODID, type = Config.Type.INSTANCE)
public class CommonConfig {
    @Config.Comment("Whether this mod should be enabled at all")
    public static boolean enableProxyProtocol = true;
    @Config.Comment({"The list of IPs to whitelist for joining the server.",
            "Generally, this list should only be your proxy IPs.",
            "If empty, all IPs are allowed."})
    public static String[] whitelistedIPs = new String[0];
    @Config.Comment("Whether TCPShield's IPs should be automatically added to the whitelist")
    public static boolean whitelistTCPShieldServers = false;
}
