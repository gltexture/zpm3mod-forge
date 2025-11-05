package ru.gltexture.zpm3.engine.service;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import ru.gltexture.zpm3.engine.core.ZombiePlague3;

import java.io.*;
import java.util.stream.Collectors;

public final class ZPUtility {
    private static final ZPUtility instance = new ZPUtility();

    private final Blocks blocks;
    private final Files files;
    private final Sides sides;
    private final Sounds sounds;
    private final Client client;
    private final Math math;
    private final MEntity entity;

    public ZPUtility() {
        this.blocks = new Blocks();
        this.files = new Files();
        this.sides = new Sides();
        this.sounds = new Sounds();
        this.client = new Client();
        this.math = new Math();
        this.entity = new MEntity();
    }

    public static Blocks blocks() {
        return ZPUtility.instance.blocks;
    }

    public static Files files() {
        return ZPUtility.instance.files;
    }

    public static Sides sides() {
        return ZPUtility.instance.sides;
    }

    public static Sounds sounds() {
        return ZPUtility.instance.sounds;
    }

    public static Client client() {
        return ZPUtility.instance.client;
    }

    public static Math math() {
        return ZPUtility.instance.math;
    }

    public static MEntity entity() {
        return ZPUtility.instance.entity;
    }

    public static boolean isDataGen() {
        return Boolean.getBoolean("zpm3.datagen");
    }

    public static final class MEntity {
        private MEntity() {
        }

        public int consumeItemFromInventory(@NotNull Inventory inventory, @NotNull Item item, int amount) {
            int toRemove = amount;
            int removed = 0;

            for (int i = 0; i < inventory.items.size(); i++) {
                ItemStack stack = inventory.items.get(i);
                if (stack.getItem().equals(item)) {
                    int stackSize = java.lang.Math.min(stack.getCount(), toRemove);
                    inventory.removeItem(i, stackSize);
                    removed += stackSize;
                    toRemove -= stackSize;
                    if (toRemove <= 0) {
                        break;
                    }
                }
            }

            return removed;
        }


        public boolean isCollidingWithBlock(@NotNull Entity entity, @NotNull Block targetBlock) {
            AABB box = entity.getBoundingBox();

            final float s = 1.0e-7f;

            BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
            BlockPos max = BlockPos.containing(box.maxX - s, box.maxY - s, box.maxZ - s);

            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                BlockState state = entity.level().getBlockState(pos);

                if (state.is(targetBlock)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static final class Blocks {
        private Blocks() {
        }

        public boolean isRainingOnBlock(Level level, BlockPos pos) {
            if (!level.isRaining()) {
                return false;
            }

            Biome biome = level.getBiome(pos).value();
            if (biome.getPrecipitationAt(pos) != Biome.Precipitation.RAIN) {
                return false;
            }

            if (!level.canSeeSky(pos.above())) {
                return false;
            }

            return level.isRainingAt(pos.above());
        }

        public BlockState copyProperties(@NotNull BlockState sourceState, @NotNull BlockState newState) {
            for (Property<?> property : sourceState.getProperties()) {
                if (newState.hasProperty(property)) {
                    newState = Blocks.copyProperty(sourceState, newState, property);
                }
            }

            return newState;
        }

        @SuppressWarnings("unchecked")
        private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<?> property) {
            return to.setValue((Property<T>) property, (T) from.getValue(property));
        }
    }

    public static final class Math {
        private Math() {
        }

        public Vec3i fromVec3(@NotNull Vec3 vec3) {
            return new Vec3i(Mth.floor(vec3.x), Mth.floor(vec3.y), Mth.floor(vec3.z));
        }
    }

    public static final class Files {
        private Files() {
        }

        public String readTextFromJar(ZPPath path) throws IOException {
            try (InputStream input = ZombiePlague3.class.getClassLoader().getResourceAsStream(path.getFullPath())) {
                if (input == null) {
                    throw new FileNotFoundException("Resource not found: " + path);
                }
                return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
            }
        }
    }

    public static final class Sounds {
        private Sounds() {
        }

        public void stop(SoundInstance soundInstance) {
            Minecraft.getInstance().getSoundManager().stop(soundInstance);
        }

        public void playDelayer(SoundInstance soundInstance, int delay) {
            Minecraft.getInstance().getSoundManager().playDelayed(soundInstance, delay);
        }

        public void play(SoundInstance soundInstance) {
            Minecraft.getInstance().getSoundManager().play(soundInstance);
        }

        public boolean isActive(SoundInstance soundInstance) {
            return Minecraft.getInstance().getSoundManager().isActive(soundInstance);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static final class Client {
        public void ifClientLevelValid(Runnable runnable) {
            if (Minecraft.getInstance().level != null) {
                runnable.run();
            }
        }
    }

    public static final class Sides {
        private Sides() {
        }

        public void onlyClient(Runnable runnable) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> runnable);
        }

        // WARN ONLY DEDICATED!!!!!!!!!!!!!!!!!!!!!!!!!
        public void onlyDedicatedServer(Runnable runnable) {
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> runnable);
        }
    }
}
