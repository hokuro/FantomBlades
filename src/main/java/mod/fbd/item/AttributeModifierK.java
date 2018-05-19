package mod.fbd.item;

import java.util.UUID;

import mod.fbd.core.log.ModLog;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class AttributeModifierK extends AttributeModifier
{

    public AttributeModifierK(String nameIn, double amountIn, int operationIn)
    {
    	super(nameIn, amountIn, operationIn);
    }

    public AttributeModifierK(UUID idIn, String nameIn, double amountIn, int operationIn)
    {
    	super(idIn,nameIn,amountIn,operationIn);
    }

    public double getAmount()
    {
    	ModLog.log().debug("amount :" + super.getAmount());
        return  super.getAmount();
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && p_equals_1_ instanceof AttributeModifier)
        {
            AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;

            if (this.getID() != null)
            {
                if (!this.getID().equals(attributemodifier.getID()))
                {
                    return false;
                }
            }
            else if (attributemodifier.getID() != null)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
