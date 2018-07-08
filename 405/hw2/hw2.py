a = [1, 9, 2, 4, 7, 5, 6]


def solve(a):
    s = []
    p = []
    i = 0
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

    answer = 0
    st = -1
    i = 0
    for value in s:
        if value > answer:
            answer = value
            st = i
        i = i + 1

    out = []
    x = 0
    while x < answer:
        out = [a[st]] + out
        st = p[st]
        x = x + 1
    return out


solve(a)
