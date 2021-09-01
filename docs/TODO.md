# TODOs

## Code Concepts

* use dynamic length encoding so that serials for low numbers are encoded as
  byte => short => int (? worth it?)
* components: as there are only components in the grid each refers to the
  aggregations that should be applied. these are pure function classes that use
  the grid and component as input to compute and add a output resource quantity
  to the output table. This is a key-value system representing "resources" in
  the game that are linked to certain actionable things. aggregations need to
  define a global order of appliance so that aggregation takes effect the way it
  is supposed to happen. aggregations are temporary and not part of any
  persistent game state => less stuff to persist.
