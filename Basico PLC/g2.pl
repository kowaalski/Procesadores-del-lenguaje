do {
    do {
        do 
            suma = suma+a+b+c;
        while (c=c+1 < 10);
    } while (b=b+1 < 10);
} while ( a=a+1 < 10 );
print(suma);        








L0:
L2:
L4:
	_t0 = suma + a;
	_t1 = _t0 + b;
	_t2 = _t1 + c;
	suma = _t2;
	_t3 = c + 1;
	c = _t3;
	if (c < 10) goto L6;
	goto L7;
L6:
	goto L4;
L7:
	_t4 = b + 1;
	b = _t4;
	if (b < 10) goto L8;
	goto L9;
L8:
	goto L2;
L9:
	_t5 = a + 1;
	a = _t5;
	if (a < 10) goto L10;
	goto L11;
L10:
	goto L0;
L11:
	print suma;
