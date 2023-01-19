i=1;
do {
    suma = suma + i;
    i = i+1;
} while (i<10) ;
print (suma);



	i = 1;
L0:
	_t0 = suma + i;
	suma = _t0;
	_t1 = i + 1;
	i = _t1;
	if (i < 10) goto L2;
	goto L3;
L2:
	goto L0;
L3:
	print suma;
