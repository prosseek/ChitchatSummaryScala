contextSummary
==============

TODO
====
1. (fixed) In the shortToByteArray, the byte array is big endian,
   and it may cause confusion with BitSet which assumes little endian.
   So, use little endian for ..toByteArray methods.
2. The randomly generated string types has high probability with just one string.
   So, if the rule says about the minimal length of string, the probability would dramatically reduced. 
   We also can filter the case with 