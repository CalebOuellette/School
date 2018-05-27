1.
  * a.
    *  Mutial Exclution: Each road is not shared.
    *  Hold and wait: Vehicles can't release a resource a road until the other road is clear.
    * No preemption: Vechiles don't release roads until they are complete.
    * Circular waiting: Each lane needs another and they end up forming a circle.
  * b. only allow in veichles from 3 lanes at a time.

2. Dead lock prevention ensure one of the 4 conditions above does not hold so that prcoesses can't deadlock. Dead lock avoidence is keeping processes in a safe state so that deadlocks can not occur. Bankers algorothim is used to ensure a safe state.

3. There are three cases for each process A. the process has no resources B. the process has one resource C. the process has two resources. If a process is an A then it is not blocking any other process. If a process is in C then it has two resources and can complete it's and then will release the resources. The only problem case is B. However because we only have three process, and four resources, One process will get the last resource and will then enter case C and complete. So even if all process has one resource there is still enough resources for one to complete, which means a deadlock cannot occur.

4. Internal fragmentation happens when we over allocate memory for a process. Say I need 4 bytes and I am given 8. 4 of those can not be used because they are assigned to me, but I don't need them. This happens in paging because the smallest unit that can be allocated is a page, which can be more then is needed. External fragmentation is when an allocated space of memory does not fit exactly into a spot on physical memory. In segmentation not all chunks are the same size and it is hard to get 100% untilization of memory. When placing the allocated memory we might find a spot that is just a little bit bigger then what we need, however if that is the best fit, the memory is placed there. The margin that bigger then what we needed is the external fragmentation because it probably won't be used.

5. 