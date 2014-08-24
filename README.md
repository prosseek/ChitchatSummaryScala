contextSummary
==============

TODO
====
1. In the shortToByteArray, the byte array is big endian,
   and it may cause confusion with BitSet which assumes little endian.
   So, use little endian for ..toByteArray methods.
