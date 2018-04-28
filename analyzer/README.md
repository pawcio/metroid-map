# metroid-map-analyzer
Tool to convert map from http://www.nesmaps.com/maps/Metroid/MetroidBG.html to data files

Main goal is to define data format to store map. This is related to metroid-map project feature:
https://github.com/pawcio/metroid-map/issues/3

Current analysis results:

* Map is a grid of rooms. Grid size is 30 x 30, so there are 900 "slots" for rooms
* There are 209 unique rooms - this includes empty space and color variations, e.g same rooms with different door color
or same rooms with different color scheme
* to be continued...
