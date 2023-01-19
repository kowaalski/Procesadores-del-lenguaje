import java_cup.runtime.*;
%%
%cup

entero = 0|[1-9][0-9]*
real = 0|[0-9][1-9]*\.[0-9]+(E-[1-9])?
identificador = [a-zA-Z][a-zA-Z0-9]*

%%
while					    { return new Symbol(sym.WHILE);  }
do					        { return new Symbol(sym.DO);  }
if                          { return new Symbol(sym.IF); }
else                        { return new Symbol(sym.ELSE); }
for                         { return new Symbol(sym.FOR); }
in                          { return new Symbol(sym.IN); }
print                       { return new Symbol(sym.PRINT); }
int                         { return new Symbol(sym.INT); }
float                       { return new Symbol(sym.FLOAT , yytext()); }
char                        { return new Symbol(sym.CHAR, yytext()); }
,                           { return new Symbol(sym.COMA); }
\+    		                { return new Symbol(sym.MAS); }
\++                         { return new Symbol(sym.MASMAS); }
-    		                { return new Symbol(sym.MENOS); }
--                          { return new Symbol(sym.MENOSMENOS); }
\*    		                { return new Symbol(sym.POR); }
%                           { return new Symbol(sym.MOD); }
\/    		                { return new Symbol(sym.DIV); }
\(    		                { return new Symbol(sym.AP); }
\)    		                { return new Symbol(sym.CP); }
\{                          { return new Symbol(sym.ALL); }
\}                          { return new Symbol(sym.CLL); }
\[                          { return new Symbol(sym.ACOR); }
\]                          { return new Symbol(sym.CCOR); }
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
{entero}				    { return new Symbol(sym.ENTERO, yytext() ); }
{real}				        { return new Symbol(sym.REAL, yytext() ); }
{identificador}		        { return new Symbol(sym.IDENT, yytext() ); }
'[a-zA-Z/]?'                { return new Symbol(sym.LETRA, yytext());}
//\r|\n                       { return new Symbol(sym.EOLN, yytext()); }
[^]						    {  }