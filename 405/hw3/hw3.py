i = [(5, 10), (10, 3), (3, 12), (12, 5), (5, 50), (50, 6)]

r = []
for off in range(0, 5):
    l = []
    for x in range(0, 5):
        l.append(0)
    r.append(l)


def calcBest(x, y):
    r = []
    if(x == y):
        return 0
    else:
        for i in range(x, y):


cnt = 0
for off in range(0, 5):
    for x in range(0, 5):
        if(x + off < 5):
            r[x][x + off] = calcBest(x, x+off)

for l in r:
    s = ""
    for n in l:
        s = s + str(n) + " "
    print(s)
