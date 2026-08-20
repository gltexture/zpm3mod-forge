# Addon Example

This section provides a small example of a ZPM3 addon. It intentionally combines several API features in one project so that you can see how the different parts of the addon API work.


> **Note:** The ZPM3 dependency setup is omitted here. It is described in [Addon Development](addon-development.md).

---

## Main Addon Class

The addon starts as a completely normal Forge mod:

```java
@Mod(TestZP3addon.MODID)
public class TestZP3addon implements IZPAddonEntry {
    public static final String MODID = "testzp3addon";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Item> TEST_PISTOL = ITEMS.register("test_pistol", () ->
            new ZPGunPistol(
                    new Item.Properties(),
                    new ZPBaseGun.GunProperties(
                            net.minecraft.world.item.Items.DIAMOND,
                            ZPBaseGun.GunProperties.HeldType.PISTOL
                    )
                            .setDamage(4)
                            .setDurability(280)
                            .setInaccuracy(1.8f)
                            .setMaxAmmo(100)
                            .setReloadTime(40)
                            .setShootCooldown(1)
                            .setClientRecoil(1.0f)
            )
    );

    public TestZP3addon() {
        ZombiePlague3.RegisterMeAsAddon(this);

        this.registerConfig("Test", CustomConfig.class);

        final IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ZPUtility.sides().onlyClient(() -> {
            ZPRenderHooksManager.INSTANCE.addItemRendering1PersonHook(
                    TEST_PISTOL,
                    ZPDefaultGunRenderers.defaultPistolRenderer
            );
        });
    }

    @Override
    public @NotNull IZPAddonImpl ZP3AddonImpl() {
        return new AddonZP3Impl();
    }
}
```

### What happens here?

The important parts for ZPM3 are:

```java
public class TestZP3addon implements IZPAddonEntry
```

The Forge mod class implements `IZPAddonEntry`, allowing ZPM3 to treat it as an addon.

The addon then registers itself manually:

```java
ZombiePlague3.RegisterMeAsAddon(this);
```

This is currently required because ZPM3 does not automatically discover addon mods.

The custom configuration is registered as well:

```java
this.registerConfig("Test", CustomConfig.class);
```

The rest of the class uses standard Forge mechanisms such as `DeferredRegister` for registering items and blocks.

---

## Custom Firearm

The example registers a ZPM3 pistol using the normal Forge `DeferredRegister`:

```java
public static final RegistryObject<Item> TEST_PISTOL = ITEMS.register("test_pistol", () ->
        new ZPGunPistol(
                new Item.Properties(),
                new ZPBaseGun.GunProperties(
                        Items.DIAMOND,
                        ZPBaseGun.GunProperties.HeldType.PISTOL
                )
                        .setDamage(4)
                        .setDurability(280)
                        .setInaccuracy(1.8f)
                        .setMaxAmmo(100)
                        .setReloadTime(40)
                        .setShootCooldown(1)
                        .setClientRecoil(1.0f)
        )
);
```

For demonstration purposes, **diamonds are used as ammunition**.

The `GunProperties` object defines the weapon's basic characteristics:

* damage;
* durability;
* inaccuracy;
* magazine capacity;
* reload time;
* shooting cooldown;
* client-side recoil.

The important point is that the weapon itself is still registered using the **standard Forge registry system**.

---

## ⚠️ Firearm Rendering

There is currently one important ZPM3-specific requirement for custom firearms.

After registering a firearm, its first-person renderer must be registered **at an early initialization stage**:

```java
ZPUtility.sides().onlyClient(() -> {
    ZPRenderHooksManager.INSTANCE.addItemRendering1PersonHook(
            TEST_PISTOL,
            ZPDefaultGunRenderers.defaultPistolRenderer
    );
});
```

This connects the item with the ZPM3 pistol renderer.

The method may appear as **deprecated** in the IDE. This is currently expected.

> ⚠️ **Do not ignore this requirement when creating custom firearms.**
>
> The current renderer registration API is temporary. A cleaner public API for custom firearm rendering is planned for a future ZPM3 version.

The renderer should be registered **immediately after the item registration**, rather than waiting until a later lifecycle stage.

---

## Custom Configuration

The addon can define its own configuration:

```java
public class CustomConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(
            description = "TEST1"
    )
    public static final ZPConfig_BOOL TEST1 =
            new ZPConfig_BOOL(true);

    @ZPVarDefinition(
            description = "TEST2"
    )
    public static final ZPConfig_BOOL TEST2 =
            new ZPConfig_BOOL(true);
}
```

The configuration implements `ZPConfigConstantsClass` and contains statically defined configuration variables.

In this example, two boolean variables are created.

The configuration is then registered in the addon constructor:

```java
this.registerConfig("Test", CustomConfig.class);
```

ZPM3 will process the configuration and create its corresponding configuration data in the ZPM3 configuration directory.

---

# ZPM3 Addon Implementation

The `IZPAddonEntry` implementation must return an `IZPAddonImpl`:

```java
@Override
public @NotNull IZPAddonImpl ZP3AddonImpl() {
    return new AddonZP3Impl();
}
```

The actual ZPM3 integration is implemented in `AddonZP3Impl`:

```java
public class AddonZP3Impl implements IZPAddonImpl {
    ...
}
```

This class contains the addon lifecycle methods.

---

## Network Accessors

The example defines two network accessors:

```java
public static final ZPNetDataIntAccessor CURSE =
        new ZPNetDataIntAccessor(
                ResourceLocation.fromNamespaceAndPath(
                        TestZP3addon.MODID,
                        "curse"
                )
        );

public static final ZPNetDataIntAccessor TEST_SOMETHING =
        new ZPNetDataIntAccessor(
                ResourceLocation.fromNamespaceAndPath(
                        TestZP3addon.MODID,
                        "test_static2"
                )
        );
```

The first accessor is attached to entities:

```java
iAddonInitContext.defineNetAccessorOnEntity(
        LivingEntity.class,
        AddonZP3Impl.CURSE
);
```

This creates a ZPM3-managed synchronized value associated with `LivingEntity` instances.

The second accessor is registered as a server-side global value:

```java
iAddonInitContext.defineStaticNetAccessor_ForServer(
        AddonZP3Impl.TEST_SOMETHING,
        new ZPNetDataInt(888)
);
```

---

## Lifecycle Integration

The example uses several lifecycle features.

### Client Setup

```java
@Override
public void clientSetup(
        @NotNull IAddonClientSetupContext context
) {
    context.registerImGuiInterface(TestImGUIInterface::new);
}
```

A custom ImGui interface is registered with ZPM3.

### PreInit

```java
@Override
public void preInitialize(
        @NotNull IAddonPreInitContext context
) {
    context.registerZoneFlag("TESTADDONFLAG");
    context.registerZoneIntVar("TESTINTVAR", 1, 0, 10);
}
```

The addon registers:

* a custom zone flag;
* a custom integer zone variable.

### Init

```java
@Override
public void initialize(
        @NotNull IAddonInitContext context
) {
    context.defineNetAccessorOnEntity(
            LivingEntity.class,
            AddonZP3Impl.CURSE
    );

    context.defineStaticNetAccessor_ForServer(
            AddonZP3Impl.TEST_SOMETHING,
            new ZPNetDataInt(888)
    );

    context.registerZP3EventHandlerClass(
            TestAddonZP3Events.class
    );
}
```

### PostInit

The example does not need any post-initialization logic:

```java
@Override
public void postInitialize(
        @NotNull IAddonPostInitContext context
) {
}
```

---

# Custom ImGui Interface

The example also demonstrates the optional ImGui integration:

```java
public class TestImGUIInterface implements IZPImGuiInterface {

    @Override
    public void drawGui(
            @NotNull Window window,
            @NotNull Input input
    ) {
        ImGui.showDemoWindow();
    }
}
```

The interface simply displays the standard ImGui demo window.

It is registered during `clientSetup`:

```java
context.registerImGuiInterface(TestImGUIInterface::new);
```

This is only an example. A real addon can use the same mechanism to implement its own debugging tools, configuration interfaces, editors, or other custom UI.

---

# Custom ZPM3 Event

Finally, the example registers an event handler:

```java
public class TestAddonZP3Events implements ZP3EventHandlerClass {

    @ZombiePlagueEvent
    public static void onSomething(
            ZPEventBus_Guns.GunShotEvent event
    ) {
        System.out.println("SHOT!");
    }
}
```

The handler is registered during `initialize`:

```java
context.registerZP3EventHandlerClass(
        TestAddonZP3Events.class
);
```

Whenever a ZPM3 gun fires and the corresponding event is dispatched, the method will be called.

The important architectural principle is that the addon itself remains a **normal Forge mod**, while `IZPAddonEntry` and `IZPAddonImpl` provide the bridge into ZPM3-specific functionality.

---

![{6850C625-2B25-4F08-9469-672B1A16C1F8}.png](pictures/%7B6850C625-2B25-4F08-9469-672B1A16C1F8%7D.png)
![{5C5E49DC-9BA5-4E03-8DBC-6C31C5AB1A59}.png](pictures/%7B5C5E49DC-9BA5-4E03-8DBC-6C31C5AB1A59%7D.png)
![{4726221D-6F57-43E0-8E0C-C992C6FF4296}.png](pictures/%7B4726221D-6F57-43E0-8E0C-C992C6FF4296%7D.png)
![{C9E2E5CE-66EE-4C52-807B-36C97FA2DB5A}.png](pictures/%7BC9E2E5CE-66EE-4C52-807B-36C97FA2DB5A%7D.png)
![{D9194B65-6A0E-4450-A2C6-76B42889C9DA}.png](pictures/%7BD9194B65-6A0E-4450-A2C6-76B42889C9DA%7D.png)
![{5900A044-EBAB-43B1-942C-4E8F6204EB16}.png](pictures/%7B5900A044-EBAB-43B1-942C-4E8F6204EB16%7D.png)
