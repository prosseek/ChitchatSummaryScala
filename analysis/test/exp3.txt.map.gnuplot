
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/exp3.txt.map.png'
plot 'test/exp3.txt.map.data' matrix with image
