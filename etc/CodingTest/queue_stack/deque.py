from collections import deque

queue = deque() # linked list 로 구현되어있는 deque

queue.append(1)
queue.append(2)
queue.append(3)
queue.append(4)

queue.popleft()
queue.popleft()
queue.popleft()
