package net.momostudios.coldsweat.core.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.MinecraftForge;
import net.momostudios.coldsweat.common.temperature.Temperature;
import net.momostudios.coldsweat.common.temperature.modifier.TempModifier;
import net.momostudios.coldsweat.common.world.TempModifierEntries;
import net.momostudios.coldsweat.core.capabilities.PlayerTempCapability;
import net.momostudios.coldsweat.core.event.csevents.TempModifierEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerTemp
{
    /**
     * Returns the player's temperature AFTER the modifiers are calculated.
     */
    public static Temperature getTemperature(PlayerEntity player, Types type)
    {
        AtomicReference<Double> temp = new AtomicReference<>(0.0d);
        player.getCapability(PlayerTempCapability.TEMPERATURE).ifPresent(capability -> temp.set(capability.get(type)));
        return new Temperature(temp.get());
    }

    /**
     * You should try to avoid using these unless you need to set the value to a fixed amount.<br>
     * Otherwise, use a {@link TempModifier} instead.
     */
    public static void setTemperature(PlayerEntity player, Temperature value, Types type)
    {
        player.getCapability(PlayerTempCapability.TEMPERATURE).ifPresent(capability ->
        {
            capability.set(type, value.get());
        });
    }

    /**
     * Applies the given modifier to the player's temperature directly.<br>
     * This is used for instant temperature-changing items (i.e. Waterskins)
     *
     * @param duplicates allows or disallows duplicate TempModifiers to be applied
     * (You might use this for things that have stacking effects, for example)
     */
    public static void applyModifier(PlayerEntity player, TempModifier modifier, Types type, boolean duplicates)
    {
        MinecraftForge.EVENT_BUS.post(new TempModifierEvent.Add(modifier, player, type, duplicates));
    }


    /**
     * Gets all TempModifiers of the specified type on the player
     * @param player is the player being sampled
     * @param type determines which TempModifier list to pull from
     * @returns a NEW list of all TempModifiers of the specified type
     */
    public static List<TempModifier> getModifiers(PlayerEntity player, Types type)
    {
        List<TempModifier> modifierList = new ArrayList<>();
        // Get the list of modifiers from the player's persistent data
        ListNBT modifiers = player.getPersistentData().getList(PlayerTemp.getModifierTag(type), 10);
        // For each modifier in the list
        modifiers.forEach(modifier ->
        {
            CompoundNBT modifierNBT = (CompoundNBT) modifier;

            // Create a new modifier from the CompoundNBT
            TempModifier newModifier = TempModifierEntries.getEntries().getEntryFor(modifierNBT.getString("id"));

            modifierNBT.keySet().forEach(key ->
            {
                // Add the modifier's arguments
                newModifier.addArgument(key, NBTHelper.getObjectFromINBT(modifierNBT.get(key)));
            });

            // Add the modifier to the player's temperature
            modifierList.add(newModifier);
        });

        return modifierList;
    }


    /**
     * Removes the specified number of TempModifiers of the specified type from the player
     * @param player The player being sampled
     * @param modClass The class of the TempModifier to remove
     * @param type Determines which TempModifier list to pull from
     * @param count The number of modifiers of the given type to be removed (can be higher than the number of modifiers on the player)
     */
    public static void removeModifier(PlayerEntity player, Class<? extends TempModifier> modClass, Types type, int count)
    {
        MinecraftForge.EVENT_BUS.post(new TempModifierEvent.Remove(player, modClass, type, count));
    }


    /**
     * Defines all types of temperature in Cold Sweat. <br>
     * These are used to get the player's temperature and/or to apply modifiers to it. <br>
     * <br>
     * {@link #AMBIENT}: The temperature of the area around the player. Should ONLY be changed by TempModifiers. <br>
     * {@link #BODY}: The temperature of the player's body. <br>
     * {@link #BASE}: A static offset applied to the player's body temperature. <br>
     * {@link #COMPOSITE}: The sum of the player's body and base temperatures. (CANNOT be set) <br>
     * {@link #RATE}: Only used by TempModifiers. Affects the rate at which the player's body temperature changes. <br>
     */
    public enum Types
    {
        AMBIENT,
        BODY,
        BASE,
        COMPOSITE,
        RATE
    }

    /**
     * Used for storing TempModifiers in the player's persistent data (NBT). <br>
     * <br>
     * @param type The type of TempModifier to be stored
     * @return The NBT tag name for the given type
     */
    public static String getModifierTag(Types type)
    {
        switch (type)
        {
            case BODY :     return "body_temp_modifiers";
            case AMBIENT :  return "ambient_temp_modifiers";
            case BASE :     return "base_temp_modifiers";
            case RATE :     return "rate_temp_modifiers";
            default : throw new IllegalArgumentException("PlayerTempHandler.getModifierTag() received illegal Type argument");
        }
    }

    /**
     * Used for storing Temperature values in the player's persistent data (NBT). <br>
     * <br>
     * @param type The type of Temperature to be stored. ({@link Types#AMBIENT} should only be stored when needed to prevent lag)
     * @return The NBT tag name for the given type
     */
    public static String getTempTag(Types type)
    {
        switch (type)
        {
            case BODY :      return "body_temperature";
            case AMBIENT :   return "ambient_temperature";
            case BASE :      return "base_temperature";
            case COMPOSITE : return "composite_temperature";
            default : throw new IllegalArgumentException("PlayerTempHandler.getTempTag() received illegal Type argument");
        }
    }
}
