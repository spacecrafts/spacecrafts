# Battle

How battles are fought...

## Ideas

A very loose collection of ideas around the battle.

* laser has benefit of firing first (speed of light)
* particle guns fire particles at about 1/2 speed of light
* shields differ also by the weapons that can be used while the shield is active
* "beam" means a weapon uses fast impulses (can be used from afar)
* "energy" means a weapon converts energy to the output
* "gun" means a weapon uses slower projectiles (used in closer distance and
  therefore later)
* spies can do sabotage during battle (they can be on a specific enemy ship)
* troops can do sabotage (more damage with knowledge)
* battle speed depends on weight and impulse drive (more weight needs more drive
  to get similar speed)
* as size is a key factor for manoeuvrability, small spacecrafts naturally have
  a higher manoeuvrability.
* gatling weapon modification reduces chance to fully miss by firing 4 shots
  with 1/4 damage. Each of which might miss. The modification costs 1 extra per
  cell and can be used with beam and mass guns. For rockets this has a different
  name but essentially one gets 4 smaller rockets launched together instead of
  1. gatling should decrease chance-to-miss directly.
* wrap disturber does not affect the possibility to use a wrap drive but
  disturbs the sensors needed to plan the jump without crashing into mass on the
  way. So ships can always chose to escape but the more powerful the wrap
  disturber used is, the higher the chance the ship is destroyed in wrap
  collision.
* There should be more tactics to battle than to have the more powerful, more
  destructive ships.

## Hull

The hull is the structure of a module. It depends on the material used for the
module, and the number of cells in the module. The material of the hull has a
damage reduction value. This is the absolute damage value that is absorbed by
the hull when hit. This means the damage is reduced by the value while the hull
structure is equally reduced by the same value. When hull structure reaches zero
the hull and with it the module is destroyed. This is the point where the module
falls apart, not the point where it has been dissolved to nothing.

## Shields

Shields should only protect a radius of cells around them - different shields
can be different in their spread and strength allow to chose easy/high coverage
vs. high strength. The spread is fix for the type of shield forcing to chose
between needing many or spread out shield or having less shield cells which can
cover anything but are weaker. This also limits the practical range of shield
strength since one could put an extra (not needed) number of cells to increase
the overall strength but this only makes sense to some degree as otherwise too
many usable cells are occupied which makes the ship tough but otherwise less
useful. Spread range is maybe 3-7 cells. Certainly below 10. This should also
encourage building multiple shield batteries.

When the component hit by an attack is decided it becomes apparent if the shield
for that cell is still working and at which rate it protects. 

## Plating

* Plating is used to do fixed damage reduction.
* the plating reduction applies to each shot
* Each time the damage is reduced the integrity of the plating is also affected.
* Plating must be repaired to get back to full integrity.
* Plating is expensive and heavy slowing down a ship.
* Plating uses custom materials each having their own cost, mass and protection
  values.
* The choice of plating is per module and its strength is related to the
  material chosen. This limits the maximum possible strength and only allows
  very strong general damage protection through the use of very expensive and
  rare materials.
