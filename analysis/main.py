import ConfigParser as config
import sys
import os
import argparse 
import re

import analyzer as a
import get_map as gm
import get_size as gs

if __name__ == "__main__":
    # 1. analyze the user's input to get the configuration file
    parser = argparse.ArgumentParser()
    parser.add_argument('--cfg', '-c', dest='cfg', type=str, default='test/config.cfg', help='configuration')
    args = vars(parser.parse_args())
    
    # 2. read the input parameters from the configuration
    cf = config.ConfigParser()
    cf.read(args['cfg'])
    
    # get the map file
    mapfile = cf.get('setup','mapfile')
    
    # get the size file
    sizefiles = cf.get('setup','sizefiles')
    sizefiles = re.split('\s+', sizefiles)

    sizes = [gs.get_size(f) for f in sizefiles]
    a = a.Analyzer(gm.get_map(mapfile), mapFilePath = mapfile, sizeFilePaths=sizefiles, sizes=sizes)