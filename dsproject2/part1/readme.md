#### For master clock,

python3 masterclock.py

#### For client clocks,

python3 clientclock.py

## Assumption

Both master clock and client clock will be running from the same machine, therefore, a little time delay is close to some nanoseconds was introduced. Also, using 'dateutil' will help to generate and assign time for each of the client node synchronization. Furthermore, based on the assumption mentioned previously, current time for all of the client nodes obtained by calculating the average. However, a delay of the sending and receiving time to and from server was not considered.



## Reference

- https://en.wikipedia.org/wiki/Berkeley_algorithm
- https://youtu.be/50k1Noa-eDQ
