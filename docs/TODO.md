# TODOs

## Code Concepts

* scrap Refs - just a PITA, improve load/save instead
* scrap annotations for IO aid => converters
* change load/store to an architecture with serialiser/deserialisers - like then
  writing to a DB, defeat cyclic refs by constructing all entities with no
  fields initialised, make the ref available for their serial and then init the
  fields. so if an instance is referenced that is not yet known just make a
  blank instance - it will be initialised later. add detection to check that
  actually is the case.
* make model just structs defined in a single file
* use dynamic length encoding so that serials for low numbers are encoded as
  byte => short => int (? worth it?)
* components: as there are only components in the grid each refers to the
  aggregations that should be applied. these are pure function classes that use
  the grid and component as input to compute and add a output resource quantity
  to the output table. This is a key-value system representing "resources" in
  the game that are linked to certain actionale things. aggregations need to
  define a global order of applyance so that aggreation takes effect the way it
  is supposed to happen. aggregations are temporary and not part of any
  persistet game state => less stuff to persist.
