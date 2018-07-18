# Caleb Ouellette
# Python 3+ will not work with python 2.
import sys


def solve(a):
    s = []
    p = []
    i = 0

    # Builds the table
    for z in a:
        x = 0
        s.append(0)
        p.append(i)
        while x < i:
            if a[x] < a[i]:
                if s[x] > s[i]:
                    s[i] = s[x]
                    p[i] = x
            x = x + 1
        s[i] = s[i] + 1
        i = i + 1

    # Finds the best answer
    answer = 0
    st = -1
    i = 0
    for value in s:
        if value > answer:
            answer = value
            st = i
        i = i + 1

    # Back trace that answer to find the array.
    out = []
    x = 0
    while x < answer:
        out = [a[st]] + out
        st = p[st]
        x = x + 1
    return out


def parseFile(path):
    f = open(path, 'r')
    read_data = f.read()
    read_data = read_data.replace(",", "")
    return list(map(lambda x: int(x), read_data.split()))


# Can use a var after the program other wise will prompt for input.
if len(sys.argv) == 2:
    path = sys.argv[1]
else:
    print("Expected file are \"1,2,3,4 \" or \"1 2 3 4 \" ")
    path = input("File path? ")

if path:
    array = parseFile(path)
    result = solve(array)
    print("length ", len(result))
    print("array ", result)
