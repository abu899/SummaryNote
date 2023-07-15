import Node

class LinkedList(object):
    def __init__(self):
        self.head = None
        self.tail = None
    def append(self, value):
        new_node = Node(value)
        if self.head is None:
            self.head = new_node
            self.tail = new_node
        # else:
        #     nextNode = self.head.next
        #     while (nextNode):
        #         nextNode = nextNode.next
        #     nextNode = new_node
        else:
            self.tail.next = new_node
            self.tail = self.tail.next
    def get(self, index):
        current = self.head
        for _ in range(index):
            current = current.next
        return current.value
    def insert(self, index, value):
        current = self.head
        for _ in range(index):
            current = current.next
        
        new_node = Node(value)
        new_node.next = current.next
        current.next = new_node
    def delete(self, index):
        prev = None
        current = self.head
        for _ in range(index):
            prev = current
            current = current.next

        prev.next = current.next
        
        
