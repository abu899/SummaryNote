import Node

class LinkedList(object):
    def __init__(self):
        self.head = None
    def append(self, value):
        new_node = Node(value)
        if self.head is None:
            self.head = new_node
        else:
            nextNode = self.head.next
            while (nextNode):
                nextNode = nextNode.next
            nextNode = new_node
    def get(self, index):
        current = self.head.next
        for i in range(index):
            current = current.next
        return current.value
    def insert(self, index, value):
        pass
    def delete(self, index):
        pass
