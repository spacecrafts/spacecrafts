# Travel

How ships travel across the universe.

## Ideas

* wrap speed is ration of ship size (not weight) to wrap drive (smaller ships
  travel faster easier)

## Drives

### Wrap Drive
Wrap speed depends on the size of the ship in relation to wrap output.

	wrap-speed = wrap-capacity / size

Wrap speed is given in parsec.
When size and output have a ration of 1:1 a ship does 1 parsec per turn.
That means the output of the wrap drive has to scale linearly with the ships size to keep up the same speed.
A speed of 1 parsec is reasonably fast.
A realistic range would be 1-10 parsec.
So a really fast (wrap drive heavy) ship should have an output of 10 times its size.
A 500 size ship with heavy wrap would use 1/4th wrap drive.
So 125 should output 5000, that is 40 per cell on average.


## Impulse Drive
Drive to move within a solar system and during battle.

Impulse speed depends on weight per drive output.
Minimal weight per cell is around 3-4. Maximum is around 30.
Minimal ship size is around 5, maximum around 500.
This gives ship weights from 15 to about 15000.
A ship that heavy should be slow unless large portion of that ships is in fact spent for drive.
A powerful drive should take about 1/4 of ship size.
So 125 drive cells should give reasonable speed for a ship weighing 15000t.
Now, a drive should be seen as a device converting energy to thrust.
So each cell can output a range depending on the energy input.
But that is just a side note as ships would usually use 100% of drive capacity.
If impulse speed 1 is a ratio thrust/weight of 1:1, and a maximum of impulse speed 10 would be a ratio of 10:1 the drive would need to output 120 thrust on average at that size. So say a drive outputs 50 trust at size 1 a small ship weighing 20 would get to impulse speed 2.5 which sounds about right.

	impulse-speed = thrust/weight
	thrust = sum(cluster(impulse-drive-base))
	weight = sum(material-weight) + sum(component-weight)

Best energy generation can output about 2300 units of energy at a size of 125 cells (1/4 of a 500 size ship).
If also about 1/4 of that 2300 is spend for drive this gives 500 energy for 125 drive cells. So each could take up 4.
It seams reasonable to lower this to 3 as we talk about the best energy generation and a quite drive heavy ship.
So a drive could use 0-3 energy. The thrust will increase linearly to the maximum as given in the thrust output sequence.
So then a certain impulse speed should be reached we compute the necessary thrust and the maximum output of the drive as well as the energy costs for that.
Than the needed thrust is put into proportion with the maximum thrust. Energy will have same proportion. This is the energy consumed. 

