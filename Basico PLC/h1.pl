for (i=1; i<10; i=i+1) 
     x = x+i;
print(x);






i = 1;
L0:
	if (i < 10) goto L2;
	goto L3;
L1:
	_t0 = i + 1;
	i = _t0;
	goto L0;
L2:
	_t1 = x + i;
	x = _t1;
	goto L1;
L3:
	print x;
