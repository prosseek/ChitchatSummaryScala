
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data2.txt.map.png'
plot 'test/data2.txt.map.data' matrix with image
