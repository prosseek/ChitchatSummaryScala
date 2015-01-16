
set terminal pngcairo enhanced font 'Verdana,10'
set output 'test/exp4.txt.map.png'
plot 'test/exp4.txt.map.data' matrix with image
