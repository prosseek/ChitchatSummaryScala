# step2 -  check code
# find the rate of missing
from __future__ import division

MAX = 255

def generate_arrays(lines):
    """Returns an array of 1 and 0
    
    Idea:
        1. given two values, put the values except the last one
        2. put the last value only at the very last
        
    Algorithm:
        find the first and next values
        1. case 1: next value is larger than the previous one
            1. put the first into an array, and then put zeros if the next is not +1 from the previous value
        2. case 2: next is smaller than the previous one
    
    >>> generate_arrays([1,2,5])
    [1, 1, 0, 0, 1]
    >>> generate_arrays([254,0])
    [1, 0, 1]
    >>> generate_arrays([254,2])
    [1, 0, 0, 0, 1]
    
    """
    
    assert len(lines) >= 2, "There should be more than two elements" # There should be at least two elements
    result = []
    
    index = 0
    first_value = lines[index]
    index += 1
    next_value = lines[index]
    
    while True:    
        # example: 3 4 -> + [1]
        #          3 5 -> + [0 1]
        if next_value > first_value:
            distance = next_value - first_value
            result += [1] + ([0] * (distance - 1)) 
            #         First  Next values 
        else:
            distance = MAX - first_value
            result += [1] + ([0] * (distance)) 
            #print "D1:%d (%d-%d)"  % (distance, first_value, next_value)
            
            distance = next_value - 0
            result += ([0] * (distance))
            #print "D2:%d"  % distance
            
        # process the next
        first_value = next_value
        index += 1
        try:
            next_value = lines[index]
        except:
            # Always put the first value
            result += [1]
            break
    return result
    
def get_map_file_name(filePath):
    return filePath + ".map"
    
def get_map(filePath):
    with open(filePath) as f:
        lines = f.readlines()
        f.close()
        
    lines = [int(line.strip()) for line in lines if not line.startswith("#")]
    res = generate_arrays(lines)
    
    with open(get_map_file_name(filePath), "w") as f:
        f.write(str(res))
        f.close()
    
    return res
    
if __name__ == "__main__":
    import doctest
    doctest.testmod()
    
    # step1 read the packets

