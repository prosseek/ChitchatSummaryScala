### [2016/04/05]08:13PM

Summary's creation method uses `var Map[]`,
which means once the map is created, we can't add or update new items.

If you want to do that, you need to create a new map, and create a new summary from the map.