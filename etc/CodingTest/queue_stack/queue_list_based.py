queue = []

queue.append(1)
queue.append(2)
queue.append(3)
queue.append(4)

queue.pop(0) # list 는 pop 을 하게되면 list 내 데이터를 앞으로 하나씩 옮겨줘야함
queue.pop(0) # 이 과정 자체가 시간복잡도가 O(n)
queue.pop(0)