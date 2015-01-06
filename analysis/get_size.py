import re

def get_size(filePath, keyWidth = 6):
    """Reads the file and get the file sizes
    
    The input example:
    0 : Index
       1   2   3 : Labeled context
                  4    5    6   : BF Folded
                                  7    8   9  : BF Complete
                                                10 11 12 : Complete
    ----------------------------------------------------
    1 160 182 143 45.0 46.0 46.0 89.0 91.0 91.0 45 47 47 
    2 160 182 143 50.0 52.0 52.0 63.0 65.0 65.0 45 47 47
    ...
    
    Out of three values
    first: theoretical counting
    second: serialzied - normally +2 from the first counting
    third: zipped serialized
    
    >>> filePath = '/Users/smcho/research/contextSummary/experiment/size/s1_1.data'
    >>> get_size(filePath)
    (1, 123, 51, 81, 47)
    """
    

    STARTING_COLUMN = 3 # zipped serial
    with open(filePath) as f:
        lines = f.readlines()
        f.close()
        
    ls = []
    for l in lines:
        # When I convert the value into int directly, error occurs with an input such as 2.0
        l = [int(float(i)) for i in re.split('\s+',l.strip())]
        ls.append(l)
    results = sorted(ls, key=lambda x: x[keyWidth]) # x[6] is the compressed zip data of bloomier filter
    result = results[0]
    width = result[0]
    label = result[STARTING_COLUMN]
    folded = result[STARTING_COLUMN + 3]
    complete_bf = result[STARTING_COLUMN + 6]
    complete = result[STARTING_COLUMN + 9]
    return (width, label, folded, complete_bf, complete)
    
if __name__ == "__main__":
    import doctest
    doctest.testmod()