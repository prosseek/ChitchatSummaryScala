
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data1.txt.map.png'
plot 'test/data1.txt.map.data' matrix with image
