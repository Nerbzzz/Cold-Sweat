package dev.momosoftworks.coldsweat.core.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import dev.momosoftworks.coldsweat.ColdSweat;
import dev.momosoftworks.coldsweat.common.effect.IceResistanceEffect;
import dev.momosoftworks.coldsweat.common.effect.InsulatedEffect;
import dev.momosoftworks.coldsweat.common.effect.GraceEffect;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ColdSweat.MOD_ID);

    public static final RegistryObject<MobEffect> INSULATED = EFFECTS.register("insulated", InsulatedEffect::new);
    public static final RegistryObject<MobEffect> GRACE = EFFECTS.register("grace", GraceEffect::new);
    public static final RegistryObject<MobEffect> ICE_RESISTANCE = EFFECTS.register("ice_resistance", IceResistanceEffect::new);
}