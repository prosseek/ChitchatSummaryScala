
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data1.txt.map.txt.png'
plot 'test/data1.txt.map.txt.data' matrix with image
