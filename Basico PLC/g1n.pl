i=1;
while (i<10) {
    suma = suma + i;
    i = i+1;
}
print (suma);


	i = 1;
L0:
	if (i < 10) goto L1;
	goto L2;
L1:
	_t0 = suma + i;
	suma = _t0;
	_t1 = i + 1;
	i = _t1;
	goto L0;
L2:
	print suma;
