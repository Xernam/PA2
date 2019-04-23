1. Sign up for Project 2 group 46 on Canvas

1.5. pull before making changes.

2. What I initially wrote for the data structure is incomplete/wrong. What needs to
be done, if I am thinking about it correctly, is that we need two hashmaps; one that
stores timestamps as a key and lists of computers that interacted at that time, and
another that stores a computer's ID as a key, with each instance of that specific 
computer ID as a list attached to that key.

3. When a computer C communicates with another, store the computer it communicated with
in the outbound neighbors list inside of C. This is how edges are defined in this graph.
Note that we have to have an edge that goes from early instances of C to later instances
of C, which is trivial with the way the data structure is set up (when a node is introduced
for each node before it, add it to its outbound neighbors).

The reason why the hashmap with ComputerNodes as keys is present, is to find the computer
that was introduced to the virus, and see if it was possible that the other computer
queried was infected.

I've also renamed the variables inside of Communication Monitor to make more sense, and
cleaned it up a lot.

----------------------------------------------------------------------------------------

Hopefully this helps. I work tonight from 4-10pm and need you two to work on this for me.
I would like it done by tonight or tomorrow so we can do load testing on Wednesday.

	-Logan