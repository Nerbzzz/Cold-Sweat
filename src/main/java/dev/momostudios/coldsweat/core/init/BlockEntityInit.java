package dev.momostudios.coldsweat.core.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import dev.momostudios.coldsweat.ColdSweat;
import dev.momostudios.coldsweat.common.te.BoilerBlockEntity;
import dev.momostudios.coldsweat.common.te.HearthBlockEntity;
import dev.momostudios.coldsweat.common.te.IceboxBlockEntity;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ColdSweat.MOD_ID);

    public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER_TILE_ENTITY_TYPE =
            BLOCK_ENTITY_TYPES.register("boiler", () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, BlockInit.BOILER.get()).build(null));
    public static final RegistryObject<BlockEntityType<IceboxBlockEntity>> ICEBOX_TILE_ENTITY_TYPE =
        BLOCK_ENTITY_TYPES.register("icebox", () -> BlockEntityType.Builder.of(IceboxBlockEntity::new, BlockInit.ICEBOX.get()).build(null));
    public static final RegistryObject<BlockEntityType<HearthBlockEntity>> HEARTH_TILE_ENTITY_TYPE =
        BLOCK_ENTITY_TYPES.register("hearth", () -> BlockEntityType.Builder.of(HearthBlockEntity::new, BlockInit.HEARTH.get()).build(null));
}
