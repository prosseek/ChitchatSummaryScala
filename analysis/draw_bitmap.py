import math
import subprocess
import itertools

import get_map as gm

GNUPLOT = '/usr/local/bin/gnuplot'
OPEN = '/usr/bin/open'
template = """
set terminal pngcairo enhanced font 'Verdana,10'
set output '%s'
plot '%s' matrix with image
"""

# http://stackoverflow.com/questions/2231663/slicing-a-list-into-a-list-of-sub-lists
def grouper(n, iterable, fillvalue=None):
    "grouper(3, 'ABCDEFG', 'x') --> ABC DEF Gxx"
    args = [iter(iterable)] * n
    return itertools.izip_longest(fillvalue=fillvalue, *args)

def generate_data(inputMapFile, outputDataFile):
    """Given input file (map file), transform the content into GNU data
    """
    with open(inputMapFile) as f:
        r = f.read()
        f.close()
    r = r[1:-1]
    r = r.split(',')
    r = [int(i) for i in r]
    
    x = int(math.ceil(math.sqrt(len(r))))
    
    zeros = x * x - len(r)
    r += [-1] * int(zeros)

    result = list(grouper(x, r))
    with open(outputDataFile, 'w') as f:
        for res in result:
            for r in res:
                f.write(str(r) + ' ')
            f.write('\n')
        f.close()

def get_gnu_code_name(name):
    return name + ".gnuplot"
    
def get_img_name(name):
    return name + ".png"

def generate_gnuplot_code(name, dataFile):
    gnu_code_name = get_gnu_code_name(name)
    img_name = get_img_name(name)
    
    t = template % (img_name, dataFile)
    with open(gnu_code_name, 'w') as f:
        f.write(t)
        f.close()

def run_gnuplot(gnu_code, img_name):
    subprocess.check_output([GNUPLOT, gnu_code])
    subprocess.check_output([OPEN, img_name])

def main(inputFile):
    gm.get_map(inputFile)
    mapFile = gm.get_map_file_name(inputFile)
    
    outputFile = mapFile + ".data"
    generate_data(mapFile, outputFile)
    generate_gnuplot_code(mapFile, outputFile)
    gnu_code = get_gnu_code_name(mapFile)
    img_name = get_img_name(mapFile)
    run_gnuplot(gnu_code, img_name)


if __name__ == "__main__":
    filePath = 'test/exp4.txt'
    main(filePath)