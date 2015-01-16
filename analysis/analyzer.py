from __future__ import division
import math

LABEL = 1
BF_FOLDED = 2
BF_COMPLETE = 3
COMPLETE = 4

class Analyzer(object):
    def __init__(self, inputMap, mapFilePath, sizes, sizeFilePaths, analysisdata = None):
        assert inputMap is not None
        assert len(inputMap) > 0
        self.map = inputMap
        self.mapFilePath = mapFilePath
        self.sizes = sizes
        self.sizeFilePaths = sizeFilePaths
        self.packetSize = 16 # 32
        self.analysisdata = analysisdata
        self.f = None
        
        if analysisdata is not None:
            self.f = open(analysisdata, 'w')
            
        self.log(str(self.map) + "\n\n\n")

    def log(self, string):
        if not isinstance(string, str):
            string = str(string)
        if self.f is not None:
            self.f.write(string)
        
    def run(self, number_of_contexts = 10, maps = None):
        """
        >>> t.run(number_of_contexts = 80)
        (83.7, 91.2, 86.2, 91.2)
        """
        if maps is None: maps = self.map
        
        label = self.generate_drop_rate(LABEL, number_of_contexts, maps)
        bf_folded = self.generate_drop_rate(BF_FOLDED, number_of_contexts, maps)
        bf_complete = self.generate_drop_rate(BF_COMPLETE, number_of_contexts, maps)
        complete = self.generate_drop_rate(COMPLETE, number_of_contexts, maps)
        
        if self.f is not None: self.f.close()
        
        return (label, bf_folded, bf_complete, complete)
        
    def generate_drop_rate(self, column, number_of_contexts = 10, maps = None):
        """
        Generate number_of_contexts
        >>> t.generate_drop_rate(LABEL, 80)
        83.7
        >>> t.generate_drop_rate(BF_FOLDED, 80)
        91.2
        >>> t.generate_drop_rate(BF_COMPLETE, 80)
        86.2
        >>> t.generate_drop_rate(COMPLETE, 80)
        91.2
        """
        if maps is None: maps = self.map
        sizes = self.generate_sizes(column, number_of_contexts, maps)
        result = self.simulate_contexts(sizes, maps)
        # we only need the first part - (50.0, [1, 1])
        return result[0]
        
    def generate_sizes(self, column, number_of_contexts = 10, maps = None):
        """
        Generate number_of_contexts
        >>> t.generate_sizes(LABEL)
        [123, 113, 123, 113, 123, 113, 123, 113, 123, 113]
        """
        if maps is None: maps = self.map
        
        sizes = []
        for size in self.sizes:
            sizes.append(size[column])
            
        # now size is [123,113] if I'm given two sizes and select the label
        number_of_repititon = math.ceil(number_of_contexts / len(sizes))
        return (sizes * number_of_contexts)[0:number_of_contexts]
        
        
    def simulate_contexts(self, sizes, maps = None):
        """
        We send 32 and 64 to [1,0,1,1,1]
        For the first 32, it will succeed -> 1
        For the next 64, it will fail -> 0
        The rest of the map will be [1,1] and the success rate is 50%
        
        >>> t.simulate_contexts([32, 64], [1,0,1,1,1])
        (50.0, [1, 1])
        >>> t.simulate_contexts([32, 64, 64], [1,0,1,1,1])
        (66.6, [])
        >>> t.simulate_contexts([32, 64, 1024], [1,0,1,1,1])
        Traceback (most recent call last):
        Exception: Getting short of map, need shorted size or longer map
        """
        if maps is None: maps = self.map
        result = []
        index = 0
        for size in sizes:
            (r, index) = self.com_simulation(index, size, maps)
            if index == -1:
                raise Exception("Getting short of map, need shorted size or longer map")
            result.append(r)
            
            #r = self.simulate_context(size, maps)
            
        self.log(str(result) + "\n\n")
        success = len(filter(lambda x: x == 1, result))
        rate = int((success/len(result)) * 1000.0)/10.0
            #result.append(rate)
        return (rate, maps[index:])

            
    def com_simulation(self, index, size, maps = None):
        """ 
        Given a map, and index, check which of the four packets will succeed the communication or not
        
        Returns a tuple of (True/False, next index)
        When next index is -1, we've used all the maps.
        
        >>> map = [1, 0, 1, 1, 1, 1, 1, 1]
        >>> index = 0
        >>> size = 123
        >>> t.com_simulation(0, size, map)
        (0, 4)
        >>> t.com_simulation(2, size, map)
        (1, 6)
        >>> t.com_simulation(10, size, map)
        Traceback (most recent call last):
        AssertionError
        >>> t.com_simulation(5, size, map)
        (0, -1)
        """
        if maps is None: maps = self.map
        
        assert(len(maps) > 0)
        assert len(maps) > index, "len(maps)/maps -> %d%s vs index -> %d" % (len(maps), maps, index)
        
        number_of_packets = int(math.ceil(size / self.packetSize))
        #self.log(str(number_of_packets))

        partial_map = maps[index:index + number_of_packets]
        if len(partial_map) < number_of_packets:
            return (0, -1)

        next_index = index + number_of_packets
        if all(partial_map): return (1, next_index)
        else: return (0, next_index)
        
        
if __name__ == "__main__":
    maps = [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
    #maps = [1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
    # width, label, folded, bf_complete, complete
    
    sizes = [(1, 123, 51, 81, 47), (1, 143, 46, 91, 47), (2, 137, 80, 101, 77), (2, 120, 68, 92, 65), (2, 145, 87, 115, 83), (2, 147, 92, 132, 87), (1, 81, 20, 34, 21), (2, 121, 94, 134, 89)]
    # sizes = [(1, 123, 51, 81, 47), (1, 113, 51, 81, 47)]
    import doctest
    t = Analyzer(maps, None, sizes = sizes, sizeFilePaths = None)
    #doctest.testmod(extraglobs={'t': Analyzer(maps, None, sizes = sizes, sizeFilePaths = None)})
    doctest.testmod()