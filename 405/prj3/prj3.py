import sys


class Ride():
    name = ""
    start = -1
    end = -1
    followingRides = []
    timeUsed = 0
    pred = None

    def __init__(self, item):
        i = item.find('[')
        c = item.find(',')
        p = item.find(')')
        self.name = item[:i]
        self.start = int(item[i + 1: c])
        self.end = int(item[c+1:p])
        self.timeUsed = self.end - self.start 

    def __str__(self):
        return  self.name + "\nStart " + str(self.start) + "\nEnd " + str(self.end)


class Day():
    rides = []
    lastBestNode = None
    def __init__(self, items):
        for s in items:
            self.rides.append(Ride(s))

        self.buildEdges()
    
    def buildEdges(self):
      startSorted = sorted(self.rides, key=lambda ride: ride.start)
      
      for ride in startSorted:
        # find rides before it
        # update waste
        best = ride.timeUsed
        pred = None
        for lastride in startSorted:          
          if ride.start >= lastride.end:
            if best < ride.timeUsed + lastride.timeUsed:
              pred = lastride
              best = ride.timeUsed + lastride.timeUsed

        ride.pred = pred
        ride.timeUsed = best
        if self.lastBestNode == None or self.lastBestNode.timeUsed < ride.timeUsed:
          self.lastBestNode = ride
      
    def printSolution(self):
      node = self.lastBestNode 
      print
      while node.pred != None:
        print(node)
        print
        node = node.pred
      print(node)
        
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
    aDay.printSolution()

