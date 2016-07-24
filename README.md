# hit2ass tool suite

# Internal naming conventions
## Grouping symbols
To avoid nomenclature like left curly brace, closing round bracket, and squiggly parenthesis, this document uses the conventions as specified below. This nomenclature is especially heavily used within the JavaCC lexer and parser configuration document.
<dl>
  <dt>(</dt>
  <dd>left parenthesis</dd>
  <dt>)</dt>
  <dd>right parenthesis</dd>
  <dt>[</dt>
  <dd>left bracket/dd>
  <dt>]</dt>
  <dd>right bracket</dd>
  <dt>{</dt>
  <dd>left brace</dd>
  <dt>}</dt>
  <dd>right brace</dd>
</dl>

## Introduction
**Hit2Ass** is a tool suite that supports the analysis of **HIT/CLOU** __text components__ 
and the automated transformation of **HIT/CLOU** __text components__ to **Assentis DocFamily** workspaces.

Thus, this tool can be used as a basis for a migration project when you need to migrate from a legacy
text system to Assentis DocFamily.

## How to use
The transformation process consists of several steps.
1. HIT/CLOU sources to AST (Abstract syntax tree). From here you can go on and apply various visitors, e.g. the PrettyPrinter to create a diagram view of the AST. 
2. AST to IRT. The *I*ntermiediate *R*epresentation *T*ree is a document representation that is completeley independet from the source and target formats. In fact, if you write an AST parser for another text system like papyrus and a transformer that yields an IRT built from the IRT components in this project's IRT domain, you will end up converting papyrus to DocFamily workspaces.
3. IRT to workspace
