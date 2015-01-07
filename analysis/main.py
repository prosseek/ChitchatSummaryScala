from __future__ import division

import ConfigParser as config
import sys
import os
import argparse 
import re

import analyzer as a
import get_map as gm
import get_size as gs

def count(inputs, the_value):
    return len(filter(lambda x: x == the_value, inputs))

def get_packet_loss(res):
    total = len(res)
    error = count(res, 0)
    
    return error/total    

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
    
    # get the total packet size to run
    contextcount = int(cf.get('setup','contextcount'))
    analysisdata = cf.get('setup', 'analysisdata')

    sizes = [gs.get_size(f) for f in sizefiles]
    m = gm.get_map(mapfile)
    t = a.Analyzer(m, mapFilePath = mapfile, sizeFilePaths=sizefiles, sizes=sizes, analysisdata=analysisdata)
    print t.run(number_of_contexts = contextcount)
    print get_packet_loss(m)