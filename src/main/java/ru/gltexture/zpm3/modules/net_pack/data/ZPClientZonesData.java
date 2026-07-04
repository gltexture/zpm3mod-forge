package ru.gltexture.zpm3.modules.net_pack.data;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3i;

import java.util.*;

public class ZPClientZonesData {
    @OnlyIn(Dist.CLIENT)
    public static Map<String, ZoneData> zoneDataList = new HashMap<>();

    public record ZoneData(String id, Vector3i min, Vector3i max, String flags) { }
}
