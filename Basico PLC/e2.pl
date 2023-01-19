x=1;
if ( a != b ) 
     if ( b == c )
           x = x+2;
     else
           x = x+3;
print (x);           


x = 1;
	if (a == b) goto L2;
	goto L1;
L1:
	if (b == c) goto L4;
	goto L5;
L4:
	_t0 = x + 2;
	x = _t0;
	goto L3;
L5:
	_t1 = x + 3;
	x = _t1;
L3:
	goto L0;
L2:
L0:
	print x;
