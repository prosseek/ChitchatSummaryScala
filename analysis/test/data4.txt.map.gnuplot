
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data4.txt.map.png'
plot 'test/data4.txt.map.data' matrix with image
