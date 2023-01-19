import java_cup.runtime.*;
%%
%cup
%%
while					    { return new Symbol(sym.WHILE);  }
do					        { return new Symbol(sym.DO);  }
if                          { return new Symbol(sym.IF); }
else                        { return new Symbol(sym.ELSE); }
for                         { return new Symbol(sym.FOR); }
print                       { return new Symbol(sym.PRINT); }
\+    		                { return new Symbol(sym.MAS); }
\-    		                { return new Symbol(sym.MENOS); }
\*    		                { return new Symbol(sym.POR); }
\/    		                { return new Symbol(sym.DIV); }
\(    		                { return new Symbol(sym.AP); }
\)    		                { return new Symbol(sym.CP); }
\{                          { return new Symbol(sym.ALL); }
\}                          { return new Symbol(sym.CLL); }
\<                          { return new Symbol(sym.LT); }
\>                          { return new Symbol(sym.GT); }
\<\=                        { return new Symbol(sym.LE); }
\>\=                        { return new Symbol(sym.GE); }
\=\=                        { return new Symbol(sym.EQ); }
\=                          { return new Symbol(sym.IGUAL); }
\!\=                        { return new Symbol(sym.NE); }
;                           { return new Symbol(sym.PYC); }
\!                          { return new Symbol(sym.NOT); }
&&                          { return new Symbol(sym.AND); }
\|\|                        { return new Symbol(sym.OR); }
0|[1-9][0-9]*				{ return new Symbol(sym.ENTERO, yytext() ); }
[a-zA-Z][a-zA-Z0-9]*		{ return new Symbol(sym.IDENT, yytext() ); }
//\r|\n                       { return new Symbol(sym.EOLN, yytext()); }
[^]						    {  }