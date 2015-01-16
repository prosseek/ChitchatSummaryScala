
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/exp1.txt.map.png'
plot 'test/exp1.txt.map.data' matrix with image
