package dev.momostudios.coldsweat.common.temperature;

import dev.momostudios.coldsweat.common.temperature.modifier.TempModifier;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This is the basis for nearly all things relating to temperature in this mod. <br>
 * While {@code Temperature} is not stored onto the player directly, it is very commonly used for calculations <br>
 *<br>
 * It is highly recommended to use Temperature in your code and convert it to a double via {@code get()}
 */
public class Temperature
{
    // Internal variable representing the actual value of the Temperature
    double temp;

    /**
    * Defines an instance of the class with a custom initial double value
    */
    public Temperature(double tempIn)
    {
        temp = tempIn;
    }
    /**
    * Defines an instance of the class with a default value of 0
    */
    public Temperature()
    {
        this(0);
    }


    /**
    * Sets the actual value of the temperature
    */
    public void set(double amount)
    {
        temp = amount;
    }

    /**
    * Adds to the actual value of the temperature
    */
    public Temperature add(double amount)
    {
        temp += amount;
        return new Temperature(temp);
    }
    /**
     * Adds to the actual value of the temperature
     * @return a new Temperature with the new value
     */
    public Temperature add(Temperature amount)
    {
        temp += amount.get();
        return new Temperature(temp);
    }

    /**
    * @return double representing the actual value of the Temperature
    */
    public double get()
    {
        return temp;
    }

    /**
     * @return  a double representing what the Temperature would be after a TempModifier is applied.
     * @param player the player this modifier should use
     * @param modifier the modifier being applied to the {@code Temperature}
     */
    public Temperature with(@Nonnull TempModifier modifier, @Nonnull Player player)
    {
        return new Temperature(modifier.calculate(new Temperature(temp), player));
    }

    /**
     * @return a double representing what the Temperature would be after a list of TempModifier(s) are applied.
     * @param player the player this list of modifiers should use
     * @param modifiers the list of modifiers being applied to the {@code Temperature}
     */
    public Temperature with(@Nonnull List<TempModifier> modifiers, @Nonnull Player player)
    {
        Temperature temp2 = new Temperature(this.temp);
        for (TempModifier modifier : modifiers)
        {
            temp2.set(modifier.calculate(temp2, player));
        }
        return temp2;
    }

    /**
     * Defines all temperature stats in Cold Sweat. <br>
     * These are used to get temperature stored on the player and/or to apply modifiers to it. <br>
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

    public enum Units
    {
        F,
        C,
        MC
    }
}