# Weapons
When thinking about useful weapons we have to think about the speed and mobility of spacecrafts in the game.
With those in mind "slowly" flying weapons really do not make much sense.
They would never hit the target because the target could simply outpace them. 
So no missiles.
Even if they would be fast enough, they do not make much sense. 
Ships need deflector shields to guard against all kind of smaller mass flying around.
Otherwise they would constantly collide with those. 
And if the deflector can guard against rocks it very well can guard a missile.
Simply not an effective weapon out there.
Also it is a control problem. Yes they could get really fast as there is no resistance but they would also need to slow down or adjust direction because of a moving target. This all adds up to a bad idea.  

Each weapon has an attack vector stating how damage affects the protection levels: [ shields, plating ]
Each level can be affected between 0% (no effective protection) and 100% (100% effective protection).
So a weapon that would always deal all damage directly to the hull would have a vector of [0%, 0%].

## Laser (Energy-wave Weapons)
Electromagnetic beams. Beams of photons (note this includes lasers, masers, xasers, gasers, etc.).
This includes all wave-lengths of the visible and invisible spectrum. 
It goes to assume that different races would end up with slightly different techniques all of which essentially transport energy to the target using a beam. But they might differ in color due to used waves. 

"Weakest but first."

Vector: [100,100]

Pros:

* long range
* speed of light (hits "instantly" on battle distances)
* most robust weapon (not that complex technique)
* lower energy required than phasers
* damages multiple components
* can fire with 1-n component strength and consumption
* high accuracy (no modification to chance to miss)

Cons:

* EM-shields are 100% effective
* most plating is effective
* cannot fire through own shields

Mods:

* pulsed
* continuous
* battery

## Phaser/Particle Cannon/Neutron-Blaster
Beams of high-energy charged particles (such as protons).
Most realistically is a neutron beam.
In contrast to lasers particle beams have mass. 
So their do not travel with the speed of light but their damage is usually higher due to the mass. 

"Medium damage, medium range."

Vector: [50,100]

Pros:

* EM-shield only 50% effective
* higher damage than lasers
* medium range
* medium time until they hit the target
* damages multiple components
* can fire with 1-n component strength and consumption

Cons:

* plating is effective
* special plating is very effective (even some lighter materials)
* higher energy consumption
* complex technique
* cannot fire through own shields
* need rare materials
* medium accuracy (small modifier to chance to miss, about 5%)

Mods:

* pulsed
* continuous
* battery


## Coilgun/Railgun/Mass Drivers
Using electro-magnetic fields to shoot projectiles.

"Largest damage but last. Requires ammunition."

Vector:
 
* Standard: [0,100]
* Armor-piercing: [0,25] 

Pros:

* shields only guard 25% of damage, mainly by slowing down the projectiles
* lower energy consumption (in comparison with directed energy weapons)
* can fire through own EM-shields 

Cons:

* short range
* longest time until they hit target
* shields reduce accuracy a little
* damages target component only (no "overkill")
* more fragile than waves
* can only fire 100%
* lower accuracy (due to limited speed; chance to miss increases about 10%)

Mods:

* battery
* Armor-piercing (than 25% damage is done to plating, 75% to structure)


## Raid-Drone



## Kinetic Strike Projectiles (Bombs)
Essentially a "massive" object dropped onto planets.
The impact causes huge explosion. (But they are not explosives!)

