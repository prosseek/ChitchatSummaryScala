
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data3.txt.map.png'
plot 'test/data3.txt.map.data' matrix with image
