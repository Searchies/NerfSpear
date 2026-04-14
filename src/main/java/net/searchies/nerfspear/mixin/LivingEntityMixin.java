package net.searchies.nerfspear.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float nerfSpearDamage(float amount, DamageSource source) {
		LivingEntity victim = (LivingEntity) (Object) this;

		// Return normal amount if spear isn't attacking a Player
		if (!(victim instanceof PlayerEntity)) {
			return amount;
		}

		String spearId = null;

		// Check if it's an attack using a Spear
		if (source.getAttacker() instanceof LivingEntity attacker) {
			String mainHandItemName = Registries.ITEM.getId(attacker.getMainHandStack().getItem()).getPath();
			String offHandItemName = Registries.ITEM.getId(attacker.getOffHandStack().getItem()).getPath();

			if (mainHandItemName.contains("spear")) {
				spearId = mainHandItemName;
			} else if (offHandItemName.contains("spear")) {
				spearId = offHandItemName;
			}
		}

		if (spearId != null) {
			float maxBaseDamage = 20.0f; // Default (10 hearts)

			if (spearId.contains("wooden")) {
				maxBaseDamage = 12.0f; // 6 hearts
			} else if (spearId.contains("stone") || spearId.contains("golden")) {
				maxBaseDamage = 14.0f; // 7 hearts
			} else if (spearId.contains("iron")) {
				maxBaseDamage = 16.0f; // 8 hearts
			} else if (spearId.contains("diamond")) {
				maxBaseDamage = 18.0f; // 9 hearts
			} else if (spearId.contains("netherite")) {
				maxBaseDamage = 22.0f; // 11 hearts (4 hearts with Full Netherite)
			}

			// Return whichever is lower: velocity damage or the maxBaseDamage
			return Math.min(amount, maxBaseDamage);
		}

		return amount;
	}
}