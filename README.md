# ContextSummary

This is an implementation of how contexts are represented.
Used in papers:

* [CHITCHAT: Navigating Tradeoffs in Device-to-Device Context Sharing](https://www.researchgate.net/publication/287249098_CHITCHAT_Navigating_Tradeoffs_in_Device-to-Device_Context_Sharing)
* [The Grapevine Context Processor: Application Support for Efficient Context Sharing](https://www.researchgate.net/publication/282441702_The_Grapevine_Context_Processor_Application_Support_for_Efficient_Context_Sharing)


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
