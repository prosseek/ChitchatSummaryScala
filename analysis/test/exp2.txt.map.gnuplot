
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/exp2.txt.map.png'
plot 'test/exp2.txt.map.data' matrix with image
