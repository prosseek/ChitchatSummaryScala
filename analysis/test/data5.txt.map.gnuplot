
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/data5.txt.map.png'
plot 'test/data5.txt.map.data' matrix with image
