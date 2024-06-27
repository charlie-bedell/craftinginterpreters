# How to build
- `make jlox` to build the java implementation of lox
- `make clox` to build the c implementation of lox (not yet supported)
- `make` to build both `clox` and `jlox` implementations

# how to run java files
- after class files have been built, you can run an individual class file that
  contains a `main()` function like so:
  `java -cp build/java/ com.craftinginterpreters.lox.AstPrinter`

# what is .dir-locals.el?
`.dir-locals.el` is used to set a directory-specific configuration in Emacs.

Here, it's used to set the `sourcePaths` for the jdtls, so it can correctly find
and provide lsp features for source files
  
Without it, jdtls may not recognize the package
`com.craftinginterpreters.lox`, and fail to provide language features.

when it cannot recognize the package, it will provide the follow error message:
`declared package com.craftinginterpreters.lox does not match expected package ""`
