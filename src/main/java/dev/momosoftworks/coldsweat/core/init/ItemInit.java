package dev.momosoftworks.coldsweat.core.init;

import dev.momosoftworks.coldsweat.ColdSweat;
import dev.momosoftworks.coldsweat.common.block.*;
import dev.momosoftworks.coldsweat.common.item.*;
import dev.momosoftworks.coldsweat.core.itemgroup.ColdSweatGroup;
import dev.momosoftworks.coldsweat.util.registries.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ColdSweat.MOD_ID);

    // Items
    public static final RegistryObject<Item> WATERSKIN = ITEMS.register("waterskin", WaterskinItem::new);
    public static final RegistryObject<Item> FILLED_WATERSKIN = ITEMS.register("filled_waterskin", FilledWaterskinItem::new);
    public static final RegistryObject<Item> MINECART_INSULATION = ITEMS.register("minecart_insulation", MinecartInsulationItem::new);
    public static final RegistryObject<Item> THERMOMETER = ITEMS.register("thermometer", () ->
            new Item(new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT).rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> SOULSPRING_LAMP = ITEMS.register("soulspring_lamp", SoulspringLampItem::new);
    public static final RegistryObject<Item> FUR = ITEMS.register("fur", () ->
            new Item(new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));
    public static final RegistryObject<Item> HOGLIN_HIDE = ITEMS.register("hoglin_hide", () ->
            new Item(new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));
    public static final RegistryObject<Item> INSULATED_MINECART = ITEMS.register("insulated_minecart", () ->
            new InsulatedMinecartItem(new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT).stacksTo(1)));
    public static final RegistryObject<Item> CHAMELEON_MOLT = ITEMS.register("chameleon_molt", () ->
            new Item(new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    // Armor Items
    public static final RegistryObject<Item> HOGLIN_HEADPIECE = ITEMS.register("hoglin_headpiece", () ->
            new HoglinArmorItem(ModArmorMaterials.HOGLIN, EquipmentSlot.HEAD, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    public static final RegistryObject<Item> HOGLIN_TUNIC = ITEMS.register("hoglin_tunic", () ->
            new HoglinArmorItem(ModArmorMaterials.HOGLIN, EquipmentSlot.CHEST, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    public static final RegistryObject<Item> HOGLIN_TROUSERS = ITEMS.register("hoglin_trousers", () ->
            new HoglinArmorItem(ModArmorMaterials.HOGLIN, EquipmentSlot.LEGS, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    public static final RegistryObject<Item> HOGLIN_HOOVES = ITEMS.register("hoglin_hooves", () ->
            new HoglinArmorItem(ModArmorMaterials.HOGLIN, EquipmentSlot.FEET, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    public static final RegistryObject<Item> FUR_CAP = ITEMS.register("fur_cap", () ->
            new GoatArmorItem(ModArmorMaterials.FUR, EquipmentSlot.HEAD, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));
    public static final RegistryObject<Item> FUR_PARKA = ITEMS.register("fur_parka", () ->
            new GoatArmorItem(ModArmorMaterials.FUR, EquipmentSlot.CHEST, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));
    public static final RegistryObject<Item> FUR_PANTS = ITEMS.register("fur_pants", () ->
            new GoatArmorItem(ModArmorMaterials.FUR, EquipmentSlot.LEGS, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));
    public static final RegistryObject<Item> FUR_BOOTS = ITEMS.register("fur_boots", () ->
            new GoatArmorItem(ModArmorMaterials.FUR, EquipmentSlot.FEET, new Item.Properties().tab(ColdSweatGroup.COLD_SWEAT)));

    // Block Items
    public static final RegistryObject<BlockItem> BOILER = ITEMS.register("boiler", () -> new BlockItem(BlockInit.BOILER.get(), BoilerBlock.getItemProperties()));
    public static final RegistryObject<BlockItem> ICEBOX = ITEMS.register("icebox", () -> new BlockItem(BlockInit.ICEBOX.get(), IceboxBlock.getItemProperties()));
    public static final RegistryObject<BlockItem> SEWING_TABLE = ITEMS.register("sewing_table", () -> new BlockItem(BlockInit.SEWING_TABLE.get(), SewingTableBlock.getItemProperties()));
    public static final RegistryObject<BlockItem> HEARTH = ITEMS.register("hearth", () -> new BlockItem(BlockInit.HEARTH_BOTTOM.get(), HearthBottomBlock.getItemProperties()));
    public static final RegistryObject<BlockItem> THERMOLITH = ITEMS.register("thermolith", () -> new BlockItem(BlockInit.THERMOLITH.get(), ThermolithBlock.getItemProperties()));
    public static final RegistryObject<BlockItem> SOUL_SPROUT = ITEMS.register("soul_sprout", () -> new SoulSproutItem(BlockInit.SOUL_STALK.get(),
            SoulStalkBlock.getItemProperties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1).alwaysEat().fast().build())));

    // Spawn Eggs
    public static final RegistryObject<ForgeSpawnEggItem> CHAMELEON_SPAWN_EGG = ITEMS.register("chameleon_spawn_egg", () ->
            new ForgeSpawnEggItem(EntityInit.CHAMELEON, 0x82C841, 0x1C9170, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}