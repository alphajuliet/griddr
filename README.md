# griddr

A Clojure library for tile edge matching in a grid.

## Context

In games involving tile placement on a grid, edges need to match so that, for example, a road connects with a road, a city with a city, and so on. Examples of these sorts of games are Carcassonne and Alhambra. These are played on a rectangular grid. Strategy board games also often use hex grids, like Catan and Tantrix. These grids are effectively infinite grids, and don't involve any boundaries or constraints on their spread, although tiles may run out, or higher-level rules may limit their placement, as in Tantrix. In some games, tiles can be rotated to fit in an empty space, and in others flipping is also allowed, giving eight different possibilities for a fit.

This library starts with the basics of edge-matching in a rectangular grid. Over time, it may cover other grids or higher-level placement rules.

## Usage

The implementation of the 2D grid is to use a sparse matrix, with grid locations as the keys in a Clojure map, e.g. `{[0 0] :val-a [1 0] :val-b ...}`. The map is therefore only ever as big as the number of placed tiles.

To match the edges, it requires that a "tile" is a Clojure map that contains (at least) a key called `edges`. This is a vector of edges, ordered clockwise from the top edge. The type of the edges doesn't matter as long as they can be determined to be equal or not. It assumes that edges must match; it doesn't handle complementary matching, e.g. A connects only to B.

The important function is `(allowed-placement grid tile)` that returns the permitted grid locations where tile can be played in a given grid. Another interesting function is `(perimeter grid)`, which returns a list of the empty locations adjacent to the occupied grid locations. These are the candidate locations for placing a tile.

See the unit tests for how to use the library. For easy threading, `grid` is always the first parameter in functions that require it.

## License

Copyright © 2021 Andrew Joyner (andrew@alphajuliet.com)

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
