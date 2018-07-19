import sys


class Edge():
  def __init__(self, to, length):
    self.to = to
    self.length = length
  to = None
  length = None

class Ride():
    name = ""
    start = -1
    end = -1
    followingRides = []

    def __init__(self, item):
        i = item.find('[')
        c = item.find(',')
        p = item.find(')')
        self.name = item[:i]
        self.start = int(item[i + 1: c])
        self.end = int(item[c+1:p])

    def __str__(self):
        return "Name: " + self.name + "\n start " + str(self.start) + "\n end " + str(self.end)


class Day():
    rides = []
    
    def __init__(self, items):
        for s in items:
            self.rides.append(Ride(s))

        self.buildEdges()
    
    def buildEdges(self):
      for ride in self.rides:
        for nextRide in self.rides:
          if(ride.end <= nextRide.start):
            ride.followingRides.append(Edge(nextRide, nextRide.start = ride.end)
        

      


def parseFile(path):
    f = open(path, 'r')
    read_data = f.read()
    read_data = read_data.replace(":", "")
    return  read_data.split()



# Can use a var after the program other wise will prompt for input.
if len(sys.argv) == 2:
    path = sys.argv[1]
else:
    path = input("File path? ")

if path:
    array = parseFile(path)
    aDay = Day(array)
    print(aDay.rides[0])
    print(aDay.rides[1])

